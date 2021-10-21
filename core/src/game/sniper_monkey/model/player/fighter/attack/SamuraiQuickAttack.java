package game.sniper_monkey.model.player.fighter.attack;

import com.badlogic.gdx.math.Vector2;
import game.sniper_monkey.model.player.fighter.Samurai;
import game.sniper_monkey.model.player.fighter.attack.attack_object.AttackObjectSpawner;
import game.sniper_monkey.model.world.CallbackTimer;

/**
 * A quick attack for the Samurai fighter, it's first attack.
 *
 * @author Dadi Andrason
 *
 * Used by AttackFactory
 *
 * Uses AttackObjectSpawner
 * Uses CallbackTimer
 */
public class SamuraiQuickAttack implements IAttack {

    private static final float damage = 10;
    private static final float attackLength = 0.4f;
    private static final float attackObjectTimeToLive = attackLength;
    private static final float hitStunLength = 0.15f;
    private static final float staminaCost = 10;
    private final CallbackTimer cbTimer;
    private boolean isFinished = true;


    /**
     * Creates an object of the Samurai Quick attack
     */
    protected SamuraiQuickAttack() {
        cbTimer = new CallbackTimer(attackLength, () -> isFinished = true);
    }


    @Override
    public boolean performAttack(float attackFactor, Vector2 playerPos, int collisionMask, boolean lookingRight, Vector2 hitboxSize) {
        if (isFinished) {
            float xSpawnPos = lookingRight ? hitboxSize.x : 0;
            Vector2 spawnPos = playerPos.add(xSpawnPos, 0);
            AttackObjectSpawner.spawnSamuraiQuickSwing(attackFactor * damage, attackObjectTimeToLive, spawnPos, collisionMask, lookingRight);
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
