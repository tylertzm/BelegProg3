package cli;

import domainlogic.Automat;
import domainlogic.EventSystem;

import java.time.LocalDate;
import java.util.Scanner;

public class CLI {

    private final Automat automat;

    public CLI(Automat automat) {
        this.automat = automat;
    }


    public void run() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Kommandozeile gestartet. Befehle: c for einfuegen, r for anzeigen, u for aendern, d for loeschen, und x for exit");

        while (true) {
            System.out.print("> ");
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("x")) {
                System.out.println("Programm wird beendet...");
                break;
            }

            String[] tokens = input.split("\\s+");
            String command = tokens[0].toLowerCase();

            switch (command) {
                case "c" -> handleEinfuegen(tokens);
                case "r" -> handleAnzeigen();
                case "u" -> handleAendern(tokens);
                case "d" -> handleLoeschen(tokens);
                default -> System.out.println("Unbekannter Befehl: " + command);
            }
        }
    }


    private void handleEinfuegen(String[] tokens) {
    //    if (tokens.length < 4) {
    //        System.out.println("Verwendung: einfuegen <Name> <Sorte> <Hersteller>");
        //       return;
    //    }

    //    String name = tokens[1];
    //    String sorte = tokens[2];
    //    String hersteller = tokens[3];
    // einfuegen vordefiniertes Kuchens, oder???
        int fach = automat.einfuegen("Schokokuchen", "Torte", "Hersteller X");
        if (fach == -1) {
            System.out.println("Automat ist voll. Kuchen konnte nicht hinzugefuegt werden.");
        } else {
            System.out.println("Der vordefinierter Kuchen Schokokuchen mit der Sorte Torte vom Hersteller X wurde im Fach " + fach + " eingefuegt.");
        }
    }


    private void handleAnzeigen() {
        System.out.println("Alle Kuchen im Automaten:");
        String liste = automat.auflisten();
        if (liste.isEmpty()) {
            System.out.println("Keine Kuchen vorhanden.");
        } else {
            System.out.println(liste);
        }
    }


    private void handleAendern(String[] tokens) {
        if (tokens.length != 3) {
            System.out.println("Verwendung: aendern <fachnummer> <JJJJ-MM-TT>");
            System.out.println("Bitte geben Sie das Datum im Format JJJJ-MM-TT ein.");
            return;
        }

        try {
            int fach = Integer.parseInt(tokens[1]);
            String dateString = tokens[2];
            LocalDate neuesDatum = LocalDate.parse(dateString); // Attempt to parse the input string

            if (automat.updateDate(fach, neuesDatum)) {
                System.out.println("Inspektionsdatum für Fach " + fach + " auf " + neuesDatum + " aktualisiert.");
            } else {
                System.out.println("Ungueltige Fachnummer oder Fach leer.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Bitte eine gueltige Fachnummer eingeben.");
        } catch (java.time.format.DateTimeParseException e) {
            System.out.println("Ungültiges Datumsformat. Bitte verwenden Sie JJJJ-MM-TT.");
        }
    }


    private void handleLoeschen(String[] tokens) {
        if (tokens.length != 2) {
            System.out.println("Verwendung: loeschen <fachnummer>");
            return;
        }

        try {
            int fach = Integer.parseInt(tokens[1]);
            if (automat.loeschen(fach)) {
                System.out.println("Kuchen aus Fach " + fach + " gelöscht.");
            } else {
                System.out.println("Fach leer oder ungueltig.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Bitte eine gueltige Fachnummer eingeben.");
        }
    }


}