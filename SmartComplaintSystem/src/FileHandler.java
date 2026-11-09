
import java.io.*;
import java.util.ArrayList;

public class FileHandler {

    private static final String FILE_NAME = "complaints.dat";

    public static void saveComplaints(ArrayList<Complaint> complaints) {
        try (ObjectOutputStream out =
             new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {

            out.writeObject(complaints);

        } catch (IOException e) {
            System.out.println("Error saving complaints: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public static ArrayList<Complaint> loadComplaints() {

        File file = new File(FILE_NAME);

        if (!file.exists()) {
            return new ArrayList<>();
        }

        try (ObjectInputStream in =
             new ObjectInputStream(new FileInputStream(FILE_NAME))) {

            return (ArrayList<Complaint>) in.readObject();

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading complaints: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}
