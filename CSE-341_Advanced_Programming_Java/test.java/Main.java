import javax.swing.*;
import java.awt.*;
import java.io.*;

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

    public String toStringPrint() {
        return "Crop: " + cropName +
                "\nSeason: " + season +
                "\nWeather: " + weather +
                "\nSunLight: " + sunLight +
                "\nWater: " + water +
                "\nPrice: " + price;
    }
}

// ================= YOUR SUGGESTION CLASS =================
class cropSuggestion {

    cropManagement read(File f, String key, int type) throws Exception {
        Scanner sc = new Scanner(f);

        while (sc.hasNext()) {
            String cropName = sc.next();
            String season = sc.next();
            String weather = sc.next();
            String sunLight = sc.next();
            int water = sc.nextInt();
            int price = sc.nextInt();

            boolean ok = false;

            if (type == 1 && key.equals(cropName)) ok = true;
            else if (type == 2 && key.equals(season)) ok = true;
            else if (type == 3 && key.equals(weather)) ok = true;

            if (ok) {
                sc.close();
                return new cropManagement(cropName, season, weather, sunLight, water, price);
            }
        }

        sc.close();
        return null;
    }

    cropManagement suggestOnCropName(String s) throws Exception {
        return read(new File("info.txt"), s, 1);
    }

    cropManagement suggestOnSeason(String s) throws Exception {
        return read(new File("info.txt"), s, 2);
    }

    cropManagement suggestOnWeather(String s) throws Exception {
        return read(new File("info.txt"), s, 3);
    }
}

// ================= MAIN GUI =================
public class Main extends JFrame {

    JTextField nameField, seasonField, weatherField, sunField, waterField, priceField;
    JTextField suggestField;
    JTextArea outputArea;

    JButton addButton, cropBtn, seasonBtn, weatherBtn;

    Main() {
        setTitle("Crop Management System");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(new BorderLayout());

        // ================= LEFT FORM =================
        JPanel formPanel = new JPanel(new GridLayout(6, 2, 10, 10));

        nameField = new JTextField();
        seasonField = new JTextField();
        weatherField = new JTextField();
        sunField = new JTextField();
        waterField = new JTextField();
        priceField = new JTextField();

        formPanel.add(new JLabel("Crop Name"));
        formPanel.add(nameField);

        formPanel.add(new JLabel("Season"));
        formPanel.add(seasonField);

        formPanel.add(new JLabel("Weather"));
        formPanel.add(weatherField);

        formPanel.add(new JLabel("Sunlight"));
        formPanel.add(sunField);

        formPanel.add(new JLabel("Water"));
        formPanel.add(waterField);

        formPanel.add(new JLabel("Price"));
        formPanel.add(priceField);

        addButton = new JButton("Add Crop");
        formPanel.add(addButton);

        add(formPanel, BorderLayout.WEST);

        // ================= RIGHT PANEL (SUGGESTION) =================
        JPanel rightPanel = new JPanel(new BorderLayout());

        JPanel top = new JPanel(new GridLayout(4, 2, 10, 10));

        suggestField = new JTextField();

        cropBtn = new JButton("Suggest by Crop");
        seasonBtn = new JButton("Suggest by Season");
        weatherBtn = new JButton("Suggest by Weather");

        top.add(new JLabel("Enter Suggestion Key:"));
        top.add(suggestField);

        top.add(cropBtn);
        top.add(seasonBtn);
        top.add(weatherBtn);

        rightPanel.add(top, BorderLayout.NORTH);

        outputArea = new JTextArea();
        outputArea.setFont(new Font("Arial", Font.BOLD, 14));
        rightPanel.add(new JScrollPane(outputArea), BorderLayout.CENTER);

        add(rightPanel, BorderLayout.CENTER);

        // ================= ADD BUTTON =================
        addButton.addActionListener(e -> {
            try {
                cropManagement cObj = new cropManagement(
                        nameField.getText(),
                        seasonField.getText(),
                        weatherField.getText(),
                        sunField.getText(),
                        Integer.parseInt(waterField.getText()),
                        Integer.parseInt(priceField.getText())
                );

                FileWriter fw = new FileWriter("info.txt", true);
                fw.write(cObj.toString());
                fw.close();

                JOptionPane.showMessageDialog(null, "Crop Added!");

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Invalid Input!");
            }
        });

        // ================= SUGGESTION =================
        cropSuggestion cs = new cropSuggestion();

        cropBtn.addActionListener(e -> {
            try {
                cropManagement r = cs.suggestOnCropName(suggestField.getText());
                outputArea.setText(r != null ? r.toStringPrint() : "Not Found");
            } catch (Exception ex) {
                outputArea.setText("Error!");
            }
        });

        seasonBtn.addActionListener(e -> {
            try {
                cropManagement r = cs.suggestOnSeason(suggestField.getText());
                outputArea.setText(r != null ? r.toStringPrint() : "Not Found");
            } catch (Exception ex) {
                outputArea.setText("Error!");
            }
        });

        weatherBtn.addActionListener(e -> {
            try {
                cropManagement r = cs.suggestOnWeather(suggestField.getText());
                outputArea.setText(r != null ? r.toStringPrint() : "Not Found");
            } catch (Exception ex) {
                outputArea.setText("Error!");
            }
        });

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {
        new Main();
    }
}