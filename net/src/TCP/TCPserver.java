package net;

import domainlogic.Automat;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDate;

public class TCPserver {
    private final Automat automat;

    public TCPserver(int capacity) {
        this.automat = new Automat(capacity);
    }

    public void start(int port) throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("TCP Server läuft auf Port " + port);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                new Thread(() -> handleClient(clientSocket)).start();
            }
        }
    }

    private void handleClient(Socket socket) {
        try (
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true)
        ) {
            String input;
            while ((input = in.readLine()) != null) {
                String response = processCommand(input);
                out.println(response);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String processCommand(String input) {
        String[] tokens = input.split(" ");
        String cmd = tokens[0];

        return switch (cmd) {
            case "c" -> {
                int fach = automat.einfuegen("Schokokuchen", "Torte", "Hersteller X");
                yield fach == -1
                        ? "Automat ist voll."
                        : "Kuchen eingefügt in Fach " + fach;
            }
            case "r" -> {
                String list = automat.auflisten();
                yield list.isEmpty() ? "Keine Kuchen vorhanden." : list;
            }
            case "u" -> {
                if (tokens.length != 3) yield "Verwendung: u <fachnummer> <JJJJ-MM-TT>";
                try {
                    int fach = Integer.parseInt(tokens[1]);
                    LocalDate date = LocalDate.parse(tokens[2]);
                    yield automat.updateDate(fach, date)
                            ? "Datum aktualisiert."
                            : "Ungültiges Fach.";
                } catch (Exception e) {
                    yield "Fehler: " + e.getMessage();
                }
            }
            case "d" -> {
                if (tokens.length != 2) yield "Verwendung: d <fachnummer>";
                try {
                    int fach = Integer.parseInt(tokens[1]);
                    yield automat.loeschen(fach) ? "Kuchen gelöscht." : "Ungültiges Fach.";
                } catch (Exception e) {
                    yield "Fehler: " + e.getMessage();
                }
            }
            default -> "Unbekannter Befehl.";
        };
    }
}