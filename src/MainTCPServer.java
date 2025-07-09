import tcp.TCPserver;
import domainlogic.Automat;
import domainlogic.EventSystem;
import io.AutomatIO;

public class MainTCPServer {
    public static void main(String[] args) {
        EventSystem eventSystem = new EventSystem();
        Automat automat = new Automat(20,eventSystem); // Capacity of 20, eventSystem for the GUI/CLI kept here.
        AutomatIO automatIO = new AutomatIO();
        TCPserver server = new TCPserver(automat, automatIO);
        
        // shutdown
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            server.shutdown();
            System.out.println("Server wurde beendet.");
        }));
        
        server.start();
    }
}