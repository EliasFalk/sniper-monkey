package game.sniper_monkey.model.player.fighter.attack;

import com.badlogic.gdx.math.Vector2;
import game.sniper_monkey.model.platform.Platform;
import game.sniper_monkey.model.player.Player;

public class HuntressArrowShot extends AttackObject {

    private static final Vector2 attackHitboxSize = new Vector2(34,7);

    public HuntressArrowShot(float damage, float timeToLive, Vector2 spawnPos, int collisionMask, boolean lookingRight, Vector2 velocity) {
        super(damage, timeToLive, spawnPos, collisionMask, lookingRight, velocity, attackHitboxSize);

        addHitResponse(Player.class, gameObject -> {
            System.out.println(damage);
            Player player = (Player) gameObject;
            player.takeDamage(damage);
            delete(); // after hit
        });

        addHitResponse(Platform.class, gameObject -> {
            delete(); // after hit
        });

    }



}
