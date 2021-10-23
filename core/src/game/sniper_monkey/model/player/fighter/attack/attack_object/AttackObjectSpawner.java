package game.sniper_monkey.model.player.fighter.attack.attack_object;

import com.badlogic.gdx.math.Vector2;
import game.sniper_monkey.model.world.World;

/**
 * A factory that creates the attackobjects and adds them to the world.
 *
 * @author Dadi Andrason
 * @author Kevin Jeryd
 *
 * Used by BowAttack
 * Used by BowTripleAttack
 * Used by ElectricalSlashAttack
 * Used by ElectricalSmashAttack
 * Used by EvilMagicHammerAttack
 * Used by EvilMagicSwingAttack
 * Used by SamuraiQuickAttack
 * Used by SamuraiShurikenAttack
 *
 * Uses World
 * Uses Arrow
 * Uses ElectricalSlash
 * Uses ElectricalSmash
 * Uses EvilMagicHammer
 * Uses EvilMagicSwing
 * Uses SamuraiQuickSwing
 * Uses Shuriken
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

    /**
     * Creates an attack object of the Samurai's quick attack swing and adds it to the world.
     *
     * @param damage        a float 0..n. A factor of how much more/less damage a fighter does. I.E. if fighter is "strong" then the attackFactor is higher.
     * @param timeToLive    a float 0..n. Determines for how long the object exists for in seconds.
     * @param spawnPos      a Vector2. A coordinate of the position where the hitbox is supposed to spawn.
     * @param collisionMask an int 0..n. A collision mask to prevent the hitbox from colliding with the attacker.
     * @param lookingRight  a boolean. Is the direction the player is facing. True if player is facing to the right, false if the player is facing the left.
     */
    public static void spawnSamuraiQuickSwing(float damage, float timeToLive, Vector2 spawnPos, int collisionMask, boolean lookingRight) {
        AttackObject samuraiSwing = new SamuraiQuickSwing(damage, timeToLive, spawnPos, collisionMask, lookingRight);
        World.getInstance().queueAddGameObject(samuraiSwing);
    }

    /**
     * Creates an attackobject of the Samurai's shuriken and adds it to the world.
     *
     * @param damage        a float 0..n. Is how much damage the AttackObject is going to do.
     * @param timeToLive    a float 0..n. Determines for how long the object exists for in seconds.
     * @param spawnPos      a Vector2. A coordinate of the position where the hitbox of the shuriken is supposed to spawn.
     * @param collisionMask an int 0..n. A collision mask to prevent the hitbox of the shuriken from colliding with the attacker.
     * @param lookingRight  a boolean. Is the direction the player is facing. True if player is facing to the right, false if the player is facing the left.
     * @param velocity      a Vector2. Is the velocity that the shuriken has, and determines how fast the shuriken moves.
     */
    public static void spawnSamuraiShuriken(float damage, float timeToLive, Vector2 spawnPos, int collisionMask, boolean lookingRight, Vector2 velocity) {
        AttackObject samuraiShuriken = new Shuriken(damage, timeToLive, spawnPos, collisionMask, lookingRight, velocity);
        World.getInstance().queueAddGameObject(samuraiShuriken);
    }

    /**
     * Creates an attackobject of the Fantasy warrior's Electrical Slash and adds it to the world.
     *
     * @param damage        a float 0..n. Is how much damage the AttackObject is going to do.
     * @param timeToLive    a float 0..n. Determines for how long the object exists for in seconds.
     * @param spawnPos      a Vector2. A coordinate of the position where the hitbox of the electrical slash is supposed to spawn.
     * @param collisionMask an int 0..n. A collision mask to prevent the hitbox of the electrical slash from colliding with the attacker.
     * @param lookingRight  a boolean. Is the direction the player is facing. True if player is facing to the right, false if the player is facing the left.
     */
    public static void spawnElectricalSlash(float damage, float timeToLive, Vector2 spawnPos, int collisionMask, boolean lookingRight) {
        AttackObject electricalSlash = new ElectricalSlash(damage, timeToLive, spawnPos, collisionMask, lookingRight);
        World.getInstance().queueAddGameObject(electricalSlash);
    }

    /**
     * Creates an attack object of type Electrical smash and it is the second attack for the Fantasy Warrior. Also adds the object to the world.
     *
     * @param damage        a float 0..n. Is how much damage the AttackObject is going to do.
     * @param timeToLive    a float 0..n. Determines for how long the object exists for in seconds.
     * @param spawnPos      a Vector2. A coordinate of the position where the hitbox of the electrical smash is supposed to spawn.
     * @param collisionMask an int 0..n. A collision mask to prevent the hitbox of the electrical smash from colliding with the attacker.
     * @param lookingRight  a boolean. Is the direction the player is facing. True if player is facing to the right, false if the player is facing the left.
     */
    public static void spawnElectricalSmash(float damage, float timeToLive, Vector2 spawnPos, int collisionMask, boolean lookingRight) {
        AttackObject electricalSmash = new ElectricalSmash(damage, timeToLive, spawnPos, collisionMask, lookingRight);
        World.getInstance().queueAddGameObject(electricalSmash);
    }

}
