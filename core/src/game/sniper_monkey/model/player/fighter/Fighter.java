package game.sniper_monkey.model.player.fighter;

import com.badlogic.gdx.math.Vector2;
import game.sniper_monkey.model.player.fighter.attack.IAttack;

import java.util.ArrayList;
import java.util.List;

/**
 * An abstract class used for fighters providing functionality such as attacking,
 * storing basic stats and hitbox settings.
 *
 * @author Elias Falk
 */
public abstract class Fighter {

    public final float ATTACK_FACTOR;
    public final float DEFENSE_FACTOR;
    public final float SPEED_FACTOR;
    protected final List<IAttack> attacks = new ArrayList<>();
    private final Vector2 hitboxSize;

    /**
     * Creates a specific fighter, such as an Evil Wizard with x Factors.
     *
     * @param attackFactor  A float in the interval [0,1] which represents a percentage of the attack factor.
     * @param defenseFactor A float in the interval [0,1] which represents a percentage of the attack defense factor.
     * @param speedFactor   A float in the interval [0,1] which represents a percentage of the speed factor.
     * @param hitboxSize    The size of the hitbox for the specific fighter, based on the idle sprite sheet.
     */
    public Fighter(float attackFactor, float defenseFactor, float speedFactor, Vector2 hitboxSize) {
        this.ATTACK_FACTOR = attackFactor;
        this.DEFENSE_FACTOR = defenseFactor;
        this.SPEED_FACTOR = speedFactor;
        this.hitboxSize = hitboxSize;
    }

    /**
     * Returns a copy of the size of the hitbox for the specific fighter.
     *
     * @return The hitbox size represented in a Vector2, x=width, y=height.
     */
    public Vector2 getHitboxSize() {
        return hitboxSize.cpy();
    }

    /**
     * Returns the number of attacks the fighter has.
     *
     * @return An int between 0..n representing the number of attacks the fighter has.
     */
    public int getTotalAttacks() {
        return attacks.size();
    }

    /**
     * Performs the attack specified by the attackNum.
     *
     * @param attackNum A number between 0..n which determines which of the (n-1) attacks to perform.
     */
    public boolean performAttack(int attackNum, Vector2 playerPos, int collisionMask, boolean lookingRight, Vector2 hitboxSize) {
        if (attackNum >= attacks.size()) {
            throw new IllegalArgumentException("attack " + attackNum + " does not exist");
        }
        return attacks.get(attackNum).performAttack(ATTACK_FACTOR, playerPos, collisionMask, lookingRight, hitboxSize);
    }

    /**
     * Returns an int between 0..100 representing the cost of performing the attack specified by attackNum.
     *
     * @param attackNum An int 0..n representing a attack of the fighter.
     * @return The stamina cost of the attack.
     */
    public float getStaminaDecrease(int attackNum) {
        if (attackNum >= attacks.size()) {
            throw new IllegalArgumentException("attack " + attackNum + " does not exist");
        }
        return attacks.get(attackNum).getStaminaCost();
    }

    /**
     * Returns the class of the attack specified by attackNum.
     *
     * @param attackNum The attack number. Starts at 0.
     * @return The class of the attack.
     */
    public Class<?> getAttackClass(int attackNum) {
        if (attackNum >= attacks.size()) {
            throw new IllegalArgumentException("attack " + attackNum + " does not exist");
        }
        return attacks.get(attackNum).getClass();
    }

    /**
     * Checks if the fighter is mid-attack and during an animation.
     *
     * @return true if the fighter is mid attack, false if not.
     */
    public boolean isAttacking() {
        boolean isAttacking = false;
        for (IAttack attack : attacks) {
            if (!attack.isFinished()) {
                isAttacking = true;
            }
        }
        return isAttacking;
    }

    /**
     * Gets the length of the specified attack in seconds.
     * @param attackNum is the index of the attack.
     * @return a float 0..n. where the float is the length of the attack in seconds.
     */
    public float getAttackLength(int attackNum) {
        return attacks.get(attackNum).getAttackLength();
    }

    /**
     * Gets the length of the hitstun of the specified attack in seconds.
     * @param attackNum is the index of the attack.
     * @return a float 0..n. where the float is the length of the hitstun in seconds.
     */
    public float getHitStunTime(int attackNum) {
        return attacks.get(attackNum).getHitStun();
    }

}
