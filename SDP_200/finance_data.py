"""Validated financial storage. Money is calculated in integer paisa."""
import csv
import sqlite3
from contextlib import closing
from datetime import datetime
from decimal import Decimal, InvalidOperation, ROUND_HALF_UP
from pathlib import Path

BASE_DIR = Path(__file__).resolve().parent
CATEGORIES = ('Seeds', 'Fertilizer', 'Labor', 'Irrigation', 'Transport', 'Other')


def positive_number(value):
    try:
        number = Decimal(str(value).strip())
        if not number.is_finite() or not 0 < number <= Decimal('1000000000'):
            raise ValueError
        return number
    except (InvalidOperation, ValueError):
        raise ValueError('Enter a number greater than zero and no larger than 1,000,000,000.') from None


def paisa(value):
    try:
        number = Decimal(str(value))
        if not number.is_finite() or not 0 <= number <= Decimal('90000000000000'):
            raise ValueError
        return int((number * 100).quantize(Decimal('1'), rounding=ROUND_HALF_UP))
    except (ValueError, InvalidOperation):
        raise ValueError('Invalid money amount.') from None


def money(value):
    return f'{Decimal(value) / 100:,.2f}'


def valid_date(value):
    for fmt in ('%d-%m-%Y', '%Y-%m-%d'):
        try:
            return datetime.strptime(str(value).strip(), fmt).date().isoformat()
        except ValueError:
            pass
    raise ValueError('Enter a valid date as DD-MM-YYYY.')


