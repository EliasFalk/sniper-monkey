package game.sniper_monkey.model.world;


/**
 * A timer that outputs true when finished and can give you them time from start.
 * @author  Kevin Jeryd
 */
public class Timer {
    private final long NANO = 1000000000; // Number of nanoseconds in a second

    //For tracking timer start, end and duration
    long start;
    long end;
    long seconds;
    int minutes;

    /**
     * Creates the timer object
     *
     * @param seconds A number representing how long the timer will count in seconds.
     */
    public Timer(int seconds) {
        this.seconds = seconds * NANO;
        start = end = 0;
    }

    /**
     * Activates the timer
     */
    public void start() {
        start = System.nanoTime();
        end = start + seconds;
    }

    /**
     * Ends the timer
     */
    public void endTimer() {
        end = 1;
    }

    //If you want to relate something to the time passed, however not used for now.

    /**
     * Returns the time passed since starting the timer
     *
     * @return An integer between 0..n representing amount of seconds since starting the timer.
     */
    public int getTimePassed() {
        return (int) (((System.nanoTime() - start) / NANO));
    }

    /**
     * Checks if the timer is done and deactivates it, if it is.
     *
     * @return A boolean-value representing if the timer is done or not.
     */
    public boolean isDone() {
        if (start > 0 && end > 0)
            return System.nanoTime() >= end;
        return false;
    }
}
