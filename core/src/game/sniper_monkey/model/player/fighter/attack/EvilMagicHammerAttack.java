package game.sniper_monkey.model.player.fighter.attack;

import com.badlogic.gdx.math.Vector2;
import game.sniper_monkey.model.world.CallbackTimer;

/**
 * An attack that represents the "Evil Wizard's" secondary attack, a stronger one.
 *
 * @author Kevin Jeryd
 * @author Dadi Andrason
 * <p>
 * Used by SniperMonkey
 * Used by AttackFactory
 * <p>
 * Uses CallbackTimer
 * Uses World
 * Uses AttackObjectSpawner
 * Uses AttackObject
 */
public class EvilMagicHammerAttack implements IAttack {

    private static final float damage = 20;
    private static final float attackLength = 1.2f;
    private static final float projectileTimeToLive = attackLength;
    private static final float hitStunLength = 0.5f;
    private final static float staminaCost = 15;
    private final CallbackTimer cbTimer;
    private boolean isFinished = true;

    /**
     * Creates an object of the strong sword attack.
     */
    public EvilMagicHammerAttack() {
        this.cbTimer = new CallbackTimer(attackLength, () -> isFinished = true);

    }


    @Override
    public boolean performAttack(float attackFactor, Vector2 playerPos, int collisionMask, boolean lookingRight, Vector2 hitboxSize) {
        if (isFinished) {
            float xSpawnPos = lookingRight ? hitboxSize.x : 0;
            Vector2 spawnPos = playerPos.add(xSpawnPos, 0);
            AttackObjectSpawner.spawnEvilMagicHammer(attackFactor * damage, projectileTimeToLive, spawnPos, collisionMask, lookingRight);
            // TODO delay attack?
            cbTimer.reset();
            cbTimer.start();
            isFinished = false;
            return true;
        }
        return false;
    }

    @Override
    public float getStaminaCost() {
        return staminaCost;
    }

    @Override
    public boolean isFinished() {
        return isFinished;
    }

    @Override
    public float getAttackLength() {
        return attackLength;
    }

    @Override
    public float getHitStunLength() {
        return hitStunLength;
    }
}
