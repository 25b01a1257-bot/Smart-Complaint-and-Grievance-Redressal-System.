
import java.util.ArrayList;

public interface ComplaintOperations {

    void registerComplaint(Complaint complaint);

    ArrayList<Complaint> getAllComplaints();

    Complaint searchComplaint(String id);

    void updateStatus(String id, String status, String remarks);
}
