import java.awt.*;
import java.awt.event.*;

public class third extends Frame {
    private Image backgroundImage;

    public third() {
        // Load the background image
        backgroundImage = Toolkit.getDefaultToolkit().getImage("C:\\Users\\Admin\\Downloads\\P3.jpg"); // Ensure the path is correct

        // Set up the frame
        setTitle("About");
        setSize(800, 600);
        setLayout(null); // Set layout to null for absolute positioning

        // Create labels
        /*Label titleLabel = new Label("ABOUT");
        titleLabel.setBounds(350, 50, 100, 30); // Centered horizontally
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        add(titleLabel);

        Label infoLabel = new Label("PROBLEM STATEMENT: To predict the appropriate layout required for the given temperature data in accordance with sustainable development goal 11 (Sustainable cities and communities)");
        infoLabel.setBounds(50, 100, 700, 40); // Adjust position
        infoLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        add(infoLabel);*/

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

        // Draw text over the image
        g.setColor(Color.WHITE);

    }

    public static void main(String[] args) {
        new third();
    }
}


