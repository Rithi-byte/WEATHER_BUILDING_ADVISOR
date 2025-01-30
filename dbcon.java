import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class dbcon {
    private static final String URL = "jdbc:mysql://localhost:3306/javap1";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "Rithi@1908";

    // Default constructor
    public dbcon() {
        // Initialize any resources if needed
    }

    // Method to insert weather data into the database
    public void insertWeatherData(String time, double temperature, long relativeHumidity, double windSpeed) {
        String sql = "INSERT INTO weatherdata (time, temperature, relative_humidity, wind_speed) VALUES (?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Set the parameters for the PreparedStatement
            stmt.setString(1, time);
            stmt.setDouble(2, temperature);
            stmt.setLong(3, relativeHumidity);
            stmt.setDouble(4, windSpeed);

            // Execute the insert operation
            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("A new weather record was inserted successfully!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
