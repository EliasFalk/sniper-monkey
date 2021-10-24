package game.sniper_monkey.model.player.fighter.attack;

import com.badlogic.gdx.math.Vector2;
import game.sniper_monkey.model.player.fighter.attack.attack_object.AttackObjectSpawner;
import game.sniper_monkey.model.time.CallbackTimer;

/**
 * An attack that represents the Huntresses first attack.
 *
 * @author Dadi Andrason
 *
 * Used by AttackFactory
 * Used by HUDUtils
 *
 * Uses CallbackTimer
 * Uses AttackObjectSpawner
 */
public class BowAttack implements IAttack {

    private static final float damage = 15;
    private static final float attackLength = 0.8f;
    private static final float attackDelay = 0.4f;
    private static final float attackObjectTimeToLive = 2.5f;
    private static final float hitStunLength = 0.2f;
    private static final float stamina = 10;
    private boolean isFinished = true;
    private final CallbackTimer cbTimer;
    private final Vector2 velocity;

    /**
     * Creates an object of a bow attack.
     */
    protected BowAttack() {
        this.cbTimer = new CallbackTimer(attackLength, () -> isFinished = true);
        velocity = new Vector2(5*60, 0);
    }

    @Override
    public boolean performAttack(float attackFactor, Vector2 playerPos, int collisionMask, boolean lookingRight, Vector2 hitboxSize) {
        if (isFinished) {
            float xSpawnPos = lookingRight ? hitboxSize.x : 0;
            Vector2 spawnPos = playerPos.add(xSpawnPos,30);
            CallbackTimer attackDelayTimer = new CallbackTimer(attackDelay, () -> AttackObjectSpawner.spawnHuntressArrowShot(attackFactor * damage, attackObjectTimeToLive, spawnPos, collisionMask, lookingRight, velocity));
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
        return stamina;
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
