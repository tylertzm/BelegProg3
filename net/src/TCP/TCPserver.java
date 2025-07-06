package TCP;
import domainlogic.Automat;
import io.AutomatIO;
import kuchen.Allergen;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDate;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import kuchen.Allergen;


public class TCPserver {
    private static final int PORT = 12345;
    private static final int MAX_THREADS = 10;
    private Automat automat;
    private final AutomatIO automatIO;
    private final ExecutorService threadPool;

    public TCPserver(Automat automat, AutomatIO automatIO) {
        this.automat = automat;
        this.automatIO = automatIO;
        this.threadPool = Executors.newFixedThreadPool(MAX_THREADS);
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server gestartet und lauscht auf Port " + PORT);

            // Load initial state
            loadAutomatState();

            while (!Thread.currentThread().isInterrupted()) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    threadPool.execute(() -> handleClient(clientSocket));
                } catch (IOException e) {
                    System.err.println("Fehler beim Annehmen der Client-Verbindung: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Server konnte nicht gestartet werden: " + e.getMessage());
        } finally {
            threadPool.shutdown();
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

private void handleClient(Socket clientSocket) {
    try (InputStream in = clientSocket.getInputStream();
         OutputStream out = clientSocket.getOutputStream()) {

        System.out.println("Client verbunden: " + clientSocket.getInetAddress());
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        
        String input;
        while ((input = reader.readLine()) != null) {
            System.out.println("Empfangener Befehl: " + input);
            String[] tokens = input.split("\\s+");
            String command = tokens[0].toLowerCase();
            String response = processCommand(command, tokens);
            
            // Send response as bytes with end marker
            out.write(response.getBytes("UTF-8"));
            out.write("\nEND_OF_RESPONSE\n".getBytes("UTF-8"));
            out.flush();
        }
    } catch (IOException e) {
        System.err.println("Fehler in Client-Handler: " + e.getMessage());
    } finally {
        try {
            clientSocket.close();
        } catch (IOException e) {
            System.err.println("Fehler beim Schließen des Client-Sockets: " + e.getMessage());
        }
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
                case "ping": return "pong";
                default: return "Unbekannter Befehl: " + command;
            }
        } catch (Exception e) {
            return "Fehler: " + e.getMessage();
        }
    }

    private String handleEinfuegen() {
        int fach = automat.einfuegen("Schokokuchen", "Torte", "Hersteller X",Allergen.Erdnuss);
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
        System.out.println("Server wird heruntergefahren...");
    }
}