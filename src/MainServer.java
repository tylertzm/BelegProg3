import TCP.TCPserver;

public class MainServer {
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Verwendung: <tcp|udp> <kapazität>");
            return;
        }

        String protocol = args[0];
        int capacity = Integer.parseInt(args[1]);

        if (protocol.equalsIgnoreCase("tcp")) {
            try {
                new TCPserver(capacity).start(12345);
            } catch (Exception e) {
                System.out.println("Fehler beim Starten des Servers: " + e.getMessage());
            }
        } else {
            System.out.println("UDP noch nicht implementiert.");
        }
    }
}