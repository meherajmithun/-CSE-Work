# crop_management_feature.py
# IN THE NAME OF ALLAH

import customtkinter as ctk
from tkinter import ttk, messagebox
import sqlite3

def ensure_crops_db():
    conn = sqlite3.connect("crops.db")
    cur = conn.cursor()
    cur.execute("""
        CREATE TABLE IF NOT EXISTS crops(
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            crop_name TEXT,
            days_to_harvest INTEGER,
            soil_type TEXT,
            sunlight TEXT,
            water_req TEXT,
            category TEXT,
            season TEXT
        )
    """)
    conn.commit()
    conn.close()

ensure_crops_db()

def open_crop_management():
    win = ctk.CTkToplevel()
    win.title("Crop Management")
    win.geometry("650x550+450+180")
    win.resizable(False, False)

    ctk.CTkLabel(win, text="Crop Management 🌱", font=("Verdana", 20, "bold")).pack(pady=14)

    frame = ctk.CTkFrame(win)
    frame.pack(padx=20, pady=10, fill="both", expand=False)

    for i in range(2):
        frame.grid_columnconfigure(i, weight=1)

    ctk.CTkLabel(frame, text="Crop Name").grid(row=0, column=0, sticky="w", pady=8, padx=10)
    crop_name = ctk.CTkEntry(frame, width=320)
    crop_name.grid(row=0, column=1, pady=8, padx=10)

    ctk.CTkLabel(frame, text="Days to Harvest").grid(row=1, column=0, sticky="w", pady=8, padx=10)
    days_to_harvest = ctk.CTkEntry(frame, width=320)
    days_to_harvest.grid(row=1, column=1, pady=8, padx=10)

    def create_combobox(parent, text, values, row):
        ctk.CTkLabel(parent, text=text).grid(row=row, column=0, sticky="w", pady=8, padx=10)
        cb = ttk.Combobox(parent, values=values, state="readonly", width=37)
        cb.grid(row=row, column=1, pady=8, padx=10)
        if values:
            cb.current(0)
        return cb

    soil_values = [
        "Alluvial Soil (Floodplain Soil)", "Red Soil (Lateritic Soil)",
        "Sandy Soil", "Clay Soil", "Loamy Soil",
        "Peaty Soil (Haor Soil)", "Saline Soil (Coastal Soil)"
    ]
    soil_cb = create_combobox(frame, "Soil Type", soil_values, 2)
    sunlight_cb = create_combobox(frame, "Sunlight Requirement", ["Low", "Medium", "High"], 3)
    water_cb = create_combobox(frame, "Water Requirement", ["Low", "Medium", "High"], 4)
    category_cb = create_combobox(frame, "Crop Category", ["Fruit", "Vegetables", "Crop"], 5)
    season_cb = create_combobox(frame, "Season", ["Summer", "Rainy", "Winter", "Monsoon"], 6)

    def reset_fields():
        crop_name.delete(0, "end")
        days_to_harvest.delete(0, "end")
        try:
            soil_cb.current(0)
            sunlight_cb.current(0)
            water_cb.current(0)
            category_cb.current(0)
            season_cb.current(0)
        except:
            pass

    def add_crop():
        name = crop_name.get().strip()
        days = days_to_harvest.get().strip()
        soil = soil_cb.get()
        sunlight = sunlight_cb.get()
        water = water_cb.get()
        category = category_cb.get()
        season = season_cb.get()

        if not name or not days:
            messagebox.showerror("Error", "Please fill Crop Name and Days to Harvest", parent=win)
            return

        try:
            days_int = int(days)
            if days_int < 0:
                raise ValueError
        except ValueError:
            messagebox.showerror("Error", "Days to harvest must be a non-negative integer", parent=win)
            return

        conn = sqlite3.connect("crops.db")
        cur = conn.cursor()
        cur.execute("""
            INSERT INTO crops (crop_name, days_to_harvest, soil_type, sunlight, water_req, category, season)
            VALUES (?, ?, ?, ?, ?, ?, ?)
        """, (name, days_int, soil, sunlight, water, category, season))
        conn.commit()
        conn.close()

        messagebox.showinfo("Success", f"Crop '{name}' added successfully!", parent=win)
        reset_fields()

    btn_frame = ctk.CTkFrame(win)
    btn_frame.pack(padx=20, pady=16, fill="x")

    reset_btn = ctk.CTkButton(btn_frame, text="Reset", width=140, command=reset_fields, fg_color="#9e9e9e")
    reset_btn.pack(side="left", padx=10)

    add_btn = ctk.CTkButton(btn_frame, text="Add Crop", width=140, command=add_crop, fg_color="#2e7d32")
    add_btn.pack(side="right", padx=10)
