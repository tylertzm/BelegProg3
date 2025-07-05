package domainlogic;

import java.util.ArrayList;
import java.util.List;

public class EventSystem {

    public interface EventListener {
        void onEvent(Event event);
    }

    public static class Event {
        private final String type;
        private final Object payload;

        public Event(String type, Object payload) {
            this.type = type;
            this.payload = payload;
        }
    }

    private final List<EventListener> listeners = new ArrayList<>();

    public void registerListener(EventListener listener) {
        listeners.add(listener);
    }

    public void fireEvent(Event event) {
        for (EventListener listener : listeners) {
            listener.onEvent(event);
        }
    }
}