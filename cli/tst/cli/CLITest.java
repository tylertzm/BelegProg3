package cli;

import domainlogic.Automat;
import domainlogic.EventSystem;
import org.junit.jupiter.api.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class CLITest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;
    private final java.io.InputStream originalIn = System.in;

    private Automat automat;
    private CLI cli;

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(outContent));
        EventSystem eventSystem = new EventSystem();
        automat = new Automat(5, eventSystem);
        cli = new CLI(automat);
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
        System.setErr(originalErr);
        System.setIn(originalIn);
    }

    @Test
    void testEinfuegenAndAnzeigen() {
        String input = "c\nr\nx\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        cli.run();

        String output = outContent.toString();

        assertTrue(output.contains("Der vordefinierter Kuchen Schokokuchen"));
        assertTrue(output.contains("Alle Kuchen im Automaten:"));
        assertTrue(output.contains("Schokokuchen")); // assuming auflisten() shows the cake name
        assertTrue(output.contains("Programm wird beendet..."));
    }

    @Test
    void testAendernValidDate() {
        // Insert a cake first to have a valid fach
        int fachNummer = automat.einfuegen("Testkuchen", "Sorte", "Hersteller");

        String input = "u " + fachNummer + " 2025-12-31\nx\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        cli.run();

        String output = outContent.toString();

        assertTrue(output.contains("Inspektionsdatum für Fach " + fachNummer + " auf 2025-12-31 aktualisiert."));
        assertTrue(output.contains("Programm wird beendet..."));
    }

    @Test
    void testLoeschen() {
        // Insert a cake first to have a valid fach
        int fachNummer = automat.einfuegen("Testkuchen", "Sorte", "Hersteller");

        String input = "d " + fachNummer + "\nx\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        cli.run();

        String output = outContent.toString();

        assertTrue(output.contains("Kuchen aus Fach " + fachNummer + " gelöscht."));
        assertTrue(output.contains("Programm wird beendet..."));
    }

    @Test
    void testUnknownCommand() {
        String input = "unknown\nx\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        cli.run();

        String output = outContent.toString();

        assertTrue(output.contains("Unbekannter Befehl: unknown"));
    }

    @Test
    void testInvalidDateFormatOnAendern() {
        int fachNummer = automat.einfuegen("Testkuchen", "Sorte", "Hersteller");

        String input = "u " + fachNummer + " 31-12-2025\nx\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        cli.run();

        String output = outContent.toString();

        assertTrue(output.contains("Ungültiges Datumsformat. Bitte verwenden Sie JJJJ-MM-TT."));
    }
}