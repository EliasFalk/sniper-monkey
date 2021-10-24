package game.sniper_monkey.model.player.fighter.attack;

import com.badlogic.gdx.math.Vector2;
import game.sniper_monkey.model.player.fighter.attack.attack_object.AttackObjectSpawner;
import game.sniper_monkey.model.time.CallbackTimer;

/**
 * An class of an attack that represents the Samurai's second attack, the Shuriken attack.
 *
 * @author Dadi Andrason
 *
 * Used by AttackFactory
 * Used by HUDUtils
 *
 * Uses CallbackTimer
 * Uses AttackObjectSpawner
 */
public class SamuraiShurikenAttack implements IAttack {

    private static final float damage = 12.5f;
    private static final float attackLength = 1.2f;
    private static final float attackDelay = 0.75f;
    private static final float attackObjectTimeToLive = 3f;
    private static final float hitStunLength = 1f;
    private static final float staminaCost = 20;
    private final CallbackTimer cbTimer;
    private boolean isFinished = true;
    private Vector2 velocity;


    /**
     * Creates an object of the SamuraiShurikenAttack.
     */
    protected SamuraiShurikenAttack() {
        this.cbTimer = new CallbackTimer(attackLength, () -> isFinished = true);
        this.velocity = new Vector2(5*60f, 0);
    }


    @Override
    public boolean performAttack(float attackFactor, Vector2 playerPos, int collisionMask, boolean lookingRight, Vector2 hitboxSize) {
        if (isFinished) {
            float xSpawnPos = lookingRight ? hitboxSize.x : 0;
            Vector2 spawnPos = playerPos.add(xSpawnPos,30);
            CallbackTimer attackDelayTimer = new CallbackTimer(attackDelay, () -> {
                AttackObjectSpawner.spawnSamuraiShuriken(attackFactor*damage, attackObjectTimeToLive, spawnPos, collisionMask, lookingRight, velocity);
                AttackObjectSpawner.spawnSamuraiShuriken(attackFactor*damage, attackObjectTimeToLive, spawnPos, collisionMask, lookingRight, velocity.cpy().add(0, 0.3f*60));
                AttackObjectSpawner.spawnSamuraiShuriken(attackFactor*damage, attackObjectTimeToLive, spawnPos, collisionMask, lookingRight, velocity.cpy().add(0, -0.3f*60));
            });
            attackDelayTimer.setStopAutoUpdatingOnFinish(true);
            attackDelayTimer.start();

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
