
import javax.swing.*;
import java.awt.*;

public class Dashboard extends JFrame {

    private ComplaintManager manager;

    private JLabel totalLabel;
    private JLabel submittedLabel;
    private JLabel progressLabel;
    private JLabel resolvedLabel;

    private JPanel contentPanel;
    private CardLayout cardLayout;

    private Charts charts;

    public Dashboard() {

        manager = new ComplaintManager();

        setTitle("Smart Complaint and Grievance Redressal System");
        setSize(1200, 750);
        setMinimumSize(new Dimension(950, 600));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLayout(new BorderLayout());

        createSidebar();
        createContent();

        refreshStatistics();
    }

    private void createSidebar() {

        JPanel sidebar = new JPanel();
        sidebar.setBackground(new Color(25, 42, 86));
        sidebar.setPreferredSize(new Dimension(230, 0));
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("<html>SMART<br>COMPLAINT<br>SYSTEM</html>");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 23));
        title.setBorder(BorderFactory.createEmptyBorder(30, 20, 35, 10));

        sidebar.add(title);

        addMenuButton(sidebar, "Dashboard", "home");
        addMenuButton(sidebar, "Analytics", "analytics");

        JButton register = createMenuButton("Register Complaint");
        register.addActionListener(e -> {
            new ComplaintForm(this, manager).setVisible(true);
            refreshStatistics();
        });

        sidebar.add(register);

        JButton view = createMenuButton("Manage Complaints");
        view.addActionListener(e ->
                new ComplaintTable(this, manager).setVisible(true));

        sidebar.add(view);

        JButton reports = createMenuButton("Generate PDF Report");
        reports.addActionListener(e ->
                ReportGenerator.generate(manager));

        sidebar.add(reports);

        JButton export = createMenuButton("Export CSV");
        export.addActionListener(e ->
                CSVExporter.export(manager, this));

        sidebar.add(export);

        sidebar.add(Box.createVerticalGlue());

        JButton logout = createMenuButton("Logout");
        logout.addActionListener(e -> {

            int answer = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to logout?",
                    "Logout",
                    JOptionPane.YES_NO_OPTION
            );

            if (answer == JOptionPane.YES_OPTION) {
                dispose();

                WelcomeScreen welcome = new WelcomeScreen();
                welcome.setVisible(true);
            }
        });

        sidebar.add(logout);

        sidebar.add(Box.createVerticalStrut(20));

        add(sidebar, BorderLayout.WEST);
    }

    private JButton createMenuButton(String text) {

        JButton button = new JButton(text);

        button.setMaximumSize(new Dimension(220, 48));
        button.setPreferredSize(new Dimension(220, 48));

        button.setAlignmentX(Component.CENTER_ALIGNMENT);

        button.setBackground(new Color(25, 42, 86));
        button.setForeground(Color.WHITE);

        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        return button;
    }

    private void addMenuButton(JPanel sidebar,
                               String text, String page) {

        JButton button = createMenuButton(text);

        button.addActionListener(e -> {
            cardLayout.show(contentPanel, page);

            if (page.equals("analytics")) {
                charts.repaint();
            }

            refreshStatistics();
        });

        sidebar.add(button);
    }

    private void createContent() {

        cardLayout = new CardLayout();

        contentPanel = new JPanel(cardLayout);
        contentPanel.setBackground(new Color(245, 247, 250));

        contentPanel.add(createHomePanel(), "home");

        charts = new Charts(manager);
        contentPanel.add(charts, "analytics");

        add(contentPanel, BorderLayout.CENTER);
    }

    private JPanel createHomePanel() {

        JPanel home = new JPanel(new BorderLayout(15, 15));
        home.setBackground(new Color(245, 247, 250));

        JLabel heading = new JLabel("Admin Dashboard");
        heading.setFont(new Font("Arial", Font.BOLD, 30));
        heading.setBorder(BorderFactory.createEmptyBorder(25, 25, 10, 10));

        home.add(heading, BorderLayout.NORTH);

        JPanel center = new JPanel(new BorderLayout(15, 15));
        center.setBackground(new Color(245, 247, 250));
        center.setBorder(BorderFactory.createEmptyBorder(15, 25, 25, 25));

        JPanel cards = new JPanel(new GridLayout(1, 4, 15, 15));
        cards.setBackground(new Color(245, 247, 250));

        totalLabel = new JLabel("0");
        submittedLabel = new JLabel("0");
        progressLabel = new JLabel("0");
        resolvedLabel = new JLabel("0");

        cards.add(createCard("Total Complaints",
                totalLabel, new Color(52, 152, 219)));

        cards.add(createCard("Submitted",
                submittedLabel, new Color(155, 89, 182)));

        cards.add(createCard("In Progress",
                progressLabel, new Color(243, 156, 18)));

        cards.add(createCard("Resolved",
                resolvedLabel, new Color(39, 174, 96)));

        center.add(cards, BorderLayout.NORTH);

        JPanel welcome = new JPanel(new GridLayout(2, 1, 5, 5));
        welcome.setBackground(Color.WHITE);
        welcome.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220)),
                BorderFactory.createEmptyBorder(30, 30, 30, 30)
        ));

        JLabel message = new JLabel(
                "Welcome to Smart Complaint Management System",
                JLabel.CENTER
        );

        message.setFont(new Font("Arial", Font.BOLD, 23));

        JLabel description = new JLabel(
                "Register, track, manage and resolve complaints easily.",
                JLabel.CENTER
        );

        description.setFont(new Font("Arial", Font.PLAIN, 17));

        welcome.add(message);
        welcome.add(description);

        center.add(welcome, BorderLayout.CENTER);

        JButton refresh = new JButton("Refresh Statistics");

        refresh.addActionListener(e -> refreshStatistics());

        JPanel bottom = new JPanel();
        bottom.setBackground(new Color(245, 247, 250));
        bottom.add(refresh);

        center.add(bottom, BorderLayout.SOUTH);

        home.add(center, BorderLayout.CENTER);

        return home;
    }

    private JPanel createCard(String title,
                              JLabel value, Color color) {

        JPanel card = new JPanel(new BorderLayout(5, 15));

        card.setBackground(color);

        card.setBorder(BorderFactory.createEmptyBorder(
                25, 10, 25, 10));

        JLabel heading = new JLabel(title, JLabel.CENTER);
        heading.setForeground(Color.WHITE);
        heading.setFont(new Font("Arial", Font.BOLD, 15));

        value.setForeground(Color.WHITE);
        value.setFont(new Font("Arial", Font.BOLD, 36));
        value.setHorizontalAlignment(JLabel.CENTER);

        card.add(heading, BorderLayout.NORTH);
        card.add(value, BorderLayout.CENTER);

        return card;
    }

    private void refreshStatistics() {

        totalLabel.setText(
                String.valueOf(manager.getAllComplaints().size()));

        submittedLabel.setText(
                String.valueOf(manager.getCount("Submitted")));

        progressLabel.setText(
                String.valueOf(manager.getCount("In Progress")));

        resolvedLabel.setText(
                String.valueOf(manager.getCount("Resolved")));

        if (charts != null) {
            charts.repaint();
        }
    }
}
