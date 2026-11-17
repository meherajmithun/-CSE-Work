"""Shared widgets for the existing green CustomTkinter interface."""
from datetime import date
import sqlite3
from tkinter import Misc, ttk, filedialog
import customtkinter as ctk
from finance_data import FinanceStore, CATEGORIES, money, export_csv


def table(parent, columns):
    frame = ctk.CTkFrame(parent)
    frame.pack(fill='both', expand=True, padx=12, pady=8)
    tree = ttk.Treeview(frame, columns=columns, show='headings', selectmode='browse', height=9)
    for column in columns:
        tree.heading(column, text=column)
        tree.column(column, width=110, minwidth=80)
    vertical = ttk.Scrollbar(frame, orient='vertical', command=tree.yview)
    horizontal = ttk.Scrollbar(frame, orient='horizontal', command=tree.xview)
    tree.configure(yscrollcommand=vertical.set, xscrollcommand=horizontal.set)
    frame.grid_rowconfigure(0, weight=1)
    frame.grid_columnconfigure(0, weight=1)
    tree.grid(row=0, column=0, sticky='nsew')
    vertical.grid(row=0, column=1, sticky='ns')
    horizontal.grid(row=1, column=0, sticky='ew')
    return tree


def crop_choices(store):
    return {f'{name} (#{crop_id})': crop_id for crop_id, name in store.crops()}


