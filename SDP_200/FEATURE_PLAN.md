# Smart Agricultural System: Next Feature Plan

## Goal

Build an **Expense Tracker and Profit Dashboard** so a farmer can record growing costs and compare them with crop sales.

Example: sell rice for 5,000 taka and record 3,000 taka in costs. The dashboard shows 5,000 taka revenue, 3,000 taka expenses, and a 2,000 taka recorded surplus.

This document is an implementation plan. The features below have not been implemented yet. Complete one step, check that it works, and then continue.

## 1. Understand the existing project

| File | Current responsibility |
| --- | --- |
| `main.py` | Registration, login, logout, and dashboard navigation |
| `crop_management_feature.py` | Add crop names and growing requirements |
| `crop_suggestion_feature.py` | Find crops matching soil, sunlight, water, and season |
| `sales_tracker_feature.py` | Add, list, and delete crop sales |
| `crops.db` | Store the crop catalog |
| `sales.db` | Store sales records |
| `admininfo.db` | Store login accounts |

The interface uses Python, Tkinter, and CustomTkinter. SQLite stores data in local database files. No external API is needed for this feature.

## 2. Fix sales entry first

**File:** `sales_tracker_feature.py`

The quantity and price fields return text. Their numeric conversion is commented out, so `total = qty * price` currently raises a `TypeError`.

- [ ] Convert quantity and price to numbers before calculating the total.
- [ ] Reject empty, invalid, negative, and non-finite values such as `NaN` and infinity. Require quantity and price to be greater than zero.
- [ ] Use decimal arithmetic for money and round totals to two decimal places.
- [ ] Validate the date with `datetime.strptime()` using `DD-MM-YYYY`.
- [ ] Prevent saving when the crop list contains only `No Crops Found`.
- [ ] Refresh the sales table immediately after adding a sale.
- [ ] Read the selected row ID with `tree.selection()[0]` when deleting, and ask for confirmation.
- [ ] Select the six displayed sales columns explicitly instead of using `SELECT *`.

The existing `sales.db` has extra columns beyond the six fields used by the interface. Keep them intact; explicit column selection avoids pulling unrelated values into the table.

**Check:** Enter 10 kg at 25 taka per kg. The saved total must be 250 taka. Entering letters or an impossible date must show a helpful message without saving a row.

## 3. Prepare storage safely

Close the application and make local backup copies of the database files before changing their structure. Do not publish backup files containing real account or farm data.

- [ ] Add an `expenses` table to `sales.db`, keeping existing tables and rows.
- [ ] Use `CREATE TABLE IF NOT EXISTS` so repeated startup is safe.
- [ ] Use SQL placeholders (`?`) for values supplied by the user.
- [ ] Resolve database paths relative to the Python files so launching from another folder does not create a different database.
- [ ] Add a small `finance_data.py` module for reading and saving financial records.

Suggested expense fields:

| Field | Meaning | Example |
| --- | --- | --- |
| `id` | Automatically generated record number | 1 |
| `crop_id` | ID of the crop in `crops.db` | 3 |
| `category` | Type of cost | Seeds |
| `amount_paisa` | Amount stored as an integer in the smallest currency unit | 150050 means 1,500.50 taka |
| `date` | Valid date stored as `YYYY-MM-DD` | 2026-11-17 |
| `notes` | Optional explanation | Seeds for winter planting |

Since crops and expenses live in separate database files, validate that the crop exists in application code. Do not assume SQLite will enforce a foreign key across those files.

Existing sales identify crops by name. Before enabling crop-specific comparisons, add a nullable `crop_id` to sales and match old rows only when exactly one catalog crop has that name. Leave ambiguous rows unassigned for manual correction; never guess or discard them. Include unassigned sales in overall totals.

For money calculations, convert existing sales totals through `Decimal(str(value))`, round to two decimal places, and aggregate in paisa. Flag invalid old amounts for correction instead of silently treating them as zero.

**Check:** Restarting the application preserves existing sales and expenses. Running database setup twice causes no errors or lost data.

## 4. Build the Expense Tracker

**New file:** `expense_tracker_feature.py`

Start with two tabs, following the existing Sales Tracker layout.

### Add Expense tab

