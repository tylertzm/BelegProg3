package TCP;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class TCPclient {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int PORT = 12345;
    private static final int RECONNECT_DELAY = 5000;

    public void start() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            try (Socket socket = new Socket(SERVER_ADDRESS, PORT);
                 BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                 PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

                System.out.println("Verbindung zum Server hergestellt.");
                System.out.println("Befehle: c (einfügen), r (anzeigen), u <fach> <datum> (ändern), d <fach> (löschen), x (beenden)");

                boolean running = true;
                while (running) {
                    try {
                        System.out.print("> ");
                        String input = scanner.nextLine().trim();

                        if (input.equalsIgnoreCase("x")) {
                            running = false;
                            continue;
                        }

                        out.println(input);

                        // Read multi-line response
                        StringBuilder response = new StringBuilder();
                        String line;
                        while ((line = in.readLine()) != null) {
                            if (line.equals("END_OF_RESPONSE")) {
                                break;
                            }
                            response.append(line).append("\n");
                        }

                        if (response.length() == 0) {
                            System.out.println("Server hat die Verbindung getrennt.");
                            break;
                        }

                        // Print response with proper newline at the end
                        System.out.print(response.toString());

                    } catch (IOException e) {
                        System.err.println("Kommunikationsfehler: " + e.getMessage());
                        break;
                    }
                }

                if (!running) {
                    System.out.println("Client wird beendet.");
                    break;
                }

            } catch (IOException e) {
                System.err.println("Verbindungsfehler: " + e.getMessage() + " - Neuer Versuch in " + RECONNECT_DELAY/1000 + " Sekunden...");
                try {
                    Thread.sleep(RECONNECT_DELAY);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }

        scanner.close();
    }
}