def open_tracker(kind, parent=None, on_change=None, store=None):
    store = store or FinanceStore()
    sale = kind == 'sales'
    win = ctk.CTkFrame(parent)
    notice = ctk.CTkLabel(win, text='', text_color='#b71c1c', wraplength=650)
    notice.pack(fill='x', padx=15, pady=(8, 0))
    tabs = ctk.CTkTabview(win)
    tabs.pack(fill='both', expand=True, padx=15, pady=15)
    add_tab = tabs.add('Add Sale' if sale else 'Add Expense')
    view_tab = tabs.add('View Sales' if sale else 'View Expenses')
    form = ctk.CTkFrame(add_tab)
    form.pack(padx=20, pady=20)
    fields = {}
    labels = ['Crop', 'Quantity (kg)', 'Price per kg (BDT)', 'Date (DD-MM-YYYY)'] if sale else [
        'Crop', 'Category', 'Amount (BDT)', 'Date (DD-MM-YYYY)', 'Notes (optional)']
    choices = {}
    for i, label in enumerate(labels):
        ctk.CTkLabel(form, text=label).grid(row=i, column=0, padx=15, pady=10, sticky='w')
        if label in ('Crop', 'Category'):
            widget = ttk.Combobox(form, state='readonly', width=32)
            if label == 'Category':
                widget.configure(values=CATEGORIES)
                widget.current(0)
        else:
            widget = ctk.CTkEntry(form, width=260)
            if label.startswith('Date'):
                widget.insert(0, date.today().strftime('%d-%m-%Y'))
        widget.grid(row=i, column=1, padx=15, pady=10)
        fields[label] = widget

    def refresh_crops():
        choices.clear()
        choices.update(crop_choices(store))
        fields['Crop'].configure(values=list(choices))
        if fields['Crop'].get() not in choices:
            fields['Crop'].set(next(iter(choices), 'Add a crop first'))

    columns = ('ID', 'Crop', 'Qty (kg)', 'Price (BDT)', 'Total (BDT)', 'Date') if sale else (
        'ID', 'Crop', 'Category', 'Amount (BDT)', 'Date', 'Notes')
    tree = table(view_tab, columns)
    status = ctk.CTkLabel(view_tab, text='')
    status.pack(pady=5)

    def refresh():
        try:
            refresh_crops()
            names = dict(store.crops())
            rows = store.rows(kind)
            for item in tree.get_children():
                tree.delete(item)
            for row in rows:
                if sale:
                    amount = money(row['total_paisa']) if row['total_paisa'] is not None else 'Invalid amount'
                    values = (row['id'], row['crop_name'], row['quantity'], row['price'], amount, row['date'])
                else:
                    values = (row['id'], names.get(row['crop_id'], 'Unassigned'), row['category'],
                              money(row['amount_paisa']), row['date'], row['notes'])
                tree.insert('', 'end', iid=str(row['id']), values=values)
            total = store.report()['revenue' if sale else 'expenses']
            status.configure(text=f'{len(rows)} records | Total: BDT {money(total)}' if rows else 'No records yet. Add your first entry.')
        except (sqlite3.Error, OSError, ValueError) as exc:
            notice.configure(text=f'Cannot load records: {exc}')

    def changed():
        refresh()
        # Notify every open finance window and dashboard, including other trackers.
        win._root().event_generate('<<FinanceChanged>>', when='tail')
        if on_change:
            on_change()

    def save():
        try:
            crop_id = choices.get(fields['Crop'].get())
            entered_date = fields['Date (DD-MM-YYYY)'].get()
            if sale:
                store.add_sale(crop_id, fields['Quantity (kg)'].get(), fields['Price per kg (BDT)'].get(), entered_date)
            else:
                store.add_expense(crop_id, fields['Category'].get(), fields['Amount (BDT)'].get(), entered_date, fields['Notes (optional)'].get())
        except (ValueError, sqlite3.Error, OSError) as exc:
            notice.configure(text=f'Cannot save: {exc}', text_color='#b71c1c')
            return
        for label, widget in fields.items():
            if label not in ('Crop', 'Category'):
                widget.delete(0, 'end')
                if label.startswith('Date'):
                    widget.insert(0, date.today().strftime('%d-%m-%Y'))
        notice.configure(text='Record saved.', text_color='#1b5e20')
        changed()
        tabs.set('View Sales' if sale else 'View Expenses')

    pending_delete = None

    def cancel_delete():
        nonlocal pending_delete
        pending_delete = None
        confirmation.pack_forget()

    def delete():
        nonlocal pending_delete
        selected = tree.selection()
        if not selected:
            notice.configure(text='Select one row to delete.', text_color='#b71c1c')
            return
        pending_delete = int(selected[0])
        confirmation.pack(fill='x', padx=12, pady=8)

    def confirm_delete():
        if pending_delete is None:
            return
        try:
            store.delete(kind, pending_delete)
        except sqlite3.Error as exc:
            notice.configure(text=f'Cannot delete: {exc}', text_color='#b71c1c')
            return
        cancel_delete()
        notice.configure(text='Record deleted.', text_color='#1b5e20')
        changed()

    confirmation = ctk.CTkFrame(view_tab)
    ctk.CTkLabel(confirmation, text='Permanently delete the selected record?').pack(side='left', padx=10)
    ctk.CTkButton(confirmation, text='Confirm delete', command=confirm_delete, fg_color='#b71c1c').pack(side='left', padx=5, pady=8)
    ctk.CTkButton(confirmation, text='Cancel', command=cancel_delete, width=80).pack(side='left', padx=5)

    ctk.CTkButton(add_tab, text='Save Sale' if sale else 'Save Expense', command=save, fg_color='#2e7d32').pack(pady=10)
    ctk.CTkButton(add_tab, text='Refresh crop list', command=refresh).pack(pady=5)
    actions = ctk.CTkFrame(view_tab)
    actions.pack(fill='x', padx=12, pady=8)
    ctk.CTkButton(actions, text='Refresh', command=refresh).pack(side='left', padx=10, pady=10)
    ctk.CTkButton(actions, text='Delete Selected', command=delete, fg_color='#b71c1c').pack(side='right', padx=10)
    bind_refresh(win, refresh)
    refresh()
    return win


def bind_refresh(widget, callback):
    root = widget._root()
    # One root binding avoids older Tkinter versions removing sibling bindings
    # when an individual finance window closes.
    if not hasattr(root, '_finance_listeners'):
        root._finance_listeners = {}
        def notify(event):
            for listener in list(root._finance_listeners.values()):
                listener()
        root.bind('<<FinanceChanged>>', notify, add='+')
    root._finance_listeners[widget] = callback
    def cleanup(event):
        if event.widget == widget:
            root._finance_listeners.pop(widget, None)
    # CTkFrame.bind forwards to its drawing canvas. Bind to the actual frame
    # so logout removes this page's listener before a new session starts.
    Misc.bind(widget, '<Destroy>', cleanup, add='+')


