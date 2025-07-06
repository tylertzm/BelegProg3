package UDP;

import java.net.*;
import java.util.Scanner;

public class UDPclient {
    public static void main(String[] args) {
        String host = "localhost";
        int port = 12346;
        Scanner scanner = new Scanner(System.in);

        try (DatagramSocket socket = new DatagramSocket()) {
            InetAddress address = InetAddress.getByName(host);
            System.out.println("Verbunden mit UDP-Server. Tippe 'exit' zum Beenden.");
            while (true) {
                System.out.print("> ");
                String input = scanner.nextLine();
                if ("exit".equalsIgnoreCase(input)) break;

                byte[] sendData = input.getBytes();
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, address, port);
                socket.send(sendPacket);

                byte[] receiveData = new byte[1024];
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                socket.receive(receivePacket);
                String response = new String(receivePacket.getData(), 0, receivePacket.getLength());
                System.out.println("Server: " + response);
            }
        } catch (Exception e) {
            System.out.println("Fehler: " + e.getMessage());
        }
    }
}