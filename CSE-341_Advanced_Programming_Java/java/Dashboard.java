import javax.swing.*;
import java.awt.*;

public class Dashboard extends JFrame {

    CardLayout card = new CardLayout();
    JPanel main = new JPanel(card);

    public Dashboard() {

        setTitle("Smart Agriculture Dashboard");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // ================= PANELS =================
        main.add(homePanel(), "home");

        // These must be separate files
        main.add(new ManagementUI(card, main), "management");
        main.add(new SuggestionUI(card, main), "suggestion");
        main.add(new SalesUI(card, main), "sales");

        add(main);

        card.show(main, "home");
        setVisible(true);
    }

    // ================= HOME PANEL =================
    JPanel homePanel() {

        JPanel p = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.insets = new Insets(15, 15, 15, 15);
        c.anchor = GridBagConstraints.CENTER;

        JLabel title = new JLabel("SMART AGRICULTURE SYSTEM");
        title.setFont(new Font("Arial", Font.BOLD, 30));

        JButton mBtn = button("Crop Management");
        JButton sBtn = button("Crop Suggestion");
        JButton saleBtn = button("Sales System");

        JButton exitBtn = button("Exit");

        // ================= NAVIGATION =================
        mBtn.addActionListener(e -> card.show(main, "management"));
        sBtn.addActionListener(e -> card.show(main, "suggestion"));
        saleBtn.addActionListener(e -> card.show(main, "sales"));

        exitBtn.addActionListener(e -> System.exit(0));

        // ================= LAYOUT =================
        c.gridx = 0;
        c.gridy = 0;
        p.add(title, c);

        c.gridy++;
        p.add(mBtn, c);

        c.gridy++;
        p.add(sBtn, c);

        c.gridy++;
        p.add(saleBtn, c);

        c.gridy++;
        p.add(exitBtn, c);

        return p;
    }

    // ================= BUTTON STYLE =================
    JButton button(String text) {

        JButton b = new JButton(text);
        b.setPreferredSize(new Dimension(250, 45));
        b.setFont(new Font("Arial", Font.BOLD, 15));

        return b;
    }

    // ================= MAIN =================
    public static void main(String[] args) {
        new Dashboard();
    }
}