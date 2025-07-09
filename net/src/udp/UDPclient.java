
package udp;

import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

public class UDPclient {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int PORT = 10000;
    private static final int TIMEOUT = 3000; // 3 Sekunden Timeout
    private static final int BUFFER_SIZE = 8192;
    private static final int MAX_RETRIES = 3;

    public void start() {
        Scanner scanner = new Scanner(System.in);
        AtomicBoolean shouldExit = new AtomicBoolean(false);

        try (DatagramSocket socket = new DatagramSocket()) {
            socket.setSoTimeout(TIMEOUT);
            InetAddress serverAddress = InetAddress.getByName(SERVER_ADDRESS);

            System.out.println("UDP Client gestartet. Befehle: c, r, u <fach> <datum>, d <fach>, x");

            while (!shouldExit.get()) {
                System.out.print("> ");
                String input = scanner.nextLine().trim();

                if (input.isEmpty()) continue;

                if (input.equalsIgnoreCase("x")) {
                    shouldExit.set(true);
                    sendRequest(socket, serverAddress, input);
                    System.out.println("Client wird beendet.");
                    continue;
                }

                // Senden und Empfangen mit Wiederholungen
                boolean receivedResponse = false;
                for (int attempt = 0; attempt < MAX_RETRIES && !receivedResponse; attempt++) {
                    sendRequest(socket, serverAddress, input);

                    // Antwort empfangen
                    byte[] buffer = new byte[BUFFER_SIZE];
                    DatagramPacket responsePacket = new DatagramPacket(buffer, buffer.length);

                    try {
                        socket.receive(responsePacket);
                        String response = new String(responsePacket.getData(), 0, responsePacket.getLength(), "UTF-8");

                        if (response.equals("SERVER_SHUTDOWN")) {
                            System.out.println("Server wurde heruntergefahren.");
                            shouldExit.set(true);
                        } else {
                            System.out.println(response);
                        }
                        receivedResponse = true;
                    } catch (SocketTimeoutException e) {
                        if (attempt == MAX_RETRIES - 1) {
                            System.err.println("Timeout: Keine Antwort vom Server erhalten nach " + MAX_RETRIES + " Versuchen.");
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Client Fehler: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }

    // Sendet ein UDP-Paket an den Server
    private void sendRequest(DatagramSocket socket, InetAddress address, String message) throws IOException {
        byte[] requestData = message.getBytes("UTF-8");
        DatagramPacket requestPacket = new DatagramPacket(
            requestData,
            requestData.length,
            address,
            PORT
        );
        socket.send(requestPacket);
    }
}