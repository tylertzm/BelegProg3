import cli.CLI;
import domainlogic.Automat;
import domainlogic.EventSystem;

public class MainCLI {
    public static void main(String[] args) {
        EventSystem eventSystem = new EventSystem();
        // vordefinierte Kapazität, wollte EventSystem implementieren aber doch keine Zeit
        Automat automat = new Automat(20, eventSystem);
        CLI cli = new CLI(automat);
        cli.run();
    }
}
