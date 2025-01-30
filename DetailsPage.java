import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class DetailsPage extends JFrame implements ActionListener {
    private JTextArea textArea;
    private JButton backButton;
    private JPanel contentPanel;

    public DetailsPage() {
        // Set up the frame
        setTitle("Green Building Design and Energy Optimization");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setResizable(false); // Prevent resizing

        // Set a modern color scheme
        Color backgroundColor = new Color(200, 255, 200); // Light green background
        Color textColor = new Color(50, 50, 50);
        Color buttonColor = new Color(70, 130, 180); // Steel Blue
        Color buttonHoverColor = new Color(100, 150, 210); // Lighter Steel Blue

        // Main content panel with a light green background
        contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(backgroundColor);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Padding

        // Title label with a more appealing font
        JLabel titleLabel = new JLabel("Green Building Design and Energy Optimization");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24)); // Use Arial bold for a cleaner look
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setForeground(textColor);
        contentPanel.add(titleLabel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Spacing

        // Text area for information
        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setFont(new Font("Arial", Font.PLAIN, 16));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setBackground(Color.WHITE);
        textArea.setForeground(textColor);
        textArea.setText(
                "Green Building Design\n\n" +
                        "Green building design is an approach to construction that seeks to minimize the impact of buildings on the environment. " +
                        "It incorporates sustainable practices such as:\n" +
                        "- Energy efficiency\n" +
                        "- Water conservation\n" +
                        "- Use of renewable resources\n" +
                        "- Sustainable materials\n\n" +
                        "Energy Optimization\n\n" +
                        "Energy optimization involves using technology and techniques to reduce energy consumption while maintaining comfort. " +
                        "Key strategies include:\n" +
                        "- Smart building systems that adapt to occupancy and usage patterns.\n" +
                        "- Energy-efficient appliances that consume less power.\n" +
                        "- Proper insulation and building orientation to maximize natural light and heat.\n" +
                        "- Renewable energy sources such as solar panels, wind turbines, and geothermal energy.\n\n" +
                        "Benefits of Green Building\n\n" +
                        "Green buildings provide numerous benefits, including:\n" +
                        "- Lower operating costs through reduced energy and water usage.\n" +
                        "- Improved indoor air quality leading to better health for occupants.\n" +
                        "- Increased property value and marketability.\n" +
                        "- Positive environmental impact through reduced waste and pollution.\n\n" +
                        "Fun Facts About Green Buildings:\n" +
                        "- The first LEED-certified building was completed in 1993.\n" +
                        "- Green roofs can help reduce energy costs by providing insulation.\n" +
                        "- Energy-efficient buildings can save up to 30% on energy bills.\n" +
                        "- The use of recycled materials in construction can significantly lower carbon footprints.\n" +
                        "- Green buildings can improve occupant health and productivity.\n\n" +
                        "Conclusion\n\n" +
                        "Adopting green building practices not only benefits the environment but also enhances the quality of life for individuals and communities."
        );

        // Create a scroll pane for text area
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(740, 400));
        contentPanel.add(scrollPane);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Spacing

        // Back button
        backButton = new JButton("Back");
        backButton.setFont(new Font("Arial", Font.BOLD, 14)); // Match button font
        backButton.setBackground(buttonColor);
        backButton.setForeground(Color.WHITE);
        backButton.setFocusPainted(false);
        backButton.setBorderPainted(false);
        backButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        backButton.addActionListener(this);
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Button hover effect
        backButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                backButton.setBackground(buttonHoverColor);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                backButton.setBackground(buttonColor);
            }
        });

        contentPanel.add(backButton);

        // Add main panel to the frame
        add(contentPanel, BorderLayout.CENTER);

        // Add a window listener to close the window
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                dispose();
            }
        });

        setVisible(true); // Make the frame visible after setting up components
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == backButton) {
            dispose(); // Close the DetailsPage
            new Main(); // Open the Main page
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(DetailsPage::new);
    }
}
