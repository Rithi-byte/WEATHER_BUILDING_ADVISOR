import java.awt.*;
import java.awt.event.*;

public class Layout extends Frame implements ActionListener {
    private Image backgroundImage;
    private Image buildingShapeImage; // Image to display next to the TextArea
    public TextField inputField;
    public TextArea weatherInfo;
    private boolean showBuildingImage = false; // Flag to control when to show the building image

    public Layout() {
        // Load the background image
        backgroundImage = Toolkit.getDefaultToolkit().getImage("C:\\Users\\Admin\\Downloads\\P2.jpg"); // Ensure the path is correct

        // Set up the frame
        setTitle("Weather App");
        setSize(800, 600);
        setLayout(null); // Set layout to null for absolute positioning

        // Create and add the text field
        inputField = new TextField(20);
        inputField.setBounds(400, 150, 157, 30);
        add(inputField);

        // Create labels
        Label titleLabel = new Label("ENTER CITY:");
        titleLabel.setBounds(200, 150, 157, 30); // Positioned to the left of the input field
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        add(titleLabel);

        // Create and add the submit button
        Button sb = new Button("Submit");
        sb.setBounds(400, 200, 80, 30); // Set bounds for the button
        sb.addActionListener(this); // Register action listener
        add(sb);

        // Create and add a TextArea for displaying weather information
        weatherInfo = new TextArea();
        weatherInfo.setBounds(140, 250, 300, 300); // Adjusted size for better visibility
        weatherInfo.setEditable(false); // Make it non-editable
        add(weatherInfo);

        // Create and add the Back button
        Button backButton = new Button("Back");
        backButton.setBounds(10, 40, 80, 30); // Position the back button
        backButton.addActionListener(e -> {
            dispose(); // Close the current frame
            new Main(); // Open the main frame
        });
        add(backButton);

        // Add a window listener to close the window
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                dispose();
            }
        });

        setVisible(true); // Make the frame visible after setting up components
    }

    @Override
    public void paint(Graphics g) {
        // Draw the background image
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);

        // Draw the building shape image only if weather data has been displayed
        if (showBuildingImage && buildingShapeImage != null) {
            int imageX = weatherInfo.getX() + weatherInfo.getWidth() + 10; // Position 10 pixels to the right of the TextArea
            int imageY = weatherInfo.getY(); // Align with the top of the TextArea
            int imageWidth = 120; // Width of the building shape image
            int imageHeight = 120; // Height of the building shape image
            g.drawImage(buildingShapeImage, imageX, imageY, imageWidth, imageHeight, this);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Handle button clicks
        String city = inputField.getText().trim();
        if (!city.isEmpty()) {
            new weather(city, this); // Pass reference to the Layout instance
        } else {
            weatherInfo.setText("Please enter a city name.");
        }
    }

    public void updateWeatherInfo(String info, int temperature) {
        weatherInfo.setText(info);

        // Determine the building shape image based on temperature range
        if (temperature <= 11) {
            buildingShapeImage = Toolkit.getDefaultToolkit().getImage("C:\\Users\\Admin\\Downloads\\cold.png");
            showBuildingImage = true; // Set flag to true to display the building shape image
            repaint();

        } else if (temperature <= 25) {
            buildingShapeImage = Toolkit.getDefaultToolkit().getImage("C:\\Users\\Admin\\Downloads\\mild.png");
            showBuildingImage = true; // Set flag to true to display the building shape image
            repaint();

        } else {
            buildingShapeImage = Toolkit.getDefaultToolkit().getImage("C:\\Users\\Admin\\Downloads\\hot.png");
            showBuildingImage = true; // Set flag to true to display the building shape image
            repaint();

        }

        // Trigger repaint to show the building image
    }

    // Method to parse temperature from the weather info
    public int parseTemperature(String info) {
        try {
            // Find the substring that includes "Temperature:" and extract the number after it
            String[] parts = info.split("Temperature: ");
            if (parts.length > 1) {
                String tempStr = parts[1].split(" ")[0]; // Extract temperature value
                return Integer.parseInt(tempStr);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Failed to parse temperature; using default value.");
        return -1; // Default to an invalid temperature if parsing fails
    }

    public static void main(String[] args) {
        new Layout();
    }
}
