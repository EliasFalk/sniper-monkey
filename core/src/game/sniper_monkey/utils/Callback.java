package game.sniper_monkey.utils;

/**
 * A functional interface used for callbacks
 *
 * @author Elias Falk
 * @author Vincent Hellner
 */
@FunctionalInterface
public interface Callback {
    /**
     * Call the callback method
     */
    void call();
}
