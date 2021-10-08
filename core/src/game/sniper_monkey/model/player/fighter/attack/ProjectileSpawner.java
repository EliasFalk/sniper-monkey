package game.sniper_monkey.model.player.fighter.attack;

import com.badlogic.gdx.math.Vector2;
import game.sniper_monkey.model.world.World;

public class ProjectileSpawner {

    public static void spawnSwordAttack(float damage, float timeToLive, Vector2 playerPos, Vector2 attackSize) {
        World.getInstance().addGameObject(new Projectile(damage, timeToLive, playerPos, attackSize));
    }

}
