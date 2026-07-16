import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.*;

public class SmartAgriSystem {

    public static void main(String[] args) {
        new MainFrame();
    }
}

/* ================= MAIN FRAME ================= */
class MainFrame extends JFrame {

    CardLayout card = new CardLayout();
    JPanel main = new JPanel(card);

    LoginPanel login = new LoginPanel(this);
    DashboardPanel dash = new DashboardPanel(this);
    CropPanel crop = new CropPanel(this);
    SuggestPanel suggest = new SuggestPanel(this);
    SalesPanel sales = new SalesPanel(this);

    public MainFrame() {
        setTitle("Smart Agricultural System");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        main.add(login, "login");
        main.add(dash, "dash");
        main.add(crop, "crop");
        main.add(suggest, "suggest");
        main.add(sales, "sales");

        add(main);

        showLogin();
        setVisible(true);
    }

    void showLogin() { card.show(main, "login"); }
    void showDash() { card.show(main, "dash"); }
    void showCrop() { card.show(main, "crop"); }
    void showSuggest() { card.show(main, "suggest"); }
    void showSales() { card.show(main, "sales"); }
}

/* ================= LOGIN ================= */
class LoginPanel extends JPanel {

    public LoginPanel(MainFrame f) {
        setLayout(null);

        JLabel l1 = new JLabel("USERNAME:");
        JLabel l2 = new JLabel("PASSWORD:");

        JTextField u = new JTextField();
        JPasswordField p = new JPasswordField();

        l1.setBounds(550, 200, 100, 30);
        l2.setBounds(550, 240, 100, 30);

        u.setBounds(650, 200, 200, 30);
        p.setBounds(650, 240, 200, 30);

        JButton login = new JButton("LOGIN");
        JButton reg = new JButton("REGISTER");

        login.setBounds(550, 300, 120, 30);
        reg.setBounds(700, 300, 120, 30);

        add(l1); add(l2); add(u); add(p);
        add(login); add(reg);

        login.addActionListener(e -> {
            if (Auth.login(u.getText(), new String(p.getPassword()))) {
                JOptionPane.showMessageDialog(this, "Login Success");
                f.showDash();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid Login");
            }
        });

        reg.addActionListener(e -> {
            Auth.register(u.getText(), new String(p.getPassword()));
            JOptionPane.showMessageDialog(this, "Registered Successfully");
        });
    }
}

/* ================= AUTH ================= */
class Auth {

    static void register(String u, String p) {
        try {
            FileWriter fw = new FileWriter("users.txt", true);
            fw.write(u + "," + p + "\n");
            fw.close();
        } catch (Exception e) {}
    }

    static boolean login(String u, String p) {
        try {
            Scanner sc = new Scanner(new File("users.txt"));
            while (sc.hasNextLine()) {
                String[] d = sc.nextLine().split(",");
                if (d[0].equals(u) && d[1].equals(p)) return true;
            }
        } catch (Exception e) {}
        return false;
    }
}

/* ================= DASHBOARD ================= */
class DashboardPanel extends JPanel {

    public DashboardPanel(MainFrame f) {
        setLayout(null);

        JButton b1 = new JButton("Crop Management");
        JButton b2 = new JButton("Crop Suggestion");
        JButton b3 = new JButton("Sales Tracking");
        JButton b4 = new JButton("Logout");

        b1.setBounds(600, 200, 200, 40);
        b2.setBounds(600, 260, 200, 40);
        b3.setBounds(600, 320, 200, 40);
        b4.setBounds(600, 380, 200, 40);

        add(b1); add(b2); add(b3); add(b4);

        b1.addActionListener(e -> f.showCrop());
        b2.addActionListener(e -> f.showSuggest());
        b3.addActionListener(e -> f.showSales());
        b4.addActionListener(e -> f.showLogin());
    }
}

/* ================= CROP ================= */
class CropPanel extends JPanel {

