package game.sniper_monkey.model.player;

/**
 * Interface for a readable player
 *
 * @author Elias Falk
 */
public interface ControllablePlayer {
    /**
     * Set an input action of the ControllablePlayer.
     *
     * @param action The action to set.
     * @see PlayerInputAction
     */
    void setInputAction(PlayerInputAction action);
}
