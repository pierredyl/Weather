import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class WeatherApp {


    //Retrieves the weather information from longitude and latitude values. Used for returning
    //the weather from the user's location. Returns the present weather and the temperatures
    //at the same time over the next four days.
     public static JSONObject getLocationWeatherByCoords(String [] coordinates) {
        double latitude = Double.parseDouble(coordinates[0]);
        double longitude = Double.parseDouble(coordinates[1]);

         String urlString = "https://api.open-meteo.com/v1/forecast?latitude=" + latitude + "&longitude=" + longitude +"&hourly=" +
                 "temperature_2m,relative_humidity_2m,weather_code,wind_speed_10m,uv_index&temperature_unit=fahrenheit&" +
                 "wind_speed_unit=mph&timezone=America%2FLos_Angeles";

        try {
            HttpURLConnection con = getAPIResponse(urlString);

            if (con.getResponseCode() != 200) {
                System.out.println("Connection to API failed");
                return null;
            }

            StringBuilder resultJson = new StringBuilder();
            Scanner scanner = new Scanner(con.getInputStream());

            while (scanner.hasNext()) {
                resultJson.append(scanner.nextLine());
            }

            scanner.close();
            con.disconnect();

            JSONParser parser = new JSONParser();
            JSONObject resultJsonObject = (JSONObject) parser.parse(String.valueOf(resultJson));

            JSONObject hourly = (JSONObject) resultJsonObject.get("hourly");

            JSONArray time = (JSONArray) hourly.get("time");

            int index = findCurrentIndexTime(time);

            //get temperatures
            JSONArray temperatureData = (JSONArray) hourly.get("temperature_2m");
            double temperature = (double) temperatureData.get(index);
            double temperature2 = (double) temperatureData.get(index + 24);
            double temperature3 = (double) temperatureData.get(index + 48);
            double temperature4 = (double) temperatureData.get(index + 72);
            double temperature5 = (double) temperatureData.get(index + 96);

            //Retrieve the weather code.
            JSONArray weathercode = (JSONArray) hourly.get("weather_code");
            String weatherCondition = convertWeatherCode((long) weathercode.get(index));

            //Retrieve the humidity level.
            JSONArray relativeHumidity = (JSONArray) hourly.get("relative_humidity_2m");
            long humidity = (long) relativeHumidity.get(index);

            //Retrieve the windspeed.
            JSONArray windspeedData = (JSONArray) hourly.get("wind_speed_10m");
            double windspeed = (double) windspeedData.get(index);

            JSONArray uvIndexData = (JSONArray) hourly.get("uv_index");
            double uvIndex = (double) uvIndexData.get(index);

            JSONObject weatherData = new JSONObject();
            weatherData.put("temp", temperature);
            weatherData.put("temp2", temperature2);
            weatherData.put("temp3", temperature3);
            weatherData.put("temp4", temperature4);
            weatherData.put("temp5", temperature5);
            weatherData.put("condition", weatherCondition);
            weatherData.put("windspeed", windspeed);
            weatherData.put("humidity", humidity);
            weatherData.put("uv", uvIndex);

            return weatherData;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }



    //Retrieves the weather of a location by a search. Returns the present weather and the
    //temperatures at the same time over the next four days.
    public static JSONObject getLocationWeatherByName(String locationName) {
        JSONArray locationData = getLocationData(locationName);

        JSONObject location = (JSONObject) locationData.get(0);
        double latitude = (double) location.get("latitude");
        double longitude = (double) location.get("longitude");
        String urlString = "https://api.open-meteo.com/v1/forecast?latitude=" + latitude + "&longitude=" + longitude +"&hourly=" +
                "temperature_2m,relative_humidity_2m,weather_code,wind_speed_10m,uv_index&temperature_unit=fahrenheit&" +
                "wind_speed_unit=mph&timezone=America%2FLos_Angeles";
        try {
            HttpURLConnection con = getAPIResponse(urlString);

            if (con.getResponseCode() != 200) {
                System.out.println("Connection to API failed");
                return null;
            }

            StringBuilder resultJson = new StringBuilder();
            Scanner scanner = new Scanner(con.getInputStream());

            while (scanner.hasNext()) {
                resultJson.append(scanner.nextLine());
            }

            scanner.close();
            con.disconnect();

            JSONParser parser = new JSONParser();
            JSONObject resultJsonObject = (JSONObject) parser.parse(String.valueOf(resultJson));

            JSONObject hourly = (JSONObject) resultJsonObject.get("hourly");

            JSONArray time = (JSONArray) hourly.get("time");

            int index = findCurrentIndexTime(time);

            //get temperature
            JSONArray temperatureData = (JSONArray) hourly.get("temperature_2m");
            double temperature = (double) temperatureData.get(index);
            double temperature2 = (double) temperatureData.get(index + 24);
            double temperature3 = (double) temperatureData.get(index + 48);
            double temperature4 = (double) temperatureData.get(index + 72);
            double temperature5 = (double) temperatureData.get(index + 96);

            //Retrieve the weather code.
            JSONArray weathercode = (JSONArray) hourly.get("weather_code");
            String weatherCondition = convertWeatherCode((long) weathercode.get(index));

            //Retrieve the humidity level.
            JSONArray relativeHumidity = (JSONArray) hourly.get("relative_humidity_2m");
            long humidity = (long) relativeHumidity.get(index);

            //Retrieve the windspeed.
            JSONArray windspeedData = (JSONArray) hourly.get("wind_speed_10m");
            double windspeed = (double) windspeedData.get(index);

            JSONArray uvIndexData = (JSONArray) hourly.get("uv_index");
            double uvIndex = (double) uvIndexData.get(index);

            JSONObject weatherData = new JSONObject();
            weatherData.put("temp", temperature);
            weatherData.put("temp2", temperature2);
            weatherData.put("temp3", temperature3);
            weatherData.put("temp4", temperature4);
            weatherData.put("temp5", temperature5);
            weatherData.put("condition", weatherCondition);
            weatherData.put("windspeed", windspeed);
            weatherData.put("humidity", humidity);
            weatherData.put("uv", uvIndex);

            return weatherData;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    //Retrieves geographic coordinates for a given location.
    private static JSONArray getLocationData(String location) {
        location = location.replaceAll(" ", "+");
        String url = "https://geocoding-api.open-meteo.com/v1/search?name="
                + location + "&count=10&language=en&format=json";

        try {
            HttpURLConnection con = getAPIResponse(url);
            if (con.getResponseCode() != 200) {
                System.out.println("Connection to API has failed");
                return null;
            } else {
                StringBuilder resultJson = new StringBuilder();
                Scanner scanner = new Scanner(con.getInputStream());

                while (scanner.hasNext()) {
                    resultJson.append(scanner.nextLine());
                }

                scanner.close();
                con.disconnect();

                JSONParser parser = new JSONParser();
                JSONObject resultJsonObject = (JSONObject) parser.parse(String.valueOf(resultJson));

                JSONArray locationData = (JSONArray) resultJsonObject.get("results");
                return locationData;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    //Fetches the API response
    private static HttpURLConnection getAPIResponse(String urlString) {
        try {
            URL url = new URL(urlString);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.connect();
            return con;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }


    //Finds the current time, then iterates through the time list retrieved by the API and matches it.
    //Returns the index of this time.
    private static int findCurrentIndexTime(JSONArray timeList) {
        String currentTime = getCurrentTime();

        for (int i = 0; i < timeList.size(); i++) {
            String time = (String) timeList.get(i);
            if (time.equalsIgnoreCase(currentTime)) {
                return i;
            }
        }

        return 0;
    }


    //Retrieves the current time and sets the format of it to match the API's.
    //Returns the formatted date and time.
    private static String getCurrentTime() {
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH':00'");
        String formattedDateTime = currentDateTime.format(format);
        return formattedDateTime;
    }

    public static String[] getUserLocation() {
        try {
            HttpURLConnection con = getAPIResponse("https://ipinfo.io/json");

            if (con.getResponseCode() != 200) {
                System.out.println("Connection to API failed");
                return null;
            } else {
                StringBuilder resultJson = new StringBuilder();
                Scanner scanner = new Scanner(con.getInputStream());

                while (scanner.hasNext()) {
                    resultJson.append(scanner.nextLine());
                }

                scanner.close();
                con.disconnect();

                JSONParser parser = new JSONParser();
                JSONObject resultJsonObject = (JSONObject) parser.parse(String.valueOf(resultJson));

                String loc = (String) resultJsonObject.get("loc");
                String[] coordinates = loc.split(",");
                return coordinates;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
}


    //Converts the weather code given by the API to a weather condition (Clear, Cloudy, Rainy, Snowy)
    //Returns the weather condition.
    private static String convertWeatherCode(long weathercode) {
        String weatherCondition = "";
        if (weathercode == 0L) {
            weatherCondition = "Clear";
        }
        else if (weathercode <= 3L && weathercode > 0L) {
            weatherCondition = "Cloudy";
        }
        else if ((weathercode >= 51L && weathercode <= 67L)
            || (weathercode >= 80L && weathercode <= 99L)) {
            weatherCondition = "Rainy";
        }
        else if (weathercode >= 71L && weathercode <= 77L) {
            weatherCondition = "Snowy";
        }

        return weatherCondition;
    }
}



