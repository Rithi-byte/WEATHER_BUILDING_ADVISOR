import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuizPage extends JFrame implements ActionListener {
    private List<Question> questions;
    private JPanel questionPanel;
    private JButton submitButton;
    private JButton backButton; // Back button to return to main menu
    private JTextArea resultsArea;
    private Map<Integer, String> selectedAnswers;

    public QuizPage() {
        // Set up the frame
        setTitle("Quiz Page");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Custom panel with image background
        GradientPanel gradientPanel = new GradientPanel();
        gradientPanel.setLayout(new BorderLayout());

        // Initialize question panel with a BoxLayout for vertical stacking
        questionPanel = new JPanel();
        questionPanel.setLayout(new BoxLayout(questionPanel, BoxLayout.Y_AXIS)); // Stack questions vertically
        questionPanel.setBackground(new Color(255, 255, 255, 200)); // Semi-transparent white background
        questionPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Padding around questions

        // Initialize results area
        resultsArea = new JTextArea();
        resultsArea.setEditable(false);
        resultsArea.setLineWrap(true);
        resultsArea.setWrapStyleWord(true);
        resultsArea.setFont(new Font("Arial", Font.PLAIN, 14));
        resultsArea.setBackground(Color.LIGHT_GRAY);
        JScrollPane resultsScrollPane = new JScrollPane(resultsArea);
        resultsScrollPane.setPreferredSize(new Dimension(780, 100));
        gradientPanel.add(resultsScrollPane, BorderLayout.SOUTH);

        // Initialize selected answers map
        selectedAnswers = new HashMap<>();

        // Initialize submit button
        submitButton = new JButton("Submit Answers");
        submitButton.setFont(new Font("Arial", Font.BOLD, 14));
        submitButton.setBackground(new Color(100, 149, 237)); // Cornflower Blue
        submitButton.setForeground(Color.WHITE);
        submitButton.setFocusPainted(false);
        submitButton.setBorderPainted(false);
        submitButton.addActionListener(this);

        // Initialize back button
        backButton = new JButton("Back to Main Menu");
        backButton.setFont(new Font("Arial", Font.BOLD, 14));
        backButton.setBackground(new Color(255, 69, 0)); // Red-Orange
        backButton.setForeground(Color.WHITE);
        backButton.setFocusPainted(false);
        backButton.setBorderPainted(false);
        backButton.addActionListener(e -> goBackToMainMenu());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.setOpaque(false);
        buttonPanel.add(submitButton);
        buttonPanel.add(backButton); // Add back button to the button panel
        gradientPanel.add(buttonPanel, BorderLayout.NORTH);

        // Fetch questions from the database
        fetchQuestions();

        // Add a scroll pane for questions
        JScrollPane questionScrollPane = new JScrollPane(questionPanel);
        questionScrollPane.setPreferredSize(new Dimension(780, 450)); // Height for questions
        gradientPanel.add(questionScrollPane, BorderLayout.CENTER);

        add(gradientPanel); // Add the gradient panel to the frame
        setVisible(true);
    }

    private void fetchQuestions() {
        questions = new ArrayList<>();
        String query = "SELECT * FROM quiz_questions ORDER BY RAND() LIMIT 15";
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/javap1", "root", "Rithi@1908");
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            if (!rs.isBeforeFirst()) {
                resultsArea.setText("No questions found in the database.");
                return;
            }

            while (rs.next()) {
                int id = rs.getInt("question_id");
                String questionText = rs.getString("question_text");
                String optionA = rs.getString("option_a");
                String optionB = rs.getString("option_b");
                String optionC = rs.getString("option_c");
                String optionD = rs.getString("option_d");
                String correctAnswer = rs.getString("correct_answer");

                questions.add(new Question(id, questionText, optionA, optionB, optionC, optionD, correctAnswer));

                // Create a new panel for the current question
                JPanel currentQuestionPanel = new JPanel();
                currentQuestionPanel.setLayout(new BoxLayout(currentQuestionPanel, BoxLayout.Y_AXIS)); // Vertical layout for options
                currentQuestionPanel.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 1)); // Border for each question
                currentQuestionPanel.setBackground(Color.WHITE); // White background for question panels
                currentQuestionPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Padding around questions

                // Add a label for the question number and text
                JLabel questionLabel = new JLabel("Question " + questions.size() + ": " + questionText);
                questionLabel.setFont(new Font("Arial", Font.BOLD, 16)); // Bold font for questions
                questionLabel.setForeground(Color.BLACK); // Set label text color to black for visibility
                currentQuestionPanel.add(questionLabel);

                // Checkbox group for options
                ButtonGroup optionsGroup = new ButtonGroup();
                JRadioButton radioButtonA = new JRadioButton(optionA);
                JRadioButton radioButtonB = new JRadioButton(optionB);
                JRadioButton radioButtonC = new JRadioButton(optionC);
                JRadioButton radioButtonD = new JRadioButton(optionD);

                optionsGroup.add(radioButtonA);
                optionsGroup.add(radioButtonB);
                optionsGroup.add(radioButtonC);
                optionsGroup.add(radioButtonD);

                // Customize radio buttons
                radioButtonA.setFont(new Font("Arial", Font.PLAIN, 14));
                radioButtonB.setFont(new Font("Arial", Font.PLAIN, 14));
                radioButtonC.setFont(new Font("Arial", Font.PLAIN, 14));
                radioButtonD.setFont(new Font("Arial", Font.PLAIN, 14));

                // Add radio buttons to the current question panel
                currentQuestionPanel.add(radioButtonA);
                currentQuestionPanel.add(radioButtonB);
                currentQuestionPanel.add(radioButtonC);
                currentQuestionPanel.add(radioButtonD);

                final int questionIndex = questions.size() - 1;

                // Add action listener to radio buttons to store selected answers
                radioButtonA.addActionListener(e -> selectedAnswers.put(questionIndex, optionA));
                radioButtonB.addActionListener(e -> selectedAnswers.put(questionIndex, optionB));
                radioButtonC.addActionListener(e -> selectedAnswers.put(questionIndex, optionC));
                radioButtonD.addActionListener(e -> selectedAnswers.put(questionIndex, optionD));

                // Add the current question panel to the main question panel
                questionPanel.add(currentQuestionPanel);
                questionPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Add spacing between questions
            }

            questionPanel.revalidate();
            questionPanel.repaint();
        } catch (SQLException e) {
            resultsArea.setText("Database connection error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        StringBuilder results = new StringBuilder();

        results.append("Quiz Results:\n\n");

        for (int i = 0; i < questions.size(); i++) {
            Question question = questions.get(i);
            String selectedOption = selectedAnswers.get(i);

            // Build result for each question
            results.append("Question ").append(i + 1).append(": ").append(question.getQuestionText()).append("\n");
            results.append("Your answer: ").append(selectedOption != null ? selectedOption : "No answer selected").append("\n");
            results.append("Correct answer: ").append(question.getCorrectAnswer()).append("\n\n");
        }

        resultsArea.setText(results.toString());
    }

    private void goBackToMainMenu() {
        // Close the quiz page and return to the main menu
        this.dispose(); // Close the current window
        new Main(); // Open the main menu (assuming you have a Main class)
    }

    public static void main(String[] args) {
        new QuizPage();
    }
}

