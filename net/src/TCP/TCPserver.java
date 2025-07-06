package TCP;

import domainlogic.Automat;
import domainlogic.EventSystem;
import io.AutomatIO;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDate;

public class TCPserver {
    private Automat automat;
    private final AutomatIO automatIO = new AutomatIO();
    private final int capacity;

    public TCPserver(int capacity) {
        this.capacity = capacity;
        EventSystem eventSystem = new EventSystem();
        // Try to load persisted Automat
        try {
            automat = (Automat) automatIO.loadAutomat("automat.ser");
            System.out.println("Automat geladen.");
        } catch (Exception e) {
            automat = new Automat(capacity, eventSystem);
            System.out.println("Neuer Automat gestartet.");
        }
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

        switch (cmd) {
            case "c" -> {
                int fach = automat.einfuegen("Schokokuchen", "Torte", "Hersteller X");
                saveAutomat();
                return fach == -1
                        ? "Automat ist voll."
                        : "Kuchen eingefügt in Fach " + fach;
            }
            case "r" -> {
                String list = automat.auflisten();
                return list.isEmpty() ? "Keine Kuchen vorhanden." : list;
            }
            case "u" -> {
                if (tokens.length != 3) return "Verwendung: u <fachnummer> <JJJJ-MM-TT>";
                try {
                    int fach = Integer.parseInt(tokens[1]);
                    LocalDate date = LocalDate.parse(tokens[2]);
                    boolean ok = automat.updateDate(fach, date);
                    if (ok) saveAutomat();
                    return ok ? "Datum aktualisiert." : "Ungültiges Fach.";
                } catch (Exception e) {
                    return "Fehler: " + e.getMessage();
                }
            }
            case "d" -> {
                if (tokens.length != 2) return "Verwendung: d <fachnummer>";
                try {
                    int fach = Integer.parseInt(tokens[1]);
                    boolean ok = automat.loeschen(fach);
                    if (ok) saveAutomat();
                    return ok ? "Kuchen gelöscht." : "Ungültiges Fach.";
                } catch (Exception e) {
                    return "Fehler: " + e.getMessage();
                }
            }
            default -> "Unbekannter Befehl.";
        }
    }

    private void saveAutomat() {
        try {
            automatIO.saveAutomat(automat, "automat.ser");
            System.out.println("Automat gespeichert.");
        } catch (IOException e) {
            System.err.println("Fehler beim Speichern des Automaten: " + e.getMessage());
        }
    }
}