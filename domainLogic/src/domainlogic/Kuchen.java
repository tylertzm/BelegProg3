package domainlogic;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import kuchen.Allergen;

import java.time.LocalDate;
import java.util.Set;

public class Kuchen {

    private final IntegerProperty fach;
    private final StringProperty name;
    private final StringProperty sorte;
    private final StringProperty hersteller;
    private final ObjectProperty<LocalDate> inspectionDate;
    private final Set<Allergen> allergene;

    public Kuchen(int fach, String name, String sorte, String hersteller, LocalDate inspectionDate, Set<Allergen> allergene) {
        this.fach = new SimpleIntegerProperty(fach);
        this.name = new SimpleStringProperty(name);
        this.sorte = new SimpleStringProperty(sorte);
        this.hersteller = new SimpleStringProperty(hersteller);
        this.inspectionDate = new SimpleObjectProperty<>(inspectionDate);
        this.allergene = allergene;
    }

    public int getFach() {
        return fach.get();
    }

    public IntegerProperty fachProperty() {
        return fach;
    }

    public void setFach(int fach) {
        this.fach.set(fach);
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getSorte() {
        return sorte.get();
    }

    public StringProperty sorteProperty() {
        return sorte;
    }

    public void setSorte(String sorte) {
        this.sorte.set(sorte);
    }

    public String getHersteller() {
        return hersteller.get();
    }

    public StringProperty herstellerProperty() {
        return hersteller;
    }

    public void setHersteller(String hersteller) {
        this.hersteller.set(hersteller);
    }

    public LocalDate getInspectionDate() {
        return inspectionDate.get();
    }

    public ObjectProperty<LocalDate> inspectionDateProperty() {
        return inspectionDate;
    }

    public void setInspectionDate(LocalDate inspectionDate) {
        this.inspectionDate.set(inspectionDate);
    }

    public Set<Allergen> getAllergene() {
        return allergene;
    }

    public String getAllergeneString() {
        if (allergene == null || allergene.isEmpty()) {
            return "";
        }
        return allergene.stream()
                .map(Allergen::toString)
                .collect(java.util.stream.Collectors.joining(", "));
    }
}