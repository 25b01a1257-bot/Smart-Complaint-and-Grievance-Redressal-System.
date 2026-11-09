
import javax.swing.*;
import java.awt.*;
import java.util.Map;
import java.util.TreeMap;

public class Charts extends JPanel {

    private ComplaintManager manager;

    public Charts(ComplaintManager manager) {
        this.manager = manager;

        setLayout(new GridLayout(1, 2, 20, 20));
        setBackground(new Color(245, 247, 250));

        add(new PieChart());
        add(new DepartmentChart());
    }

    class PieChart extends JPanel {

        public PieChart() {
            setBackground(Color.WHITE);
            setBorder(BorderFactory.createTitledBorder(
                    "Complaint Status Distribution"));
        }

        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            int submitted = manager.getCount("Submitted");
            int progress = manager.getCount("In Progress");
            int resolved = manager.getCount("Resolved");

            int total = submitted + progress + resolved;

            int x = 35, y = 65, size = 220;

            if (total == 0) {
                g.setColor(Color.GRAY);
                g.drawString("No complaint data available", 45, 160);
                return;
            }

            int a1 = submitted * 360 / total;
            int a2 = progress * 360 / total;
            int a3 = 360 - a1 - a2;

            int start = 0;

            g.setColor(new Color(52, 152, 219));
            g.fillArc(x, y, size, size, start, a1);

            start += a1;

            g.setColor(new Color(243, 156, 18));
            g.fillArc(x, y, size, size, start, a2);

            start += a2;

            g.setColor(new Color(46, 204, 113));
            g.fillArc(x, y, size, size, start, a3);

            int ly = 110;

            g.setColor(new Color(52, 152, 219));
            g.fillRect(285, ly, 15, 15);
            g.setColor(Color.BLACK);
            g.drawString("Submitted: " + submitted, 310, ly + 13);

            ly += 40;

            g.setColor(new Color(243, 156, 18));
            g.fillRect(285, ly, 15, 15);
            g.setColor(Color.BLACK);
            g.drawString("In Progress: " + progress, 310, ly + 13);

            ly += 40;

            g.setColor(new Color(46, 204, 113));
            g.fillRect(285, ly, 15, 15);
            g.setColor(Color.BLACK);
            g.drawString("Resolved: " + resolved, 310, ly + 13);
        }
    }

    class DepartmentChart extends JPanel {

        public DepartmentChart() {
            setBackground(Color.WHITE);
            setBorder(BorderFactory.createTitledBorder(
                    "Department-wise Complaints"));
        }

        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            Map<String, Integer> departmentCounts = new TreeMap<>();

            for (Complaint c : manager.getAllComplaints()) {

                String department = c.getDepartment();

                if (department != null && !department.trim().isEmpty()) {
                    departmentCounts.put(
                            department,
                            departmentCounts.getOrDefault(department, 0) + 1
                    );
                }
            }

            if (departmentCounts.isEmpty()) {
                g.setColor(Color.GRAY);
                g.drawString("No department data available", 40, 100);
                return;
            }

            int max = 1;

            for (int value : departmentCounts.values()) {
                max = Math.max(max, value);
            }

            int x = 40;
            int y = 75;
            int barHeight = 25;
            int gap = 65;

            int maxBarWidth = Math.max(50, getWidth() - 130);

            Color[] colors = {
                new Color(52, 152, 219),
                new Color(46, 204, 113),
                new Color(243, 156, 18),
                new Color(155, 89, 182),
                new Color(231, 76, 60)
            };

            int i = 0;

            for (Map.Entry<String, Integer> entry
                    : departmentCounts.entrySet()) {

                String department = entry.getKey();
                int value = entry.getValue();

                int width = value * maxBarWidth / max;

                g.setColor(colors[i % colors.length]);

                g.fillRoundRect(
                        x, y + i * gap,
                        width, barHeight, 8, 8
                );

                g.setColor(Color.BLACK);

                g.drawString(department, x, y - 12 + i * gap);

                g.drawString(
                        String.valueOf(value),
                        x + width + 8,
                        y + 18 + i * gap
                );

                i++;
            }
        }
    }
}
