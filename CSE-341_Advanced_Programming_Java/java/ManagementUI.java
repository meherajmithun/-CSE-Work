import javax.swing.*;
import java.awt.*;
import java.io.*;
public class ManagementUI extends JPanel {

    public ManagementUI(CardLayout card, JPanel main) {

        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.insets = new Insets(10, 10, 10, 10);

        JTextField crop = new JTextField(12);
        JTextField season = new JTextField(12);
        JTextField weather = new JTextField(12);
        JTextField sun = new JTextField(12);
        JTextField water = new JTextField(12);
        JTextField price = new JTextField(12);

        JButton add = new JButton("Add Crop");
        JButton back = new JButton("Back to Dashboard");

        back.addActionListener(e -> card.show(main, "home"));

        c.gridx = 0; c.gridy = 0;
        add(new JLabel("Crop"), c);
        c.gridx = 1;
        add(crop, c);

        c.gridx = 0; c.gridy++;
        add(new JLabel("Season"), c);
        c.gridx = 1;
        add(season, c);

        c.gridx = 0; c.gridy++;
        add(new JLabel("Weather"), c);
        c.gridx = 1;
        add(weather, c);

        c.gridx = 0; c.gridy++;
        add(new JLabel("Sun"), c);
        c.gridx = 1;
        add(sun, c);

        c.gridx = 0; c.gridy++;
        add(new JLabel("Water"), c);
        c.gridx = 1;
        add(water, c);

        c.gridx = 0; c.gridy++;
        add(new JLabel("Price"), c);
        c.gridx = 1;
        add(price, c);

        c.gridy++;
        add(add, c);

        c.gridy++;
        add.addActionListener(e -> {
            try {
                String cName = crop.getText();
                String s = season.getText();
                String w = weather.getText();
                String su = sun.getText();
                String wa = water.getText();
                String p = price.getText();

                FileWriter fw = new FileWriter("info.txt", true); // true = append mode
                fw.write(cName + "," + s + "," + w + "," + su + "," + wa + "," + p + "\n");
                fw.close();

                JOptionPane.showMessageDialog(this, "Crop Added Successfully!");

                // Clear fields
                crop.setText("");
                season.setText("");
                weather.setText("");
                sun.setText("");
                water.setText("");
                price.setText("");

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error saving data!");
            }
        });
        add(back, c);
    }
}