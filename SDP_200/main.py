"""Single-window Smart Agricultural System."""
import sqlite3
from contextlib import closing
from tkinter import Tk
import customtkinter as ctk

from crop_management_feature import open_crop_management
from crop_suggestion_feature import open_crop_suggestion
from sales_tracker_feature import open_sales_tracker
from expense_tracker_feature import open_expense_tracker
from finance_ui import FinanceDashboard
from finance_data import BASE_DIR


class Login_Window:
    def __init__(self, root):
        self.root = root
        root.title('Smart Agricultural System')
        root.geometry(f'{root.winfo_screenwidth()}x{root.winfo_screenheight()}+0+0')
        root.minsize(900, 650)
        ctk.set_appearance_mode('light')
        ctk.set_default_color_theme('green')
        self.fullscreen = True
        root.attributes('-fullscreen', True)
        root.bind('<Escape>', lambda event: self.set_fullscreen(False))
        root.bind('<F11>', lambda event: self.set_fullscreen(not self.fullscreen))
        toolbar = ctk.CTkFrame(root, fg_color='#1e5631', corner_radius=0)
        toolbar.pack(fill='x')
        ctk.CTkLabel(toolbar, text='SMART AGRICULTURAL SYSTEM', text_color='white',
                     font=('Verdana', 20, 'bold')).pack(side='left', padx=20, pady=12)
        ctk.CTkButton(toolbar, text='Exit app', width=80, command=root.destroy,
                      fg_color='#b71c1c').pack(side='right', padx=(5, 15))
        self.fullscreen_button = ctk.CTkButton(toolbar, text='Exit full screen', width=140,
                                              command=lambda: self.set_fullscreen(not self.fullscreen))
        self.fullscreen_button.pack(side='right', padx=5)
        self.body = ctk.CTkFrame(root, fg_color='#e8f5e9', corner_radius=0)
        self.body.pack(fill='both', expand=True)
        self.setup_database()
        self.show_login()

    def set_fullscreen(self, enabled):
        self.fullscreen = enabled
        self.root.attributes('-fullscreen', enabled)
        self.fullscreen_button.configure(text='Exit full screen' if enabled else 'Full screen')
        return 'break'

    def clear_body(self):
        for child in self.body.winfo_children():
            child.destroy()
        self.pages = {}

    def setup_database(self):
        with closing(sqlite3.connect(BASE_DIR / 'admininfo.db')) as conn, conn:
            conn.execute('CREATE TABLE IF NOT EXISTS users (username TEXT PRIMARY KEY, password TEXT NOT NULL)')

    def account_form(self, title):
        self.clear_body()
        panel = ctk.CTkFrame(self.body, fg_color='#0b3d0b', width=420)
        panel.place(relx=0.5, rely=0.5, anchor='center')
        ctk.CTkLabel(panel, text=title, font=('Verdana', 24, 'bold'), text_color='white').pack(padx=45, pady=(25, 20))
        ctk.CTkLabel(panel, text='Username', text_color='white').pack(anchor='w', padx=30)
        self.txtuser = ctk.CTkEntry(panel, width=320)
        self.txtuser.pack(padx=30, pady=(5, 15))
        ctk.CTkLabel(panel, text='Password', text_color='white').pack(anchor='w', padx=30)
        self.txtpass = ctk.CTkEntry(panel, width=320, show='*')
        self.txtpass.pack(padx=30, pady=(5, 15))
        self.account_status = ctk.CTkLabel(panel, text='', text_color='#ffcccc', wraplength=320)
        self.account_status.pack(padx=30, pady=5)
        return panel

    def show_login(self, message=''):
        panel = self.account_form('Welcome back')
        self.account_status.configure(text=message)
        ctk.CTkButton(panel, text='Login', command=self.login, width=320).pack(padx=30, pady=10)
        ctk.CTkButton(panel, text='New User? Register', command=self.register, width=320).pack(padx=30, pady=10)
        ctk.CTkButton(panel, text='Forgot Password?', command=self.forgot_password, width=320).pack(padx=30, pady=(10, 25))
        self.txtpass.bind('<Return>', lambda event: self.login())
        self.txtuser.focus_set()

    def login(self):
        username, password = self.txtuser.get().strip(), self.txtpass.get()
        if not username or not password:
            self.account_status.configure(text='Enter your username and password.')
            return
        try:
            with closing(sqlite3.connect(BASE_DIR / 'admininfo.db')) as conn:
                found = conn.execute('SELECT 1 FROM users WHERE username=? AND password=?', (username, password)).fetchone()
        except sqlite3.Error as exc:
            self.account_status.configure(text=f'Cannot sign in: {exc}')
            return
        if found:
            self.username = username
            self.open_dashboard()
        else:
            self.account_status.configure(text='Invalid username or password.')

    def register(self):
        panel = self.account_form('Create account')
        def save_user():
            username, password = self.txtuser.get().strip(), self.txtpass.get()
            if not username or not password:
                self.account_status.configure(text='Enter a username and password.')
                return
            try:
                with closing(sqlite3.connect(BASE_DIR / 'admininfo.db')) as conn, conn:
                    conn.execute('INSERT INTO users VALUES (?, ?)', (username, password))
            except sqlite3.IntegrityError:
                self.account_status.configure(text='Username already exists.')
                return
            except sqlite3.Error as exc:
                self.account_status.configure(text=f'Cannot register: {exc}')
                return
            self.show_login('Account created. You can now sign in.')
        ctk.CTkButton(panel, text='Register', command=save_user, width=320).pack(padx=30, pady=10)
        ctk.CTkButton(panel, text='Back to login', command=self.show_login, width=320).pack(padx=30, pady=(10, 25))

    def forgot_password(self):
        self.account_status.configure(text='Password recovery is not available yet.')

    def open_dashboard(self):
        self.clear_body()
        sidebar = ctk.CTkFrame(self.body, width=210, fg_color='#2e7d32', corner_radius=0)
        sidebar.pack(side='left', fill='y')
        ctk.CTkLabel(sidebar, text=f'Signed in as {self.username}', wraplength=180,
                     text_color='white').pack(padx=15, pady=20)
        self.content = ctk.CTkFrame(self.body, fg_color='white', corner_radius=0)
        self.content.pack(side='right', fill='both', expand=True)
        self.builders = {'Dashboard': FinanceDashboard, 'Crop Management': open_crop_management,
                         'Crop Suggestion': open_crop_suggestion, 'Sales Tracker': open_sales_tracker,
                         'Expense Tracker': open_expense_tracker}
        self.nav_buttons = {}
        for name in self.builders:
            button = ctk.CTkButton(sidebar, text=name, width=180, height=42,
                                   command=lambda page=name: self.show_page(page))
            button.pack(padx=15, pady=8)
            self.nav_buttons[name] = button
        ctk.CTkButton(sidebar, text='Logout', width=180, command=self.logout,
                      fg_color='#b71c1c').pack(side='bottom', padx=15, pady=20)
        self.show_page('Dashboard')

    def show_page(self, name):
        if name not in self.pages:
            try:
                self.pages[name] = self.builders[name](self.content)
            except (sqlite3.Error, OSError) as exc:
                page = ctk.CTkFrame(self.content)
                ctk.CTkLabel(page, text=f'Cannot load {name}: {exc}', wraplength=600).pack(pady=30)
                self.pages[name] = page
        for page in self.pages.values():
            page.pack_forget()
        self.pages[name].pack(fill='both', expand=True)
        self.current_page = self.pages[name]
        for label, button in self.nav_buttons.items():
            button.configure(fg_color='#164a28' if label == name else '#388e3c')
        self.root.event_generate('<<FinanceChanged>>', when='tail')

    def logout(self):
        self.username = ''
        self.show_login()


if __name__ == '__main__':
    root = Tk()
    app = Login_Window(root)
    root.mainloop()
