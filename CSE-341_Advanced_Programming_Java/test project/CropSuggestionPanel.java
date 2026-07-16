import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class CropSuggestionPanel extends JPanel {

    DefaultTableModel model;

    public CropSuggestionPanel() {
        setLayout(new BorderLayout());

        model = new DefaultTableModel(
                new String[]{"Name", "Soil", "Water", "Weather", "Place"}, 0);

        JTable table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel top = new JPanel();

        JTextField soil = new JTextField(8);
        JTextField water = new JTextField(8);

        JButton search = new JButton("Search");

        top.add(new JLabel("Soil"));
        top.add(soil);
        top.add(new JLabel("Water"));
        top.add(water);
        top.add(search);

        add(top, BorderLayout.NORTH);

        search.addActionListener(e -> {
            model.setRowCount(0);

            List<String> crops = FileUtils.readAll("data/crops.txt");

            for (String c : crops) {
                String[] s = c.split(",");

                if (s[1].equalsIgnoreCase(soil.getText()) &&
                    s[2].equalsIgnoreCase(water.getText())) {

                    model.addRow(s);
                }
            }
        });
    }
}