    public CropPanel(MainFrame f) {
        setLayout(null);

        JTextField name = new JTextField();
        JTextField life = new JTextField();
        JTextField weather = new JTextField();
        JTextField water = new JTextField();
        JTextField sun = new JTextField();
        JTextField price = new JTextField();

        name.setBounds(500, 120, 200, 30);
        life.setBounds(500, 160, 200, 30);
        weather.setBounds(500, 200, 200, 30);
        water.setBounds(500, 240, 200, 30);
        sun.setBounds(500, 280, 200, 30);
        price.setBounds(500, 320, 200, 30);

        add(name); add(life); add(weather);
        add(water); add(sun); add(price);

        JButton save = new JButton("SAVE");
        JButton back = new JButton("BACK");

        save.setBounds(500, 370, 100, 30);
        back.setBounds(620, 370, 100, 30);

        add(save); add(back);

        save.addActionListener(e -> {
            try {
                FileWriter fw = new FileWriter("crops.txt", true);
                fw.write(name.getText() + "," +
                        life.getText() + "," +
                        weather.getText() + "," +
                        water.getText() + "," +
                        sun.getText() + "," +
                        price.getText() + "\n");
                fw.close();

                JOptionPane.showMessageDialog(this, "Saved");
            } catch (Exception ex) {}
        });

        back.addActionListener(e -> f.showDash());
    }
}

/* ================= SUGGESTION ================= */
class SuggestPanel extends JPanel {

    public SuggestPanel(MainFrame f) {
        setLayout(null);

        JTextField weather = new JTextField();
        JTextField water = new JTextField();
        JTextField sun = new JTextField();

        weather.setBounds(500, 120, 200, 30);
        water.setBounds(500, 160, 200, 30);
        sun.setBounds(500, 200, 200, 30);

        JTextArea result = new JTextArea();

        result.setBounds(500, 300, 300, 200);

        JButton find = new JButton("FIND");
        JButton back = new JButton("BACK");

        find.setBounds(500, 250, 100, 30);
        back.setBounds(620, 250, 100, 30);

        add(weather); add(water); add(sun);
        add(result); add(find); add(back);

        find.addActionListener(e -> {
            result.setText("");

            try {
                Scanner sc = new Scanner(new File("crops.txt"));

                while (sc.hasNextLine()) {
                    String[] c = sc.nextLine().split(",");

                    if (c[2].equals(weather.getText()) &&
                        c[3].equals(water.getText()) &&
                        c[4].equals(sun.getText())) {

                        result.append(c[0] + "\n");
                    }
                }
            } catch (Exception ex) {}
        });

        back.addActionListener(e -> f.showDash());
    }
}

/* ================= SALES ================= */
class SalesPanel extends JPanel {

    double totalProfit = 0;

    public SalesPanel(MainFrame f) {
        setLayout(null);

        JTextField crop = new JTextField();
        JTextField buy = new JTextField();
        JTextField sell = new JTextField();

        JLabel result = new JLabel();

        crop.setBounds(500, 120, 200, 30);
        buy.setBounds(500, 160, 200, 30);
        sell.setBounds(500, 200, 200, 30);
        result.setBounds(500, 300, 300, 30);

        JButton calc = new JButton("CALCULATE");
        JButton back = new JButton("BACK");

        calc.setBounds(500, 250, 150, 30);
        back.setBounds(670, 250, 100, 30);

        add(crop); add(buy); add(sell);
        add(result); add(calc); add(back);

        calc.addActionListener(e -> {
            double b = Double.parseDouble(buy.getText());
            double s = Double.parseDouble(sell.getText());

            double profit = s - b;
            totalProfit += profit;

            result.setText("Profit: " + profit + " | Total: " + totalProfit);

            try {
                FileWriter fw = new FileWriter("sales.txt", true);
                fw.write(crop.getText() + "," + b + "," + s + "," + profit + "\n");
                fw.close();
            } catch (Exception ex) {}
        });

        back.addActionListener(e -> f.showDash());
    }
}