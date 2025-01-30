import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EnergyEfficiencyCalculator extends JFrame implements ActionListener {
    // Input fields
    private JTextField areaField, totalEnergyUseField, renewableEnergyField, outputEnergyField;
    private JTextArea descriptionArea, resultArea;
    private JButton calculateButton, saveButton, backButton;

    // Database credentials
    private static final String URL = "jdbc:mysql://localhost:3306/javap1"; // Update with your database URL
    private static final String USERNAME = "root"; // Update with your DB username
    private static final String PASSWORD = "Rithi@1908"; // Update with your DB password

    public EnergyEfficiencyCalculator() {
        setTitle("Energy Efficiency Metrics Calculator");
        setSize(400, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window

        // Custom JPanel for a gradient background
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gradient = new GradientPaint(0, 0, new Color(0, 150, 136), 0, getHeight(), new Color(33, 150, 243));
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        panel.setLayout(new GridBagLayout()); // Set layout for the panel
        setContentPane(panel); // Set the custom panel as the content pane

        // Use a GridBagLayout for better control over component placement
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10); // Add spacing between components

        // Title label
        JLabel titleLabel = new JLabel("Energy Efficiency Calculator");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE); // White color for text
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(titleLabel, gbc);

        // Create input fields with labels
        gbc.gridwidth = 1;

        addLabel("Building Area (m²):", gbc, 1);
        areaField = createTextField(gbc, 1);

        addLabel("Total Energy Use (kWh):", gbc, 2);
        totalEnergyUseField = createTextField(gbc, 2);

        addLabel("Renewable Energy Produced (kWh):", gbc, 3);
        renewableEnergyField = createTextField(gbc, 3);

        addLabel("Output Energy (kWh):", gbc, 4);
        outputEnergyField = createTextField(gbc, 4);

        // Building Description
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.weighty = 1.0; // Allow the description area to expand
        JLabel descriptionLabel = new JLabel("Building Description:");
        descriptionLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        descriptionLabel.setForeground(Color.WHITE); // White color for text
        add(descriptionLabel, gbc);

        descriptionArea = new JTextArea(4, 20);
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        descriptionArea.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        JScrollPane scrollPane = new JScrollPane(descriptionArea);
        gbc.gridy = 6;
        add(scrollPane, gbc);

        // Buttons
        gbc.gridwidth = 1;
        gbc.weighty = 0; // Reset weight for buttons

        calculateButton = createButton("Calculate Metrics", gbc, 7, new Color(76, 175, 80)); // Green
        saveButton = createButton("Save to Database", gbc, 8, new Color(33, 150, 243)); // Blue
        backButton = createButton("Back to Main", gbc, 9, Color.RED); // Back button

        // Result area
        gbc.gridx = 0;
        gbc.gridy = 10;
        gbc.gridwidth = 2;
        gbc.weighty = 1.0; // Allow the result area to expand
        resultArea = new JTextArea(8, 20);
        resultArea.setEditable(false);
        resultArea.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        resultArea.setBackground(Color.WHITE);
        JScrollPane resultScrollPane = new JScrollPane(resultArea);
        add(resultScrollPane, gbc);

        setVisible(true);
    }

    private void addLabel(String text, GridBagConstraints gbc, int y) {
        gbc.gridx = 0;
        gbc.gridy = y;
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.PLAIN, 14));
        label.setForeground(Color.WHITE); // White color for text
        add(label, gbc);
    }

    private JTextField createTextField(GridBagConstraints gbc, int y) {
        gbc.gridx = 1;
        gbc.gridy = y;
        JTextField textField = new JTextField();
        textField.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        add(textField, gbc);
        return textField;
    }

    private JButton createButton(String text, GridBagConstraints gbc, int y, Color color) {
        gbc.gridx = 0;
        gbc.gridy = y;
        JButton button = new JButton(text);
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder());
        button.setPreferredSize(new Dimension(150, 30)); // Smaller button size
        button.addActionListener(this);
        add(button, gbc);
        return button;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == calculateButton) {
            calculateMetrics();
        } else if (e.getSource() == saveButton) {
            saveToDatabase();
        } else if (e.getSource() == backButton) {
            dispose(); // Close the current frame
            new Main(); // Open the main application frame
        }
    }

    private void calculateMetrics() {
        try {
            // Get user inputs
            double area = Double.parseDouble(areaField.getText());
            double totalEnergyUse = Double.parseDouble(totalEnergyUseField.getText());
            double renewableEnergy = Double.parseDouble(renewableEnergyField.getText());
            double outputEnergy = Double.parseDouble(outputEnergyField.getText());

            // Calculate metrics
            double eui = totalEnergyUse / area; // Energy Use Intensity
            double eer = outputEnergy / totalEnergyUse * 100; // Energy Efficiency Ratio

            // Display results
            resultArea.setText("Energy Use Intensity (EUI): " + eui + " kWh/m²\n");
            resultArea.append("Energy Efficiency Ratio (EER): " + eer + " %\n");
        } catch (NumberFormatException ex) {
            resultArea.setText("Please enter valid numerical values.");
        }
    }

    private void saveToDatabase() {
        String area = areaField.getText();
        String totalEnergyUse = totalEnergyUseField.getText();
        String renewableEnergy = renewableEnergyField.getText();
        String outputEnergy = outputEnergyField.getText();
        String description = descriptionArea.getText();

        String sql = "INSERT INTO energy_data (area, total_energy_use, renewable_energy, output_energy, description) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, area);
            pstmt.setString(2, totalEnergyUse);
            pstmt.setString(3, renewableEnergy);
            pstmt.setString(4, outputEnergy);
            pstmt.setString(5, description);
            pstmt.executeUpdate();
            resultArea.setText("Data saved successfully.");
        } catch (SQLException ex) {
            resultArea.setText("Error saving data: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        new EnergyEfficiencyCalculator();
    }
}
