
import java.io.Serializable;
import java.util.ArrayList;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Complaint implements Serializable {

    private static final long serialVersionUID = 1L;

    private String complaintId;
    private String name;
    private String email;
    private String category;
    private String department;
    private String description;
    private String status;
    private String priority;
    private String date;
    private String remarks;

    private ArrayList<String> history;

    public Complaint(String complaintId, String name, String email,
                     String category, String department,
                     String description, String priority, String date) {

        this.complaintId = complaintId;
        this.name = name;
        this.email = email;
        this.category = category;
        this.department = department;
        this.description = description;
        this.priority = priority;
        this.date = date;

        this.status = "Submitted";
        this.remarks = "Not resolved yet";

        history = new ArrayList<>();

        addHistory("Complaint submitted. Status: Submitted");
    }

    private void initializeHistory() {
        if (history == null) {
            history = new ArrayList<>();
            history.add(date + " - Previous complaint history unavailable.");
        }
    }

    public void addHistory(String message) {

        initializeHistory();

        String time = LocalDateTime.now().format(
                DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")
        );

        history.add(time + " - " + message);
    }

    public ArrayList<String> getHistory() {
        initializeHistory();
        return new ArrayList<>(history);
    }

    public String getComplaintId() { return complaintId; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getCategory() { return category; }
    public String getDepartment() { return department; }
    public String getDescription() { return description; }
    public String getStatus() { return status; }
    public String getPriority() { return priority; }
    public String getDate() { return date; }
    public String getRemarks() { return remarks; }

    public void setStatus(String status) {

        if (!this.status.equals(status)) {
            this.status = status;
            addHistory("Status changed to: " + status);
        }
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
        addHistory("Resolution remarks updated: " + remarks);
    }
}
