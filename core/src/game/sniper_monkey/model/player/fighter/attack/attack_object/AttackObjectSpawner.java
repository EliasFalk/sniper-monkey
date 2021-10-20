package game.sniper_monkey.model.player.fighter.attack.attack_object;

import com.badlogic.gdx.math.Vector2;
import game.sniper_monkey.model.world.World;

/**
 * A factory that creates the attackobjects and adds them to the world.
 *
 * @author Dadi Andrason
 * @author Kevin Jeryd
 *
 * Used by SwordAttack
 *
 * Uses World
 */
public class AttackObjectSpawner {

    /**
     * Creates the actual "attack object" of EvilWizards first attack and adds it to the world. In this case, the swing of the sword is created.
     *
     * @param damage        a float 0..n. A factor of how much more/less damage a fighter does. I.E. if fighter is "strong" then the attackFactor is higher.
     * @param timeToLive    a float 0..n. Determines for how long the object exists for in seconds.
     * @param spawnPos      a Vector2. A coordinate of the position where the hitbox is supposed to spawn.
     * @param collisionMask an int 0..n. A collision mask to prevent the hitbox from colliding with the attacker.
     * @param lookingRight  a boolean. Is the direction the player is facing. True if player is facing to the right, false if the player is facing the left.
     */
    public static void spawnEvilMagicSwing(float damage, float timeToLive, Vector2 spawnPos, int collisionMask, boolean lookingRight) {
        AttackObject swing = new EvilMagicSwing(damage, timeToLive, spawnPos, collisionMask, lookingRight);
        World.getInstance().queueAddGameObject(swing);
    }

    /**
     * Creates the attack object of EvilWizards second more stronger attack and adds it to the world.
     *
     * @param damage        a float 0..n. A factor of how much more/less damage a fighter does. I.E. if fighter is "strong" then the attackFactor is higher.
     * @param timeToLive    a float 0..n. Determines for how long the object exists for in seconds.
     * @param spawnPos      a Vector2. A coordinate of the position where the hitbox is supposed to spawn.
     * @param collisionMask an int 0..n. A collision mask to prevent the hitbox from colliding with the attacker.
     * @param lookingRight  a boolean. Is the direction the player is facing. True if player is facing to the right, false if the player is facing the left.
     */
    public static void spawnEvilMagicHammer(float damage, float timeToLive, Vector2 spawnPos, int collisionMask, boolean lookingRight) {
        AttackObject strongSwing = new EvilMagicHammer(damage, timeToLive, spawnPos, collisionMask, lookingRight);
        World.getInstance().queueAddGameObject(strongSwing);
    }

    /**
     * Creates an attack object of the Huntresses arrow attacks and adds it to the world.
     *
     * @param damage        a float 0..n. A factor of how much more/less damage a fighter does. I.E. if fighter is "strong" then the attackFactor is higher.
     * @param timeToLive    a float 0..n. Determines for how long the object exists for in seconds.
     * @param spawnPos      a Vector2. A coordinate of the position where the hitbox is supposed to spawn.
     * @param collisionMask an int 0..n. A collision mask to prevent the hitbox from colliding with the attacker.
     * @param lookingRight  a boolean. Is the direction the player is facing. True if player is facing to the right, false if the player is facing the left.
     * @param velocity      a Vector2. Is the velocity that the arrow has, and determines how fast the arrow moves.
     */
    public static void spawnHuntressArrowShot(float damage, float timeToLive, Vector2 spawnPos, int collisionMask, boolean lookingRight, Vector2 velocity) {
        AttackObject huntressArrow = new Arrow(damage, timeToLive, spawnPos, collisionMask, lookingRight, velocity);
        World.getInstance().queueAddGameObject(huntressArrow);
    }


}
