package game.sniper_monkey.model.player.fighter.attack;

import com.badlogic.gdx.math.Vector2;
import game.sniper_monkey.model.player.fighter.attack.attack_object.AttackObjectSpawner;
import game.sniper_monkey.model.world.CallbackTimer;

/**
 * An attack that represents the Huntresses second attack. A triple arrow bow shot.
 *
 * @author Dadi Andrason
 *
 * Used by AttackFactory
 *
 * Uses CallbackTimer
 * Uses AttackObjectSpawner
 */
public class BowTripleAttack implements IAttack {

    private final float damage = 12.5f;
    private boolean isFinished = true;
    private final CallbackTimer cbTimer;
    private final float attackLength = 1.5f;
    private final float projectileTimeToLive = 3f;
    private final float hitStunLength = 0.5f;
    private final float stamina = 17.5f;
    private Vector2 velocity;

    /**
     * Creates an object of the triple bow attack.
     */
    protected BowTripleAttack() {
        this.cbTimer = new CallbackTimer(attackLength, () -> isFinished = true);
        this.velocity = new Vector2(5,0);
    }

    @Override
    public boolean performAttack(float attackFactor, Vector2 playerPos, int collisionMask, boolean lookingRight, Vector2 hitboxSize) {
        if (isFinished) {
            float xSpawnPos = lookingRight ? hitboxSize.x : 0;
            Vector2 spawnPos = playerPos.add(xSpawnPos,30);
            AttackObjectSpawner.spawnHuntressArrowShot(attackFactor*damage, projectileTimeToLive, spawnPos, collisionMask, lookingRight, velocity);
            AttackObjectSpawner.spawnHuntressArrowShot(attackFactor*damage, projectileTimeToLive, spawnPos, collisionMask, lookingRight, velocity.cpy().add(0,1));
            AttackObjectSpawner.spawnHuntressArrowShot(attackFactor*damage, projectileTimeToLive, spawnPos, collisionMask, lookingRight, velocity.cpy().add(0,-1));
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
