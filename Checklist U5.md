# Übung 5
Realisieren Sie die Funktionalität den Zustand der Geschäftslogik zu laden und zu speichern.
Erstellen Sie zur Demonstration eine main die einen Automaten mit Kuchen befüllt und den Zustand ausgibt. Anschließend sollte er gespeichert und geladen werden und der Zustand erneut ausgegeben werden.

Änderungen an den Interfaces des Vertrages sind ab jetzt zulässig. Dabei müssen Funktionsumfang, Kapselung und die Erweiterbarkeit erhalten bleiben.

Weitere Informationen stehen im Anforderungsdokument unter der Überschrift I/O.

## Abgabeanforderungen
Die Abgabe hat als zip-Datei zu erfolgen, die ein lauffähiges IntelliJ-IDEA-Projekt enthält. Sie sollte die befüllte Checkliste im root des Projektes (neben der iml-Datei) enthalten in der der erreichte Stand bezüglich des Bewertungsschemas vermerkt ist.

Änderungen an der Checkliste sind grundsätzlich nicht zulässig. Davon ausgenommen ist das Befüllen der Checkboxen und ergänzende Anmerkungen die _kursiv gesetzt_ sind.

## Quellen
Zulässige Quellen sind suchmaschinen-indizierte Internetseiten. Werden mehr als drei zusammenhängende Anweisungen übernommen ist die Quelle in den Kommentaren anzugeben. Ausgeschlossen sind Quellen, die auch als Beleg oder Übungsaufgabe abgegeben werden oder wurden. Zulässig sind außerdem die über moodle bereitgestellten Materialien, diese können für die Übungsaufgaben und den Beleg ohne Quellenangabe verwendet werden.
Flüchtige Quellen, wie Sprachmodelle, sind per screen shot zu dokumentieren.

## Bewertung
1 Punkt für die Erfüllung des Pflichtteils

### Pflichtteil
- [ ] Quellen angegeben
- [ ] zip Archiv
- [ ] IntelliJ-Projekt (kein Gradle, Maven o.ä.)
- [ ] keine weiteren Bibliotheken außer JUnit5, Mockito und JavaFX (und deren Abhängigkeiten)
- [ ] keine Umlaute, Sonderzeichen, etc. in Datei- und Pfadnamen
- [ ] kompilierbar
- [ ] Trennung zwischen Test- und Produktiv-Code
- [ ] main-Methoden nur im default package des module belegProg3
- [ ] keine vorgetäuschte Funktionalität (inkl. leere Tests)
- [ ] ausführbar
- [ ] Speichern und Laden der Geschäftslogik mit JOS oder JBP
- [ ] main zur Demonstration der Persistierung

### empfohlene Realisierungen als Vorbereitung auf den Beleg

werden überprüft (aber nicht bewertet), wenn hier als bearbeitet angegeben   

- [ ] Einbindung der Persistierung im CLI
- [ ] Stellvertreter-Tests für das Lesen und Schreiben aus/in Streams
- [ ] Speichern und Laden der Geschäftslogik mit JOS **und** JBP
