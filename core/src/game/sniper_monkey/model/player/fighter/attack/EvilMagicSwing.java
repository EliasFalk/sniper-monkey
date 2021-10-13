package game.sniper_monkey.model.player.fighter.attack;

import com.badlogic.gdx.math.Vector2;
import game.sniper_monkey.model.player.Player;
import game.sniper_monkey.model.world.GameObject;

import java.util.function.Consumer;

public class EvilMagicSwing extends AttackObject {

    private final Vector2 attackHitboxSize = new Vector2(90,115);

    public EvilMagicSwing(float damage, float timeToLive, Vector2 spawnPos, int collisionMask, boolean lookingRight) {
        super(damage, timeToLive, spawnPos, collisionMask);

        Vector2 attackHitboxPos = spawnPos.add(lookingRight ? 0 : -attackHitboxSize.x, 0);
        setHitboxPos(attackHitboxPos);
        setHitboxSize(attackHitboxSize);

        addHitResponse(Player.class, gameObject -> {
            System.out.println("hitted player");
            //TODO add take damage functionality



            delete(); // after hit
        });
    }




    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

    }
}
