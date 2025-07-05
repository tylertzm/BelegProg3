package sim;

import domainlogic.Automat;
import domainlogic.EventSystem;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class KuchenEinfuegerTest {

    @Test
    public void testEinfuegenFügtKuchenEin() throws InterruptedException {
        EventSystem eventSystem = new EventSystem();
        Automat automat = new Automat(5, eventSystem);

        KuchenEinfueger einfueger = new KuchenEinfueger(automat);
        Thread thread = new Thread(einfueger);

        thread.start();
        Thread.sleep(2000);
        thread.interrupt();

        String inhalt = automat.auflisten();
        assertTrue(inhalt.contains("Kuchen"), "Automat sollte mindestens einen Kuchen enthalten");
    }
}