- [ ] Add a crop dropdown using names from the crop catalog and retain each selected crop's ID.
- [ ] Add a category dropdown: Seeds, Fertilizer, Labor, Irrigation, Transport, and Other.
- [ ] Add amount, date, and optional notes fields.
- [ ] Default the date to today.
- [ ] Require a real crop, a positive finite amount, and a valid date.
- [ ] Convert the amount to paisa before saving.
- [ ] Save the record, clear the form, and refresh the list.

### View Expenses tab

- [ ] Show crop, category, amount in taka, date, and notes in a scrollable table.
- [ ] Show the sum of the displayed expenses.
- [ ] Allow deleting a selected expense after confirmation.
- [ ] Show a helpful empty-state message when there are no records.

**Check:** Add a 1,500.50 taka expense, close and reopen the window, and verify that it remains exactly 1,500.50 taka. Deleting it must update the list and total.

## 5. Add the dashboard summary

**Files:** `main.py` and `finance_data.py`

Replace the welcome-only content with three summary cards:

1. **Sales revenue:** Sum of saved sales totals.
2. **Recorded expenses:** Sum of saved expense amounts.
3. **Recorded surplus / deficit:** Revenue minus expenses.

The third figure is a simple comparison of recorded amounts. It is not a complete accounting profit calculation: unsold harvests, missing costs, and expenses from another planting cycle can affect the result.

- [ ] Add an Expense Tracker button to the sidebar.
- [ ] Display all amounts in taka with two decimal places.
- [ ] Refresh totals after records change and provide a Refresh button.
- [ ] Display zero for an empty database.
- [ ] Display a negative result clearly when expenses exceed revenue.

**Check:** With 5,000 taka sales and 3,000 taka expenses, show 5,000, 3,000, and 2,000. Increase expenses to 6,000; the final card must show a 1,000 taka deficit.

## 6. Add filters and CSV export

Complete the basic tracker and dashboard before starting this step.

- [ ] Add filters for All Crops or one crop, plus a date range.
- [ ] Apply the same filters to both sales and expenses.
- [ ] Reject an end date earlier than the start date.
- [ ] Normalize old sales dates to `YYYY-MM-DD` after backing up the database. Flag invalid dates for correction and report excluded records when filtering.
- [ ] Use crop IDs for crop-specific reports; show unassigned legacy sales separately.
- [ ] Add Export CSV using Python's built-in `csv` module and a save-file dialog.
- [ ] Export the filtered records, currency, filter dates, and summary totals.

**Check:** Records outside the selected crop or date range must not affect the filtered totals. Open the exported CSV and compare its numbers with the screen.

## 7. Final checks

Use temporary test databases so checks do not change real farm records.

- [ ] Sales entry works with valid numbers and rejects invalid input.
- [ ] Expenses remain available after restarting the app.
- [ ] Empty datasets, decimal amounts, and negative balances display correctly.
- [ ] Date filters work across month and year boundaries.
- [ ] Invalid legacy data is reported rather than silently ignored.
- [ ] Adding or deleting records refreshes the relevant totals.
- [ ] Cancelling deletion or CSV export makes no changes.
- [ ] Existing crop suggestions and login still work.
- [ ] Add focused automated tests for amount validation, date parsing, summary calculations, and database migration preservation.

## Suggested work order

| Milestone | Deliverable |
| --- | --- |
| 1 | Reliable sales entry and refreshed sales table |
| 2 | Expense storage and working Add/View Expenses tabs |
| 3 | Dashboard totals with clear currency labels |
| 4 | Crop/date filters and CSV export |
| 5 | Verification and a short README explaining how to run the app |

Commit each completed milestone separately so it is easy to review or undo.

## Later improvements

1. **Crop list, search, and editing:** Make the catalog easier to maintain; archive crops referenced by financial records instead of deleting them.
2. **Planting cycles and harvest reminders:** Track planting dates and link sales and expenses to a particular harvest.
3. **Account improvements:** Replace plain-text passwords with password hashes and implement a verified recovery flow.
4. **Separate farmer records:** Pass the signed-in user to feature modules and store record ownership before using the app for independent farmers. The current app shares crop and sales records across accounts; this first version continues that single-farm model.
5. **Project setup:** Document dependencies and exclude local environments, caches, and private databases from future commits. Ignoring files alone does not remove files already tracked by Git.
