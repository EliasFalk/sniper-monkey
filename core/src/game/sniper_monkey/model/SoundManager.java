package game.sniper_monkey.model;

import java.util.ArrayList;
import java.util.List;

public final class SoundManager {
    private SoundManager() {}

    private static final List<ISoundManagerObserver> observers = new ArrayList<>();

    public static void registerObserver(ISoundManagerObserver observer) {
        observers.add(observer);
    }

    public static void unregisterObserver(ISoundManagerObserver observer) {
        observers.remove(observer);
    }

    public static void PlaySoundEffect(SoundEffect effect) {
        for(ISoundManagerObserver observer : observers) {
            observer.OnSoundEffectPlayed(effect);
        }
    }
}
