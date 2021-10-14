package game.sniper_monkey.model.player;

import game.sniper_monkey.model.player.fighter.Fighter;

/**
 * Interface for a player which data can be read
 *
 * @author Elias Falk
 */
public interface ReadablePlayer {
    /**
     * Get the current animation
     *
     * @return The current fighter animation
     */
    FighterAnimation getCurrentFighterAnimation();

    /**
     * Is the player looking right
     *
     * @return Whether or not the player is looking right
     */
    boolean isLookingRight();

    /**
     * Get the class of the active fighter
     *
     * @return The Class of the active fighter
     */
    Class<? extends Fighter> getActiveFighterClass();
}
