import TCP.TCPserver;
import domainlogic.Automat;
import domainlogic.EventSystem;
import io.AutomatIO;

public class MainTCPServer {
    public static void main(String[] args) {
        EventSystem eventSystem = new EventSystem();
        Automat automat = new Automat(10,eventSystem); // Capacity of 10
        AutomatIO automatIO = new AutomatIO();
        TCPserver server = new TCPserver(automat, automatIO);
        
        // Add shutdown hook
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            server.shutdown();
            System.out.println("Server wurde beendet.");
        }));
        
        server.start();
    }
}