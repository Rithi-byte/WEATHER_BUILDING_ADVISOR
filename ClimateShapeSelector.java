import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class ClimateShapeSelector extends JFrame {

    public ClimateShapeSelector() {
        createAndShowGUI();
    }

    private void createAndShowGUI() {
        setTitle("Climate and Building Shape Selection");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        // Main panel with gradient background
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(0, 0, new Color(255, 255, 224), 0, getHeight(), new Color(224, 255, 255));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        mainPanel.setLayout(new BorderLayout(20, 20));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Title label
        JLabel titleLabel = new JLabel("Climate and Building Shape Selection");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0)); // Space below title
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Left panel for the plant image
        JPanel imagePanel = new JPanel();
        JLabel plantImageLabel = new JLabel(new ImageIcon("C:\\Users\\Admin\\Downloads\\zakariyyah-khan-ITOl9J9Q2Ic-unsplash.jpg")); // Your specified plant image path
        imagePanel.add(plantImageLabel);
        imagePanel.setPreferredSize(new Dimension(140, 0)); // Adjust size as needed
        mainPanel.add(imagePanel, BorderLayout.WEST);

        // Define climate data
        Map<String, String[]> climateData = new HashMap<>();
        climateData.put("Dfa/Dfb", new String[]{"Humid Continental", "-30 to 30", "30 to 60", "Focus on insulation; passive solar heating."});
        climateData.put("Cfa/Cfb", new String[]{"Humid Subtropical", "0 to 30", "50 to 80", "Maximize ventilation; consider overhangs for shading."});
        climateData.put("BWh", new String[]{"Hot Desert", "20 to 50", "10 to 30", "High thermal mass; minimize sun exposure."});
        climateData.put("BSh", new String[]{"Hot Semi-Arid", "15 to 40", "20 to 40", "Natural ventilation; strategic shading."});
        climateData.put("Af", new String[]{"Tropical Rainforest", "20 to 35", "80 to 100", "Promote airflow; large windows for light."});
        climateData.put("Aw", new String[]{"Tropical Savanna", "15 to 35", "50 to 80", "Enhance cross-ventilation; shaded outdoor spaces."});
        climateData.put("Csa/Csb", new String[]{"Mediterranean", "5 to 30", "40 to 70", "Adapt to seasonal changes; outdoor spaces."});
        climateData.put("ET", new String[]{"Tundra", "-50 to 10", "30 to 60", "Minimize surface area; high insulation."});
        climateData.put("EF", new String[]{"Ice Cap", "-60 to 0", "50 to 80", "High insulation; withstand heavy snow loads."});
        climateData.put("Dfc/Dfd", new String[]{"Subarctic", "-40 to 20", "30 to 60", "High insulation; airtight design."});
        climateData.put("Cfc", new String[]{"Oceanic", "0 to 25", "60 to 80", "Promote airflow; large windows for light."});
        climateData.put("Cwb", new String[]{"Highland", "0 to 20", "50 to 80", "Adapt to elevation; ensure proper insulation."});
        climateData.put("Csa", new String[]{"Mediterranean", "10 to 25", "30 to 60", "Incorporate water-saving features; use natural materials."});
        climateData.put("Dsa", new String[]{"Hot Semi-Arid", "10 to 40", "20 to 40", "Use of thick walls; orientation to maximize wind."});
        climateData.put("Cfa", new String[]{"Humid Subtropical", "0 to 35", "40 to 90", "Design for summer shading; include thermal mass."});

        // Create sections for each climate type
        JPanel cardsPanel = new JPanel();
        cardsPanel.setLayout(new BoxLayout(cardsPanel, BoxLayout.Y_AXIS));

        for (Map.Entry<String, String[]> entry : climateData.entrySet()) {
            String climateType = entry.getKey();
            String[] details = entry.getValue();
            JPanel climatePanel = new JPanel();
            climatePanel.setLayout(new BorderLayout());
            climatePanel.setBorder(BorderFactory.createTitledBorder(climateType));

            StringBuilder climateDetails = new StringBuilder("<html>");
            climateDetails.append("Type: ").append(details[0]).append("<br>")
                    .append("Temp Range: ").append(details[1]).append("<br>")
                    .append("Humidity Range: ").append(details[2]).append("<br>")
                    .append("Advice: ").append(details[3]);
            climateDetails.append("</html>");

            JLabel climateLabel = new JLabel(climateDetails.toString());
            climatePanel.add(climateLabel, BorderLayout.CENTER);
            cardsPanel.add(climatePanel);
        }

        // Add scroll pane
        JScrollPane scrollPane = new JScrollPane(cardsPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setPreferredSize(new Dimension(600, 400)); // Adjust size as needed

        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Create a small back button
        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Arial", Font.PLAIN, 10)); // Set a smaller font size
        backButton.setPreferredSize(new Dimension(50, 20)); // Set a very small size
        backButton.setBackground(new Color(173, 216, 230)); // Light blue background
        backButton.setForeground(Color.BLACK); // Text color
        backButton.setFocusPainted(false); // Remove focus border
        backButton.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1)); // Add border
        backButton.setOpaque(true); // Ensure background is painted
        backButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); // Hand cursor on hover
        backButton.setMargin(new Insets(2, 5, 2, 5)); // Add padding for a cuter look

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the ClimateShapeSelector window
                new Main(); // Return to the main window
            }
        });
        mainPanel.add(backButton, BorderLayout.SOUTH);

        add(mainPanel);
        setVisible(true);
    }

    public static void main(String[] args) {
        new ClimateShapeSelector(); // Removed; to be instantiated from Main
    }
}
