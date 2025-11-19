# sales_tracker_feature.py
# IN THE NAME OF ALLAH
import customtkinter as ctk
from tkinter import ttk, messagebox
import sqlite3


# ==========================
#   OPEN SALES TRACKER
# ==========================
def open_sales_tracker():
    create_sales_db()

    win = ctk.CTkToplevel()
    win.title("Crop Sales Tracker")
    win.geometry("800x600")
    win.resizable(False, False)

    ctk.CTkLabel(win, text="🌾 CROP SALES TRACKER", font=("Verdana", 24, "bold")).pack(pady=10)

    tabview = ctk.CTkTabview(win, width=760, height=500)
    tabview.pack(pady=15)

    tab_add = tabview.add("Add Sale")
    tab_view = tabview.add("View Sales")

    build_add_sales(tab_add)
    build_sales_table(tab_view)


# ==========================
#   DATABASE CREATION
# ==========================
def create_sales_db():
    conn = sqlite3.connect("sales.db")
    cur = conn.cursor()
    cur.execute("""
        CREATE TABLE IF NOT EXISTS sales(
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            crop_name TEXT,
            quantity REAL,
            price REAL,
            total REAL,
            date TEXT
        )
    """)
    conn.commit()
    conn.close()


# ==========================
#   ADD SALES TAB
# ==========================
def build_add_sales(frame):
    # --- Fetch crop names from crops.db ---
    conn = sqlite3.connect("crops.db")
    cur = conn.cursor()
    cur.execute("SELECT crop_name FROM crops")
    crops = [row[0] for row in cur.fetchall()]
    conn.close()

    if not crops:
        crops = ["No Crops Found"]

    ctk.CTkLabel(frame, text="Add Crop Sale Entry", font=("Verdana", 18, "bold")).pack(pady=10)

    form = ctk.CTkFrame(frame)
    form.pack(pady=20)

    # Crop Name
    ctk.CTkLabel(form, text="Crop Name:", font=("Verdana", 14)).grid(row=0, column=0, pady=8, sticky='w')
    crop_cb = ttk.Combobox(form, values=crops, state="readonly", width=30)
    crop_cb.grid(row=0, column=1, pady=8)
    crop_cb.current(0)

    # Quantity
    ctk.CTkLabel(form, text="Quantity (kg):", font=("Verdana", 14)).grid(row=1, column=0, pady=8, sticky='w')
    qty_entry = ctk.CTkEntry(form, width=200)
    qty_entry.grid(row=1, column=1, pady=8)

    # Price
    ctk.CTkLabel(form, text="Price per kg:", font=("Verdana", 14)).grid(row=2, column=0, pady=8, sticky='w')
    price_entry = ctk.CTkEntry(form, width=200)
    price_entry.grid(row=2, column=1, pady=8)

    # Date
    ctk.CTkLabel(form, text="Date (DD-MM-YYYY):", font=("Verdana", 14)).grid(row=3, column=0, pady=8, sticky='w')
    date_entry = ctk.CTkEntry(form, width=200)
    date_entry.grid(row=3, column=1, pady=8)

    # Add Button
    def add_sale():
        crop = crop_cb.get()
        qty = qty_entry.get()
        price = price_entry.get()
        date = date_entry.get()

        if not qty or not price or not date:
            messagebox.showerror("Error", "All fields are required")
            return

        # try:
        #     qty = float(qty)
        #     price = float(price)
        # except:
        #     messagebox.showerror("Error", "Quantity and Price must be numbers")
        #     return

        total = qty * price

        conn = sqlite3.connect("sales.db")
        cur = conn.cursor()
        cur.execute("INSERT INTO sales(crop_name,quantity,price,total,date) VALUES (?,?,?,?,?)",
                    (crop, qty, price, total, date))
        conn.commit()
        conn.close()

        messagebox.showinfo("Success", "Sale added successfully")

        qty_entry.delete(0, "end")
        price_entry.delete(0, "end")
        date_entry.delete(0, "end")

    ctk.CTkButton(frame, text="Add Sale Entry", width=200, height=40,
                  fg_color="#2e7d32", hover_color="#1b5e20",
                  command=add_sale).pack(pady=20)


# ==========================
#   VIEW SALES TAB
# ==========================
def build_sales_table(frame):
    table_frame = ctk.CTkFrame(frame)
    table_frame.pack(pady=10)

    columns = ("ID", "Crop", "Qty", "Price", "Total", "Date")

    tree = ttk.Treeview(table_frame, columns=columns, show="headings", height=15)
    tree.pack()

    for col in columns:
        tree.heading(col, text=col)
        tree.column(col, width=100)

    load_sales(tree)

    # BUTTONS: Edit / Delete
    btn_frame = ctk.CTkFrame(frame)
    btn_frame.pack(pady=10)

    def delete_sale():
        selected = tree.selection()
        if not selected:
            messagebox.showerror("Error", "Select a sale to delete")
            return

        sale_id = tree.item(selected)['values'][0]

        conn = sqlite3.connect("sales.db")
        cur = conn.cursor()
        cur.execute("DELETE FROM sales WHERE id=?", (sale_id,))
        conn.commit()
        conn.close()

        messagebox.showinfo("Deleted", "Sale entry deleted")
        load_sales(tree)

    ctk.CTkButton(btn_frame, text="Delete Selected", fg_color="#b71c1c",
                  hover_color="#7f0000", width=150, command=delete_sale).grid(row=0, column=0, padx=20)


# ==========================
#   LOAD SALES INTO TABLE
# ==========================
def load_sales(tree):
    for row in tree.get_children():
        tree.delete(row)

    conn = sqlite3.connect("sales.db")
    cur = conn.cursor()
    cur.execute("SELECT * FROM sales ORDER BY id DESC")
    rows = cur.fetchall()
    conn.close()

    for row in rows:
        tree.insert("", "end", values=row)
