package game.sniper_monkey.utils.time;

/**
 * An interface that serves the purpose to notify an observer of a timer of when that timer changes.
 */
public interface TimerObserver {
    /**
     * The method that is called when the timer changes it's time left or timer length.
     *
     * @param timerLength The length of the timer in seconds.
     * @param timeLeft    The time left of the timer in seconds.
     */
    void onTimeUpdated(float timerLength, float timeLeft);
}
