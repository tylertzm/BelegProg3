package UDP;

import java.net.*;

public class UDPserver {
    public static void main(String[] args) {
        int port = 12346;
        byte[] buffer = new byte[1024];
        try (DatagramSocket socket = new DatagramSocket(port)) {
            System.out.println("UDP Server läuft auf Port " + port);
            while (true) {
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);
                String received = new String(packet.getData(), 0, packet.getLength());
                // Hier: Geschäftslogik aufrufen und Antwort generieren
                String response = "Empfangen: " + received;
                byte[] responseData = response.getBytes();
                DatagramPacket responsePacket = new DatagramPacket(
                    responseData, responseData.length, packet.getAddress(), packet.getPort());
                socket.send(responsePacket);
            }
        } catch (Exception e) {
            System.out.println("Fehler: " + e.getMessage());
        }
    }
}