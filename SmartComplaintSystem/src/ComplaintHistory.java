
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ComplaintHistory extends JDialog {

    public ComplaintHistory(JFrame parent, Complaint complaint) {

        super(parent, "Complaint History", true);

        setSize(600, 450);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout(10, 10));

        JLabel heading = new JLabel(
                "COMPLAINT STATUS HISTORY",
                JLabel.CENTER
        );

        heading.setFont(new Font("Arial", Font.BOLD, 22));
        heading.setBorder(BorderFactory.createEmptyBorder(15, 5, 15, 5));

        add(heading, BorderLayout.NORTH);

        JTextArea historyArea = new JTextArea();
        historyArea.setEditable(false);
        historyArea.setFont(new Font("Monospaced", Font.PLAIN, 14));

        historyArea.append("Complaint ID: " +
                complaint.getComplaintId() + "\n");
        historyArea.append("Complainant: " +
                complaint.getName() + "\n");
        historyArea.append("Current Status: " +
                complaint.getStatus() + "\n");

        historyArea.append("\n------------------------------------\n");
        historyArea.append("STATUS HISTORY\n");
        historyArea.append("------------------------------------\n\n");

        ArrayList<String> history = complaint.getHistory();

        for (String entry : history) {
            historyArea.append(entry + "\n\n");
        }

        add(new JScrollPane(historyArea), BorderLayout.CENTER);

        JButton closeButton = new JButton("Close");

        closeButton.addActionListener(e -> dispose());

        JPanel bottom = new JPanel();
        bottom.add(closeButton);

        add(bottom, BorderLayout.SOUTH);
    }
}
