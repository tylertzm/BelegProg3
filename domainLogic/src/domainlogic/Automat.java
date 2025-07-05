package domainlogic;

import kuchen.Allergen;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class Automat {

    private final Kuchen[] kuchenFaecher;
    private final boolean[] belegteFaecher;
    private final EventSystem eventSystem;
    private final Set<Allergen> allergeneSet = Collections.synchronizedSet(new HashSet<>());

    public Automat(int faecherCount, EventSystem eventSystem) {
        this.kuchenFaecher = new Kuchen[faecherCount];
        this.belegteFaecher = new boolean[faecherCount];
        this.eventSystem = eventSystem;
    }

    private synchronized Optional<Integer> findeFreienFach() {
        for (int i = 0; i < belegteFaecher.length; i++) {
            if (!belegteFaecher[i]) {
                return Optional.of(i);
            }
        }
        return Optional.empty();
    }

    public synchronized int einfuegen(String name, String sorte, String hersteller, Allergen... allergene) {
        Optional<Integer> freiesFach = findeFreienFach();
        if (freiesFach.isEmpty()) {
            return -1;
        }
        int fach = freiesFach.get();
        LocalDate date = LocalDate.now();

        Set<Allergen> allergenSet = EnumSet.noneOf(Allergen.class);
        Collections.addAll(allergenSet, allergene);

        Kuchen kuchen = new Kuchen(fach, name, sorte, hersteller, date, allergenSet);
        kuchenFaecher[fach] = kuchen;
        belegteFaecher[fach] = true;

        double capacityUsed = (double) getBelegteFaecher() / getGesamtKapazitaet();
        if (capacityUsed >= 0.9) {
            eventSystem.fireEvent(new EventSystem.Event("CapacityExceeded", capacityUsed));
        }

        List<Allergen> newAllergens = Arrays.stream(allergene)
                .filter(a -> !allergeneSet.contains(a))
                .collect(Collectors.toList());

        if (!newAllergens.isEmpty()) {
            allergeneSet.addAll(newAllergens);
            eventSystem.fireEvent(new EventSystem.Event("AllergenAdded", newAllergens));
        }

        return fach;
    }

    public synchronized String auflisten() {
        StringBuilder sb = new StringBuilder();
        Arrays.stream(kuchenFaecher)
                .filter(Objects::nonNull)
                .forEach(k -> sb.append(k.toString()).append("\n"));
        return sb.toString();
    }

    public synchronized boolean loeschen(int fach) {
        if (fach >= 0 && fach < kuchenFaecher.length && belegteFaecher[fach]) {
            kuchenFaecher[fach] = null;
            belegteFaecher[fach] = false;
            return true;
        }
        return false;
    }

    public synchronized boolean updateDate(int fach, LocalDate newDate) {
        if (fach >= 0 && fach < kuchenFaecher.length && belegteFaecher[fach]) {
            kuchenFaecher[fach].setInspectionDate(newDate);
            return true;
        }
        return false;
    }

    public synchronized int getGesamtKapazitaet() {
        return kuchenFaecher.length;
    }

    public synchronized int getBelegteFaecher() {
        int count = 0;
        for (boolean b : belegteFaecher) {
            if (b) count++;
        }
        return count;
    }

    public synchronized boolean istVoll() {
        return getBelegteFaecher() == getGesamtKapazitaet();
    }

    public Set<Allergen> getAllergene() {
        synchronized (allergeneSet) {
            return Collections.unmodifiableSet(new HashSet<>(allergeneSet));
        }
    }

    public List<Kuchen> getKuchenList() {
        List<Kuchen> list = new ArrayList<>();
        for (Kuchen kuchen : kuchenFaecher) {
            if (kuchen != null) {
                list.add(kuchen);
            }
        }
        return list;
    }
}