class FinanceStore:
    def __init__(self, directory=BASE_DIR):
        self.directory = Path(directory)
        self.path = self.directory / 'sales.db'
        self.crops_path = self.directory / 'crops.db'
        self.setup()

    def connect(self):
        conn = sqlite3.connect(self.path)
        conn.row_factory = sqlite3.Row
        return conn

    def crops(self):
        with closing(sqlite3.connect(self.crops_path)) as conn:
            return conn.execute('SELECT id, crop_name FROM crops ORDER BY crop_name, id').fetchall()

    def setup(self):
        # Snapshot existing data before the first migration; never overwrite a backup.
        if self.path.exists():
            with closing(self.connect()) as conn:
                migrated = conn.execute("SELECT name FROM sqlite_master WHERE name='finance_migrations'").fetchone()
                if not migrated:
                    backup_dir = self.directory / 'backups'
                    backup_dir.mkdir(exist_ok=True)
                    stamp = datetime.now().strftime('%Y%m%d-%H%M%S-%f')
                    with closing(sqlite3.connect(backup_dir / f'sales-{stamp}.db')) as backup:
                        conn.backup(backup)
        with closing(sqlite3.connect(self.crops_path)) as conn, conn:
            conn.execute('''CREATE TABLE IF NOT EXISTS crops (
                id INTEGER PRIMARY KEY AUTOINCREMENT, crop_name TEXT,
                days_to_harvest INTEGER, soil_type TEXT, sunlight TEXT,
                water_req TEXT, category TEXT, season TEXT)''')
        with closing(self.connect()) as conn, conn:
            conn.execute('''CREATE TABLE IF NOT EXISTS sales (
                id INTEGER PRIMARY KEY AUTOINCREMENT, crop_name TEXT,
                quantity REAL, price REAL, total REAL, date TEXT)''')
            columns = {row['name'] for row in conn.execute('PRAGMA table_info(sales)')}
            for name, kind in (('crop_id', 'INTEGER'), ('total_paisa', 'INTEGER')):
                if name not in columns:
                    conn.execute(f'ALTER TABLE sales ADD COLUMN {name} {kind}')
            conn.execute('''CREATE TABLE IF NOT EXISTS expenses (
                id INTEGER PRIMARY KEY AUTOINCREMENT, crop_id INTEGER NOT NULL,
                category TEXT NOT NULL, amount_paisa INTEGER NOT NULL CHECK(amount_paisa > 0),
                date TEXT NOT NULL, notes TEXT NOT NULL DEFAULT '')''')
            conn.execute('CREATE TABLE IF NOT EXISTS finance_migrations (version INTEGER PRIMARY KEY)')
            if not conn.execute('SELECT 1 FROM finance_migrations WHERE version=1').fetchone():
                names = {}
                for crop_id, name in self.crops():
                    names.setdefault(name, []).append(crop_id)
                for row in conn.execute('SELECT id, crop_name, date, total FROM sales').fetchall():
                    matches = names.get(row['crop_name'], [])
                    crop_id = matches[0] if len(matches) == 1 else None
                    try:
                        date = valid_date(row['date'])
                    except ValueError:
                        date = row['date']
                    try:
                        amount = paisa(row['total'])
                    except ValueError:
                        amount = None
                    conn.execute('UPDATE sales SET crop_id=?, date=?, total_paisa=? WHERE id=?',
                                 (crop_id, date, amount, row['id']))
                conn.execute('INSERT INTO finance_migrations VALUES (1)')

    def crop_name(self, crop_id):
        crops = dict(self.crops())
        if crop_id not in crops:
            raise ValueError('Select an existing crop. Add a crop first if the list is empty.')
        return crops[crop_id]

    def add_sale(self, crop_id, quantity, price, date):
        name = self.crop_name(crop_id)
        quantity, price = positive_number(quantity), positive_number(price)
        amount = paisa(quantity * price)
        if amount == 0:
            raise ValueError('Sale total must be at least 0.01 taka.')
        date = valid_date(date)
        with closing(self.connect()) as conn, conn:
            conn.execute('''INSERT INTO sales
                (crop_id, crop_name, quantity, price, total, total_paisa, date)
                VALUES (?, ?, ?, ?, ?, ?, ?)''',
                (crop_id, name, str(quantity), str(price), amount / 100, amount, date))

    def add_expense(self, crop_id, category, amount, date, notes=''):
        self.crop_name(crop_id)
        if category not in CATEGORIES:
            raise ValueError('Select a valid expense category.')
        amount = paisa(positive_number(amount))
        if amount == 0:
            raise ValueError('Expense must be at least 0.01 taka.')
        with closing(self.connect()) as conn, conn:
            conn.execute('INSERT INTO expenses (crop_id, category, amount_paisa, date, notes) VALUES (?, ?, ?, ?, ?)',
                         (crop_id, category, amount, valid_date(date), notes.strip()))

    def rows(self, kind):
        query = {'sales': 'SELECT id, crop_id, crop_name, quantity, price, total, total_paisa, date FROM sales ORDER BY id DESC',
                 'expenses': 'SELECT id, crop_id, category, amount_paisa, date, notes FROM expenses ORDER BY id DESC'}[kind]
        with closing(self.connect()) as conn:
            return [dict(row) for row in conn.execute(query)]

    def delete(self, kind, row_id):
        query = {'sales': 'DELETE FROM sales WHERE id=?', 'expenses': 'DELETE FROM expenses WHERE id=?'}[kind]
        with closing(self.connect()) as conn, conn:
            conn.execute(query, (row_id,))

    def report(self, crop_id=None, start='', end=''):
        start = valid_date(start) if start.strip() else ''
        end = valid_date(end) if end.strip() else ''
        if start and end and start > end:
            raise ValueError('The end date must be on or after the start date.')
        crops = dict(self.crops())
        records, warnings = [], []
        for kind in ('sales', 'expenses'):
            for row in self.rows(kind):
                unassigned = row['crop_id'] not in crops
                if crop_id == 'unassigned' and not unassigned:
                    continue
                if crop_id not in (None, 'unassigned') and row['crop_id'] != crop_id:
                    continue
                label = f'{kind.title()} #{row["id"]}'
                try:
                    date = valid_date(row['date'])
                except ValueError:
                    warnings.append(f'{label}: invalid date' + ('; excluded by date filter.' if start or end else '; included without date filtering.'))
                    if start or end:
                        continue
                    date = str(row['date'])
                if (start and date < start) or (end and date > end):
                    continue
                amount = row['total_paisa'] if kind == 'sales' else row['amount_paisa']
                if not isinstance(amount, int) or amount < 0:
                    warnings.append(f'{label}: invalid amount; excluded from totals. Correct the original record.')
                    continue
                if unassigned:
                    warnings.append(f'{label}: unassigned crop; included in this report.')
                records.append({'kind': kind, 'id': row['id'],
                                'crop': crops.get(row['crop_id'], row.get('crop_name', 'Unknown')),
                                'crop_id': row['crop_id'], 'date': date, 'amount_paisa': amount,
                                'category': row.get('category', ''), 'notes': row.get('notes', '')})
        revenue = sum(r['amount_paisa'] for r in records if r['kind'] == 'sales')
        expenses = sum(r['amount_paisa'] for r in records if r['kind'] == 'expenses')
        return dict(records=records, warnings=warnings, revenue=revenue, expenses=expenses,
                    balance=revenue-expenses, start=start, end=end,
                    crop=crops.get(crop_id, 'Unassigned records' if crop_id == 'unassigned' else 'All Crops'))


def export_csv(path, report):
    def safe(value):
        text = str(value)
        return "'" + text if text.lstrip().startswith(('=', '+', '-', '@')) else text
    with open(path, 'w', newline='', encoding='utf-8-sig') as file:
        writer = csv.writer(file)
        writer.writerow(['Currency', 'BDT (taka)'])
        writer.writerow(['Crop', safe(report['crop']), 'Start', report['start'], 'End', report['end']])
        writer.writerow(['Type', 'ID', 'Crop', 'Date', 'Amount (BDT)', 'Category', 'Notes'])
        for row in report['records']:
            writer.writerow([row['kind'], row['id'], safe(row['crop']), safe(row['date']),
                             f'{Decimal(row["amount_paisa"]) / 100:.2f}', safe(row['category']), safe(row['notes'])])
        writer.writerow([])
        for key in ('revenue', 'expenses', 'balance'):
            writer.writerow([key.title(), f'{Decimal(report[key]) / 100:.2f}'])
        for warning in report['warnings']:
            writer.writerow(['Data warning', warning])
