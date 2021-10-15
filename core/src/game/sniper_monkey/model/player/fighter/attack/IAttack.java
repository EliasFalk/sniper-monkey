package game.sniper_monkey.model.player.fighter.attack;

import com.badlogic.gdx.math.Vector2;

/**
 * An interface used for attacks providing ability to perform the attack
 * and read its stamina cost.
 *
 * @author Elias Falk
 * @author Dadi Andrason
 */
public interface IAttack {

    /**
     * Performs an attack with and places a hitbox in the world and if the opponent collides with this hitbox then the attack is successful.
     *
     * @param attackFactor a float 0..n. A factor of how much more/less damage a fighter does. I.E. if fighter is "strong" then the attackFactor is higher.
     * @param playerPos a Vector2. A coordinate of the players position. Is used to correctly place the hitbox of the attack.
     * @param collisionMask an int 0..n. A collision mask to prevent the hitbox from colliding with the attacker.
     * @param lookingRight a boolean. Is the direction the player is facing. True if player is facing to the right, false if the player is facing the left.
     * @param hitboxSize a Vector2. Is the size of the players hitbox in x and y. Used to correctly place the attacks hitbox.
     * @return a boolean. true if the attack succeeded, false if it did not.
     */
    boolean performAttack(float attackFactor, Vector2 playerPos, int collisionMask, boolean lookingRight, Vector2 hitboxSize);

    /**
     * Gets the stamina cost of the attack.
     *
     * @return a float 0..n. Is the amount of stamina that the attack uses.
     */
    float getStaminaCost();

    /**
     * Checks if the fighter is mid-attack and during an animation.
     *
     * @return true if the fighter is mid attack, false if not.
     */
    boolean isFinished();

    /**
     * Gets the length of the specified attack in seconds.
     *
     * @return a float 0..n. where the float is the length of the attack in seconds.
     */
    float getAttackLength();

    /**
     * Gets the length of the hitstun of the attack in seconds.
     *
     * @return a float 0..n. where the float is the length of the hitstun in seconds.
     */
    float getHitStun();
}
