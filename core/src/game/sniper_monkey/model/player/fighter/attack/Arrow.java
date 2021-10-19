package game.sniper_monkey.model.player.fighter.attack;

import com.badlogic.gdx.math.Vector2;
import game.sniper_monkey.model.player.Player;
import game.sniper_monkey.model.world_brick.WorldBrick;

public class Arrow extends AttackObject {

    private static final Vector2 attackHitboxSize = new Vector2(34,7);

    private final boolean lookingRight;

    public Arrow(float damage, float timeToLive, Vector2 spawnPos, int collisionMask, boolean lookingRight, Vector2 velocity) {
        super(damage, timeToLive, spawnPos, collisionMask, lookingRight, velocity, attackHitboxSize);

        this.lookingRight = lookingRight;

        addHitResponse(Player.class, gameObject -> {
            System.out.println(damage);
            Player player = (Player) gameObject;
            player.takeDamage(damage);
            delete(); // after hit
        });

        addHitResponse(WorldBrick.class, gameObject -> {
            delete(); // after hit
        });

    }

    public boolean isLookingRight() {
        return lookingRight;
    }


}
