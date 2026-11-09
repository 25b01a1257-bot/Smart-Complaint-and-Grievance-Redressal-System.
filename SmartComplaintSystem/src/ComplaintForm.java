
import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

public class ComplaintForm extends JDialog {

    private ComplaintManager manager;

    private JTextField nameField;
    private JTextField emailField;
    private JComboBox<String> categoryBox;
    private JComboBox<String> priorityBox;
    private JTextArea descriptionArea;

    public ComplaintForm(JFrame parent, ComplaintManager manager) {

        super(parent, "Register Complaint", true);
        this.manager = manager;

        setSize(500, 520);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout(10, 10));

        JLabel heading = new JLabel("REGISTER NEW COMPLAINT",
                JLabel.CENTER);
        heading.setFont(new Font("Arial", Font.BOLD, 22));
        heading.setBorder(BorderFactory.createEmptyBorder(15, 5, 15, 5));
        add(heading, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridLayout(5, 2, 10, 15));
        form.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        nameField = new JTextField();
        emailField = new JTextField();

        categoryBox = new JComboBox<>(new String[]{
            "Infrastructure",
            "Academics",
            "Facilities",
            "Administration"
        });

        priorityBox = new JComboBox<>(new String[]{
            "Low", "Medium", "High"
        });

        form.add(new JLabel("Complainant Name:"));
        form.add(nameField);

        form.add(new JLabel("Email:"));
        form.add(emailField);

        form.add(new JLabel("Category:"));
        form.add(categoryBox);

        form.add(new JLabel("Priority:"));
        form.add(priorityBox);

        form.add(new JLabel("Description:"));

        descriptionArea = new JTextArea(4, 20);
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);

        form.add(new JScrollPane(descriptionArea));

        add(form, BorderLayout.CENTER);

        JButton submitButton = new JButton("Submit Complaint");
        JButton cancelButton = new JButton("Cancel");

        JPanel buttons = new JPanel();
        buttons.add(submitButton);
        buttons.add(cancelButton);

        add(buttons, BorderLayout.SOUTH);

        submitButton.addActionListener(e -> submitComplaint());
        cancelButton.addActionListener(e -> dispose());
    }

    private void submitComplaint() {

        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        String category = (String) categoryBox.getSelectedItem();
        String priority = (String) priorityBox.getSelectedItem();
        String description = descriptionArea.getText().trim();

       
if (name.isEmpty() || email.isEmpty() || description.isEmpty()) {

    JOptionPane.showMessageDialog(this,
            "Please fill all the fields!");
    return;
}

if (!name.matches("[A-Za-z ]{2,50}")) {

    JOptionPane.showMessageDialog(this,
            "Enter a valid name!");
    return;
}

if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {

    JOptionPane.showMessageDialog(this,
            "Enter a valid email address!");
    return;
}

if (description.length() < 10) {

    JOptionPane.showMessageDialog(this,
            "Description must contain at least 10 characters!");
    return;
}

if (manager.isDuplicate(email, category, description)) {

    JOptionPane.showMessageDialog(this,
            "A similar complaint is already registered!");
    return;
}


        String id = manager.generateId();
        String department = manager.getDepartment(category);

        Complaint complaint = new Complaint(
                id,
                name,
                email,
                category,
                department,
                description,
                priority,
                LocalDate.now().toString()
        );

        manager.registerComplaint(complaint);

        JOptionPane.showMessageDialog(this,
                "Complaint Registered Successfully!\n\n" +
                "Complaint ID: " + id +
                "\nDepartment: " + department +
                "\nStatus: Submitted");

        dispose();
    }
}
