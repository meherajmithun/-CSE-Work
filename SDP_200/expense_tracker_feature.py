"""Expense entry and history."""
from finance_ui import open_tracker


def open_expense_tracker(parent=None, on_change=None, store=None):
    return open_tracker('expenses', parent, on_change, store)
