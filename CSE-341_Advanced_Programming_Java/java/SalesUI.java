import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.Scanner;

public class SalesUI extends JPanel {

    JTextField crop, sale, qty;
    JTextArea output;

    public SalesUI(CardLayout card, JPanel main) {

        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.insets = new Insets(8, 8, 8, 8);
        c.anchor = GridBagConstraints.CENTER;

        crop = box();
        sale = box();
        qty = box();

        JButton calc = button("Calculate");
        JButton back = button("Back to Dashboard");

        output = new JTextArea(5, 25);
        output.setFont(new Font("Arial", Font.PLAIN, 13));
        output.setEditable(false);

        calc.addActionListener(e -> calculate());

        // ✅ NO NEW WINDOW — just switch panel
        back.addActionListener(e -> card.show(main, "home"));

        int y = 0;

        addRow(this, "Crop", crop, c, y++);
        addRow(this, "Sale Price", sale, c, y++);
        addRow(this, "Quantity", qty, c, y++);

        c.gridx = 0;
        c.gridy = y;
        add(calc, c);

        c.gridx = 1;
        add(back, c);

        y++;
        c.gridx = 0;
        c.gridy = y;
        c.gridwidth = 2;
        add(new JScrollPane(output), c);
    }

    // ================= CALCULATION =================
    void calculate() {
        try {
            Scanner sc = new Scanner(new File("info.txt"));

            String cropName = crop.getText().trim();
            int salePrice = Integer.parseInt(sale.getText().trim());
            int quantity = Integer.parseInt(qty.getText().trim());

            boolean found = false;

            while (sc.hasNext()) {

                String c = sc.next();
                String season = sc.next();
                String weather = sc.next();
                String sun = sc.next();
                int water = sc.nextInt();
                int purchase = sc.nextInt();

                if (c.equalsIgnoreCase(cropName)) {

                    found = true;

                    int result = (salePrice * quantity) - (purchase * quantity);

                    if (result >= 0) {
                        output.setText("PROFIT: " + result);
                    } else {
                        output.setText("LOSS: " + Math.abs(result));
                    }

                    FileWriter fw = new FileWriter("sales.txt", true);
                    fw.write(c + " " + salePrice + " " + quantity + " " + result + "\n");
                    fw.close();

                    break;
                }
            }

            sc.close();

            if (!found) output.setText("Crop Not Found");

        } catch (Exception e) {
            output.setText("Error");
        }
    }

    // ================= HELPERS =================
    JTextField box() {
        JTextField t = new JTextField(12);
        t.setFont(new Font("Arial", Font.BOLD, 13));
        return t;
    }

    JButton button(String t) {
        JButton b = new JButton(t);
        b.setPreferredSize(new Dimension(150, 30));
        return b;
    }

    void addRow(JPanel p, String label, JTextField f, GridBagConstraints c, int y) {
        c.gridx = 0;
        c.gridy = y;
        p.add(new JLabel(label), c);

        c.gridx = 1;
        p.add(f, c);
    }
}