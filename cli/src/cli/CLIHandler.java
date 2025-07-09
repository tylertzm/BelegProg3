package cli;

import domainlogic.Automat;
import io.AutomatIO;
import kuchen.Allergen;
import java.time.LocalDate;

public class CLIHandler {
    private final Automat automat;
    private final AutomatIO automatIO;

    public CLIHandler(Automat automat, AutomatIO automatIO) {
        this.automat = automat;
        this.automatIO = automatIO;
    }

    public String handleCommand(String mode, String params) {
        mode = mode.trim().toUpperCase();
        String[] tokens = params.trim().split("\\s+");
        switch (mode) {
            case "C": {
                if (tokens.length < 3) {
                    return "Verwendung: <Name> <Sorte> <Hersteller> [Allergene...]";
                }
                String name = tokens[0];
                String sorte = tokens[1];
                String hersteller = tokens[2];
                Allergen[] allergene = new Allergen[0];
                if (tokens.length > 3) {
                    allergene = new Allergen[tokens.length - 3];
                    for (int i = 3; i < tokens.length; i++) {
                        try {
                            allergene[i - 3] = Allergen.valueOf(tokens[i]);
                        } catch (IllegalArgumentException e) {
                            return "Unbekanntes Allergen: " + tokens[i];
                        }
                    }
                }
                int fach = automat.einfuegen(name, sorte, hersteller, allergene);
                if (fach == -1) {
                    return "Automat ist voll. Kuchen konnte nicht hinzugefuegt werden.";
                } else {
                    handleSave();
                    return "Kuchen hinzugefuegt: " + name + " " + sorte + " " + hersteller + " (Fach " + fach + ")";
                }
            }
            case "R": {
                String liste = automat.auflisten();
                if (liste.isEmpty()) {
                    return "Keine Kuchen vorhanden.";
                } else {
                    return liste;
                }
            }
            case "U": {
                if (tokens.length != 2) {
                    return "Verwendung: <Fachnummer> <NeuesDatumJJJJ-MM-TT>";
                }
                try {
                    int fach = Integer.parseInt(tokens[0]);
                    String dateString = tokens[1];
                    LocalDate neuesDatum = LocalDate.parse(dateString);
                    if (automat.updateDate(fach, neuesDatum)) {
                        handleSave();
                        return "Inspektionsdatum fuer Fach " + fach + " auf " + neuesDatum + " aktualisiert.";
                    } else {
                        return "Ungueltige Fachnummer oder Fach leer.";
                    }
                } catch (NumberFormatException e) {
                    return "Bitte eine gueltige Fachnummer eingeben.";
                } catch (java.time.format.DateTimeParseException e) {
                    return "Ungueltiges Datumsformat. Bitte verwenden Sie JJJJ-MM-TT.";
                }
            }
            case "D": {
                if (tokens.length != 1) {
                    return "Verwendung: <Fachnummer>";
                }
                try {
                    int fach = Integer.parseInt(tokens[0]);
                    if (automat.loeschen(fach)) {
                        handleSave();
                        return "Kuchen aus Fach " + fach + " geloescht.";
                    } else {
                        return "Fach leer oder ungueltig.";
                    }
                } catch (NumberFormatException e) {
                    return "Bitte eine gueltige Fachnummer eingeben.";
                }
            }
            default:
                return "Unbekannter Modus: " + mode;
        }
    }

    private void handleSave() {
        try {
            automatIO.saveAutomat(automat, "automat.ser");
        } catch (Exception e) {
            // Optionally log error
        }
    }
}
