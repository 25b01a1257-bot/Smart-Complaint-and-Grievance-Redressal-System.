
import javax.swing.*;
import java.awt.*;

public class Login extends JDialog {

    private JTextField usernameField;
    private JPasswordField passwordField;

    private boolean authenticated = false;

    public Login(JFrame parent) {

        super(parent, "Admin Login", true);

        setSize(400, 300);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout(15, 15));

        JLabel heading = new JLabel(
                "ADMIN LOGIN",
                JLabel.CENTER
        );

        heading.setFont(new Font("Arial", Font.BOLD, 24));
        heading.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));

        add(heading, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridLayout(2, 2, 10, 15));
        form.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        usernameField = new JTextField();
        passwordField = new JPasswordField();

        form.add(new JLabel("Username:"));
        form.add(usernameField);

        form.add(new JLabel("Password:"));
        form.add(passwordField);

        add(form, BorderLayout.CENTER);

        JButton loginButton = new JButton("Login");
        JButton cancelButton = new JButton("Cancel");

        JPanel buttons = new JPanel();

        buttons.add(loginButton);
        buttons.add(cancelButton);

        add(buttons, BorderLayout.SOUTH);

        loginButton.addActionListener(e -> checkLogin());

        cancelButton.addActionListener(e -> dispose());
    }

    private void checkLogin() {

        String username = usernameField.getText().trim();

        String password = new String(
                passwordField.getPassword()
        );

        if (username.equals("admin") &&
            password.equals("admin123")) {

            authenticated = true;

            JOptionPane.showMessageDialog(this,
                    "Admin Login Successful!");

            dispose();

        } else {

            JOptionPane.showMessageDialog(this,
                    "Invalid Username or Password!");

            passwordField.setText("");
        }
    }

    public boolean isAuthenticated() {
        return authenticated;
    }
}
