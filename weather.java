import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.TimeZone;

public class weather {
    weather(String city, Layout layout) {
        try {
            if (city.isEmpty()) {
                layout.updateWeatherInfo("Please enter a valid city name.", -1);
                return;
            }

            // Get location data
            JSONObject cityLocationData = getLocationData(city);
            if (cityLocationData == null) {
                layout.updateWeatherInfo("City not found.", -1);
                return;
            }

            double latitude = ((Number) cityLocationData.get("latitude")).doubleValue();
            double longitude = ((Number) cityLocationData.get("longitude")).doubleValue();

            displayWeatherData(latitude, longitude, layout);
        } catch (Exception e) {
            layout.updateWeatherInfo("An error occurred: " + e.getMessage(), -1);
            e.printStackTrace();
        }
    }

    private static JSONObject getLocationData(String city) {
        city = city.replaceAll(" ", "+");

        String urlString = "https://geocoding-api.open-meteo.com/v1/search?name=" +
                city + "&count=1&language=en&format=json";

        try {
            // Fetch the API response
            HttpURLConnection apiConnection = fetchApiResponse(urlString);

            // Check for response status
            if (apiConnection.getResponseCode() != 200) {
                System.out.println("Error: Could not connect to Geocoding API");
                return null;
            }

            // Read the response and convert to String
            String jsonResponse = readApiResponse(apiConnection);
            System.out.println("Geocoding API Response: " + jsonResponse); // Debugging

            // Parse the string into a JSON Object
            JSONParser parser = new JSONParser();
            JSONObject resultsJsonObj = (JSONObject) parser.parse(jsonResponse);

            // Retrieve Location Data
            JSONArray locationData = (JSONArray) resultsJsonObj.get("results");
            if (locationData == null || locationData.isEmpty()) {
                return null;
            }
            return (JSONObject) locationData.get(0);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void displayWeatherData(double latitude, double longitude, Layout layout) {
        try {
            String url = "https://api.open-meteo.com/v1/forecast?latitude=" + latitude +
                    "&longitude=" + longitude +
                    "&current_weather=true" +
                    "&hourly=relativehumidity_2m" +
                    "&temperature_unit=celsius" +
                    "&windspeed_unit=kmh" +
                    "&timezone=auto"; // Ensures times are in local timezone
            HttpURLConnection apiConnection = fetchApiResponse(url);

            if (apiConnection.getResponseCode() != 200) {
                layout.updateWeatherInfo("Error: Could not connect to Weather API", -1);
                return;
            }

            String jsonResponse = readApiResponse(apiConnection);
            System.out.println("Weather API Response: " + jsonResponse); // Debugging

            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(jsonResponse);

            // Parse current weather
            JSONObject currentWeatherJson = (JSONObject) jsonObject.get("current_weather");
            if (currentWeatherJson == null) {
                layout.updateWeatherInfo("Current weather data not available.", -1);
                return;
            }

            String currentTime = (String) currentWeatherJson.get("time");
            double temperature = ((Number) currentWeatherJson.get("temperature")).doubleValue();
            double windSpeed = ((Number) currentWeatherJson.get("windspeed")).doubleValue();

            // Parse hourly data for relative humidity
            JSONObject hourly = (JSONObject) jsonObject.get("hourly");
            if (hourly == null) {
                layout.updateWeatherInfo("Hourly weather data not available.", -1);
                return;
            }

            JSONArray hourlyTimes = (JSONArray) hourly.get("time");
            JSONArray hourlyRelativeHumidity = (JSONArray) hourly.get("relativehumidity_2m");
            StringBuilder humidityData = new StringBuilder();

            // Retrieve the first few hourly humidity values
            for (int i = 0; i < Math.min(3, hourlyRelativeHumidity.size()); i++) {
                String time = (String) hourlyTimes.get(i);
                long humidity = (long) hourlyRelativeHumidity.get(i);
                humidityData.append("Time: ").append(time).append(", Humidity: ").append(humidity).append("%\n");
            }

            // Format and display the weather information
            StringBuilder weatherInfo = new StringBuilder();
            weatherInfo.append("Current Time: ").append(formatDate(currentTime)).append("\n");
            weatherInfo.append("Temperature: ").append(temperature).append(" °C\n");
            weatherInfo.append("Wind Speed: ").append(windSpeed).append(" km/h\n");
            weatherInfo.append("Humidity Data:\n").append(humidityData);

            // Update weather information in the Layout
            layout.updateWeatherInfo(weatherInfo.toString(), (int) temperature);
        } catch (Exception e) {
            layout.updateWeatherInfo("An error occurred while fetching weather data.", -1);
            e.printStackTrace();
        }
    }

    private static HttpURLConnection fetchApiResponse(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection apiConnection = (HttpURLConnection) url.openConnection();
        apiConnection.setRequestMethod("GET");
        return apiConnection;
    }

    private static String readApiResponse(HttpURLConnection connection) throws IOException {
        Scanner scanner = new Scanner(connection.getInputStream());
        StringBuilder jsonResponse = new StringBuilder();
        while (scanner.hasNextLine()) {
            jsonResponse.append(scanner.nextLine());
        }
        scanner.close();
        return jsonResponse.toString();
    }

    private static String formatDate(String isoDate) {
        SimpleDateFormat isoFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        outputFormat.setTimeZone(TimeZone.getDefault());
        try {
            Date date = isoFormat.parse(isoDate);
            return outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return isoDate;
    }
}
