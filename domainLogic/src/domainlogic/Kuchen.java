package domainlogic;

import javafx.beans.property.*;
import kuchen.Allergen;

import java.io.*;
import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

public class Kuchen implements Serializable {
    private static final long serialVersionUID = 1L;
    

    // Serializable fields (backing values)
    private int fachValue;
    private String nameValue;
    private String sorteValue;
    private String herstellerValue;
    private LocalDate inspectionDateValue;
    private Set<Allergen> allergene;

    // Transient JavaFX properties
    private transient IntegerProperty fach;
    private transient StringProperty name;
    private transient StringProperty sorte;
    private transient StringProperty hersteller;
    private transient ObjectProperty<LocalDate> inspectionDate;

    public Kuchen(int fach, String name, String sorte, String hersteller, LocalDate inspectionDate, Set<Allergen> allergene) {
        this.fachValue = fach;
        this.nameValue = name;
        this.sorteValue = sorte;
        this.herstellerValue = hersteller;
        this.inspectionDateValue = inspectionDate;
        this.allergene = allergene;

        initProperties();
    }

    private void initProperties() {
        this.fach = new SimpleIntegerProperty(fachValue);
        this.name = new SimpleStringProperty(nameValue);
        this.sorte = new SimpleStringProperty(sorteValue);
        this.hersteller = new SimpleStringProperty(herstellerValue);
        this.inspectionDate = new SimpleObjectProperty<>(inspectionDateValue);
    }

    // Getters and setters using properties
    public int getFach() {
        return fach.get();
    }

    public IntegerProperty fachProperty() {
        return fach;
    }

    public void setFach(int fach) {
        this.fach.set(fach);
        this.fachValue = fach;
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
        this.nameValue = name;
    }

    public String getSorte() {
        return sorte.get();
    }

    public StringProperty sorteProperty() {
        return sorte;
    }

    public void setSorte(String sorte) {
        this.sorte.set(sorte);
        this.sorteValue = sorte;
    }

    public String getHersteller() {
        return hersteller.get();
    }

    public StringProperty herstellerProperty() {
        return hersteller;
    }

    public void setHersteller(String hersteller) {
        this.hersteller.set(hersteller);
        this.herstellerValue = hersteller;
    }

    public LocalDate getInspectionDate() {
        return inspectionDate.get();
    }

    public ObjectProperty<LocalDate> inspectionDateProperty() {
        return inspectionDate;
    }

    public void setInspectionDate(LocalDate date) {
        this.inspectionDate.set(date);
        this.inspectionDateValue = date;
    }

    public Set<Allergen> getAllergene() {
        return allergene;
    }

    public String getAllergeneString() {
        if (allergene == null || allergene.isEmpty()) return "";
        return allergene.stream().map(Allergen::toString).collect(Collectors.joining(", "));
    }

    @Override
    public String toString() {
        return "Fach: " + getFach() + ", Name: " + getName() + ", Sorte: " + getSorte() +
                ", Hersteller: " + getHersteller() + ", Datum: " + getInspectionDate() +
                ", Allergene: " + getAllergeneString();
    }

    // Custom serialization to handle transient JavaFX properties
    private void writeObject(ObjectOutputStream out) throws IOException {
        // update values before saving
        fachValue = getFach();
        nameValue = getName();
        sorteValue = getSorte();
        herstellerValue = getHersteller();
        inspectionDateValue = getInspectionDate();

        out.defaultWriteObject(); // save all serializable fields
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject(); // restore all serializable fields
        initProperties();       // recreate transient JavaFX properties from values
    }
}