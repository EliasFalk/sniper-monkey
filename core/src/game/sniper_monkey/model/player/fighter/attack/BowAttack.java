package game.sniper_monkey.model.player.fighter.attack;

import com.badlogic.gdx.math.Vector2;
import game.sniper_monkey.model.world.CallbackTimer;

public class BowAttack implements IAttack {

    private final float damage = 15;
    private boolean canAttack = true;
    private final CallbackTimer cbTimer;
    private final float attackLength = 0.8f;
    private final float projectileTimeToLive = attackLength;
    private final float hitStunLength = 0.2f;
    private final float stamina = 10;
    private final Vector2 velocity;

    public BowAttack() {
        this.cbTimer = new CallbackTimer(attackLength, () -> canAttack = true);
        velocity = new Vector2(5, 0);
    }

    @Override
    public boolean performAttack(float attackFactor, Vector2 playerPos, int collisionMask, boolean lookingRight, Vector2 hitboxSize) {
        if (canAttack) {
            float xSpawnPos = lookingRight ? hitboxSize.x : 0;
            Vector2 spawnPos = playerPos.add(xSpawnPos,30);
            AttackObjectSpawner.spawnHuntressArrowShot(attackFactor*damage, projectileTimeToLive, spawnPos, collisionMask, lookingRight, velocity);
            cbTimer.reset();
            cbTimer.start();
            canAttack = false;
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
        return canAttack;
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
