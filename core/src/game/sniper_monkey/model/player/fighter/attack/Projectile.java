package game.sniper_monkey.model.player.fighter.attack;

import com.badlogic.gdx.math.Vector2;
import game.sniper_monkey.model.world.GameObject;
import game.sniper_monkey.model.world.World;

public class Projectile extends GameObject {

    private final float damage;
    private final float timeToLive;
    public final Vector2 size;

    public Projectile(float damage, float timeToLive, Vector2 playerPos, Vector2 attackSize) {
        super(playerPos, true);
        this.size = attackSize;
        this.damage = damage;
        this.timeToLive = timeToLive;
    }
}
