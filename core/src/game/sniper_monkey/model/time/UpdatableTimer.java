package game.sniper_monkey.model.time;

/**
 * An interface providing functionality for timers that should be updated every frame
 *
 * Used by CallBackTimer.
 * 
 * @author Elias Falk
 */
public interface UpdatableTimer {
    /**
     * Update the timer
     * @param deltaTime Time since last update in seconds
     */
    void update(float deltaTime);
}
