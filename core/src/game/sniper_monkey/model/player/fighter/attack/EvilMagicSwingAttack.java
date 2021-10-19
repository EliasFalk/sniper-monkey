package game.sniper_monkey.model.player.fighter.attack;

import com.badlogic.gdx.math.Vector2;
import game.sniper_monkey.model.world.CallbackTimer;

/**
 * An attack that represents the "Evil Wizard's" first attack
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
public class EvilMagicSwingAttack implements IAttack {


    private static final float damage = 15;
    private static final float attackLength = 0.8f;
    private static final float attackObjectTimeToLive = attackLength;
    private static final float hitStunLength = 0.2f;
    private static final float staminaCost = 10;
    private final CallbackTimer cbTimer;
    private boolean isFinished = true;


    /**
     * Creates an object of a SwordAttack.
     */
    public EvilMagicSwingAttack() {
        this.cbTimer = new CallbackTimer(attackLength, () -> isFinished = true);
    }

    @Override
    public boolean performAttack(float attackFactor, Vector2 playerPos, int collisionMask, boolean lookingRight, Vector2 hitboxSize) {
        if (isFinished) {
            float xSpawnPos = lookingRight ? hitboxSize.x : 0;
            Vector2 spawnPos = playerPos.add(xSpawnPos, 0);
            AttackObjectSpawner.spawnEvilMagicSwing(attackFactor * damage, attackObjectTimeToLive, spawnPos, collisionMask, lookingRight);
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