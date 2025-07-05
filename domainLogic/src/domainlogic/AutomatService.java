package domainlogic;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import kuchen.Allergen;

import java.time.LocalDate;
import java.util.Set;

public class AutomatService {

    private final Automat automat;

    public AutomatService(Automat automat) {
        this.automat = automat;
    }

    public int einfuegenKuchen(String name, String sorte, String hersteller, LocalDate inspektionsDatum, Set<Allergen> allergene) {
        Allergen[] allergenArray = allergene.toArray(new Allergen[0]);
        return automat.einfuegen(name, sorte, hersteller, allergenArray);
    }

    public boolean loeschenKuchen(int fach) {
        return automat.loeschen(fach);
    }

    public boolean updateKuchen(int fach, String name, String sorte, String hersteller, LocalDate inspektionsDatum, Set<Allergen> allergene) {
        // Implement update logic here
        return false;
    }

    public ObservableList<Kuchen> getKuchenList() {
        return FXCollections.observableArrayList(automat.getKuchenList());
    }
}