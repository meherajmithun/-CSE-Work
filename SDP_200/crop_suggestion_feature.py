# IN THE NAME OF ALLAH
import customtkinter as ctk
from tkinter import ttk, messagebox
import sqlite3


def open_crop_suggestion():
    win = ctk.CTkToplevel()
    win.title("Crop Suggestion")
    win.geometry("480x350")
    win.resizable(False, False)

    ctk.CTkLabel(win, text="Crop Suggestion 🌾", font=("Verdana", 22, "bold")).pack(pady=20)

    btn_frame = ctk.CTkFrame(win)
    btn_frame.pack(pady=20)

    # ✅ Option 1 (Main Feature)
    ctk.CTkButton(btn_frame, text="Suggestion from Database",
                  width=280, height=45,
                  fg_color="#66bb6a", hover_color="#2e7d32",
                  command=open_suggestion_from_database).pack(pady=10)

    # ✅ Option 2 (Future Update)
    ctk.CTkButton(btn_frame, text="Suggestion Based on Sales Tracking",
                  width=280, height=45,
                  fg_color="#81c784", hover_color="#1b5e20",
                  command=lambda: messagebox.showinfo("Coming Soon",
                                                      "Sales tracking suggestion is coming in future ✅")
                  ).pack(pady=10)


# ✅ SUGGESTION FROM DATABASE SCREEN
def open_suggestion_from_database():
    win = ctk.CTkToplevel()
    win.title("Database Crop Suggestion")
    win.geometry("600x450")
    win.resizable(False, False)

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

        conn = sqlite3.connect("crops.db")
        cur = conn.cursor()

        cur.execute("""
            SELECT crop_name, category, days_to_harvest
            FROM crops
            WHERE soil_type=? AND sunlight=? AND water_req=? AND season=?
        """, (soil, sunlight, water, season))

        results = cur.fetchall()
        conn.close()

        if not results:
            messagebox.showinfo("No Result", "No matching crop found!")
            return

        # Create result window
        result_win = ctk.CTkToplevel(win)
        result_win.title("Suggested Crop")
        result_win.geometry("400x350")

        ctk.CTkLabel(result_win, text="Suggested Crops ✅",
                     font=("Verdana", 20, "bold")).pack(pady=10)

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
