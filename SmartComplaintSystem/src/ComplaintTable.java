
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ComplaintTable extends JDialog {

    private ComplaintManager manager;
    private DefaultTableModel model;
    private JTable table;

    private JTextField searchField;
    private JComboBox<String> statusFilter;
    private JComboBox<String> priorityFilter;
    private JComboBox<String> categoryFilter;

    public ComplaintTable(JFrame parent, ComplaintManager manager) {

        super(parent, "Complaint Management", true);

        this.manager = manager;

        setSize(1250, 650);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout(10, 10));

        JLabel heading = new JLabel("COMPLAINT MANAGEMENT",
                JLabel.CENTER);

        heading.setFont(new Font("Arial", Font.BOLD, 24));
        heading.setBorder(BorderFactory.createEmptyBorder(15, 5, 15, 5));

       

        String[] columns = {
            "Complaint ID", "Name", "Email", "Category",
            "Department", "Priority", "Status", "Date"
        };

        model = new DefaultTableModel(columns, 0) {

            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(model);
        table.setRowHeight(28);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        int[] widths = {130, 120, 170, 120, 170, 90, 110, 110};

        for (int i = 0; i < widths.length; i++) {
            table.getColumnModel().getColumn(i)
                    .setPreferredWidth(widths[i]);
        }

        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel filters = new JPanel(new FlowLayout(FlowLayout.CENTER));

        searchField = new JTextField(12);

        statusFilter = new JComboBox<>(new String[]{
            "All Status", "Submitted", "In Progress", "Resolved"
        });

        priorityFilter = new JComboBox<>(new String[]{
            "All Priority", "Low", "Medium", "High"
        });

        categoryFilter = new JComboBox<>(new String[]{
            "All Categories", "Infrastructure", "Academics",
            "Facilities", "Administration"
        });

        JButton searchButton = new JButton("Search");
        JButton filterButton = new JButton("Apply Filters");
        JButton resetButton = new JButton("Reset");

        filters.add(new JLabel("ID / Name:"));
        filters.add(searchField);
        filters.add(new JLabel("Status:"));
        filters.add(statusFilter);
        filters.add(new JLabel("Priority:"));
        filters.add(priorityFilter);
        filters.add(new JLabel("Category:"));
        filters.add(categoryFilter);
        filters.add(searchButton);
        filters.add(filterButton);
        filters.add(resetButton);

        JPanel top = new JPanel(new BorderLayout());
        top.add(heading, BorderLayout.NORTH);
        top.add(filters, BorderLayout.SOUTH);

        add(top, BorderLayout.NORTH);

        JButton updateButton = new JButton("Update Status");
        JButton historyButton = new JButton("View History");
        JButton exportButton = new JButton("Export CSV");
        JButton closeButton = new JButton("Close");

        JPanel bottom = new JPanel();

        bottom.add(updateButton);
        bottom.add(historyButton);
        bottom.add(exportButton);
        bottom.add(closeButton);

        add(bottom, BorderLayout.SOUTH);

        searchButton.addActionListener(e -> applyFilters());
        filterButton.addActionListener(e -> applyFilters());

        searchField.addActionListener(e -> applyFilters());

        resetButton.addActionListener(e -> {
            searchField.setText("");
            statusFilter.setSelectedIndex(0);
            priorityFilter.setSelectedIndex(0);
            categoryFilter.setSelectedIndex(0);
            loadComplaints();
        });

        updateButton.addActionListener(e -> updateComplaint());
        historyButton.addActionListener(e -> showHistory());

        exportButton.addActionListener(e ->
                CSVExporter.export(manager, parent));

        closeButton.addActionListener(e -> dispose());

        loadComplaints();
    }

    private void loadComplaints() {

        model.setRowCount(0);

        for (Complaint c : manager.getAllComplaints()) {
            addRow(c);
        }
    }

    private void addRow(Complaint c) {

        model.addRow(new Object[]{
            c.getComplaintId(),
            c.getName(),
            c.getEmail(),
            c.getCategory(),
            c.getDepartment(),
            c.getPriority(),
            c.getStatus(),
            c.getDate()
        });
    }

    private void applyFilters() {

        model.setRowCount(0);

        String search = searchField.getText().trim().toLowerCase();

        String status = statusFilter.getSelectedItem().toString();
        String priority = priorityFilter.getSelectedItem().toString();
        String category = categoryFilter.getSelectedItem().toString();

        for (Complaint c : manager.getAllComplaints()) {

            boolean searchMatch =
                    c.getComplaintId().toLowerCase().contains(search) ||
                    c.getName().toLowerCase().contains(search);

            boolean statusMatch =
                    status.equals("All Status") ||
                    c.getStatus().equals(status);

            boolean priorityMatch =
                    priority.equals("All Priority") ||
                    c.getPriority().equals(priority);

            boolean categoryMatch =
                    category.equals("All Categories") ||
                    c.getCategory().equals(category);

            if (searchMatch && statusMatch &&
                priorityMatch && categoryMatch) {

                addRow(c);
            }
        }
    }

    private void updateComplaint() {

        int row = table.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(this,
                    "Select a complaint first!");
            return;
        }

        String id = model.getValueAt(row, 0).toString();

        Complaint c = manager.searchComplaint(id);

        if (c == null) {
            return;
        }

        JComboBox<String> statusBox = new JComboBox<>(
                new String[]{"Submitted", "In Progress", "Resolved"});

        statusBox.setSelectedItem(c.getStatus());

        JTextArea remarks = new JTextArea(4, 25);
        remarks.setLineWrap(true);
        remarks.setWrapStyleWord(true);
        remarks.setText(c.getRemarks());

        JPanel panel = new JPanel(new BorderLayout(5, 5));

        panel.add(new JLabel("New Status:"), BorderLayout.NORTH);
        panel.add(statusBox, BorderLayout.CENTER);
        panel.add(new JScrollPane(remarks), BorderLayout.SOUTH);

        int result = JOptionPane.showConfirmDialog(
                this, panel, "Update Complaint",
                JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {

            String newStatus = statusBox.getSelectedItem().toString();
            String newRemarks = remarks.getText().trim();

            if (newStatus.equals("Resolved") && newRemarks.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Resolution remarks are required!");
                return;
            }

            manager.updateStatus(id, newStatus, newRemarks);

            JOptionPane.showMessageDialog(this,
                    "Complaint updated successfully!");

            applyFilters();
        }
    }

    private void showHistory() {

        int row = table.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(this,
                    "Select a complaint first!");
            return;
        }

        String id = model.getValueAt(row, 0).toString();

        Complaint c = manager.searchComplaint(id);

        if (c != null) {
            new ComplaintHistory(
                    (JFrame) SwingUtilities.getWindowAncestor(this),
                    c
            ).setVisible(true);
        }
    }
}
