import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    JPanel contentPanel;

    public MainFrame() {
        setTitle("Smart Agricultural System");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setLayout(new BorderLayout());

        // Sidebar
        add(new Sidebar(this), BorderLayout.WEST);

        // Content area
        contentPanel = new JPanel(new BorderLayout());
        add(contentPanel, BorderLayout.CENTER);

        showPanel(new DashboardPanel());

        setVisible(true);
    }

    public void showPanel(JPanel panel) {
        contentPanel.removeAll();
        contentPanel.add(panel);
        contentPanel.revalidate();
        contentPanel.repaint();
    }
}