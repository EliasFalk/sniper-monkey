package game.sniper_monkey.utils.time;

/**
 * An interface providing functionality for timers that should be updated every frame
 *
 * @author Elias Falk
 */
public interface UpdatableTimer {
    void update(float deltaTime);
}
