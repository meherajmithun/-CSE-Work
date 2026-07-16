import javax.swing.*;
import java.awt.*;

public class DashboardPanel extends JPanel {

    public DashboardPanel() {
        setLayout(null);

        JLabel title = new JLabel("🌿 SMART AGRICULTURAL SYSTEM");
        title.setFont(new Font("Arial", Font.BOLD, 30));
        title.setBounds(400, 100, 700, 40);
        add(title);

        JLabel msg = new JLabel("Welcome to your Smart Farming Dashboard");
        msg.setBounds(450, 180, 400, 30);
        add(msg);
    }
}