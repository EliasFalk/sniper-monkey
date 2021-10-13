package game.sniper_monkey.model.player.fighter.attack;

import com.badlogic.gdx.math.Vector2;
import game.sniper_monkey.model.world.World;

public class AttackObjectSpawner {

    public static void spawnEvilMagicSwingAttack(float damage, float timeToLive, Vector2 spawnPos, int collisionMask, boolean lookingRight) {
        AttackObject swing = new EvilMagicSwing(damage, timeToLive, spawnPos, collisionMask, lookingRight);
        World.getInstance().queueAddGameObject(swing);
    }

}
