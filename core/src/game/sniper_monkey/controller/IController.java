package game.sniper_monkey.controller;

/**
 * <p>
 *     Used by GameController
 *     Used by SniperMonkey
 *     Used by CharacterSelectionScreenController
 * </p>
 * Interface representing a tickable controller which needs to be updated every frame.
 */
public interface IController {

    /**
     * Updates the controller.
     * @param deltaTime The time between frames.
     */
    void tick(float deltaTime);
}
