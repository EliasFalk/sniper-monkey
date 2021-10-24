package game.sniper_monkey.model.player.fighter.attack.attack_object;

import com.badlogic.gdx.math.Vector2;
import game.sniper_monkey.model.player.DamageablePlayer;
import game.sniper_monkey.model.player.Player;

/**
 * A class for the attack object of the Fantasy warriors second attack, the Electrical smash. This is the literal object in the world that collides with the player.
 *
 * @author Dadi Andrason
 *
 * Used by AttackObjectSpawner
 */
public class ElectricalSmash extends AttackObject {

    /**
     * Size of the attack objects hitbox
     */
    public static final Vector2 attackHitboxSize = new Vector2(53, 100);

    /**
     * Creates an attack object of the electrical smash attack. Adds a hit response to it that executes when the attack object collides with a player.
     *
     * @param damage a float 0..n. Is how much damage the AttackObject is going to do.
     * @param timeToLive a float 0..n. Determines for how long the object exists for in seconds.
     * @param spawnPos a Vector2. A coordinate of the position where the hitbox is supposed to spawn.
     * @param collisionMask an int 0..n. A collision mask to prevent the hitbox from colliding with the attacker.
     * @param lookingRight a boolean. Is the direction the player is facing. True if player is facing to the right, false if the player is facing the left.
     */
    protected ElectricalSmash(float damage, float timeToLive, Vector2 spawnPos, int collisionMask, boolean lookingRight) {
        super(damage, timeToLive, spawnPos, collisionMask, lookingRight, attackHitboxSize);

        addHitResponse(Player.class, gameObject -> {
            DamageablePlayer player = (DamageablePlayer) gameObject;
            player.takeDamage(damage);
            delete();
        });
    }



}
