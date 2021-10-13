package game.sniper_monkey.model.player.fighter.attack;

import com.badlogic.gdx.math.Vector2;
import game.sniper_monkey.model.world.CallbackTimer;

/**
 * An attack that represents the "Evil Wizard's" first attack
 *
 * @author Kevin Jeryd
 * @author Dadi Andrason
 *
 * Used by SniperMonkey
 * Used by AttackFactory
 *
 * Uses CallbackTimer
 * Uses World
 * Uses ProjectileSpawner
 * Uses Projectile
 */

public class SwordAttack implements IAttack {

    public float getDamage() {
        return damage;
    }


    public float getCooldownDuration() {
        return cooldownDuration;
    }

    public Vector2 getAttackSize() {
        return attackSize;
    }

    private final float damage = 15;
    private final float cooldownDuration = 0.2f;
    private final Vector2 attackSize = new Vector2(50, 50);
    private boolean canAttack = true;
    private final CallbackTimer cbTimer;
    private final float attackLength = 0.8f;
    private final float projectileTimeToLive = attackLength;
    private Vector2 velocity = new Vector2(10,0);

    public SwordAttack() {
        this.cbTimer = new CallbackTimer(attackLength, () -> canAttack = true);
    }

    @Override
    public boolean performAttack(float attackFactor, Vector2 playerPos, int collisionMask, boolean lookingRight, Vector2 hitboxSize) {
        if (canAttack) {
            float xSpawnPos = lookingRight ? hitboxSize.x : 0;
            Vector2 spawnPos = playerPos.add(xSpawnPos,0);
            AttackObjectSpawner.spawnEvilMagicSwingAttack(damage, projectileTimeToLive, spawnPos, collisionMask, lookingRight);
            cbTimer.reset();

            cbTimer.start();
            System.out.println("test1");
            canAttack = false;
            return true;
        }
        return false;
    }


    @Override
    public float getStaminaCost() {
        return (float) 10;
    }

    @Override
    public float getCooldown() {
        return cooldownDuration;
    }

    @Override
    public float getTimeToLive() {
        return projectileTimeToLive;
    }

    public boolean isFinished() {
        return canAttack;
    }

    @Override
    public float getAttackLength() {
        return attackLength;
    }

}
