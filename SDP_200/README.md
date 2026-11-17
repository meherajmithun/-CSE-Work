# Smart Agricultural System

A Python desktop app for a single farm: manage a crop catalog, find matching crops, record sales and expenses, and compare recorded revenue with costs.

## Run the app

Use Python 3.10 or newer with Tkinter installed. Open a terminal inside `SDP_200`:

```sh
python3 -m venv .venv
source .venv/bin/activate
python -m pip install -r requirements.txt
python main.py
```

On Windows, activate with `.venv\Scripts\activate` instead. If Python cannot import `tkinter`, install the Tkinter support package for your Python distribution. Create a new virtual environment on your computer; the older checked-in `venv` folders may belong to another operating system.

Register an account, sign in, and add a crop using **Crop Management** before recording sales or expenses.

## Sales and expenses

- **Sales Tracker:** select a crop, enter kilograms, price per kilogram in BDT, and a date. Saving opens the updated sales list.
- **Expense Tracker:** select a crop and expense category, then enter an amount, date, and optional notes.
- Dates default to today. Use `DD-MM-YYYY`; stored dates use `YYYY-MM-DD`.
- Amounts must be positive. Money is rounded to the nearest paisa (0.01 taka), with half-paisa rounded up.
- Use **Delete Selected** to remove an incorrect entry after confirmation, then enter its replacement. Editing existing entries is a future feature.
- Use **Refresh crop list** if you added a crop while a tracker was already open.

## Dashboard and export

The dashboard shows sales revenue, recorded expenses, and their difference. A negative difference is a deficit. This is a summary of recorded amounts, not full accounting profit; it does not value unsold harvests or allocate costs to planting cycles.

Select **All Crops**, a specific crop, or **Unassigned records**. Optionally enter start/end dates and choose **Apply / Refresh**. Date boundaries are inclusive. Blank dates mean no date limit. **Export CSV** saves records, filter details, BDT totals, and data warnings for the selected filters.

New sales and expenses update open trackers and dashboards automatically. A Refresh button is also available.

## Existing data and backups

Database paths are relative to this project, regardless of where you launch Python. The first financial upgrade creates a timestamped backup of existing `sales.db` inside `backups/` before changing its schema. It retains all original rows and extra columns, adds crop IDs and integer sale totals, and creates the expense table. Reopening the application does not repeat the migration.

Old sales are assigned to a crop only when exactly one catalog entry matches their name. Ambiguous or missing names stay unassigned and appear in **All Crops** and **Unassigned records**. Select the intended crop when recreating an incorrect entry; duplicate crop names have IDs in dropdown labels.

Invalid legacy amounts are excluded from totals with a visible warning. Invalid dates are included in unfiltered totals but excluded when a date filter is active, also with a warning. Inspect the original record in Sales Tracker and correct it by replacing it after checking the backup. Never infer that a warned report is complete.

To restore a backup, close the app, preserve a copy of the current database, then copy the chosen backup over `sales.db`. This restores the entire sales/expenses database to that backup's point in time, so later entries will not be present. The next launch will migrate it again.

This version uses shared farm records for all accounts. Existing password storage and password recovery have not been upgraded. Use demo credentials until authentication and ownership improvements are implemented.

Local databases, backups, caches, and environments are ignored for new Git files. Files already tracked in the parent repository remain tracked; ignoring them does not remove them or erase Git history.

## Check the financial logic

These tests use temporary databases and do not change your farm data:

```sh
python -m unittest discover -s tests -v
```

Tests cover validation, rounding, persistence, deletion, crop/date filters, export, migration preservation, and backup behavior.

For an optional desktop integration check, run `python tests/gui_smoke.py`. It opens temporary windows, copies the application modules into a temporary folder, and uses disposable databases. It requires a working desktop session and GUI dependencies.

See [FEATURE_PLAN.md](FEATURE_PLAN.md) for the implementation checklist and later improvements.
