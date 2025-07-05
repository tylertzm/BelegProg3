package sim;

import domainlogic.Automat;

import java.util.Random;

public class KuchenLoescher implements Runnable {
    private final Automat automat;
    private final Random random = new Random();

    public KuchenLoescher(Automat automat) {
        this.automat = automat;
    }

    @Override
    public void run() {
        while (true) {
            String[] kuchenListe = automat.auflisten().split("\n");

            if (kuchenListe.length > 0) {
                int index = random.nextInt(kuchenListe.length);
                String zeile = kuchenListe[index];
                try {
                    int fachNummer = Integer.parseInt(zeile.split(" ")[1].replace(":", ""));

                    boolean geloescht = automat.loeschen(fachNummer);
                    if (geloescht) {
                        System.out.println("Entfernt aus Fach: " + fachNummer);
                    } else {
                        System.out.println("Fehler beim Entfernen aus Fach: " + fachNummer);
                    }
                } catch (Exception e) {
                    System.out.println("Fehler beim Parsen der Fachnummer: " + zeile);
                }
            }

            try {
                Thread.sleep(700);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}