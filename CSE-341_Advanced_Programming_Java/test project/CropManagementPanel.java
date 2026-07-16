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

        JLabel title = new JLabel("SMART AGRICULTURAL SYSTEM LOGIN");
        title.setBounds(500, 100, 400, 30);
        add(title);

        JLabel uL = new JLabel("Enter Username:");
        JLabel pL = new JLabel("Enter Password:");

        JTextField u = new JTextField();
        JPasswordField p = new JPasswordField();

        uL.setBounds(500, 200, 150, 30);
        pL.setBounds(500, 240, 150, 30);

        u.setBounds(650, 200, 200, 30);
        p.setBounds(650, 240, 200, 30);

        JButton login = new JButton("LOGIN");
        JButton reg = new JButton("REGISTER");

        login.setBounds(500, 300, 120, 30);
        reg.setBounds(650, 300, 120, 30);

        add(uL); add(pL);
        add(u); add(p);
        add(login); add(reg);

        login.addActionListener(e -> {
            if (Auth.login(u.getText(), new String(p.getPassword()))) {
                JOptionPane.showMessageDialog(this, "Login Successful");
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

/* ================= AUTH FILE ================= */
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

        JLabel title = new JLabel("DASHBOARD");
        title.setBounds(650, 100, 200, 30);
        add(title);

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

/* ================= CROP MANAGEMENT ================= */
class CropPanel extends JPanel {

    public CropPanel(MainFrame f) {
        setLayout(null);

        JLabel title = new JLabel("CROP MANAGEMENT");
        title.setBounds(600, 50, 200, 30);
        add(title);

        JLabel l1 = new JLabel("Crop Name:");
        JLabel l2 = new JLabel("Lifetime:");
        JLabel l3 = new JLabel("Weather:");
        JLabel l4 = new JLabel("Water:");
        JLabel l5 = new JLabel("Sunlight:");
        JLabel l6 = new JLabel("Purchase Price:");

        JTextField name = new JTextField();
        JTextField life = new JTextField();
        JTextField weather = new JTextField();
        JTextField water = new JTextField();
        JTextField sun = new JTextField();
        JTextField price = new JTextField();

        l1.setBounds(450, 120, 150, 30);
        l2.setBounds(450, 160, 150, 30);
        l3.setBounds(450, 200, 150, 30);
        l4.setBounds(450, 240, 150, 30);
        l5.setBounds(450, 280, 150, 30);
        l6.setBounds(450, 320, 150, 30);

        name.setBounds(600, 120, 200, 30);
        life.setBounds(600, 160, 200, 30);
        weather.setBounds(600, 200, 200, 30);
        water.setBounds(600, 240, 200, 30);
        sun.setBounds(600, 280, 200, 30);
        price.setBounds(600, 320, 200, 30);

        add(l1); add(l2); add(l3);
        add(l4); add(l5); add(l6);

        add(name); add(life); add(weather);
        add(water); add(sun); add(price);

        JButton save = new JButton("SAVE");
        JButton back = new JButton("BACK");

        save.setBounds(550, 380, 100, 30);
        back.setBounds(670, 380, 100, 30);

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

                JOptionPane.showMessageDialog(this, "Crop Saved!");
            } catch (Exception ex) {}
        });

        back.addActionListener(e -> f.showDash());
    }
}

/* ================= SUGGESTION ================= */
class SuggestPanel extends JPanel {

    public SuggestPanel(MainFrame f) {
        setLayout(null);

        JLabel title = new JLabel("CROP SUGGESTION SYSTEM");
        title.setBounds(550, 50, 250, 30);
        add(title);

        JLabel l1 = new JLabel("Weather:");
        JLabel l2 = new JLabel("Water:");
        JLabel l3 = new JLabel("Sunlight:");

        JTextField weather = new JTextField();
        JTextField water = new JTextField();
        JTextField sun = new JTextField();

        l1.setBounds(450, 120, 150, 30);
        l2.setBounds(450, 160, 150, 30);
        l3.setBounds(450, 200, 150, 30);

        weather.setBounds(600, 120, 200, 30);
        water.setBounds(600, 160, 200, 30);
        sun.setBounds(600, 200, 200, 30);

        JTextArea result = new JTextArea();
        result.setBounds(500, 320, 400, 200);

        JButton find = new JButton("FIND");
        JButton back = new JButton("BACK");

        find.setBounds(500, 260, 100, 30);
        back.setBounds(620, 260, 100, 30);

        add(l1); add(l2); add(l3);
        add(weather); add(water); add(sun);
        add(result); add(find); add(back);

        find.addActionListener(e -> {
            result.setText("");

            try {
                Scanner sc = new Scanner(new File("crops.txt"));
                StringBuilder sb = new StringBuilder();

                while (sc.hasNextLine()) {
                    String[] d = sc.nextLine().split(",");

                    if (d[2].equals(weather.getText()) &&
                        d[3].equals(water.getText()) &&
                        d[4].equals(sun.getText())) {

                        sb.append("Crop Name : ").append(d[0]).append("\n");
                        sb.append("----------------------\n");
                    }
                }

                result.setText(sb.toString());

            } catch (Exception ex) {}
        });

        back.addActionListener(e -> f.showDash());
    }
}

/* ================= SALES ================= */
class SalesPanel extends JPanel {

    public SalesPanel(MainFrame f) {
        setLayout(null);

        JLabel title = new JLabel("SALES & PROFIT SYSTEM");
        title.setBounds(550, 50, 250, 30);
        add(title);

        JLabel l1 = new JLabel("Crop Name:");
        JLabel l2 = new JLabel("Purchase Price:");
        JLabel l3 = new JLabel("Selling Price:");

        JTextField crop = new JTextField();
        JTextField buy = new JTextField();
        JTextField sell = new JTextField();

        l1.setBounds(450, 120, 150, 30);
        l2.setBounds(450, 160, 150, 30);
        l3.setBounds(450, 200, 150, 30);

        crop.setBounds(600, 120, 200, 30);
        buy.setBounds(600, 160, 200, 30);
        sell.setBounds(600, 200, 200, 30);

        JTextArea result = new JTextArea();
        result.setBounds(500, 320, 400, 200);

        JButton calc = new JButton("CALCULATE");
        JButton back = new JButton("BACK");

        calc.setBounds(500, 260, 120, 30);
        back.setBounds(640, 260, 100, 30);

        add(l1); add(l2); add(l3);
        add(crop); add(buy); add(sell);
        add(result); add(calc); add(back);

        calc.addActionListener(e -> {
            try {
                double b = Double.parseDouble(buy.getText());
                double s = Double.parseDouble(sell.getText());
                double profit = s - b;

                StringBuilder sb = new StringBuilder();
                sb.append("Crop Name : ").append(crop.getText()).append("\n");
                sb.append("Purchase  : ").append(b).append("\n");
                sb.append("Selling   : ").append(s).append("\n");
                sb.append("Profit    : ").append(profit).append("\n");
                sb.append("----------------------\n");

                result.append(sb.toString());

                FileWriter fw = new FileWriter("sales.txt", true);
                fw.write(crop.getText() + "," + b + "," + s + "," + profit + "\n");
                fw.close();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid Input!");
            }
        });

        back.addActionListener(e -> f.showDash());
    }
}