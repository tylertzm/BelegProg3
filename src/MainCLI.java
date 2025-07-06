import cli.CLI;
import domainlogic.Automat;
import domainlogic.EventSystem;
import io.AutomatIO; // Add this import

public class MainCLI {
    public static void main(String[] args) {
        EventSystem eventSystem = new EventSystem();
        Automat automat = new Automat(20, eventSystem);
        AutomatIO automatIO = new AutomatIO(); // Create IO instance
        CLI cli = new CLI(automat, automatIO); // Pass to CLI
        cli.run();
    }
}
