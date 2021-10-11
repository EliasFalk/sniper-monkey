package game.sniper_monkey.model.player.fighter.attack;

import com.badlogic.gdx.math.Vector2;
import game.sniper_monkey.model.world.World;

public class ProjectileSpawner {

    public static Projectile spawnSwordAttack(float damage, float timeToLive, Vector2 playerPos, Vector2 attackSize) {
        Projectile projectile = new Projectile(damage, timeToLive, playerPos, attackSize);
        World.getInstance().addGameObject(projectile);
        return projectile;
    }

}
