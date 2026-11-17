"""Sales entry and history."""
from finance_ui import open_tracker


def open_sales_tracker(parent=None, on_change=None, store=None):
    return open_tracker('sales', parent, on_change, store)
