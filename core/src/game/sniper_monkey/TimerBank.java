package game.sniper_monkey;

import java.util.ArrayList;
import java.util.List;

public class TimerBank {
    private static final List<UpdatableTimer> timers = new ArrayList<>();

    /**
     * Adds an updatable timer to the bank.
     *
     * @param timer The timer to be added to the bank.
     * @return True if the timer was added to the bank. False if the timer already exists in the bank.
     * @throws IllegalArgumentException If the timer trying to be added is null.
     */
    public static boolean addTimer(UpdatableTimer timer) {
        if (timer == null) throw new IllegalArgumentException("Timer cannot be null.");
        if (timers.contains(timer)) return false;
        timers.add(timer);
        return true;
    }

    /**
     * Removes and existing timer from the bank.
     *
     * @param timer The timer to be removed to the bank.
     * @return True if the timer was removed from the bank.
     * False if the timer could not be found in the bank.
     * @throws IllegalArgumentException If the timer trying to be removed is null.
     */
    public static boolean removeTimer(UpdatableTimer timer) {
        if (timer == null) throw new IllegalArgumentException("Timer cannot be null.");
        if (!timers.contains(timer)) return false;
        timers.remove(timer);
        return true;
    }

    /**
     * Calls update() on each timer in the timer bank.
     *
     * @param deltaTime The time between frames.
     */
    public static void updateTimers(float deltaTime) {
        for (UpdatableTimer timer : timers) {
            timer.update(deltaTime);
        }
    }

    /**
     * Returns true if the timer exists within the bank, false if not.
     *
     * @param timer The timer being checked if it exists in the bank.
     * @return True if the timer exists within the bank, false if not.
     */
    public static boolean contains(UpdatableTimer timer) {
        return timers.contains(timer);
    }
}
