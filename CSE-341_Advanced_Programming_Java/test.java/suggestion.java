import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.Scanner;

// ================= LOGIC (UNCHANGED) =================
class cropSuggestion {

    cropManagement suggestOnCropName(String s) throws Exception {
        File f = new File("info.txt");
        Scanner sc = new Scanner(f);

        while (sc.hasNext()) {
            String cropName = sc.next();
            String season = sc.next();
            String weather = sc.next();
            String sunLight = sc.next();
            int water = sc.nextInt();
            int price = sc.nextInt();

            if (s.equals(cropName)) {
                sc.close();
                return new cropManagement(cropName, season, weather, sunLight, water, price);
            }
        }
        sc.close();
        return null;
    }

    cropManagement suggestOnSeason(String s) throws Exception {
        File f = new File("info.txt");
        Scanner sc = new Scanner(f);

        while (sc.hasNext()) {
            String cropName = sc.next();
            String season = sc.next();
            String weather = sc.next();
            String sunLight = sc.next();
            int water = sc.nextInt();
            int price = sc.nextInt();

            if (s.equals(season)) {
                sc.close();
                return new cropManagement(cropName, season, weather, sunLight, water, price);
            }
        }
        sc.close();
        return null;
    }

    cropManagement suggestOnWeather(String s) throws Exception {
        File f = new File("info.txt");
        Scanner sc = new Scanner(f);

        while (sc.hasNext()) {
            String cropName = sc.next();
            String season = sc.next();
            String weather = sc.next();
            String sunLight = sc.next();
            int water = sc.nextInt();
            int price = sc.nextInt();

            if (s.equals(weather)) {
                sc.close();
                return new cropManagement(cropName, season, weather, sunLight, water, price);
            }
        }
        sc.close();
        return null;
    }
}

// ================= GUI =================
public class suggestion extends JFrame {

    CardLayout card = new CardLayout();
    JPanel main = new JPanel(card);

    JButton cropBtn, seasonBtn, weatherBtn;
    JButton backBtn, searchBtn;

    JTextField input;
    JTextArea output;

    cropSuggestion cs = new cropSuggestion();

    public suggestion() {

        setTitle("Crop Suggestion System");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // ================= MENU SCREEN =================
        JPanel menu = new JPanel(new GridBagLayout()); // CENTER FIX
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.insets = new Insets(15, 0, 15, 0);
        gbc.anchor = GridBagConstraints.CENTER;

        JLabel title = new JLabel("Choose Suggestion Type");
        title.setFont(new Font("Arial", Font.BOLD, 28));

        cropBtn = new JButton("Crop Name");
        seasonBtn = new JButton("Season");
        weatherBtn = new JButton("Weather");

        Dimension size = new Dimension(200, 40);
        cropBtn.setPreferredSize(size);
        seasonBtn.setPreferredSize(size);
        weatherBtn.setPreferredSize(size);

        menu.add(title, gbc);
        menu.add(cropBtn, gbc);
        menu.add(seasonBtn, gbc);
        menu.add(weatherBtn, gbc);

        // ================= WORK SCREEN =================
        JPanel work = new JPanel(new BorderLayout());

        JPanel center = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(10, 10, 10, 10);

        input = new JTextField();
        input.setPreferredSize(new Dimension(200, 30));

        searchBtn = new JButton("Search");
        backBtn = new JButton("Back");

        c.gridx = 0;
        c.gridy = 0;
        center.add(new JLabel("Enter Value:"), c);

        c.gridx = 1;
        center.add(input, c);

        c.gridx = 0;
        c.gridy = 1;
        center.add(searchBtn, c);

        c.gridx = 1;
        center.add(backBtn, c);

        output = new JTextArea();
        output.setFont(new Font("Arial", Font.BOLD, 16));

        work.add(center, BorderLayout.CENTER);
        work.add(new JScrollPane(output), BorderLayout.SOUTH);

        main.add(menu, "menu");
        main.add(work, "work");

        add(main);

        // ================= NAVIGATION =================
        cropBtn.addActionListener(e -> {
            card.show(main, "work");
            searchBtn.setActionCommand("crop");
        });

        seasonBtn.addActionListener(e -> {
            card.show(main, "work");
            searchBtn.setActionCommand("season");
        });

        weatherBtn.addActionListener(e -> {
            card.show(main, "work");
            searchBtn.setActionCommand("weather");
        });

        // ================= SEARCH =================
        searchBtn.addActionListener(e -> {
            try {
                String type = searchBtn.getActionCommand();
                String val = input.getText();

                cropManagement r = null;

                if ("crop".equals(type))
                    r = cs.suggestOnCropName(val);
                else if ("season".equals(type))
                    r = cs.suggestOnSeason(val);
                else if ("weather".equals(type))
                    r = cs.suggestOnWeather(val);

                output.setText(r == null ? "Not found" : r.toString());

            } catch (Exception ex) {
                output.setText("Error!");
            }
        });

        // ================= BACK =================
        backBtn.addActionListener(e -> {
            input.setText("");
            output.setText("");
            card.show(main, "menu");
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        new suggestion();
    }
}