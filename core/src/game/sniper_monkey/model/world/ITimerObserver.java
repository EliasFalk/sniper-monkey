package game.sniper_monkey.model.world;

/**
 * An observer used for observing when the time has changed in the world.
 *
 * @author Kevin Jeryd
 */
public interface ITimerObserver {

    /**
     * Called when the time has changed
     * @param time Inform the observer of the new time
     */
    void onTimerChange(int time);
}
