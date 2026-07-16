import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.Scanner;

// ================= DATA MODEL =================
class cropManagement {
    String cropName, season, weather, sunLight;
    int water, price;

    cropManagement(String cropName, String season, String weather, String sunLight, int water, int price) {
        this.cropName = cropName;
        this.season = season;
        this.weather = weather;
        this.sunLight = sunLight;
        this.water = water;
        this.price = price;
    }

    public String toString() {
        return cropName + " " + season + " " + weather + " " + sunLight + " " + water + " " + price + "\n";
    }

    public String pretty() {
        return "Crop: " + cropName +
                "\nSeason: " + season +
                "\nWeather: " + weather +
                "\nSunLight: " + sunLight +
                "\nWater: " + water +
                "\nPrice: " + price;
    }
}

// ================= SUGGESTION LOGIC =================
class cropSuggestion {

    private cropManagement read(String key, int type) throws Exception {

        File f = new File("info.txt");
        Scanner sc = new Scanner(f);

        while (sc.hasNext()) {
            String cropName = sc.next();
            String season = sc.next();
            String weather = sc.next();
            String sunLight = sc.next();
            int water = sc.nextInt();
            int price = sc.nextInt();

            boolean ok =
                    (type == 1 && key.equalsIgnoreCase(cropName)) ||
                    (type == 2 && key.equalsIgnoreCase(season)) ||
                    (type == 3 && key.equalsIgnoreCase(weather));

            if (ok) {
                sc.close();
                return new cropManagement(cropName, season, weather, sunLight, water, price);
            }
        }

        sc.close();
        return null;
    }

    cropManagement byCrop(String s) throws Exception { return read(s, 1); }
    cropManagement bySeason(String s) throws Exception { return read(s, 2); }
    cropManagement byWeather(String s) throws Exception { return read(s, 3); }
}

// ================= SALES LOGIC =================
class calculation {
    int calculate(String crop, int sale_price, int qty) throws Exception {

        File f = new File("info.txt");
        Scanner sc = new Scanner(f);

        while (sc.hasNext()) {
            String cropName = sc.next();
            String season = sc.next();
            String weather = sc.next();
            String sunLight = sc.next();
            int water = sc.nextInt();
            int purchase_price = sc.nextInt();

            if (crop.equalsIgnoreCase(cropName)) {
                sc.close();
                return (sale_price * qty) - (purchase_price * qty);
            }
        }

        sc.close();
        return 0;
    }
}

// ================= MAIN DASHBOARD =================
public class DashboardApp extends JFrame {

    CardLayout card = new CardLayout();
    JPanel main = new JPanel(card);

    cropSuggestion cs = new cropSuggestion();
    calculation calc = new calculation();

    JTextArea suggestOut = new JTextArea();
    JTextArea salesOut = new JTextArea();

    public DashboardApp() {

        setTitle("Smart Agriculture Dashboard");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        main.add(home(), "home");
        main.add(cropPanel(), "crop");
        main.add(suggestPanel(), "suggest");
        main.add(salesPanel(), "sales");

        add(main);
        card.show(main, "home");

        setVisible(true);
    }

