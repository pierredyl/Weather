import javax.swing.*;

public class Launcher {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                //open the GUI
                new WeatherAppGUI().setVisible(true);
            }
        });
    }
}
