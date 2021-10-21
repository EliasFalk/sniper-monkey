package game.sniper_monkey.model.player;

/**
 * Interface for a damageable player.
 * <p>
 * Used by most if not all concrete attack objects, see AttackObject.
 *
 * @author Elias Falk
 */
public interface DamageablePlayer {
    /**
     * Takes a certain amount of damage from the player.
     *
     * @param damageAmount The amount of damage to take.
     */
    void takeDamage(float damageAmount);
}
