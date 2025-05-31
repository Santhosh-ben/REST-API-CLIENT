import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONObject;

public class WeatherAPIConsumer {
    public static void main(String[] args) {
        String urlString = "https://api.openweathermap.org/data/2.5/weather?lat=11.933483&lon=79.829763&appid=1db2a9f0f7d542c35577eb84013c9e9a&units=metric";
        
        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            
            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuilder responseContent = new StringBuilder();
                
                while ((inputLine = in.readLine()) != null) {
                    responseContent.append(inputLine);
                }
                in.close();
                
                JSONObject jsonResponse = new JSONObject(responseContent.toString());
                
                String cityName = jsonResponse.getString("name");
                JSONObject main = jsonResponse.getJSONObject("main");
                double temperature = main.getDouble("temp");
                int humidity = main.getInt("humidity");
                JSONObject wind = jsonResponse.getJSONObject("wind");
                double windSpeed = wind.getDouble("speed");
                JSONArray weatherArray = jsonResponse.getJSONArray("weather");
                JSONObject weather = weatherArray.getJSONObject(0);
                String weatherMain = weather.getString("main");
                String weatherDescription = weather.getString("description");
                
                System.out.println("Weather in " + cityName);
                System.out.println("Temperature: " + temperature + " °C");
                System.out.println("Humidity: " + humidity + "%");
                System.out.println("Wind Speed: " + windSpeed + " m/s");
                System.out.println("Condition: " + weatherMain + " (" + weatherDescription + ")");
                
            } else {
                System.out.println("GET request failed. HTTP Code: " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
