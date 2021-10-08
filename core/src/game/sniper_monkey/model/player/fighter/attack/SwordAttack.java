package game.sniper_monkey.model.player.fighter.attack;

import com.badlogic.gdx.math.Vector2;
import game.sniper_monkey.model.collision.Hitbox;
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
    private final float timeToLive = 2;
    private final float cooldownDuration = 0.2f;
    private final Vector2 attackSize = new Vector2(50, 50);



    @Override
    public void performAttack(float attackFactor, Vector2 playerPos) {
        playerPos.x += 30;
        playerPos.y += 30;
        ProjectileSpawner.spawnSwordAttack(damage, timeToLive, playerPos, attackSize);
    }

    @Override
    public float getStaminaCost() {
        return staminaCost;
    }

    @Override
    public float getCooldown() {
        return cooldownDuration;
    }
}
