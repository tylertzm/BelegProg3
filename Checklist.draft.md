# Beleg SS 25 Vorschau (93)
Checkboxen befuellen und _kursiv_ gesetzten Text durch entsprechende Angaben ersetzten.
Bei keiner Angabe wird nur Entwurf, Testqualitaet, Testabdeckung GL, Fehlerfreiheit und Basisfunktionalitaet bewertet.
Die Zahl in der Klammer sind die jeweiligen Punkte fuer die Bewertung.
Die empfohlenen Realisierungen zum Bestehen der Pruefung sind **fett** gesetzt.
Ergaenzende Anmerkungen bitte immer _kursiv_ setzen. Andere aenderungen sind nicht zulaessig.

## Vorrausetzungen fuer das Bestehen
- [ ] Quellen angegeben
- [ ] keine vorgetaeuschte Funktionalitaet (inkl. leere Tests)
- [ ] zip Archiv mit dem Projekt im root
- [ ] IntelliJ-Projekt (kein Gradle, Maven o.ae.)
- [ ] keine weiteren Bibliotheken ausser JUnit5, Mockito und JavaFX
- [ ] keine Umlaute, Sonderzeichen, etc. in Datei- und Pfadnamen
- [ ] Trennung zwischen Test- und Produktiv-Code
- [ ] kompilierbar
- [ ] geforderte main-Methoden nur im default package des module belegProg3
  - [ ] CLI
  - [ ] alternatives CLI
  - [ ] je eine fuer jede Simulation
  - [ ] GUI
  - [ ] Server


## Entwurf (10)
- [ ] **Benennung** (2)
- [ ] **Zustaendigkeit** (2)
- [ ] **Paketierung** (2)
- [ ] **Schichtenaufteilung (via modules)** (2)
- [ ] **nur absolut notwendige down casts** (1)
- [ ] keine Duplikate (1)

## Tests (28)
- [ ] **Testqualitaet** (7)
- [ ] **Testabdeckung GL inkl. Abhaengigkeiten (100% additiv)** (7) _Abdeckung in Prozent angeben_
- [ ] Testabdeckung Rest (jeweils 100% additiv) (6)
  - [ ] Einfuegen von Hersteller*innen ueber das CLI _getestete Klassen angeben_
  - [ ] Anzeigen von Hersteller*innen ueber das CLI _getestete Klassen angeben_
  - [ ] ein Beobachter bzw. dessen alternative Implementierung _getestete Klassen angeben_
  - [ ] deterministische Funktionalitaet der Simulationen _getestete Klassen angeben_
  - [ ] Speichern via JOS oder JBP _getestete Klassen angeben_
  - [ ] Laden via JOS oder JBP _getestete Klassen angeben_
- [ ] **mindestens 5 Unittests, die Mockito verwenden** (4)
- [ ] mindestens 4 Spy- / Verhaltens-Tests (3)
- [ ] **keine unbeabsichtigt fehlschlagenden Test** (1)

## Fehlerfreiheit (10)
- [ ] **Kapselung** (5)
- [ ] **keine Ablauffehler** (5)

## Basisfunktionalitaet (12)
- [ ] **CRUD** (2)
- [ ] **CLI** (2)
  * Syntax gemaess Anforderungen
- [ ] **Simulation** (2)
  * ohne race conditions
- [ ] **GUI** (2)
- [ ] **I/O** (2)
  * in CLI oder GUI integriert
- [ ] **Net** (2)

## Funktionalitaet (23)
- [ ] vollstaendige GL (2)
- [ ] threadsichere GL (1)
- [ ] vollstaendiges CLI (1)
- [ ] alternatives CLI (1)
  * _angeben welche Funktionalitaet im alternativen CLI deaktiviert_
- [ ] ausdifferenziertes event-System mit mindestens 3 events (2)
- [ ] observer (2)
- [ ] bzgl. den Anforderungen angemessene Typen der collections (2)
- [ ] Simulation 2 (1)
- [ ] Simulation 3 (1)
- [ ] skalierbare GUI (1)
- [ ] vollstaendige GUI (1)
- [ ] FXML verwendet (1)
- [ ] aenderung des Fachnummer mittels drag&drop (1)
- [ ] Einfuegen von Kuchen via GUI erfolgt nebenlaeufig (1)
- [ ] sowohl JBP als auch JOS (2)
- [ ] sowohl tcp als auch udp (1)
- [ ] Server unterstuetzt konkurierende Clients fuer tcp oder udp (2)

## zusaetzliche Anforderungen (10)

