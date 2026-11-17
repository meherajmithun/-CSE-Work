import csv
import sqlite3
import tempfile
import unittest
from pathlib import Path
from finance_data import FinanceStore, export_csv, paisa, positive_number, valid_date


class FinanceTests(unittest.TestCase):
    def setUp(self):
        self.temp = tempfile.TemporaryDirectory()
        self.addCleanup(self.temp.cleanup)
        self.directory = Path(self.temp.name)
        self.store = FinanceStore(self.directory)
        with sqlite3.connect(self.store.crops_path) as conn:
            conn.executemany('INSERT INTO crops (crop_name) VALUES (?)', [('Rice',), ('Wheat',)])

    def test_sale_and_expense_persist_and_delete(self):
        self.store.add_sale(1, '10', '25', '17-11-2026')
        self.store.add_expense(1, 'Seeds', '1500.50', '17-11-2026')
        store = FinanceStore(self.directory)
        report = store.report()
        self.assertEqual((report['revenue'], report['expenses'], report['balance']), (25000, 150050, -125050))
        store.delete('expenses', store.rows('expenses')[0]['id'])
        self.assertEqual(store.report()['balance'], 25000)

    def test_decimal_rounding(self):
        self.assertEqual(paisa('1.005'), 101)
        self.store.add_sale(1, '3', '0.10', '17-11-2026')
        self.assertEqual(self.store.report()['revenue'], 30)
        self.store.add_expense(1, 'Seeds', '0.105', '17-11-2026')
        self.assertEqual(self.store.report()['expenses'], 11)

    def test_invalid_inputs_do_not_save(self):
        for value in ('', 'abc', '-1', '0', 'NaN', 'Infinity', '1e1000'):
            with self.subTest(value=value), self.assertRaises(ValueError):
                self.store.add_sale(1, value, '25', '17-11-2026')
            with self.subTest(value=value), self.assertRaises(ValueError):
                self.store.add_expense(1, 'Seeds', value, '17-11-2026')
        for date in ('31-02-2026', '', 'yesterday'):
            with self.assertRaises(ValueError):
                self.store.add_sale(1, '10', '25', date)
        with self.assertRaises(ValueError):
            self.store.add_expense(999, 'Seeds', '10', '17-11-2026')
        with self.assertRaises(ValueError):
            self.store.add_expense(1, 'Bad category', '10', '17-11-2026')
        self.assertEqual(self.store.rows('sales'), [])
        self.assertEqual(self.store.rows('expenses'), [])

    def test_filters_cross_year_and_crop(self):
        self.store.add_sale(1, '10', '25', '31-12-2025')
        self.store.add_sale(2, '20', '25', '01-01-2026')
        self.store.add_expense(1, 'Seeds', '50', '01-01-2026')
        self.store.add_expense(1, 'Seeds', '100', '02-01-2026')
        report = self.store.report(1, '31-12-2025', '01-01-2026')
        self.assertEqual((report['revenue'], report['expenses'], report['balance']), (25000, 5000, 20000))
        with self.assertRaises(ValueError):
            self.store.report(start='02-01-2026', end='01-01-2026')

    def test_empty_and_csv(self):
        self.assertEqual(self.store.report()['balance'], 0)
        self.store.add_expense(1, 'Seeds', '1.25', '17-11-2026', '=SUM(A1:A3)')
        report = self.store.report(1, '01-11-2026', '30-11-2026')
        path = self.directory / 'report.csv'
        export_csv(path, report)
        with path.open(encoding='utf-8-sig', newline='') as file:
            rows = list(csv.reader(file))
        self.assertIn(['Balance', '-1.25'], rows)
        self.assertEqual(rows[3][-1], "'=SUM(A1:A3)")
        self.assertEqual(rows[1], ['Crop', 'Rice', 'Start', '2026-11-01', 'End', '2026-11-30'])


class MigrationTests(unittest.TestCase):
    def test_preserves_data_and_backs_up_once(self):
        with tempfile.TemporaryDirectory() as folder:
            path = Path(folder)
            with sqlite3.connect(path / 'crops.db') as conn:
                conn.execute('CREATE TABLE crops (id INTEGER PRIMARY KEY, crop_name TEXT)')
                conn.executemany('INSERT INTO crops VALUES (?, ?)', [(1, 'Rice'), (2, 'Wheat'), (3, 'Wheat')])
            with sqlite3.connect(path / 'sales.db') as conn:
                conn.execute('CREATE TABLE sales (id INTEGER PRIMARY KEY, crop_name TEXT, quantity REAL, price REAL, total REAL, date TEXT, extra REAL)')
                conn.executemany('INSERT INTO sales VALUES (?, ?, 1, 10, ?, ?, 42)', [
                    (1, 'Rice', 10, '31-12-2025'), (2, 'Wheat', 20, '01-01-2026'),
                    (3, 'Missing', 30, 'invalid'), (4, 'Rice', 'bad', '01-01-2026')])
            store = FinanceStore(path)
            FinanceStore(path)
            self.assertEqual(len(list((path / 'backups').glob('*.db'))), 1)
            with sqlite3.connect(path / 'sales.db') as conn:
                self.assertEqual(conn.execute('SELECT extra FROM sales').fetchall(), [(42.0,)] * 4)
                self.assertEqual(conn.execute('SELECT crop_id FROM sales ORDER BY id').fetchall(), [(1,), (None,), (None,), (1,)])
                self.assertEqual(conn.execute('SELECT date FROM sales WHERE id=1').fetchone()[0], '2025-12-31')
            with sqlite3.connect(next((path / 'backups').glob('*.db'))) as conn:
                self.assertEqual(conn.execute('SELECT date FROM sales WHERE id=1').fetchone()[0], '31-12-2025')
            report = store.report()
            self.assertEqual(report['revenue'], 6000)
            self.assertTrue(any('invalid amount' in warning for warning in report['warnings']))
            self.assertEqual(store.report('unassigned')['revenue'], 5000)
            filtered = store.report(start='01-01-2026')
            self.assertEqual(filtered['revenue'], 2000)
            self.assertTrue(any('excluded by date filter' in warning for warning in filtered['warnings']))


if __name__ == '__main__':
    unittest.main()
