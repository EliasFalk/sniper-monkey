package game.sniper_monkey.model.player.fighter.attack;

/**
 * An interface used for attacks providing ability to perform the attack
 * and read its stamina cost.
 *
 * @author Elias Falk
 */
public interface IAttack {
    void performAttack(float attackFactor);

    float getStaminaCost();

    float getCooldown();
}
