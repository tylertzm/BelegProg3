import domainlogic.Automat;
import domainlogic.EventSystem;
import java.io.IOException;

public class MainIO {
    public static void main(String[] args) {
        EventSystem eventSystem = new EventSystem();
        io.AutomatIO automatIO = new io.AutomatIO();
        // Example Automat instance (replace with your actual Automat class)
        Automat automat = new Automat(20,eventSystem); // Make sure Automat implements Serializable

        try {
            // Save Automat state
            automatIO.saveAutomat(automat, "automat.ser");
            // Load Automat state
            Automat loadedAutomat = (Automat) automatIO.loadAutomat("automat.ser");
            System.out.println("Automat loaded: " + loadedAutomat);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
