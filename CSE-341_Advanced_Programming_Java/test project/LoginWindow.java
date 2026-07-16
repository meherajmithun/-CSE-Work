import javax.swing.*;
import java.awt.*;
import java.util.List;

public class LoginWindow extends JPanel {

    JTextField userField;
    JPasswordField passField;
    MainFrame frame;

    public LoginWindow(MainFrame frame) {
        this.frame = frame;

        setLayout(null);

        JLabel title = new JLabel("SMART AGRICULTURAL SYSTEM");
        title.setFont(new Font("Arial", Font.BOLD, 30));
        title.setBounds(400, 100, 600, 40);
        add(title);

        userField = new JTextField();
        userField.setBounds(500, 250, 200, 30);
        add(userField);

        passField = new JPasswordField();
        passField.setBounds(500, 300, 200, 30);
        add(passField);

        JButton login = new JButton("Login");
        login.setBounds(500, 360, 90, 40);
        add(login);

        JButton register = new JButton("Register");
        register.setBounds(610, 360, 90, 40);
        add(register);

        login.addActionListener(e -> loginUser());
        register.addActionListener(e -> registerUser());
    }

    void loginUser() {
        String u = userField.getText();
        String p = new String(passField.getPassword());

        List<String> users = FileUtils.readAll("data/users.txt");

        for (String line : users) {
            String[] s = line.split(",");
            if (s.length == 2 && s[0].equals(u) && s[1].equals(p)) {
                frame.showDashboard(u);
                return;
            }
        }

        JOptionPane.showMessageDialog(this, "Invalid Login!");
    }

    void registerUser() {
        String u = userField.getText();
        String p = new String(passField.getPassword());

        FileUtils.writeLine("data/users.txt", u + "," + p);
        JOptionPane.showMessageDialog(this, "Registered!");
    }
}