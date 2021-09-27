package game.sniper_monkey.player.fighter;

import com.badlogic.gdx.math.Vector2;
import game.sniper_monkey.collision.Hitbox;
import game.sniper_monkey.player.fighter.attack.IAttack;

import java.util.ArrayList;
import java.util.List;

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
     * Returns the size of the hitbox for the specific fighter.
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
    public void performAttack(int attackNum) {
        if (attackNum >= attacks.size()) {
            // TODO throw error?
        }
        attacks.get(attackNum).performAttack(ATTACK_FACTOR);
    }

    /**
     * Returns an int between 0..100 representing the cost of performing the attack specified by attackNum.
     *
     * @param attackNum An int 0..n representing a attack of the fighter.
     * @return The stamina cost of the attack.
     */
    public float getStaminaDecrease(int attackNum) {
        if (attackNum >= attacks.size()) {
            // TODO throw error?
        }
        return attacks.get(attackNum).getStaminaCost();
    }


}
