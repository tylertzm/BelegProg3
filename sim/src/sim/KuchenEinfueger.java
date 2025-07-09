package sim;

import domainlogic.Automat;
import kuchen.Allergen;

import java.util.Random;

public class KuchenEinfueger implements Runnable {
    private final Automat automat;
    private final Random random = new Random();
    private int counter = 0;

    public KuchenEinfueger(Automat automat) {
        this.automat = automat;
    }

    @Override
    public void run() {
        while (true) {
            String name = "Kuchen" + counter++;
            String sorte = "Obst";
            String baecker = "Baecker" + random.nextInt(10);

            int fach = automat.einfuegen(name, sorte, baecker, Allergen.Gluten, Allergen.Haselnuss);
            if (fach >= 0) {
                System.out.println("Eingefuegt: " + name + " in Fach " + fach);
            } else {
                System.out.println("Kein freies Fach fuer " + name);
            }

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}