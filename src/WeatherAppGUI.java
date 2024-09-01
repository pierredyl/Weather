import org.json.simple.JSONObject;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class WeatherAppGUI extends JFrame {
    private JSONObject weatherData;

    public WeatherAppGUI() {
        super("Weather");

        setDefaultCloseOperation(EXIT_ON_CLOSE);

        //Set properties of window
        setSize(800, 600);
        setLocationRelativeTo(null);
        setLayout(null);
        setResizable(false);

        GUIComponents();

    }




    private void GUIComponents() {
        //Name Text
        JLabel nameText = new JLabel("Dylan Pierre");
        nameText.setBounds(300, 260, 500, 200);
        nameText.setFont(new Font("Dialog", Font.BOLD, 32));
        nameText.setForeground(Color.BLUE);
        add(nameText);

        //Info Button
        JTextPane jtp = new JTextPane();
        String message = "The Product Manager Accelerator Program is designed to support PM professionals through every stage of their career. From students looking for entry-level jobs to Directors looking to take on a leadership role, our program has helped over hundreds of students fulfill their career aspirations.\n" +
                "\n" +
                "Our Product Manager Accelerator community are ambitious and committed. Through our program they have learnt, honed and developed new PM and leadership skills, giving them a strong foundation for their future endeavours.\n" +
                "\n" +
                "Learn product management for free today on our YouTube channel \n" +
                "https://www.youtube.com/c/drnancyli?sub_confirmation=1\n" +
                "\n" +
                "Interested in PM Accelerator Pro? \n" +
                "Step 1: Attend the Product Masterclass to learn more about the program details, price, different packages, and stay until the end to get FREE  AI Course. \n" +
                "\n" +
                "Learn how to create a killer product portfolio 2 two weeks that will help you land any PM job( traditional or AI) even if you were laid off or have zero PM experience\n" +
                "\n" +
                "https://www.drnancyli.com/masterclass\n" +
                "\n" +
                "Step 2⃣: Reserve your early bird ticket and submit an application to talk to our Head of Admission\n" +
                "\n" +
                "Step 3⃣: Successful applicants join our PMA Pro community to receive customized coaching!\n" +
                "\n" +
                "Website\n" +
                "http://www.drnancyli.com\n" +
                "Phone\n" +
                "+1 6176106855Phone number is +1 6176106855\n" +
                "Industry\n" +
                "E-Learning Providers\n" +
                "Company size\n" +
                "2-10 employees\n" +
                "54 associated members LinkedIn members who’ve listed Product Manager Accelerator as their current workplace on their profile.\n" +
                "Headquarters\n" +
                "Boston, MA\n" +
                "Founded\n" +
                "2020\n" +
                "Specialties\n" +
                "Product Management, Product Manager, Product Management Training, Product Management Certification, Product Lead, Product Executive, Associate Product Manager, product management coaching, product manager resume, Product Management Interview, VP of Product, Director of Product, and Chief Product Officer";
        JButton infoButton = new JButton(loadImage("images/info.png"));
        infoButton.setBounds(650, 335, 35, 35);
        infoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jtp.setSize(new Dimension(480, 10));
                jtp.setPreferredSize(new Dimension(480, 10));
                JOptionPane.showMessageDialog(jtp, message);
            }
        });
        add(infoButton);


        //Temperature(s) Text
        JLabel temperatureText = new JLabel();
        temperatureText.setBounds(570, 80, 200, 200);
        temperatureText.setFont(new Font("Dialog", Font.BOLD, 48));
        add(temperatureText);

        JLabel temperature2Text = new JLabel();
        temperature2Text.setBounds(60, 350, 200, 200);
        temperature2Text.setFont(new Font("Dialog", Font.BOLD, 34));
        add(temperature2Text);

        JLabel temperature3Text = new JLabel();
        temperature3Text.setBounds(260, 350, 200, 200);
        temperature3Text.setFont(new Font("Dialog", Font.BOLD, 34));
        add(temperature3Text);

        JLabel temperature4Text = new JLabel();
        temperature4Text.setBounds(460, 350, 200, 200);
        temperature4Text.setFont(new Font("Dialog", Font.BOLD, 34));
        add(temperature4Text);

        JLabel temperature5Text = new JLabel();
        temperature5Text.setBounds(660, 350, 200, 200);
        temperature5Text.setFont(new Font("Dialog", Font.BOLD, 34));
        add(temperature5Text);


        //Weather Condition Text and Image
        JLabel conditionText = new JLabel();
        conditionText.setBounds(600, 130, 200, 200);
        conditionText.setFont(new Font("Dialog", Font.BOLD, 34));
        add(conditionText);

        JLabel weatherConditionImage = new JLabel();
        weatherConditionImage.setBounds(270, 110, 245, 217);
        add(weatherConditionImage);


        //Humidity Text and Image
        JLabel humidityImage = new JLabel(loadImage("images/humidity.png"));
        humidityImage.setBounds(30, 100, 74, 66);
        add(humidityImage);

        JLabel humidityText = new JLabel();
        humidityText.setBounds(100, 85, 100, 100);
        add(humidityText);

        //Windspeed Image and Text
        JLabel windspeedImage = new JLabel(loadImage("images/windspeed.png"));
        windspeedImage.setBounds(15, 200, 74, 66);
        add(windspeedImage);

        JLabel windspeedText = new JLabel();
        windspeedText.setBounds(100, 180, 250, 100);
        add(windspeedText);



        //UV Index Image and Text
        JLabel uvIndexImage = new JLabel(loadImage("images/uv_index.png"));
        uvIndexImage.setBounds(25, 275, 75, 75);
        add(uvIndexImage);

        JLabel uvIndexText = new JLabel();
        uvIndexText.setBounds(100, 210, 200, 200);
        add(uvIndexText);


        //Search Bar
        JTextField searchBar = new JTextField();
        searchBar.setBounds(9, 15, 750, 45);
        searchBar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userInput = searchBar.getText();

                if (userInput.replaceAll("\\s", "").length() <= 0) {
                    return;
                }
                weatherData = WeatherApp.getLocationWeatherByName(userInput);

                String weatherCondition = (String) weatherData.get("condition");

                switch (weatherCondition) {
                    case "Clear":
                        weatherConditionImage.setIcon(loadImage("images/clear.png"));
                        break;
                    case "Cloudy":
                        weatherConditionImage.setIcon(loadImage("images/cloudy.png"));
                        break;
                    case "Rainy":
                        weatherConditionImage.setIcon(loadImage("images/rain.png"));
                        break;
                    case "Snowy":
                        weatherConditionImage.setIcon(loadImage("images/snow.png"));
                        break;
                }

                temperatureText.setText((double) weatherData.get("temp") + "°F");
                temperature2Text.setText((double) weatherData.get("temp2") + "°F");
                temperature3Text.setText((double) weatherData.get("temp3") + "°F");
                temperature4Text.setText((double) weatherData.get("temp4") + "°F");
                temperature5Text.setText((double) weatherData.get("temp5") + "°F");

                conditionText.setText((String) weatherData.get("condition"));

                long humidity = (long) weatherData.get("humidity");
                humidityText.setText("<html><b>Humidity</b> " + humidity + "%</html>");

                double windspeed = (double) weatherData.get("windspeed");
                windspeedText.setText("<html><b>Windspeed</b> " + windspeed + "mph</html>");

                double uvIndex = (double) weatherData.get("uv");
                uvIndexText.setText("<html><b>UV_INDEX</b> " + uvIndex + "</html>");
            }
        });
        add(searchBar);





        //User Location Button
        JButton locationButton = new JButton(loadImage("images/location.png"));
        locationButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        locationButton.setBounds(755, 15, 43, 45);
        locationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] coordinates = WeatherApp.getUserLocation();
                weatherData = WeatherApp.getLocationWeatherByCoords(coordinates);
                String weatherCondition = (String) weatherData.get("condition");

                switch (weatherCondition) {
                    case "Clear":
                        weatherConditionImage.setIcon(loadImage("images/clear.png"));
                        break;
                    case "Cloudy":
                        weatherConditionImage.setIcon(loadImage("images/cloudy.png"));
                        break;
                    case "Rainy":
                        weatherConditionImage.setIcon(loadImage("images/rain.png"));
                        break;
                    case "Snowy":
                        weatherConditionImage.setIcon(loadImage("images/snow.png"));
                        break;
                }

                temperatureText.setText((double) weatherData.get("temp") + "°F");
                temperature2Text.setText((double) weatherData.get("temp2") + "°F");
                temperature3Text.setText((double) weatherData.get("temp3") + "°F");
                temperature4Text.setText((double) weatherData.get("temp4") + "°F");
                temperature5Text.setText((double) weatherData.get("temp5") + "°F");

                conditionText.setText((String) weatherData.get("condition"));

                long humidity = (long) weatherData.get("humidity");
                humidityText.setText("<html><b>Humidity</b> " + humidity + "%</html>");

                double windspeed = (double) weatherData.get("windspeed");
                windspeedText.setText("<html><b>Windspeed</b> " + windspeed + "mph</html>");

                double uvIndex = (double) weatherData.get("uv");
                uvIndexText.setText("<html><b>UV_INDEX</b> " + uvIndex + "</html>");
            }
        });
        add(locationButton);
    }





    //Loads an image as an icon for the Swing components.
    private ImageIcon loadImage(String path) {
        try {
            BufferedImage image = ImageIO.read(new File(path));
            return new ImageIcon(image);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Error loading image");
        return null;
    }
}