class FinanceDashboard(ctk.CTkFrame):
    def __init__(self, parent, store=None):
        super().__init__(parent, fg_color='white')
        self.store = store or FinanceStore()
        ctk.CTkLabel(self, text='Farm finances', font=('Verdana', 24, 'bold'), text_color='#1b5e20').pack(anchor='w', padx=20, pady=(18, 8))
        filters = ctk.CTkFrame(self)
        filters.pack(fill='x', padx=15, pady=8)
        self.crop = ttk.Combobox(filters, state='readonly', width=24)
        self.crop.grid(row=0, column=0, padx=8, pady=10)
        self.start = ctk.CTkEntry(filters, placeholder_text='From DD-MM-YYYY', width=155)
        self.end = ctk.CTkEntry(filters, placeholder_text='To DD-MM-YYYY', width=155)
        self.start.grid(row=0, column=1, padx=5)
        self.end.grid(row=0, column=2, padx=5)
        self.cards = []
        cards = ctk.CTkFrame(self, fg_color='transparent')
        cards.pack(fill='x', padx=15, pady=12)
        for i, title in enumerate(('Sales revenue', 'Recorded expenses', 'Surplus / deficit')):
            cards.grid_columnconfigure(i, weight=1)
            card = ctk.CTkFrame(cards, fg_color='#e8f5e9')
            card.grid(row=0, column=i, sticky='nsew', padx=4)
            ctk.CTkLabel(card, text=title).pack(padx=10, pady=(12, 4))
            value = ctk.CTkLabel(card, text='BDT 0.00', font=('Verdana', 17, 'bold'))
            value.pack(padx=10, pady=(4, 12))
            self.cards.append(value)
        controls = ctk.CTkFrame(self, fg_color='transparent')
        controls.pack(fill='x', padx=20)
        ctk.CTkButton(controls, text='Apply / Refresh', command=self.refresh, width=140).pack(side='left')
        ctk.CTkButton(controls, text='Export CSV', command=self.export, width=120).pack(side='left', padx=10)
        self.tree = table(self, ('Type', 'Crop', 'Date', 'Amount (BDT)'))
        self.status = ctk.CTkTextbox(self, height=85, wrap='word')
        self.status.pack(fill='x', padx=15, pady=5)
        ctk.CTkLabel(self, text='Recorded revenue minus expenses; does not include unsold harvests or missing costs.',
                     wraplength=660, text_color='#555555').pack(padx=15, pady=8)
        self.choices = {}
        bind_refresh(self, self.refresh)
        self.refresh()

    def set_status(self, text):
        self.status.configure(state='normal')
        self.status.delete('1.0', 'end')
        self.status.insert('1.0', text)
        self.status.configure(state='disabled')

    def read_report(self):
        return self.store.report(self.choices.get(self.crop.get()), self.start.get(), self.end.get())

    def refresh(self):
        try:
            self.choices = {'All Crops': None, 'Unassigned records': 'unassigned', **crop_choices(self.store)}
            self.crop.configure(values=list(self.choices))
            if self.crop.get() not in self.choices:
                self.crop.set('All Crops')
            report = self.read_report()
        except (ValueError, sqlite3.Error, OSError) as exc:
            self.set_status(f'Cannot refresh report: {exc}')
            return None
        for label, key in zip(self.cards, ('revenue', 'expenses', 'balance')):
            label.configure(text=f'BDT {money(report[key])}', text_color='#b71c1c' if report[key] < 0 else '#1b5e20')
        for item in self.tree.get_children():
            self.tree.delete(item)
        for row in report['records']:
            self.tree.insert('', 'end', values=(row['kind'].title(), row['crop'], row['date'], money(row['amount_paisa'])))
        self.status.configure(state='normal')
        self.status.delete('1.0', 'end')
        self.status.insert('1.0', '\n'.join(report['warnings']) or ('No records for these filters.' if not report['records'] else f'{len(report["records"])} records shown. All amounts in BDT.'))
        self.status.configure(state='disabled')
        return report

    def export(self):
        try:
            report = self.refresh()
            if report is None:
                return
            path = filedialog.asksaveasfilename(parent=self, defaultextension='.csv', filetypes=[('CSV file', '*.csv')], initialfile='farm-finances.csv')
            if path:
                export_csv(path, report)
                self.set_status('Your filtered report has been saved.')
        except (ValueError, sqlite3.Error, OSError) as exc:
            self.set_status(f'Cannot export: {exc}')
