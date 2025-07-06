import domainlogic.Automat;
import io.AutomatIO;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDate;

public class TCPserver {
    private static final int PORT = 12345;
    private Automat automat;
    private final AutomatIO automatIO;

    public TCPserver(Automat automat, AutomatIO automatIO) {
        this.automat = automat;
        this.automatIO = automatIO;
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server gestartet und lauscht auf Port " + PORT);

            // Beim Start versuchen zu laden
            try {
                automat = (Automat) automatIO.loadAutomat("automat.ser");
                System.out.println("Automat automatisch geladen.");
            } catch (Exception e) {
                System.out.println("Kein gespeicherter Automat gefunden, starte mit leerem Automaten.");
            }

            while (true) {
                try (Socket clientSocket = serverSocket.accept();
                     BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                     PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

                    System.out.println("Neue Client-Verbindung: " + clientSocket.getInetAddress());
                    String input = in.readLine();
                    if (input == null) continue;

                    String[] tokens = input.split("\\s+");
                    String command = tokens[0].toLowerCase();
                    String response = processCommand(command, tokens);

                    out.println(response);
                } catch (IOException e) {
                    System.err.println("Fehler bei Client-Verbindung: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Server konnte nicht gestartet werden: " + e.getMessage());
        }
    }

    private String processCommand(String command, String[] tokens) {
        try {
            switch (command) {
                case "c":
                    return handleEinfuegen();
                case "r":
                    return handleAnzeigen();
                case "u":
                    return handleAendern(tokens);
                case "d":
                    return handleLoeschen(tokens);
                case "save":
                    return handleSave();
                case "load":
                    return handleLoad();
                default:
                    return "Unbekannter Befehl: " + command;
            }
        } catch (Exception e) {
            return "Fehler: " + e.getMessage();
        }
    }

    private String handleEinfuegen() {
        int fach = automat.einfuegen("Schokokuchen", "Torte", "Hersteller X");
        if (fach == -1) {
            return "Automat ist voll. Kuchen konnte nicht hinzugefuegt werden.";
        } else {
            handleSave();
            return "Der vordefinierte Kuchen Schokokuchen mit der Sorte Torte vom Hersteller X wurde im Fach " + fach + " eingefuegt.";
        }
    }

    private String handleAnzeigen() {
        String liste = automat.auflisten();
        if (liste.isEmpty()) {
            return "Keine Kuchen vorhanden.";
        } else {
            return liste;
        }
    }

    private String handleAendern(String[] tokens) {
        if (tokens.length != 3) {
            return "Verwendung: u <fachnummer> <JJJJ-MM-TT>\nBitte geben Sie das Datum im Format JJJJ-MM-TT ein.";
        }

        try {
            int fach = Integer.parseInt(tokens[1]);
            LocalDate neuesDatum = LocalDate.parse(tokens[2]);

            if (automat.updateDate(fach, neuesDatum)) {
                handleSave();
                return "Inspektionsdatum für Fach " + fach + " auf " + neuesDatum + " aktualisiert.";
            } else {
                return "Ungueltige Fachnummer oder Fach leer.";
            }
        } catch (NumberFormatException e) {
            return "Bitte eine gueltige Fachnummer eingeben.";
        } catch (java.time.format.DateTimeParseException e) {
            return "Ungültiges Datumsformat. Bitte verwenden Sie JJJJ-MM-TT.";
        }
    }

    private String handleLoeschen(String[] tokens) {
        if (tokens.length != 2) {
            return "Verwendung: d <fachnummer>";
        }

        try {
            int fach = Integer.parseInt(tokens[1]);
            if (automat.loeschen(fach)) {
                handleSave();
                return "Kuchen aus Fach " + fach + " gelöscht.";
            } else {
                return "Fach leer oder ungueltig.";
            }
        } catch (NumberFormatException e) {
            return "Bitte eine gueltige Fachnummer eingeben.";
        }
    }

    private String handleSave() {
        try {
            automatIO.saveAutomat(automat, "automat.ser");
            return "Automat gespeichert.";
        } catch (Exception e) {
            return "Fehler beim Speichern: " + e.getMessage();
        }
    }

    private String handleLoad() {
        try {
            automat = (Automat) automatIO.loadAutomat("automat.ser");
            return "Automat geladen.";
        } catch (Exception e) {
            return "Fehler beim Laden: " + e.getMessage();
        }
    }

    public static void main(String[] args) {
        Automat automat = new Automat(10); // Assuming Automat has a constructor with capacity
        AutomatIO automatIO = new AutomatIO();
        TCPserver server = new TCPserver(automat, automatIO);
        server.start();
    }
}