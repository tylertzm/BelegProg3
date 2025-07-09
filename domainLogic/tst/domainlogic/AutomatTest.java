package domainlogic;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AutomatTest {

    private Automat automat;
    private EventSystem eventSystem;

    @BeforeEach
    public void setUp() {
        eventSystem = new EventSystem();  // EventSystem instanziieren
        automat = new Automat(3, eventSystem);  // Automat mit 3 Faechern
    }

    @Test
    public void testEinfuegenAndAuflisten() {
        int fach1 = automat.einfuegen("Schwarzwaelder Kirschtorte", "Kremkuchen", "Baeckerei Mueller");
        assertTrue(fach1 >= 0);
        String liste = automat.auflisten();
        assertTrue(liste.contains("Schwarzwaelder Kirschtorte"));
    }

    @Test
    public void testEinfuegenBeyondCapacity() {
        automat.einfuegen("Apfelstrudel", "Obstkuchen", "Cafe Schmidt");
        automat.einfuegen("Sachertorte", "Kremkuchen", "Baeckerei Wagner");
        automat.einfuegen("Erdbeerkuchen", "Obstkuchen", "Baeckerei Fischer");
        int fach4 = automat.einfuegen("Kaesekuchen", "Quarkkuchen", "Baeckerei Hoffmann");
        assertEquals(-1, fach4, "Automat sollte keine Kuchen mehr aufnehmen koennen");
    }

    @Test
    public void testLoeschen() {
        int fach = automat.einfuegen("Butterkuchen", "Ruehrkuchen", "Baeckerei Schmidt");
        assertTrue(automat.loeschen(fach));
        assertFalse(automat.loeschen(fach), "Fach sollte jetzt leer sein");
    }

    @Test
    public void testUpdateDate() {
        int fach = automat.einfuegen("Donauwelle", "Schichtkuchen", "Baeckerei Weber");
        LocalDate newDate = LocalDate.of(2025, 5, 20);
        boolean updated = automat.updateDate(fach, newDate);
        assertTrue(updated, "Inspektionsdatum sollte aktualisiert werden");
    }

    @Test
    public void testIstVoll() {
        automat.einfuegen("Linzer Torte", "Obstkuchen", "Baeckerei Maier");
        automat.einfuegen("Mohnkuchen", "Ruehrkuchen", "Baeckerei Klein");
        automat.einfuegen("Zitronenkuchen", "Ruehrkuchen", "Baeckerei Gross");
        assertTrue(automat.istVoll(), "Automat sollte voll sein");
    }

    @Test
    public void testGetGesamtKapazitaet() {
        assertEquals(3, automat.getGesamtKapazitaet());
    }

    @Test
    public void testGetBelegteFaecher() {
        assertEquals(0, automat.getBelegteFaecher());
        automat.einfuegen("Frankfurter Kranz", "Sahnekuchen", "Baeckerei Braun");
        assertEquals(1, automat.getBelegteFaecher());
    }
}