package UDP;

import domainlogic.Automat;
import io.AutomatIO;
import java.io.*;
import java.net.*;
import java.time.LocalDate;
import java.util.concurrent.*;
import kuchen.Allergen;


public class UDPserver {
    private static final int PORT = 12345;
    private static final int BUFFER_SIZE = 8192;
    private DatagramSocket socket;
    private Automat automat;
    private final AutomatIO automatIO;
    private final ExecutorService threadPool;

    public UDPserver(Automat automat, AutomatIO automatIO) {
        this.automat = automat;
        this.automatIO = automatIO;
        this.threadPool = Executors.newFixedThreadPool(10);
    }

    public void start() {
        try {
            socket = new DatagramSocket(PORT);
            System.out.println("UDP Server gestartet auf Port " + PORT);
            loadAutomatState();

            while (!Thread.currentThread().isInterrupted()) {
                byte[] buffer = new byte[BUFFER_SIZE];
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);
                threadPool.execute(() -> handleRequest(packet));
            }
        } catch (IOException e) {
            System.err.println("Server Fehler: " + e.getMessage());
        } finally {
            threadPool.shutdown();
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        }
    }

    private void loadAutomatState() {
        try {
            automat = (Automat) automatIO.loadAutomat("automat.ser");
            System.out.println("Automat erfolgreich geladen.");
        } catch (Exception e) {
            System.out.println("Kein gespeicherter Automat gefunden, starte mit leerem Automaten.");
        }
    }

    private void handleRequest(DatagramPacket requestPacket) {
        try {
            String input = new String(requestPacket.getData(), 0, requestPacket.getLength()).trim();
            System.out.println("Empfangener Befehl: " + input);

            String[] tokens = input.split("\\s+");
            String command = tokens[0].toLowerCase();
            String response = processCommand(command, tokens);

            byte[] responseData = response.getBytes("UTF-8");
            DatagramPacket responsePacket = new DatagramPacket(
                responseData, 
                responseData.length, 
                requestPacket.getAddress(), 
                requestPacket.getPort()
            );
            socket.send(responsePacket);
        } catch (IOException e) {
            System.err.println("Fehler bei der Anfragebearbeitung: " + e.getMessage());
        }
    }

    private synchronized String processCommand(String command, String[] tokens) {
        try {
            switch (command) {
                case "c": return handleEinfuegen();
                case "r": return handleAnzeigen();
                case "u": return handleAendern(tokens);
                case "d": return handleLoeschen(tokens);
                case "save": return handleSave();
                case "load": return handleLoad();
                case "x": return "SERVER_SHUTDOWN";
                default: return "Unbekannter Befehl: " + command;
            }
        } catch (Exception e) {
            return "Fehler: " + e.getMessage();
        }
    }

    private String handleEinfuegen() {
        int fach = automat.einfuegen("Schokokuchen", "Torte", "Hersteller X", Allergen.Gluten);
        if (fach == -1) {
            return "Automat ist voll. Kuchen konnte nicht hinzugefuegt werden.";
        }
        handleSave();
        return "Kuchen in Fach " + fach + " eingefuegt.";
    }

    private String handleAnzeigen() {
        String liste = automat.auflisten();
        return liste.isEmpty() ? "Keine Kuchen vorhanden." : liste;
    }

    private String handleAendern(String[] tokens) {
        if (tokens.length != 3) {
            return "Verwendung: u <fachnummer> <JJJJ-MM-TT>";
        }

        try {
            int fach = Integer.parseInt(tokens[1]);
            LocalDate neuesDatum = LocalDate.parse(tokens[2]);
            
            if (neuesDatum.isBefore(LocalDate.now())) {
                return "Datum darf nicht in der Vergangenheit liegen";
            }

            if (automat.updateDate(fach, neuesDatum)) {
                handleSave();
                return "Inspektionsdatum aktualisiert.";
            }
            return "Ungültige Fachnummer oder Fach leer.";
        } catch (NumberFormatException e) {
            return "Ungültige Fachnummer";
        } catch (java.time.format.DateTimeParseException e) {
            return "Ungültiges Datumsformat. Verwenden Sie JJJJ-MM-TT.";
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
                return "Kuchen gelöscht.";
            }
            return "Fach leer oder ungültig.";
        } catch (NumberFormatException e) {
            return "Ungültige Fachnummer";
        }
    }

    private String handleSave() {
        try {
            automatIO.saveAutomat(automat, "automat.ser");
            return "Automat gespeichert.";
        } catch (Exception e) {
            return "Speicherfehler: " + e.getMessage();
        }
    }

    private String handleLoad() {
        try {
            automat = (Automat) automatIO.loadAutomat("automat.ser");
            return "Automat geladen.";
        } catch (Exception e) {
            return "Ladefehler: " + e.getMessage();
        }
    }

    public void shutdown() {
        threadPool.shutdown();
        if (socket != null) {
            socket.close();
        }
        System.out.println("Server wird heruntergefahren...");
    }
}