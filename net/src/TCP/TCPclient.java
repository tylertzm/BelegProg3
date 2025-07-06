import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class TCPclient {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int PORT = 12345;

    public void start() {
        try (Socket socket = new Socket(SERVER_ADDRESS, PORT);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             Scanner scanner = new Scanner(System.in)) {

            System.out.println("Verbindung zum Server hergestellt.");
            System.out.println("Befehle: c for einfuegen, r for anzeigen, u for aendern, d for loeschen, und x for exit");

            while (true) {
                System.out.print("> ");
                String input = scanner.nextLine().trim();

                if (input.equalsIgnoreCase("x")) {
                    System.out.println("Programm wird beendet...");
                    break;
                }

                out.println(input);
                String response = in.readLine();
                System.out.println(response);
            }
        } catch (IOException e) {
            System.err.println("Fehler bei der Verbindung zum Server: " + e.getMessage());
        }
    }
}