package game.sniper_monkey.model.time;

import java.util.ArrayList;
import java.util.List;

/**
 * A static class used for storing and updating UpdatableTimers
 *
 * @author Elias Falk
 */
public final class TimerBank {
    private static final List<UpdatableTimer> timers = new ArrayList<>();
    private static final List<UpdatableTimer> timersToBeAdded = new ArrayList<>();
    private static final List<UpdatableTimer> timersTobeRemoved = new ArrayList<>();

    private TimerBank() {
    }

    /**
     * Adds an updatable timer to the bank.
     * The timer won't be added until the next call on update.
     *
     * @param timer The timer to be added to the bank.
     * @return True if the timer was added to the bank. False if the timer already exists in the bank.
     * @throws IllegalArgumentException If the timer trying to be added is null.
     */
    public static boolean addTimer(UpdatableTimer timer) {
        if (timer == null) throw new IllegalArgumentException("Timer cannot be null.");
        if (timers.contains(timer) || timersToBeAdded.contains(timer)) return false;
        timersToBeAdded.add(timer);
        return true;
    }

    /**
     * Removes and existing timer from the bank.
     * The timer won't be removed until the next call on update.
     *
     * @param timer The timer to be removed to the bank.
     * @return True if the timer was removed from the bank.
     * False if the timer could not be found in the bank.
     * @throws IllegalArgumentException If the timer trying to be removed is null.
     */
    public static boolean removeTimer(UpdatableTimer timer) {
        if (timer == null) throw new IllegalArgumentException("Timer cannot be null.");
        if (!timers.contains(timer) || timersTobeRemoved.contains(timer)) return false;
        timersTobeRemoved.add(timer);
        return true;
    }

    /**
     * Calls update() on each timer in the timer bank.
     *
     * @param deltaTime The time between frames.
     */
    public static void updateTimers(float deltaTime) {
        addAndRemoveQueuedTimers();
        for (UpdatableTimer timer : timers) {
            timer.update(deltaTime);
        }
    }

    private static void addAndRemoveQueuedTimers() {
        timers.addAll(timersToBeAdded);
        timers.removeAll(timersTobeRemoved);
        timersToBeAdded.clear();
        timersTobeRemoved.clear();
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

    /**
     * Empties the timer bank of all timers and will no longer update these timers.
     */
    public static void clear() {
        timers.clear();
    }
}
