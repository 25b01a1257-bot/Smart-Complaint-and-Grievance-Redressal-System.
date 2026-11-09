
import javax.swing.*;
import java.awt.*;

public class WelcomeScreen extends JFrame {

    private ComplaintManager manager;

    public WelcomeScreen() {

        manager = new ComplaintManager();

        setTitle("Smart Complaint and Grievance Redressal System");
        setSize(700, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(20, 20));

        JLabel heading = new JLabel(
                "SMART COMPLAINT SYSTEM",
                JLabel.CENTER
        );

        heading.setFont(new Font("Arial", Font.BOLD, 27));
        heading.setBorder(BorderFactory.createEmptyBorder(35, 10, 15, 10));

        add(heading, BorderLayout.NORTH);

        JLabel message = new JLabel(
                "Register your complaints and track their resolution",
                JLabel.CENTER
        );

        message.setFont(new Font("Arial", Font.PLAIN, 16));

        JButton registerButton = new JButton("Register Complaint");
        JButton adminButton = new JButton("Admin Login");

        registerButton.setFont(new Font("Arial", Font.BOLD, 18));
        adminButton.setFont(new Font("Arial", Font.BOLD, 18));

        JPanel buttons = new JPanel(new GridLayout(2, 1, 20, 20));
        buttons.setBorder(BorderFactory.createEmptyBorder(30, 100, 50, 100));

        buttons.add(registerButton);
        buttons.add(adminButton);

        JPanel center = new JPanel(new BorderLayout());
        center.add(message, BorderLayout.NORTH);
        center.add(buttons, BorderLayout.CENTER);

        add(center, BorderLayout.CENTER);

        JLabel footer = new JLabel(
                "Educational Institution Grievance Portal",
                JLabel.CENTER
        );

        add(footer, BorderLayout.SOUTH);

        registerButton.addActionListener(e -> {
            new ComplaintForm(this, manager).setVisible(true);
        });

        adminButton.addActionListener(e -> {

            Login login = new Login(this);
            login.setVisible(true);

            if (login.isAuthenticated()) {
                dispose();

                Dashboard dashboard = new Dashboard();
                dashboard.setVisible(true);
            }
        });
    }
}
