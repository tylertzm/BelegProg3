import domainlogic.Automat;
import io.AutomatIO;

public class ServerMain {
    public static void main(String[] args) {
        Automat automat = new Automat(20); // Assuming Automat has a constructor with capacity
        AutomatIO automatIO = new AutomatIO();
        TCPserver server = new TCPserver(automat, automatIO);
        server.start();
    }
}