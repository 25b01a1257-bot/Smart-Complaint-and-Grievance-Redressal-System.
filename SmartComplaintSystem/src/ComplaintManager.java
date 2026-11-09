
import java.util.*;
import java.io.*;

public class ComplaintManager implements ComplaintOperations {

    private ArrayList<Complaint> complaints;

    public ComplaintManager() {
        complaints = FileHandler.loadComplaints();
    }

    @Override
    public void registerComplaint(Complaint complaint) {
        complaints.add(complaint);
        FileHandler.saveComplaints(complaints);
    }

public boolean isDuplicate(String email,
                           String category,
                           String description) {

    for (Complaint c : complaints) {

        if (c.getEmail().equalsIgnoreCase(email) &&
            c.getCategory().equalsIgnoreCase(category) &&
            c.getDescription().equalsIgnoreCase(description.trim())) {

            return true;
        }
    }

    return false;
}


    @Override
    public ArrayList<Complaint> getAllComplaints() {
        return complaints;
    }

    @Override
    public Complaint searchComplaint(String id) {
        for (Complaint c : complaints) {
            if (c.getComplaintId().equalsIgnoreCase(id)) {
                return c;
            }
        }
        return null;
    }

    
@Override
public void updateStatus(String id, String status, String remarks) {

    Complaint c = searchComplaint(id);

    if (c != null) {

        c.setStatus(status);
        c.setRemarks(remarks);

        FileHandler.saveComplaints(complaints);
    }
}


    public String generateId() {
        int max = 0;

        for (Complaint c : complaints) {
            try {
                String[] parts = c.getComplaintId().split("-");
                int number = Integer.parseInt(parts[2]);
                max = Math.max(max, number);
            } catch (Exception e) {
                // Ignore IDs that do not follow the expected format
            }
        }

        return String.format("CMP-%d-%04d",
                java.time.Year.now().getValue(), max + 1);
    }

    public String getDepartment(String category) {
        switch (category) {
            case "Infrastructure":
                return "Maintenance Department";

            case "Academics":
                return "Academic Department";

            case "Facilities":
                return "Facilities Department";

            case "Administration":
                return "Administration Department";

            default:
                return "General Department";
        }
    }

    public int getCount(String status) {
        int count = 0;

        for (Complaint c : complaints) {
            if (c.getStatus().equals(status)) {
                count++;
            }
        }

        return count;
    }

    public void generateReport() {
        try (PrintWriter writer =
             new PrintWriter(new FileWriter("Complaint_Report.txt"))) {

            writer.println("SMART COMPLAINT MANAGEMENT SYSTEM");
            writer.println("COMPLAINT RESOLUTION REPORT");
            writer.println("========================================");

            for (Complaint c : complaints) {
                writer.println("Complaint ID: " + c.getComplaintId());
                writer.println("Name: " + c.getName());
                writer.println("Email: " + c.getEmail());
                writer.println("Category: " + c.getCategory());
                writer.println("Department: " + c.getDepartment());
                writer.println("Description: " + c.getDescription());
                writer.println("Priority: " + c.getPriority());
                writer.println("Status: " + c.getStatus());
                writer.println("Date: " + c.getDate());
                writer.println("Resolution Remarks: " + c.getRemarks());
                writer.println("----------------------------------------");
            }

            writer.println("Total Complaints: " + complaints.size());
            writer.println("Submitted: " + getCount("Submitted"));
            writer.println("In Progress: " + getCount("In Progress"));
            writer.println("Resolved: " + getCount("Resolved"));
javax.swing.JOptionPane.showMessageDialog(null,
                    "Report generated successfully!");

        } catch (IOException e) {
            javax.swing.JOptionPane.showMessageDialog(null,
                    "Report generation failed: " + e.getMessage());
        }
    }
}
