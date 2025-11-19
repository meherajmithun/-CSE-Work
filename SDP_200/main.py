# main.py
# IN THE NAME OF ALLAH

from tkinter import *
from tkinter import ttk, messagebox
from PIL import Image, ImageTk, ImageDraw
import sqlite3, os
import customtkinter as ctk

# Import features
from crop_management_feature import open_crop_management
from crop_suggestion_feature import open_crop_suggestion
from sales_tracker_feature import open_sales_tracker


class Login_Window:
    def __init__(self, root):
        self.root = root
        self.root.title("Smart Agricultural System - Login")
        self.root.geometry("1550x880+0+0")

        # ===== Gradient Background =====
        gradient = Image.new("RGB", (1550, 880), "#b3ffb3")
        draw = ImageDraw.Draw(gradient)
        for i in range(880):
            color = (180 - i//10, 255 - i//5, 180 - i//10)
            draw.line([(0, i), (1550, i)], fill=color)
        self.bg = ImageTk.PhotoImage(gradient)
        bg_lbl = Label(self.root, image=self.bg)
        bg_lbl.place(x=0, y=0, relwidth=1, relheight=1)

        # ===== Title Banner =====
        title_lbl = Label(
            self.root,
            text="🌿 SMART AGRICULTURAL SYSTEM 🌿",
            font=("Trebuchet MS", 28, "bold"),
            fg="white",
            bg="#1e5631",
            relief="ridge",
            bd=6,
            padx=10,
            pady=10
        )
        title_lbl.place(x=0, y=0, relwidth=1)

        # ===== Login Frame =====
        frame = Frame(self.root, bg="#0b3d0b", highlightbackground="#ffffff", highlightthickness=2)
        frame.place(x=520, y=200, width=400, height=480)

        img_path = "/home/meheraj/Pictures/295128.png"
        if os.path.exists(img_path):
            img = Image.open(img_path)
            img = img.resize((90, 90), Image.LANCZOS)
            self.photoimage1 = ImageTk.PhotoImage(img)
            lblimg1 = Label(frame, image=self.photoimage1, bg="#0b3d0b", borderwidth=0)
            lblimg1.place(x=150, y=10)
        else:
            lblimg1 = Label(frame, text="🌱", font=("Arial", 40), bg="#0b3d0b", fg="white")
            lblimg1.place(x=170, y=20)

        get_str = Label(frame, text="Welcome Back!", font=("Georgia", 20, "bold"), fg="white", bg="#0b3d0b")
        get_str.place(x=110, y=110)

        username_lbl = Label(frame, text="Username", font=("Verdana", 13, "bold"), fg="white", bg="#0b3d0b")
        username_lbl.place(x=60, y=160)
        self.txtuser = ttk.Entry(frame, font=("times new roman", 15, "bold"))
        self.txtuser.place(x=60, y=190, width=280)

        password_lbl = Label(frame, text="Password", font=("Verdana", 13, "bold"), fg="white", bg="#0b3d0b")
        password_lbl.place(x=60, y=230)
        self.txtpass = ttk.Entry(frame, font=("times new roman", 15, "bold"), show="*")
        self.txtpass.place(x=60, y=260, width=280)

        style = ttk.Style()
        style.configure("TButton", font=("Verdana", 12, "bold"), padding=6)

        loginbtn = Button(frame, text="Login", command=self.login, font=("Verdana", 13, "bold"),
                          bd=3, relief=RIDGE, fg="white", bg="#1e5631", activeforeground="white",
                          activebackground="#2e8b57", cursor="hand2")
        loginbtn.place(x=60, y=310, width=280, height=40)

        registerbtn = Button(frame, text="New User? Register", command=self.register,
                             font=("Verdana", 10, "bold"), borderwidth=0,
                             fg="#80ff80", bg="#0b3d0b", activeforeground="white", cursor="hand2")
        registerbtn.place(x=60, y=370, width=280)

        forgotbtn = Button(frame, text="Forgot Password?", command=self.forgot_password,
                           font=("Verdana", 10, "bold"), borderwidth=0,
                           fg="#ff9999", bg="#0b3d0b", activeforeground="white", cursor="hand2")
        forgotbtn.place(x=60, y=400, width=280)

        footer_lbl = Label(self.root, text="© 2025 Smart Agro System | Developed by Meheraj Mithun",
                           font=("Verdana", 11), fg="white", bg="#1e5631")
        footer_lbl.pack(side=BOTTOM, fill=X)

        self.setup_database()

    # ===== Database Setup =====
    def setup_database(self):
        conn = sqlite3.connect("admininfo.db")
        cur = conn.cursor()
        cur.execute("""
            CREATE TABLE IF NOT EXISTS users(
                username TEXT PRIMARY KEY,
                password TEXT NOT NULL
            )
        """)
        conn.commit()
        conn.close()

    # ===== Login Function =====
    def login(self):
        username = self.txtuser.get()
        password = self.txtpass.get()
        if username == "" or password == "":
            messagebox.showerror("Error", "All fields required!", parent=self.root)
            return

        conn = sqlite3.connect("admininfo.db")
        cur = conn.cursor()
        cur.execute("SELECT * FROM users WHERE username=? AND password=?", (username, password))
        row = cur.fetchone()
        conn.close()

        if row:
            messagebox.showinfo("Success", f"Welcome {username}!", parent=self.root)
            self.open_dashboard()
        else:
            messagebox.showerror("Error", "Invalid Username or Password", parent=self.root)

    # ===== Register Function =====
    def register(self):
        top = Toplevel(self.root)
        top.title("Register New User")
        top.geometry("400x300+700+250")
        top.config(bg="white")

        Label(top, text="Create Account", font=("times new roman", 18, "bold"), bg="white").pack(pady=10)
        Label(top, text="Username:", bg="white").pack()
        user = Entry(top, font=("times new roman", 13))
        user.pack(pady=5)
        Label(top, text="Password:", bg="white").pack()
        pas = Entry(top, font=("times new roman", 13), show="*")
        pas.pack(pady=5)

        def save_user():
            if user.get() == "" or pas.get() == "":
                messagebox.showerror("Error", "All fields are required", parent=top)
                return
            conn = sqlite3.connect("admininfo.db")
            cur = conn.cursor()
            try:
                cur.execute("INSERT INTO users VALUES (?, ?)", (user.get(), pas.get()))
                conn.commit()
                messagebox.showinfo("Success", "Account Created Successfully", parent=top)
                top.destroy()
            except sqlite3.IntegrityError:
                messagebox.showerror("Error", "Username already exists", parent=top)
            conn.close()

        Button(top, text="Register", command=save_user, font=("times new roman", 13, "bold"),
               bg="green", fg="white").pack(pady=20)

    def forgot_password(self):
        messagebox.showinfo("Info", "Password reset feature coming soon!", parent=self.root)

    # ===== Dashboard =====
    def open_dashboard(self):
        username = self.txtuser.get()
        self.root.withdraw()

        dash = ctk.CTkToplevel(self.root)
        dash.title("Smart Agricultural System - Dashboard")
        dash.geometry("1000x650+400+200")
        dash.resizable(False, False)

        ctk.set_appearance_mode("light")
        ctk.set_default_color_theme("green")

        # ===== Header =====
        header = ctk.CTkFrame(dash, fg_color="#1e5631", height=70)
        header.pack(fill="x")

        logout_btn = ctk.CTkButton(header, text="Logout", fg_color="#b71c1c", hover_color="#7f0000",
                                   width=90, command=lambda: self.logout(dash))
        logout_btn.place(x=20, y=18)

        ctk.CTkLabel(header, text="🌾 SMART AGRICULTURAL DASHBOARD 🌾",
                     font=("Trebuchet MS", 24, "bold"), text_color="white").place(relx=0.5, rely=0.5, anchor="center")

        ctk.CTkLabel(header, text=f"👤 {username}",
                     font=("Verdana", 16, "bold"), text_color="white").place(x=830, y=18)

        # ===== Main Frames =====
        main_frame = ctk.CTkFrame(dash, corner_radius=0, fg_color="#e8f5e9")
        main_frame.pack(fill="both", expand=True)

        sidebar = ctk.CTkFrame(main_frame, width=220, fg_color="#2e7d32")
        sidebar.pack(side="left", fill="y")

        ctk.CTkLabel(sidebar, text="🌿 Menu", font=("Georgia", 20, "bold"), text_color="white").pack(pady=25)

        # Crop Management Button
        ctk.CTkButton(sidebar, text="Crop Management", font=("Verdana", 15, "bold"),
                      height=45, width=180, corner_radius=10, fg_color="#66bb6a", hover_color="#388e3c",
                      command=open_crop_management).pack(pady=15)

        # Crop Suggestion Button
        ctk.CTkButton(sidebar, text="Crop Suggestion", font=("Verdana", 15, "bold"),
                      height=45, width=180, corner_radius=10, fg_color="#81c784",
                      hover_color="#388e3c", command=open_crop_suggestion).pack(pady=15)

        # Sales Tracker Button
        ctk.CTkButton(sidebar, text="Sales Tracker", font=("Verdana", 15, "bold"),
                      height=45, width=180, corner_radius=10,
                      fg_color="#a5d6a7", hover_color="#66bb6a",
                      command=open_sales_tracker).pack(pady=15)

        ctk.CTkLabel(sidebar, text="(More features coming...)",
                     text_color="#c8e6c9", font=("Verdana", 11, "italic")).pack(side="bottom", pady=20)

        # ===== Content Area =====
        content = ctk.CTkFrame(main_frame, fg_color="white")
        content.pack(side="right", expand=True, fill="both")

        ctk.CTkLabel(content, text="Welcome to the Smart Agro Dashboard 🌾",
                     font=("Trebuchet MS", 22, "bold"), text_color="#1b5e20").place(relx=0.5, rely=0.3, anchor="center")
        ctk.CTkLabel(content, text="Select an option from the left menu to begin",
                     font=("Verdana", 14), text_color="#2e7d32").place(relx=0.5, rely=0.4, anchor="center")

    # ===== Logout Function =====
    def logout(self, dashboard_window):
        dashboard_window.destroy()
        self.root.deiconify()


if __name__ == "__main__":
    root = Tk()
    app = Login_Window(root)
    root.mainloop()
