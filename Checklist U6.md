# uebung 6
Erweitern Sie das CLI als Client-Server-Loesung. Der Client soll dabei die Oberflaeche zur Bedienung realisieren und der Server die Geschaeftslogik enthalten.

Die Beruecksichtigung von Skalierbarkeit, Sicherheit und Transaktionskontrollen ist nicht gefordert.

Die Observer muessen nicht im Netzwerk funktionieren.

Clients und Servers haben jeweils eine eigene main-Methode (IntelliJ kann mehrere Applikationen parallel ausfuehren).

Weitere Informationen stehen im Anforderungsdokument unter der ueberschrift net.

## Abgabeanforderungen
Die Abgabe hat als zip-Datei zu erfolgen, die ein lauffaehiges IntelliJ-IDEA-Projekt enthaelt. Sie sollte die befuellte Checkliste im root des Projektes (neben der iml-Datei) enthalten in der der erreichte Stand bezueglich des Bewertungsschemas vermerkt ist.

aenderungen an der Checkliste sind grundsaetzlich nicht zulaessig. Davon ausgenommen ist das Befuellen der Checkboxen und ergaenzende Anmerkungen die _kursiv gesetzt_ sind.

## Quellen
Zulaessige Quellen sind suchmaschinen-indizierte Internetseiten. Werden mehr als drei zusammenhaengende Anweisungen uebernommen ist die Quelle in den Kommentaren anzugeben. Ausgeschlossen sind Quellen, die auch als Beleg oder uebungsaufgabe abgegeben werden oder wurden. Zulaessig sind ausserdem die ueber moodle bereitgestellten Materialien, diese koennen fuer die uebungsaufgaben und den Beleg ohne Quellenangabe verwendet werden.
Fluechtige Quellen, wie Sprachmodelle, sind per screen shot zu dokumentieren.

## Bewertung
1 Punkt fuer die Erfuellung des Pflichtteils

### Pflichtteil
- [ ] Quellen angegeben
- [ ] zip Archiv
- [ ] IntelliJ-Projekt (kein Gradle, Maven o.ae.)
- [ ] keine weiteren Bibliotheken ausser JUnit5, Mockito und JavaFX (und deren Abhaengigkeiten)
- [ ] keine Umlaute, Sonderzeichen, etc. in Datei- und Pfadnamen
- [ ] kompilierbar
- [ ] Trennung zwischen Test- und Produktiv-Code
- [ ] main-Methoden nur im default package des module belegProg3, nicht in den sub modules
- [ ] keine vorgetaeuschte Funktionalitaet (inkl. leere Tests)
- [ ] ausfuehrbar
- [ ] CRUD fuer eine Kuchensorte via tcp oder udp
- [ ] Trennung zwischen Oberflaeche (Client) und Geschaeftslogik (Server)

### empfohlene Realisierungen als Vorbereitung auf den Beleg

werden ueberprueft (aber nicht bewertet), wenn hier in der vorgegebenen Reihenfolge als bearbeitet angegeben   

- [ ] je ein Stellvertreter-Test fuer Einfuegen und Anzeigen pro Server
- [ ] Implementierung von Client und Server fuer tcp und udp
- [ ] Unterstuetzung mehrerer konkurierender Clients pro Server (tcp oder udp)
