"""Optional GUI integration check: run with a desktop session and dependencies.
All application modules and database writes are isolated in a temporary folder.
"""
import faulthandler
import shutil
import sys
import tempfile
from pathlib import Path


def descendants(widget):
    for child in widget.winfo_children():
        yield child
        yield from descendants(child)


def run():
    faulthandler.dump_traceback_later(30, exit=True)
    with tempfile.TemporaryDirectory() as folder:
        for source in Path(__file__).resolve().parents[1].glob('*.py'):
            shutil.copy2(source, folder)
        sys.path.insert(0, folder)
        import tkinter as tk
        from tkinter import ttk, messagebox, filedialog
        import customtkinter as ctk
        import main
        from finance_data import FinanceStore
        from finance_ui import FinanceDashboard
        from sales_tracker_feature import open_sales_tracker
        from expense_tracker_feature import open_expense_tracker

        errors = []
        messagebox.showerror = lambda title, message, **kw: errors.append((title, message))
        messagebox.showinfo = lambda *args, **kw: None
        messagebox.askyesno = lambda *args, **kw: True
        print('Creating Tk root', flush=True)
        root = tk.Tk()
        def pump():
            root.after(300, root.quit)
            root.mainloop()

        root.report_callback_exception = lambda *args: errors.append(args)
        try:
            print('Building login', flush=True)
            app = main.Login_Window(root)
            store = FinanceStore(folder)
            with store.connect() as conn:
                pass
            import sqlite3
            with sqlite3.connect(store.crops_path) as conn:
                conn.execute("INSERT INTO crops (crop_name) VALUES ('Rice')")
            with sqlite3.connect(Path(folder) / 'admininfo.db') as conn:
                conn.execute("INSERT INTO users VALUES ('demo', 'test-only')")
            app.txtuser.insert(0, 'demo')
            app.txtpass.insert(0, 'test-only')
            print('Signing in', flush=True)
            app.login()
            pump()
            dashboards = [w for w in descendants(root) if isinstance(w, FinanceDashboard)]
            assert len(dashboards) == 1
            dashboard = dashboards[0]
            dash = dashboard.winfo_toplevel()

            def button(window, text):
                return next(w for w in descendants(window) if isinstance(w, ctk.CTkButton) and w.cget('text') == text)

            def entries(window, values):
                widgets = [w for w in descendants(window) if isinstance(w, ctk.CTkEntry)]
                assert len(widgets) == len(values), (len(widgets), values)
                for widget, value in zip(widgets, values):
                    widget.delete(0, 'end')
                    widget.insert(0, value)

            print('Opening trackers', flush=True)
            sales = open_sales_tracker(dash, store=store)
            expense = open_expense_tracker(dash, store=store)
            pump()
            entries(sales, ['10', '25', '17-11-2026'])
            button(sales, 'Save Sale').invoke()
            pump()
            assert dashboard.cards[0].cget('text') == 'BDT 250.00'
            entries(expense, ['150.50', '17-11-2026', 'Seeds'])
            button(expense, 'Save Expense').invoke()
            pump()
            assert dashboard.cards[1].cget('text') == 'BDT 150.50'
            assert dashboard.cards[2].cget('text') == 'BDT 99.50'
            tree = next(w for w in descendants(expense) if isinstance(w, ttk.Treeview))
            tree.selection_set(tree.get_children()[0])
            messagebox.askyesno = lambda *args, **kw: False
            button(expense, 'Delete Selected').invoke()
            assert len(store.rows('expenses')) == 1
            messagebox.askyesno = lambda *args, **kw: True
            button(expense, 'Delete Selected').invoke()
            pump()
            assert store.rows('expenses') == []
            assert dashboard.cards[2].cget('text') == 'BDT 250.00'
            expense.destroy()
            entries(sales, ['1', '10', '17-11-2026'])
            button(sales, 'Save Sale').invoke()
            pump()
            assert dashboard.cards[0].cget('text') == 'BDT 260.00'
            filedialog.asksaveasfilename = lambda **kw: ''
            dashboard.export()  # Cancellation must not write anything.
            output = Path(folder) / 'report.csv'
            assert not output.exists()
            filedialog.asksaveasfilename = lambda **kw: str(output)
            dashboard.export()
            assert output.exists()
            main.open_crop_suggestion()
            main.open_crop_management()
            pump()
            assert not errors, errors
            app.logout(dash)
            pump()
            assert root.state() == 'normal'
            print('GUI smoke passed: login, dashboard, sales, expenses, refresh, delete/cancel, export/cancel, crop windows, logout.')
        finally:
            root.destroy()
            faulthandler.cancel_dump_traceback_later()


if __name__ == '__main__':
    run()
