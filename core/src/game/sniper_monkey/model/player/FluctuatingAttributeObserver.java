package game.sniper_monkey.model.player;

/**
 * An observer interface that is used to get notified of changes made to a fluctuating attribute.
 * <p>
 * Used by GameController.
 * Used by BarView.
 * (Used by Player to an extent. Player does not observe an attribute but rather gives a method to observe it's fluctuating attributes.).
 * Used by FluctuatingAttribute
 *
 * @author Elias Falk
 * @author Vincent Hellner
 */
public interface FluctuatingAttributeObserver {
    /**
     * The method that will be called each time the fluctuating attribute's value is changed.
     *
     * @param min     The minimum value of the attribute.
     * @param max     The maximum value of the attribute.
     * @param current The current value of the attribute.
     */
    void onValueChange(float min, float max, float current);
}
