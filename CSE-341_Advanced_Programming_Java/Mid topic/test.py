import sqlite3
from tkinter import *
from tkinter import messagebox, ttk
from datetime import datetime
import matplotlib.pyplot as plt

# ----------------- Database Setup -----------------
conn = sqlite3.connect('expenses.db')
c = conn.cursor()
c.execute('''
CREATE TABLE IF NOT EXISTS expenses (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    date TEXT,
    category TEXT,
    amount REAL,
    note TEXT
)
''')
conn.commit()

# ----------------- Functions -----------------
def add_expense():
    date = datetime.now().strftime("%Y-%m-%d")
    category = category_entry.get()
    amount = amount_entry.get()
    note = note_entry.get()
    if category and amount:
        try:
            c.execute("INSERT INTO expenses (date, category, amount, note) VALUES (?, ?, ?, ?)",
                      (date, category, float(amount), note))
            conn.commit()
            messagebox.showinfo("Success", "Expense added!")
            category_entry.delete(0, END)
            amount_entry.delete(0, END)
            note_entry.delete(0, END)
            view_expenses()
        except:
            messagebox.showerror("Error", "Enter a valid amount")
    else:
        messagebox.showwarning("Input Error", "Category and Amount required")

def view_expenses():
    for row in tree.get_children():
        tree.delete(row)
    c.execute("SELECT date, category, amount, note FROM expenses ORDER BY date DESC")
    for row in c.fetchall():
        tree.insert("", END, values=row)

def monthly_summary():
    month = month_entry.get()
    if not month:
        messagebox.showwarning("Input Error", "Enter month in YYYY-MM format")
        return
    c.execute("SELECT category, SUM(amount) FROM expenses WHERE strftime('%Y-%m', date)=? GROUP BY category", (month,))
    data = c.fetchall()
    if not data:
        messagebox.showinfo("Info", "No data for this month")
        return
    categories = [x[0] for x in data]
    amounts = [x[1] for x in data]
    
    plt.figure(figsize=(6,6))
    plt.pie(amounts, labels=categories, autopct='%1.1f%%')
    plt.title(f"Expense Summary for {month}")
    plt.show()

# ----------------- GUI -----------------
root = Tk()
root.title("Daily Expense Tracker")
root.geometry("600x500")

# Add Expense Frame
frame1 = Frame(root)
frame1.pack(pady=10)

Label(frame1, text="Category:").grid(row=0, column=0, padx=5, pady=5)
category_entry = Entry(frame1)
category_entry.grid(row=0, column=1, padx=5, pady=5)

Label(frame1, text="Amount:").grid(row=1, column=0, padx=5, pady=5)
amount_entry = Entry(frame1)
amount_entry.grid(row=1, column=1, padx=5, pady=5)

Label(frame1, text="Note:").grid(row=2, column=0, padx=5, pady=5)
note_entry = Entry(frame1)
note_entry.grid(row=2, column=1, padx=5, pady=5)

Button(frame1, text="Add Expense", command=add_expense).grid(row=3, column=0, columnspan=2, pady=10)

# View Expenses Frame
frame2 = Frame(root)
frame2.pack(pady=10)

tree = ttk.Treeview(frame2, columns=("Date","Category","Amount","Note"), show='headings')
tree.heading("Date", text="Date")
tree.heading("Category", text="Category")
tree.heading("Amount", text="Amount")
tree.heading("Note", text="Note")
tree.pack()

# Monthly Summary Frame
frame3 = Frame(root)
frame3.pack(pady=10)

Label(frame3, text="Enter month (YYYY-MM):").grid(row=0, column=0, padx=5)
month_entry = Entry(frame3)
month_entry.grid(row=0, column=1, padx=5)
Button(frame3, text="Show Monthly Summary", command=monthly_summary).grid(row=0, column=2, padx=5)

# Load initial data
view_expenses()

root.mainloop()