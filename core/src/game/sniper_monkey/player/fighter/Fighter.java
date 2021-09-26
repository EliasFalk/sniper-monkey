package game.sniper_monkey.player.fighter;

import game.sniper_monkey.player.fighter.attack.IAttack;

import java.util.ArrayList;
import java.util.List;

public abstract class Fighter {

    public final float ATTACK_FACTOR;
    public final float DEFENSE_FACTOR;
    public final float SPEED_FACTOR;
    protected final List<IAttack> attacks = new ArrayList<>();

    public Fighter(float attackFactor, float defenseFactor, float speedFactor) {
        this.ATTACK_FACTOR = attackFactor;
        this.DEFENSE_FACTOR = defenseFactor;
        this.SPEED_FACTOR = speedFactor;
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
        attacks.get(attackNum).performAttack(ATTACK_FACTOR); // attackModifier for when blocked for a while? -> attackDmg need to decrease
    }

    /**
     * Returns an int between 0..100 representing the cost of performing the attack specified by attackNum.
     *
     * @param attackNum An int 0..n representing a attack of the fighter.
     * @return The stamina cost of the attack.
     */
    public int getStaminaDecrease(int attackNum) {
        if (attackNum >= attacks.size()) {
            // TODO throw error?
        }
        return attacks.get(attackNum).getStaminaCost();
    }


}
