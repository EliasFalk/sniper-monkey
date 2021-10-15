package game.sniper_monkey.model;

import java.util.ArrayList;
import java.util.List;

public final class AudibleEventManager {
    private AudibleEventManager() {}

    private static final List<IAudibleEventObserver> observers = new ArrayList<>();

    public static void registerObserver(IAudibleEventObserver observer) {
        observers.add(observer);
    }

    public static void unregisterObserver(IAudibleEventObserver observer) {
        observers.remove(observer);
    }

    public static void createEvent(AudibleEvent event) {
        for(IAudibleEventObserver observer : observers) {
            observer.OnAudibleEvent(event);
        }
    }
}
