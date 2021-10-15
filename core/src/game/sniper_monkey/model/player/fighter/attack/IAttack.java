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
    boolean performAttack(float attackFactor, Vector2 playerPos, int collisionMask, boolean lookingRight, Vector2 hitboxSize);

    float getStaminaCost();

    float getCooldown();

    float getTimeToLive();

    boolean isFinished();

    float getAttackLength();

    float getHitStun();
}
