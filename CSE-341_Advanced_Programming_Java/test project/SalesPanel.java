import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class SalesPanel extends JPanel {

    DefaultTableModel model;

    public SalesPanel() {
        setLayout(new BorderLayout());

        model = new DefaultTableModel(
                new String[]{"Crop", "Cost", "Sell", "Profit"}, 0);

        JTable table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel form = new JPanel();

        JTextField crop = new JTextField(7);
        JTextField cost = new JTextField(7);
        JTextField sell = new JTextField(7);

        JButton add = new JButton("Add Sale");
        JButton print = new JButton("Print Sales");

        form.add(crop);
        form.add(cost);
        form.add(sell);
        form.add(add);
        form.add(print);

        add(form, BorderLayout.NORTH);

        loadData();

        add.addActionListener(e -> {
            try {
                int c = Integer.parseInt(cost.getText());
                int s = Integer.parseInt(sell.getText());

                int profit = s - c;

                String data = crop.getText() + "," + c + "," + s + "," + profit;

                FileUtils.writeLine("data/sales.txt", data);
                model.addRow(data.split(","));

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid input!");
            }
        });

        // 🖨️ PRINT SALES
        print.addActionListener(e -> {

            List<String> list = FileUtils.readAll("data/sales.txt");

            StringBuilder sb = new StringBuilder();
            sb.append("💰 SALES REPORT\n\n");

            for (String s : list) {
                String[] d = s.split(",");

                sb.append("Crop   : ").append(d[0]).append("\n");
                sb.append("Cost   : ").append(d[1]).append("\n");
                sb.append("Sell   : ").append(d[2]).append("\n");
                sb.append("Profit : ").append(d[3]).append("\n");
                sb.append("----------------------\n");
            }

            JOptionPane.showMessageDialog(this, sb.toString());
        });
    }

    void loadData() {
        List<String> list = FileUtils.readAll("data/sales.txt");
        for (String s : list) {
            model.addRow(s.split(","));
        }
    }
}