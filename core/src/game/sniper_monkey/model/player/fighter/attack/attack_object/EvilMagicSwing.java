package game.sniper_monkey.model.player.fighter.attack.attack_object;

import com.badlogic.gdx.math.Vector2;
import game.sniper_monkey.model.player.DamageablePlayer;
import game.sniper_monkey.model.player.Player;

/**
 * A class of the attackswords "swing". This is basically the attacks "projectile" and is the thing that determines if it collides with the opponent.
 *
 * @author Dadi Andrason
 * @author Kevin Jeryd
 *
 */
public class EvilMagicSwing extends AttackObject {

    private static final Vector2 attackHitboxSize = new Vector2(90,115);

    /**
     * Creates an EvilMagicSwing object and adds a hit response that executes when colliding with a player.
     *
     * @param damage a float 0..n. Is how much damage the AttackObject is going to do.
     * @param timeToLive a float 0..n. Determines for how long the object exists for in seconds.
     * @param spawnPos a Vector2. A coordinate of the position where the hitbox is supposed to spawn.
     * @param collisionMask an int 0..n. A collision mask to prevent the hitbox from colliding with the attacker.
     * @param lookingRight a boolean. Is the direction the player is facing. True if player is facing to the right, false if the player is facing the left.
     */
    protected EvilMagicSwing(float damage, float timeToLive, Vector2 spawnPos, int collisionMask, boolean lookingRight) {
        super(damage, timeToLive, spawnPos, collisionMask, lookingRight, attackHitboxSize);



        addHitResponse(Player.class, gameObject -> {
            DamageablePlayer player = (DamageablePlayer) gameObject;
            player.takeDamage(damage);
            delete(); // after hit
        });
    }

}
