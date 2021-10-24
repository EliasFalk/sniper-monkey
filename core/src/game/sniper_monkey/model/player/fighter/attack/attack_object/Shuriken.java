package game.sniper_monkey.model.player.fighter.attack.attack_object;

import com.badlogic.gdx.math.Vector2;
import game.sniper_monkey.model.player.DamageablePlayer;
import game.sniper_monkey.model.player.Player;
import game.sniper_monkey.model.world_brick.WorldBrick;

/**
 * A class of the Shuriken attackobject that the Samurai's second attack uses.
 *
 * @author Dadi Andrason
 *
 * Used by AttackObjectSpawner
 * Used by ShurikenView
 * Used by GameObjectViewFactory
 *
 * Uses DamageablePlayer
 */
public class Shuriken extends AttackObject {

    private static final Vector2 attackHitboxSize = new Vector2(16,14);


    /**
     * Creates a shuriken with specified attributes and adds a hitresponse to it that executes when colliding with a player.
     * Gets removed if it collides with a wall.
     *
     * @param damage        a float 0..n. Is how much damage the AttackObject is going to do.
     * @param timeToLive    a float 0..n. Determines for how long the object exists for in seconds.
     * @param spawnPos      a Vector2. A coordinate of the position where the hitbox of the shuriken is supposed to spawn.
     * @param collisionMask an int 0..n. A collision mask to prevent the hitbox of the shuriken from colliding with the attacker.
     * @param lookingRight  a boolean. Is the direction the player is facing. True if player is facing to the right, false if the player is facing the left.
     * @param velocity      a Vector2. Is the velocity that the shuriken has, and determines how fast the shuriken moves.
     */
    protected Shuriken(float damage, float timeToLive, Vector2 spawnPos, int collisionMask, boolean lookingRight, Vector2 velocity) {
        super(damage, timeToLive, spawnPos, collisionMask, lookingRight, velocity, attackHitboxSize);

        addHitResponse(Player.class, gameObject -> {
            DamageablePlayer player = (DamageablePlayer) gameObject;
            player.takeDamage(damage);
            delete();
        });

        addHitResponse(WorldBrick.class, gameObject -> delete());

    }
}
