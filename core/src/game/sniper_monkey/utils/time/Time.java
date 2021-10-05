package game.sniper_monkey.utils.time;

/**
 * A Time utility
 *
 * @author Elias Falk
 * @author Vincent Hellner
 * @author Kevin Jeryd
 */
public final class Time {

    /**
     * The time when the application started in milliseconds
     */
    public static final long START_TIME = System.currentTimeMillis();

    private Time() {
    }

    /**
     * Get the amount of time in seconds passed since starting the application
     *
     * @return The time in seconds since starting the application
     */
    public static float getElapsedTime() {
        return (System.currentTimeMillis() - START_TIME) / 1000f;
    }
}
