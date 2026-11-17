"""Desktop integration check using copied modules and disposable databases."""
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
    faulthandler.dump_traceback_later(60, exit=True)
    with tempfile.TemporaryDirectory() as folder:
        for source in Path(__file__).resolve().parents[1].glob('*.py'):
            shutil.copy2(source, folder)
        sys.path.insert(0, folder)
        import tkinter as tk
        from tkinter import ttk, filedialog
        import customtkinter as ctk
        import main
        from finance_data import FinanceStore

        errors = []
        root = tk.Tk()
        def pump():
            root.after(350, root.quit)
            root.mainloop()

        def one_window():
            assert not [w for w in descendants(root) if isinstance(w, tk.Toplevel)]
            assert root.state() != 'withdrawn'

        def button(parent, text):
            return next(w for w in descendants(parent) if isinstance(w, ctk.CTkButton) and w.cget('text') == text)

        def entries(parent, values):
            widgets = [w for w in descendants(parent) if isinstance(w, ctk.CTkEntry)]
            assert len(widgets) == len(values), (len(widgets), values)
            for widget, value in zip(widgets, values):
                widget.delete(0, 'end')
                widget.insert(0, value)

        def navigate(name):
            button(root, name).invoke()
            pump()
            one_window()
            return app.current_page

        root.report_callback_exception = lambda *args: errors.append(args)
        try:
            print('Checking full-screen startup and account navigation', flush=True)
            app = main.Login_Window(root)
            pump()
            assert bool(root.attributes('-fullscreen')), (app.fullscreen, root.attributes('-fullscreen'))
            assert root.winfo_width() >= root.winfo_screenwidth() * .9
            button(root, 'Forgot Password?').invoke()
            one_window()
            button(root, 'New User? Register').invoke()
            one_window()
            entries(app.body, ['demo', 'test-only'])
            button(root, 'Register').invoke()
            pump()
            entries(app.body, ['demo', 'test-only'])
            button(root, 'Login').invoke()
            pump()
            one_window()
            dashboard = app.current_page
            store = FinanceStore(folder)

            print('Checking crop entry and inline suggestion results', flush=True)
            crop = navigate('Crop Management')
            entries(crop, ['Rice', '90'])
            button(crop, 'Add Crop').invoke()
            pump()
            assert len(store.crops()) == 1
            one_window()
            suggestion = navigate('Crop Suggestion')
            button(suggestion, 'Search Crop').invoke()
            pump()
            one_window()
            assert any(isinstance(w, ctk.CTkLabel) and 'Rice' in w.cget('text') for w in descendants(suggestion))
            combos = [w for w in descendants(suggestion) if isinstance(w, ttk.Combobox)]
            combos[0].current(1)
            button(suggestion, 'Search Crop').invoke()
            pump()
            assert any(isinstance(w, ctk.CTkLabel) and 'No matching crop' in w.cget('text') for w in descendants(suggestion))
            one_window()

            print('Checking in-page finance workflows and preserved forms', flush=True)
            sales = navigate('Sales Tracker')
            entries(sales, ['10', '25', '17-11-2026'])
            navigate('Dashboard')
            assert navigate('Sales Tracker') is sales
            button(sales, 'Save Sale').invoke()
            pump()
            one_window()
            assert dashboard.cards[0].cget('text') == 'BDT 250.00'
            expense = navigate('Expense Tracker')
            entries(expense, ['150.50', '17-11-2026', 'Seeds'])
            button(expense, 'Save Expense').invoke()
            pump()
            one_window()
            assert dashboard.cards[2].cget('text') == 'BDT 99.50'
            tree = next(w for w in descendants(expense) if isinstance(w, ttk.Treeview))
            tree.selection_set(tree.get_children()[0])
            button(expense, 'Delete Selected').invoke()
            one_window()
            button(expense, 'Cancel').invoke()
            assert len(store.rows('expenses')) == 1
            button(expense, 'Delete Selected').invoke()
            button(expense, 'Confirm delete').invoke()
            pump()
            assert store.rows('expenses') == []
            assert dashboard.cards[2].cget('text') == 'BDT 250.00'
            navigate('Dashboard')
            filedialog.asksaveasfilename = lambda **kw: ''
            dashboard.export()
            output = Path(folder) / 'report.csv'
            assert not output.exists()
            filedialog.asksaveasfilename = lambda **kw: str(output)
            dashboard.export()
            assert output.exists()
            one_window()

            print('Checking full-screen controls and session cleanup', flush=True)
            root.focus_force()
            root.event_generate('<Escape>')
            pump()
            assert not bool(root.attributes('-fullscreen'))
            # macOS reserves F11; synthetic F11 events are not portable.
            # Check the binding and exercise the same toggle through the UI.
            assert root.bind('<F11>')
            button(root, 'Full screen').invoke()
            pump()
            assert bool(root.attributes('-fullscreen')), (app.fullscreen, root.attributes('-fullscreen'))
            button(root, 'Exit full screen').invoke()
            pump()
            assert not bool(root.attributes('-fullscreen'))
            button(root, 'Full screen').invoke()
            pump()
            assert bool(root.attributes('-fullscreen')), (app.fullscreen, root.attributes('-fullscreen'))
            button(root, 'Logout').invoke()
            pump()
            one_window()
            assert bool(root.attributes('-fullscreen')), (app.fullscreen, root.attributes('-fullscreen'))
            assert not root._finance_listeners
            assert not errors, errors
            entries(app.body, ['demo', 'test-only'])
            button(root, 'Login').invoke()
            pump()
            one_window()
            assert app.current_page.cards[0].cget('text') == 'BDT 250.00'
            assert not errors, errors
            print('GUI smoke passed: one window, full screen, registration/login, all pages, inline results, preserved forms, finance, export, logout/relogin.')
        finally:
            root.destroy()
            faulthandler.cancel_dump_traceback_later()


if __name__ == '__main__':
    run()
