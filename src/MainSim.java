import domainlogic.Automat;
import domainlogic.EventSystem;
import sim.KuchenEinfueger;
import sim.KuchenLoescher;

public class MainSim {

    public static void main(String[] args) {
        EventSystem eventSystem = new EventSystem();
        Automat automat = new Automat(5, eventSystem);

        Thread einfuegenThread = new Thread(new KuchenEinfueger(automat));
        Thread loeschenThread = new Thread(new KuchenLoescher(automat));

        einfuegenThread.start();
        loeschenThread.start();
    }

}