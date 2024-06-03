/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package weatherApp;

/**
 *
 * @author hp
 */
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class WeatherApp {
    private static final String API_KEY = "b415abc898ffc770d4034adabbe22299"; 
    private static final String API_URL = "http://api.openweathermap.org/data/2.5/weather?q=%s&appid=%s";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter city name: ");
        String cityName = scanner.nextLine();
        scanner.close();

        try {
            String response = getWeatherData(cityName);
            if (response != null) {
                parseAndDisplayWeatherData(response);
            } else {
                System.out.println("Could not fetch weather data.");
            }
        } catch (Exception e) {
        }
    }

    private static String getWeatherData(String cityName) throws Exception {
        String urlString = String.format(API_URL, cityName, API_KEY);
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();
        if (responseCode == 200) {             StringBuilder content;
            try ( // 200 OK
                    BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                String inputLine;
                content = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }
            }
            connection.disconnect();
            return content.toString();
        } else {
            connection.disconnect();
            return null;
        }
    }

    private static void parseAndDisplayWeatherData(String response) {
        JSONObject jsonObject = new JSONObject(response);
        String cityName = jsonObject.getString("name");
        JSONObject main = jsonObject.getJSONObject("main");
        double temperature = main.getDouble("temp") - 273.15; // Convert from Kelvin to Celsius
        int humidity = main.getInt("humidity");
        JSONObject weather = jsonObject.getJSONArray("weather").getJSONObject(0);
        String description = weather.getString("description");

        System.out.println("City: " + cityName);
        System.out.println("Temperature: " + String.format("%.2f", temperature) + " °C");
        System.out.println("Humidity: " + humidity + "%");
        System.out.println("Weather: " + description);
    }
}
