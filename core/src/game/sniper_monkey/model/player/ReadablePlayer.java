package game.sniper_monkey.model.player;

import game.sniper_monkey.model.player.fighter.Fighter;
import game.sniper_monkey.model.player.fighter.attack.IAttack;

/**
 * Interface for a player which data can be read.
 * <p>
 * Used by Player.
 * Used by concrete FighterViews, such as EvilWizardView.
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
     *
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

    /**
     * Returns the length of a given attack, specified by the attackNum.
     *
     * @param attackNum The attack identifier. An int between 0..n.
     * @return The length to execute the attack in seconds.
     */
    float getAttackLength(int attackNum);

    /**
     * Returns the current health of the player.
     *
     * @return The health of the player.
     */
    float getHealth();


    /**
     * Returns the class of the active fighter's attack, specified by the attack number.
     *
     * @param attackNum The attack specifier. Starts at 0.
     * @return The class of the specified attack of the active fighter.
     */
    Class<? extends IAttack> getAttackClass(int attackNum);
}
