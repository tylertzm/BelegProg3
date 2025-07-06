package TCP;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class TCPclient {
    private final String host;
    private final int port;

    public TCPclient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void run() {
        try (
            Socket socket = new Socket(host, port);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            Scanner scanner = new Scanner(System.in)
        ) {
            System.out.println("Verbunden mit Server: " + host + ":" + port);
            System.out.println("Befehle: c, r, u <fach> <JJJJ-MM-TT>, d <fach>, x");

            while (true) {
                System.out.print("> ");
                String input = scanner.nextLine();

                if (input.equalsIgnoreCase("x")) {
                    System.out.println("Beendet.");
                    break;
                }

                out.println(input);
                String response = in.readLine();
                System.out.println(response);
            }

        } catch (IOException e) {
            System.out.println("Verbindung zum Server fehlgeschlagen: " + e.getMessage());
        }
    }
}