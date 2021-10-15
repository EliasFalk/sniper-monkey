package game.sniper_monkey.model.player;

import game.sniper_monkey.model.player.fighter.Fighter;

/**
 * Interface for a player which data can be read
 *
 * @author Elias Falk
 */
public interface ReadablePlayer {
    /**
     * Returns the physical state of the player.
     *
     * @return The physical state of the player.
     * @see PhysicalState
     */
    PhysicalState getCurrentPhysicalState();

    /**
     * Returns true if the player is facing the right. False if facing the left.
     * @return True if the player is facing the right. False if facing the left.
     */
    boolean isLookingRight();

    /**
     * Get the type of the active fighter.
     *
     * @return Type of the active fighter.
     */
    Class<? extends Fighter> getActiveFighterClass();

    /**
     * Returns the class of the inactive fighter.
     *
     * @return The class of the inactive fighter.
     */
    Class<? extends Fighter> getInactiveFighterClass();
}
