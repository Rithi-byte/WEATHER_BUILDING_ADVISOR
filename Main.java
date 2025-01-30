import java.awt.*;
import java.awt.event.*;

public class Main extends Frame implements ActionListener {
    public Image backgroundImage;

    public Main() {
        // Load the background image
        backgroundImage = Toolkit.getDefaultToolkit().getImage("C:\\Users\\Admin\\Downloads\\P1.jpg"); // Replace with your image path

        // Set up the frame
        setTitle("Application Main Page");
        setSize(800, 600);
        setLayout(null);

        // Create buttons
        Button bt1 = new Button("Layout");
        bt1.setBounds(360, 310, 100, 30);
        bt1.addActionListener(this);
        add(bt1);

        Button bt2 = new Button("About");
        bt2.setBounds(360, 350, 100, 30);
        bt2.addActionListener(this);
        add(bt2);

        Button bt3 = new Button("Exit");
        bt3.setBounds(360, 500, 100, 30);
        bt3.addActionListener(this);
        add(bt3);

        // New Quiz button
        Button bt4 = new Button("Quiz");
        bt4.setBounds(360, 450, 100, 30);
        bt4.addActionListener(this);
        add(bt4);

        // New Energy Calculator button
        Button bt5 = new Button("Energy Calculator");
        bt5.setBounds(360, 400, 100, 30);
        bt5.addActionListener(this);
        add(bt5);

        // New Details button
        Button bt6 = new Button("Details");
        bt6.setBounds(360, 270, 100, 30);
        bt6.addActionListener(this);
        add(bt6);

        // New Climate Shape button
        Button bt7 = new Button("Climate Shape");
        bt7.setBounds(360, 230, 100, 30); // Set position and size
        bt7.addActionListener(this);
        add(bt7);

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
        g.setColor(Color.WHITE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Handle button clicks
        String command = e.getActionCommand();
        if (command.equals("Layout")) {
            dispose();
            new Layout();
        } else if (command.equals("About")) {
            dispose();
            new third();
        } else if (command.equals("Exit")) {
            dispose();
        } else if (command.equals("Quiz")) {
            dispose();
            new QuizPage(); // Opens the QuizPage
        } else if (command.equals("Energy Calculator")) {
            dispose();
            new EnergyEfficiencyCalculator(); // Opens the Energy Efficiency Calculator
        } else if (command.equals("Details")) {
            dispose();
            new DetailsPage(); // Opens the DetailsPage
        } else if (command.equals("Climate Shape")) {
            dispose();
            new ClimateShapeSelector(); // Now calls the ClimateShapeSelector
        }
    }

    public static void main(String[] args) {
        new Main();
    }
}
