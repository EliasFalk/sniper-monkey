package game.sniper_monkey.model.player.fighter.attack;

import com.badlogic.gdx.math.Vector2;
import game.sniper_monkey.model.world.CallbackTimer;
import game.sniper_monkey.model.world.World;

public class SwordAttack implements IAttack {

    public float getDamage() {
        return damage;
    }

    public float getTimeToLive() {
        return timeToLive;
    }

    public float getCooldownDuration() {
        return cooldownDuration;
    }

    public Vector2 getAttackSize() {
        return attackSize;
    }

    private final float staminaCost = 10;
    private final float damage = 15;
    private final float timeToLive = 2f;
    private final float cooldownDuration = 0.2f;
    private final Vector2 attackSize = new Vector2(50, 50);
    private boolean canAttack = true;
    private CallbackTimer cbTimer;
    private Projectile projectile;
    private float attackLength = 0.8f;

    public SwordAttack() {
        this.cbTimer = new CallbackTimer(attackLength, () -> {deSpawnAttack(this.projectile); canAttack = true;});
    }

    @Override
    public boolean performAttack(float attackFactor, Vector2 playerPos, int collisionMask) {
        if (canAttack) {
            playerPos.x += 30;
            playerPos.y += 30;
            projectile = ProjectileSpawner.spawnSwordAttack(damage, attackLength, playerPos, attackSize, collisionMask);
            cbTimer.reset();
            cbTimer.start();
            System.out.println("test1");
            canAttack = false;
            return true;
        }
        return false;
    }

    public void deSpawnAttack(Projectile projectile) {
        System.out.println("dabness despwan");
        World.getInstance().queueRemoveGameObject(projectile);
    }

    @Override
    public float getStaminaCost() {
        return staminaCost;
    }

    @Override
    public float getCooldown() {
        return cooldownDuration;
    }

    public boolean isFinished() {
        return canAttack;
    }

    @Override
    public float getAttackLength() {
        return attackLength;
    }

}
