package game.sniper_monkey.model.player.fighter.attack.attack_object;

import com.badlogic.gdx.math.Vector2;
import game.sniper_monkey.model.player.DamageablePlayer;
import game.sniper_monkey.model.player.Player;
import game.sniper_monkey.model.world_brick.WorldBrick;

/**
 * A class of the Arrow attackobject that the huntress shoots.
 *
 * @author Dadi Andrason
 *
 * Uses DamagablePlayer
 *
 * Used by AttackObjectSpawner
 */
public class Arrow extends AttackObject {

    private static final Vector2 attackHitboxSize = new Vector2(34,7);

    private final boolean lookingRight;

    /**
     * Creates an arrow with some attributes and adds a hit response that executes when hitting a player. Also disappears if it hits the wall.
     *
     * @param damage        a float 0..n. Is how much damage the AttackObject is going to do.
     * @param timeToLive    a float 0..n. Determines for how long the object exists for in seconds.
     * @param spawnPos      a Vector2. A coordinate of the position where the hitbox is supposed to spawn.
     * @param collisionMask an int 0..n. A collision mask to prevent the hitbox from colliding with the attacker.
     * @param lookingRight  a boolean. Is the direction the player is facing. True if player is facing to the right, false if the player is facing the left.
     * @param velocity      a Vector2. Is the velocity that the arrow has, and determines how fast the arrow moves.
     */
    protected Arrow(float damage, float timeToLive, Vector2 spawnPos, int collisionMask, boolean lookingRight, Vector2 velocity) {
        super(damage, timeToLive, spawnPos, collisionMask, lookingRight, velocity, attackHitboxSize);

        this.lookingRight = lookingRight;

        addHitResponse(Player.class, gameObject -> {
            DamageablePlayer player = (DamageablePlayer) gameObject;
            player.takeDamage(damage);
            delete(); // after hit
        });

        addHitResponse(WorldBrick.class, gameObject -> delete());

    }

    /**
     * Just returns the lookingRight variable.
     *
     * @return a boolean. Simply the lookingRight variable.
     */
    public boolean isLookingRight() {
        return lookingRight;
    } // this method cannot get tested because of protected constructor :(

}
