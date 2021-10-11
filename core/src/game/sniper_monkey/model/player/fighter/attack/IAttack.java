package game.sniper_monkey.model.player.fighter.attack;

import com.badlogic.gdx.math.Vector2;

/**
 * An interface used for attacks providing ability to perform the attack
 * and read its stamina cost.
 *
 * @author Elias Falk
 */
public interface IAttack {
    boolean performAttack(float attackFactor, Vector2 playerPos);

    float getStaminaCost();

    float getCooldown();

    float getTimeToLive();

    boolean isFinished();

    float getAttackLength();

}
