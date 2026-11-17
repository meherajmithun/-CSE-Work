# IN THE NAME OF ALLAH
import customtkinter as ctk
from tkinter import ttk
import sqlite3
from finance_data import BASE_DIR


def open_crop_suggestion(parent):
    return open_suggestion_from_database(parent)


def open_suggestion_from_database(parent):
    win = ctk.CTkFrame(parent)

    ctk.CTkLabel(win, text="Get Best Crop Suggestion 🌱",
                 font=("Verdana", 20, "bold")).pack(pady=20)

    frame = ctk.CTkFrame(win)
    frame.pack(pady=10)

    # ===== COMBOBOX CREATOR =====
    def create_combobox(label, values, row):
        ctk.CTkLabel(frame, text=label, font=("Verdana", 13)).grid(row=row, column=0, sticky="w", pady=8)
        cb = ttk.Combobox(frame, values=values, state="readonly", width=28)
        cb.grid(row=row, column=1, pady=8, padx=10)
        cb.current(0)
        return cb

    # ===== 4 COMBOBOXES =====
    soil_cb = create_combobox("Soil Type", [
        "Alluvial Soil (Floodplain Soil)",
        "Red Soil (Lateritic Soil)",
        "Sandy Soil",
        "Clay Soil",
        "Loamy Soil",
                "Peaty Soil (Haor Soil)",
        "Saline Soil (Coastal Soil)"
    ], 0)

    sunlight_cb = create_combobox("Sunlight Requirement", ["Low", "Medium", "High"], 1)
    water_cb = create_combobox("Water Requirement", ["Low", "Medium", "High"], 2)
    season_cb = create_combobox("Season", ["Summer", "Rainy", "Winter", "Monsoon"], 3)

    # ===== SEARCH FUNCTION =====
    def search_crop():
        soil = soil_cb.get()
        sunlight = sunlight_cb.get()
        water = water_cb.get()
        season = season_cb.get()

        conn = sqlite3.connect(BASE_DIR / "crops.db")
        cur = conn.cursor()

        cur.execute("""
            SELECT crop_name, category, days_to_harvest
            FROM crops
            WHERE soil_type=? AND sunlight=? AND water_req=? AND season=?
        """, (soil, sunlight, water, season))

        results = cur.fetchall()
        conn.close()

        for child in result_win.winfo_children():
            child.destroy()
        if not results:
            ctk.CTkLabel(result_win, text='No matching crop found. Try different growing conditions.').pack(pady=15)
            return

        # Show all matched crops
        for name, category, days in results:
            ctk.CTkLabel(result_win,
                         text=f"🌿 {name}\nCategory: {category}\nDays: {days}",
                         font=("Verdana", 14),
                         text_color="#1b5e20",
                         anchor="w",
                         justify="left").pack(pady=8)

    # ===== SEARCH BUTTON =====
    ctk.CTkButton(win, text="Search Crop", width=180, height=40,
                  fg_color="#2e7d32", hover_color="#1b5e20",
                  command=search_crop).pack(pady=25)

    result_win = ctk.CTkScrollableFrame(win, label_text='Suggested crops')
    result_win.pack(fill='both', expand=True, padx=20, pady=(0, 20))
    ctk.CTkLabel(result_win, text='Choose growing conditions and select Search Crop.').pack(pady=15)
    return win
