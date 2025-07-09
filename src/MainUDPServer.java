import domainlogic.Automat;
import domainlogic.EventSystem;
import io.AutomatIO;
import udp.UDPserver;

public class MainUDPServer {
    public static void main(String[] args) {
        EventSystem eventSystem = new EventSystem();
        Automat automat = new Automat(20,eventSystem);
        AutomatIO automatIO = new AutomatIO();
        UDPserver server = new UDPserver(automat, automatIO);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            server.shutdown();
            System.out.println("Server stopped.");
        }));

        server.start();
    }
}