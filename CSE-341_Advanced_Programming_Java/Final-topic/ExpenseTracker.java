import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.time.*;
import java.util.*;

class Expense {
    String type;
    String category;
    double amount;
    String date;

    Expense(String t, String c, double a, String d) {
        type = t;
        category = c;
        amount = a;
        date = d;
    }

    public String toString() {
        return type + "," + category + "," + amount + "," + date;
    }
}

// 🌈 Gradient Background
class GradientPanel extends JPanel {
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        int w = getWidth();
        int h = getHeight();

        Color c1 = new Color(20, 20, 30);
        Color c2 = new Color(63, 81, 181);

        GradientPaint gp = new GradientPaint(0, 0, c1, w, h, c2);
        g2d.setPaint(gp);
        g2d.fillRect(0, 0, w, h);
    }
}

public class ExpenseTracker extends JFrame {

    JTextField categoryField, amountField, dateField;
    JComboBox<String> typeBox;
    JTextArea display;

    ArrayList<Expense> list = new ArrayList<>();

    public ExpenseTracker() {

        setTitle("Expense Tracker");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel panel = new GradientPanel();
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 15));

        // ================= INPUTS =================
        typeBox = new JComboBox<>(new String[]{"Income", "Expense"});

        categoryField = new JTextField(10);
        amountField = new JTextField(10);
        dateField = new JTextField(10);

        styleField(categoryField);
        styleField(amountField);
        styleField(dateField);

        // ================= LABELS =================
        JLabel t1 = label("Type:");
        JLabel t2 = label("Category:");
        JLabel t3 = label("Amount:");
        JLabel t4 = label("Date (yyyy-MM-dd):");

        // ================= BUTTONS =================
        JButton addBtn = createButton("Add Today", new Color(46, 204, 113));
        JButton addDateBtn = createButton("Add With Date", new Color(0, 200, 200));
        JButton saveBtn = createButton("Save", new Color(52, 152, 219));
        JButton loadBtn = createButton("Load", new Color(155, 89, 182));
        JButton showBtn = createButton("Show All", new Color(241, 196, 15));
        JButton filterCatBtn = createButton("Filter Category", new Color(230, 126, 34));
        JButton filterRangeBtn = createButton("Filter Range", new Color(0, 150, 200));
        JButton insightBtn = createButton("Insight", new Color(231, 76, 60));

        // ================= DISPLAY =================
        display = new JTextArea(15, 45);
        display.setBackground(new Color(15, 15, 15));
        display.setForeground(Color.WHITE);
        display.setFont(new Font("Consolas", Font.PLAIN, 14));

        JScrollPane sp = new JScrollPane(display);

        // ================= ADD UI =================
        panel.add(t1); panel.add(typeBox);
        panel.add(t2); panel.add(categoryField);
        panel.add(t3); panel.add(amountField);
        panel.add(t4); panel.add(dateField);

        panel.add(addBtn);
        panel.add(addDateBtn);
        panel.add(saveBtn);
        panel.add(loadBtn);
        panel.add(showBtn);
        panel.add(filterCatBtn);
        panel.add(filterRangeBtn);
        panel.add(insightBtn);

        panel.add(sp);

        add(panel);

        // ================= LOGIC =================

        // 🔥 Add Today
        addBtn.addActionListener(e -> {
            try {
                String type = typeBox.getSelectedItem().toString();
                String cat = categoryField.getText();
                double amt = Double.parseDouble(amountField.getText());
                String date = LocalDate.now().toString();

                list.add(new Expense(type, cat, amt, date));

                display.setText("Added (Today): " + date);

            } catch (Exception ex) {
                display.setText("Invalid input!");
            }
        });

        // 🔥 Add With Date
        addDateBtn.addActionListener(e -> {
            try {
                String type = typeBox.getSelectedItem().toString();
                String cat = categoryField.getText();
                double amt = Double.parseDouble(amountField.getText());
                String date = dateField.getText();

                LocalDate.parse(date); // validation

                list.add(new Expense(type, cat, amt, date));

                display.setText("Added with date: " + date);

                dateField.setText("");

            } catch (Exception ex) {
                display.setText("Invalid date format (yyyy-MM-dd)");
            }
        });

        // 💾 Save
        saveBtn.addActionListener(e -> {
            try {
                BufferedWriter bw = new BufferedWriter(new FileWriter("data.txt"));

                for (Expense ex : list) {
                    bw.write(ex.toString());
                    bw.newLine();
                }

                bw.close();
                display.setText("Saved Successfully!");

            } catch (Exception ex) {
                display.setText("Save Error!");
            }
        });

        // 📂 Load
        loadBtn.addActionListener(e -> {
            try {
                list.clear();

                BufferedReader br = new BufferedReader(new FileReader("data.txt"));
                String line;

                while ((line = br.readLine()) != null) {
                    String[] p = line.split(",");
                    list.add(new Expense(
                            p[0],
                            p[1],
                            Double.parseDouble(p[2]),
                            p[3]
                    ));
                }

                br.close();
                display.setText("Loaded Successfully!");

            } catch (Exception ex) {
                display.setText("Load Error!");
            }
        });

        // 📊 Show All
        showBtn.addActionListener(e -> {
            display.setText("");
            double total = 0;

            for (Expense ex : list) {
                display.append(ex.type + " | " + ex.category + " | " +
                        ex.amount + " | " + ex.date + "\n");

                if (ex.type.equals("Expense")) total -= ex.amount;
                else total += ex.amount;
            }

            display.append("\nBalance: " + total);
        });

        // 🔍 Filter Category
        filterCatBtn.addActionListener(e -> {
            String cat = JOptionPane.showInputDialog("Enter Category:");
            display.setText("");

            for (Expense ex : list) {
                if (ex.category.equalsIgnoreCase(cat)) {
                    display.append(ex.type + " | " + ex.category + " | " +
                            ex.amount + " | " + ex.date + "\n");
                }
            }
        });

        // 📅 Filter Range (IMPORTANT FEATURE)
        filterRangeBtn.addActionListener(e -> {
            try {
                String from = JOptionPane.showInputDialog("From Date (yyyy-MM-dd):");
                String to = JOptionPane.showInputDialog("To Date (yyyy-MM-dd):");

                LocalDate fromDate = LocalDate.parse(from);
                LocalDate toDate = LocalDate.parse(to);

                display.setText("");

                for (Expense ex : list) {
                    LocalDate d = LocalDate.parse(ex.date);

                    if ((d.isEqual(fromDate) || d.isAfter(fromDate)) &&
                            (d.isEqual(toDate) || d.isBefore(toDate))) {

                        display.append(ex.type + " | " + ex.category + " | " +
                                ex.amount + " | " + ex.date + "\n");
                    }
                }

            } catch (Exception ex) {
                display.setText("Invalid date format!");
            }
        });

        // 💡 Insight
        insightBtn.addActionListener(e -> {
            HashMap<String, Double> map = new HashMap<>();

            for (Expense ex : list) {
                if (ex.type.equals("Expense")) {
                    map.put(ex.category,
                            map.getOrDefault(ex.category, 0.0) + ex.amount);
                }
            }

            String maxCat = "";
            double maxVal = 0;

            for (String key : map.keySet()) {
                if (map.get(key) > maxVal) {
                    maxVal = map.get(key);
                    maxCat = key;
                }
            }

            display.setText("💡 Top Spending: " + maxCat + " = " + maxVal);
        });

        setVisible(true);
    }

    // ================= HELPERS =================

    private JButton createButton(String text, Color color) {
        JButton btn = new JButton(text);
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Arial", Font.BOLD, 13));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        return btn;
    }

    private void styleField(JTextField f) {
        f.setBackground(new Color(50, 50, 50));
        f.setForeground(Color.WHITE);
    }

    private JLabel label(String text) {
        JLabel l = new JLabel(text);
        l.setForeground(Color.WHITE);
        return l;
    }

    public static void main(String[] args) {
        new ExpenseTracker();
    }
}