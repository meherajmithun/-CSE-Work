import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class PlantWateringBuddy extends JFrame {
    // UI Components
    private JLabel moistureLabel, lightLabel, healthLabel, statusLabel;
    private JProgressBar moistureBar, lightBar, healthBar;
    private JButton waterButton, logPhotoButton;
    private JTextArea logArea;
    private JPanel mainPanel;

    // Plant Data
    private int moisture = 40; // 0-100%
    private int light = 60;    // 0-100%
    private int health = 75;   // 0-100
    private List<String> photoTimeline = new ArrayList<>();
    private Random random = new Random();
    private Timer sensorTimer;

    public PlantWateringBuddy() {
        setTitle("🌱 Smart Plant Watering Buddy");
        setSize(500, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        initUI();
        startSensors();
    }

    private void initUI() {
        mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Header
        JLabel title = new JLabel("🌿 YOUR PLANT STATUS", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setForeground(new Color(34, 139, 34));

        // Panels
        JPanel sensorPanel = createSensorDashboard();
        JPanel controlPanel = createControlPanel();
        JPanel photoPanel = createPhotoTimeline();

        // Log
        logArea = new JTextArea(8, 30);
        logArea.setEditable(false);
        logArea.setFont(new Font("Consolas", Font.PLAIN, 12));
        JScrollPane logScroll = new JScrollPane(logArea);
        logScroll.setBorder(BorderFactory.createTitledBorder("📋 Activity Log"));

        mainPanel.add(title, BorderLayout.NORTH);
        mainPanel.add(sensorPanel, BorderLayout.CENTER);
        mainPanel.add(controlPanel, BorderLayout.SOUTH);

        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, photoPanel, logScroll);
        splitPane.setResizeWeight(0.6);

        add(mainPanel, BorderLayout.CENTER);
        add(splitPane, BorderLayout.SOUTH);
    }

    private JPanel createSensorDashboard() {
        JPanel panel = new JPanel(new GridLayout(4, 1, 10, 10));
        panel.setBorder(BorderFactory.createTitledBorder("📊 SENSORS"));

        moistureLabel = new JLabel("💧 Soil Moisture: 40%");
        moistureBar = new JProgressBar(0, 100);
        moistureBar.setValue(40);
        moistureBar.setStringPainted(true);

        lightLabel = new JLabel("☀️ Light: 60%");
        lightBar = new JProgressBar(0, 100);
        lightBar.setValue(60);
        lightBar.setStringPainted(true);

        healthLabel = new JLabel("❤️ Health: 75/100");
        healthBar = new JProgressBar(0, 100);
        healthBar.setValue(75);
        healthBar.setStringPainted(true);

        statusLabel = new JLabel("⏳ Monitoring...", SwingConstants.CENTER);
        statusLabel.setFont(new Font("Arial", Font.BOLD, 16));

        panel.add(createSensorRow("💧 Moisture", moistureLabel, moistureBar));
        panel.add(createSensorRow("☀️ Light", lightLabel, lightBar));
        panel.add(createSensorRow("❤️ Health", healthLabel, healthBar));
        panel.add(statusLabel);

        return panel;
    }

    private JPanel createSensorRow(String title, JLabel label, JProgressBar bar) {
        JPanel row = new JPanel(new BorderLayout());
        row.setBorder(BorderFactory.createEtchedBorder());
        row.add(new JLabel(title), BorderLayout.NORTH);
        row.add(label, BorderLayout.CENTER);
        row.add(bar, BorderLayout.SOUTH);
        return row;
    }

    private JPanel createControlPanel() {
        JPanel panel = new JPanel(new FlowLayout());

        waterButton = new JButton("💦 WATER PLANT");
        waterButton.setFont(new Font("Arial", Font.BOLD, 16));
        waterButton.setBackground(new Color(52, 152, 219));
        waterButton.setForeground(Color.WHITE);
        waterButton.addActionListener(e -> waterPlant());

        logPhotoButton = new JButton("📸 Log Growth Photo");
        logPhotoButton.addActionListener(e -> logPhoto());

        panel.add(waterButton);
        panel.add(logPhotoButton);

        return panel;
    }

    private JPanel createPhotoTimeline() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("📷 Growth Timeline"));

        JLabel photoLabel = new JLabel(createDummyPlantImage(), JLabel.CENTER);
        photoLabel.setPreferredSize(new Dimension(300, 200));

        panel.add(photoLabel, BorderLayout.CENTER);
        return panel;
    }

    private void startSensors() {
        sensorTimer = new Timer(3000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                simulateSensors();
                updateUI();
                checkStatus();
            }
        });
        sensorTimer.start();
        log("🌱 Plant monitoring started!");
    }

    private void simulateSensors() {
        if (moisture > 10) moisture -= random.nextInt(5) + 1;

        light += random.nextInt(21) - 10;
        light = Math.max(0, Math.min(100, light));

        health = (int) (0.6 * moisture + 0.4 * light * 0.8);
        health = Math.max(0, Math.min(100, health));
    }

    private void waterPlant() {
        moisture = 95;
        waterButton.setBackground(new Color(52, 152, 219)); // reset color
        log("💦 Plant watered!");
        updateUI();
    }

    private void logPhoto() {
        String timestamp = new SimpleDateFormat("HH:mm:ss").format(new Date());
        String entry = "[" + timestamp + "] 📸 Growth captured (Health: " + health + ")";
        photoTimeline.add(entry);
        log(entry);
    }

    private void updateUI() {
        moistureLabel.setText("💧 Soil Moisture: " + moisture + "%");
        moistureBar.setValue(moisture);

        lightLabel.setText("☀️ Light: " + light + "%");
        lightBar.setValue(light);

        healthLabel.setText("❤️ Health: " + health + "/100");
        healthBar.setValue(health);
    }

    private void checkStatus() {
        if (moisture < 30) {
            statusLabel.setText("🚨 DRY! Water now!");
            statusLabel.setForeground(Color.RED);
            waterButton.setBackground(Color.RED);
        } else if (health < 50) {
            statusLabel.setText("⚠️ Plant stressed!");
            statusLabel.setForeground(Color.ORANGE);
        } else {
            statusLabel.setText("✅ Plant happy!");
            statusLabel.setForeground(new Color(34, 139, 34));
        }
    }

    private void log(String msg) {
        String time = new SimpleDateFormat("HH:mm:ss").format(new Date());
        logArea.append("[" + time + "] " + msg + "\n");
    }

    private ImageIcon createDummyPlantImage() {
        BufferedImage img = new BufferedImage(300, 200, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = img.createGraphics();

        g.setColor(new Color(144, 238, 144));
        g.fillRect(0, 0, 300, 200);

        g.setColor(new Color(139, 69, 19));
        g.fillRect(120, 140, 60, 40);

        g.setColor(Color.GREEN);
        g.fillOval(130, 80, 40, 60);

        g.dispose();
        return new ImageIcon(img);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new PlantWateringBuddy().setVisible(true);
        });
    }
}