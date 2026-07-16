import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.Scanner;

public class SuggestionUI extends JPanel {

    CardLayout card;
    JPanel main;

    JTextField input;
    JTextArea output;

    String mode = "";

    public SuggestionUI(CardLayout card, JPanel main) {

        this.card = card;
        this.main = main;

        setLayout(new BorderLayout());

        JPanel container = new JPanel(new CardLayout());
        CardLayout inner = new CardLayout();

        container.setLayout(inner);

        container.add(menuPanel(inner, container), "menu");
        container.add(inputPanel(inner, container), "input");

        add(container);

        inner.show(container, "menu");
    }

    // ================= MENU =================
    JPanel menuPanel(CardLayout inner, JPanel container) {

        JPanel p = new JPanel(new GridBagLayout());
        GridBagConstraints c = center();

        JLabel title = new JLabel("CROP SUGGESTION");
        title.setFont(new Font("Arial", Font.BOLD, 28));

        JButton crop = button("By Crop");
        JButton season = button("By Season");
        JButton weather = button("By Weather");
        JButton back = button("Back to Dashboard");

        crop.addActionListener(e -> {
            mode = "crop";
            inner.show(container, "input");
        });

        season.addActionListener(e -> {
            mode = "season";
            inner.show(container, "input");
        });

        weather.addActionListener(e -> {
            mode = "weather";
            inner.show(container, "input");
        });

        back.addActionListener(e -> card.show(main, "home"));

        p.add(title, c);
        p.add(crop, c);
        p.add(season, c);
        p.add(weather, c);
        p.add(back, c);

        return p;
    }

    // ================= INPUT =================
    JPanel inputPanel(CardLayout inner, JPanel container) {

        JPanel p = new JPanel(new GridBagLayout());
        GridBagConstraints c = center();

        input = new JTextField();
        input.setPreferredSize(new Dimension(200, 30));
        input.setFont(new Font("Arial", Font.BOLD, 14));

        output = new JTextArea(10, 30);
        output.setFont(new Font("Arial", Font.BOLD, 14));

        JButton search = button("Search");
        JButton back = button("Back");

        search.addActionListener(e -> searchData());
        back.addActionListener(e -> inner.show(container, "menu"));

        p.add(new JLabel("Enter Value:"), c);
        p.add(input, c);
        p.add(search, c);
        p.add(back, c);
        p.add(new JScrollPane(output), c);

        return p;
    }

    void searchData() {

        try {
            Scanner sc = new Scanner(new File("info.txt"));
            String key = input.getText();

            while (sc.hasNext()) {

                String crop = sc.next();
                String season = sc.next();
                String weather = sc.next();
                String sun = sc.next();
                int water = sc.nextInt();
                int price = sc.nextInt();

                boolean ok =
                        (mode.equals("crop") && crop.equalsIgnoreCase(key)) ||
                        (mode.equals("season") && season.equalsIgnoreCase(key)) ||
                        (mode.equals("weather") && weather.equalsIgnoreCase(key));

                if (ok) {
                    output.setText(
                            "Crop: " + crop + "\n" +
                            "Season: " + season + "\n" +
                            "Weather: " + weather + "\n" +
                            "Sun: " + sun + "\n" +
                            "Water: " + water + "\n" +
                            "Price: " + price
                    );
                    sc.close();
                    return;
                }
            }

            sc.close();
            output.setText("Not Found");

        } catch (Exception e) {
            output.setText("Error");
        }
    }

    // ================= UI =================
    JButton button(String t) {
        JButton b = new JButton(t);
        b.setPreferredSize(new Dimension(200, 40));
        b.setFont(new Font("Arial", Font.BOLD, 14));
        return b;
    }

    GridBagConstraints center() {
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(10, 10, 10, 10);
        c.gridx = 0;
        c.gridy = GridBagConstraints.RELATIVE;
        c.anchor = GridBagConstraints.CENTER;
        return c;
    }
}