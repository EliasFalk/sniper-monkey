package game.sniper_monkey.controller;

/**
 * <p>
 * Used by GameController
 * Used by SniperMonkey
 * Used by CharacterSelectionScreenController
 * </p>
 * Interface representing a tickable controller which needs to be updated every frame.
 *
 * @author Kevin Jeryd
 */
public interface IScreenController {

    /**
     * Updates the controller.
     *
     * @param deltaTime The time between frames.
     */
    void tick(float deltaTime);

    /**
     * Updates the view with the new viewport width and height.
     *
     * @param w The width of the new viewport.
     * @param h The height of the new viewport.
     */
    void onResize(int w, int h);
}