    // ================= HOME =================
    JPanel home() {

        JPanel p = new JPanel(new GridBagLayout());
        GridBagConstraints c = center();

        JLabel title = new JLabel("SMART AGRICULTURE SYSTEM");
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));

        JButton b1 = button("Crop Management");
        JButton b2 = button("Crop Suggestion");
        JButton b3 = button("Sales System");

        b1.addActionListener(e -> card.show(main, "crop"));
        b2.addActionListener(e -> card.show(main, "suggest"));
        b3.addActionListener(e -> card.show(main, "sales"));

        p.add(title, c);
        p.add(b1, c);
        p.add(b2, c);
        p.add(b3, c);

        return p;
    }

    // ================= CROP PANEL =================
    JPanel cropPanel() {

        JPanel p = panel();

        JTextField a = box(), b = box(), c1 = box(),
                   d = box(), e = box(), f = box();

        JButton add = button("Add Crop");
        JButton back = button("Back");

        add.addActionListener(ev -> {
            try {
                FileWriter fw = new FileWriter("info.txt", true);

                cropManagement cm = new cropManagement(
                        a.getText(), b.getText(), c1.getText(),
                        d.getText(),
                        Integer.parseInt(e.getText()),
                        Integer.parseInt(f.getText())
                );

                fw.write(cm.toString());
                fw.close();

                JOptionPane.showMessageDialog(null, "Crop Added!");
            } catch (Exception ex) {}
        });

        back.addActionListener(ev -> card.show(main, "home"));

        addForm(p, "Crop Name", a);
        addForm(p, "Season", b);
        addForm(p, "Weather", c1);
        addForm(p, "SunLight", d);
        addForm(p, "Water", e);
        addForm(p, "Price", f);

        p.add(add, center());
        p.add(back, center());

        return p;
    }

    // ================= SUGGESTION (POPUP STYLE) =================
    JPanel suggestPanel() {

        JPanel p = new JPanel(new GridBagLayout());
        GridBagConstraints c = center();

        JButton cropBtn = button("Suggest by Crop Name");
        JButton seasonBtn = button("Suggest by Season");
        JButton weatherBtn = button("Suggest by Weather");
        JButton backBtn = button("Back");

        suggestOut.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        cropBtn.addActionListener(e -> {
            String key = JOptionPane.showInputDialog("Enter Crop Name:");
            if (key != null) showSuggest(key, 1);
        });

        seasonBtn.addActionListener(e -> {
            String key = JOptionPane.showInputDialog("Enter Season:");
            if (key != null) showSuggest(key, 2);
        });

        weatherBtn.addActionListener(e -> {
            String key = JOptionPane.showInputDialog("Enter Weather:");
            if (key != null) showSuggest(key, 3);
        });

        backBtn.addActionListener(e -> card.show(main, "home"));

        p.add(cropBtn, c);
        p.add(seasonBtn, c);
        p.add(weatherBtn, c);
        p.add(backBtn, c);
        p.add(new JScrollPane(suggestOut), c);

        return p;
    }

    void showSuggest(String key, int type) {
        try {
            cropManagement r =
                    (type == 1) ? cs.byCrop(key) :
                    (type == 2) ? cs.bySeason(key) :
                                   cs.byWeather(key);

            suggestOut.setText(r == null ? "Not Found" : r.pretty());

        } catch (Exception e) {
            suggestOut.setText("Error");
        }
    }

    // ================= SALES =================
    JPanel salesPanel() {

        JPanel p = panel();

        JTextField crop = box(), price = box(), qty = box();

        JButton calcBtn = button("Calculate");
        JButton back = button("Back");

        calcBtn.addActionListener(e -> {
            try {
                int profit = calc.calculate(
                        crop.getText(),
                        Integer.parseInt(price.getText()),
                        Integer.parseInt(qty.getText())
                );

                salesOut.setText("Profit: " + profit);

                FileWriter fw = new FileWriter("sales_record.txt", true);
                fw.write(crop.getText() + " " + profit + "\n");
                fw.close();

            } catch (Exception ex) {}
        });

        back.addActionListener(e -> card.show(main, "home"));

        addForm(p, "Crop", crop);
        addForm(p, "Price", price);
        addForm(p, "Quantity", qty);

        p.add(calcBtn, center());
        p.add(back, center());
        p.add(new JScrollPane(salesOut), center());

        return p;
    }

    // ================= UI HELPERS =================
    JPanel panel() {
        return new JPanel(new GridBagLayout());
    }

    GridBagConstraints center() {
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(10,10,10,10);
        c.gridx = 0;
        c.gridy = GridBagConstraints.RELATIVE;
        c.anchor = GridBagConstraints.CENTER;
        return c;
    }

    JTextField box() {
        JTextField t = new JTextField();
        t.setPreferredSize(new Dimension(200, 28));
        return t;
    }

    JButton button(String text) {
        JButton b = new JButton(text);
        b.setPreferredSize(new Dimension(220, 40));
        return b;
    }

    void addForm(JPanel p, String label, JTextField field) {
        p.add(new JLabel(label), center());
        p.add(field, center());
    }

    public static void main(String[] args) {
        new DashboardApp();
    }
}