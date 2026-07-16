import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.Scanner;

// ================= LOGIC CLASS (UNCHANGED) =================
class calculation {

    int calculate(String crop, int sale_price, int Quantity) throws Exception {

        File f = new File("info.txt");
        Scanner sc = new Scanner(f);

        while (sc.hasNext()) {

            String cropName = sc.next();
            String season = sc.next();
            String weather = sc.next();
            String sunLight = sc.next();
            int water = sc.nextInt();
            int purchase_price = sc.nextInt();

            if (crop.equals(cropName)) {

                sc.close();

                int purchase_cost = purchase_price * Quantity;
                int sale_cost = sale_price * Quantity;

                return sale_cost - purchase_cost;
            }
        }

        sc.close();
        return 0;
    }
}

// ================= GUI CLASS =================
public class Sales extends JFrame {

    JTextField cropField, priceField, qtyField;
    JTextArea output;

    JButton calcBtn, backBtn;

    JPanel mainPanel = new JPanel(new CardLayout());
    CardLayout card = (CardLayout) mainPanel.getLayout();

    calculation c = new calculation();

    public Sales() {

        setTitle("Sales & Profit System");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // ================= MENU SCREEN =================
        JPanel menu = new JPanel(new GridBagLayout());
        GridBagConstraints g = new GridBagConstraints();
        g.gridx = 0;
        g.gridy = GridBagConstraints.RELATIVE;
        g.insets = new Insets(15, 0, 15, 0);

        JLabel title = new JLabel("Sales Calculation System");
        title.setFont(new Font("Arial", Font.BOLD, 28));

        JButton startBtn = new JButton("Start Calculation");
        startBtn.setPreferredSize(new Dimension(220, 45));

        menu.add(title, g);
        menu.add(startBtn, g);

        // ================= WORK SCREEN =================
        JPanel work = new JPanel(new GridBagLayout());
        GridBagConstraints cst = new GridBagConstraints();
        cst.insets = new Insets(10, 10, 10, 10);

        cropField = new JTextField();
        cropField.setPreferredSize(new Dimension(200, 30));

        priceField = new JTextField();
        priceField.setPreferredSize(new Dimension(200, 30));

        qtyField = new JTextField();
        qtyField.setPreferredSize(new Dimension(200, 30));

        calcBtn = new JButton("Calculate Profit");
        backBtn = new JButton("Back");

        output = new JTextArea(10, 30);
        output.setFont(new Font("Arial", Font.BOLD, 16));

        // row 0
        cst.gridx = 0; cst.gridy = 0;
        work.add(new JLabel("Crop Name:"), cst);
        cst.gridx = 1;
        work.add(cropField, cst);

        // row 1
        cst.gridx = 0; cst.gridy = 1;
        work.add(new JLabel("Sale Price:"), cst);
        cst.gridx = 1;
        work.add(priceField, cst);

        // row 2
        cst.gridx = 0; cst.gridy = 2;
        work.add(new JLabel("Quantity:"), cst);
        cst.gridx = 1;
        work.add(qtyField, cst);

        // buttons row
        cst.gridx = 0; cst.gridy = 3;
        work.add(calcBtn, cst);
        cst.gridx = 1;
        work.add(backBtn, cst);

        // output
        cst.gridx = 0; cst.gridy = 4;
        cst.gridwidth = 2;
        work.add(new JScrollPane(output), cst);

        // add screens
        mainPanel.add(menu, "menu");
        mainPanel.add(work, "work");

        add(mainPanel);

        // ================= NAVIGATION =================
        startBtn.addActionListener(e -> card.show(mainPanel, "work"));

        backBtn.addActionListener(e -> {
            cropField.setText("");
            priceField.setText("");
            qtyField.setText("");
            output.setText("");
            card.show(mainPanel, "menu");
        });

        // ================= CALCULATION BUTTON =================
        calcBtn.addActionListener(e -> {
            try {

                String crop = cropField.getText();
                int price = Integer.parseInt(priceField.getText());
                int qty = Integer.parseInt(qtyField.getText());

                int profit = c.calculate(crop, price, qty);

                output.setText("Profit: " + profit);

                // save record
                FileWriter fw = new FileWriter("sales_record.txt", true);
                fw.write(crop + " " + profit + "\n");
                fw.close();

            } catch (Exception ex) {
                output.setText("Invalid Input!");
            }
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        new Sales();
    }
}