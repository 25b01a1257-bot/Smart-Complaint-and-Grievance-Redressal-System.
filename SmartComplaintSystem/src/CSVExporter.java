
import javax.swing.*;
import java.io.*;
import java.nio.charset.StandardCharsets;

public class CSVExporter {

    public static void export(ComplaintManager manager, JFrame parent) {

        JFileChooser chooser = new JFileChooser();

        chooser.setDialogTitle("Save Complaint Records");

        chooser.setSelectedFile(new File("Complaints.csv"));

        int result = chooser.showSaveDialog(parent);

        if (result != JFileChooser.APPROVE_OPTION) {
            return;
        }

        File file = chooser.getSelectedFile();

        if (!file.getName().toLowerCase().endsWith(".csv")) {
            file = new File(file.getAbsolutePath() + ".csv");
        }

        try (PrintWriter writer = new PrintWriter(
                new OutputStreamWriter(
                        new FileOutputStream(file),
                        StandardCharsets.UTF_8))) {

            writer.println(
                "Complaint ID,Name,Email,Category,Department," +
                "Description,Priority,Status,Date,Remarks"
            );

            for (Complaint c : manager.getAllComplaints()) {

                writer.println(
                    cell(c.getComplaintId()) + "," +
                    cell(c.getName()) + "," +
                    cell(c.getEmail()) + "," +
                    cell(c.getCategory()) + "," +
                    cell(c.getDepartment()) + "," +
                    cell(c.getDescription()) + "," +
                    cell(c.getPriority()) + "," +
                    cell(c.getStatus()) + "," +
                    cell(c.getDate()) + "," +
                    cell(c.getRemarks())
                );
            }

            JOptionPane.showMessageDialog(parent,
                    "CSV exported successfully!\n" +
                    file.getAbsolutePath());

        } catch (IOException e) {

            JOptionPane.showMessageDialog(parent,
                    "Export failed: " + e.getMessage());
        }
    }

    private static String cell(String value) {

        if (value == null) {
            value = "";
        }

        return "\"" + value.replace("\"", "\"\"") + "\"";
    }
}