class Question {
    private int id;
    private String questionText;
    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;
    private String correctAnswer;

    public Question(int id, String questionText, String optionA, String optionB, String optionC, String optionD, String correctAnswer) {
        this.id = id;
        this.questionText = questionText;
        this.optionA = optionA;
        this.optionB = optionB;
        this.optionC = optionC;
        this.optionD = optionD;
        this.correctAnswer = correctAnswer;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public String getQuestionText() {
        return questionText;
    }
}

// Custom JPanel to draw an image background
class GradientPanel extends JPanel {
    private Image backgroundImage;

    public GradientPanel() {
        // Load the background image
        try {
            backgroundImage = new ImageIcon("C:\\Users\\Admin\\Downloads\\quizz.jpg").getImage();
            if (backgroundImage == null) {
                throw new Exception("Image could not be loaded.");
            }
        } catch (Exception e) {
            System.err.println("Error loading background image: " + e.getMessage());
            backgroundImage = new BufferedImage(800, 600, BufferedImage.TYPE_INT_RGB); // Default blank image
            Graphics g = backgroundImage.getGraphics();
            g.setColor(Color.LIGHT_GRAY);
            g.fillRect(0, 0, 800, 600); // Fill with a default color if image fails to load
            g.dispose();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Draw the background image
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
