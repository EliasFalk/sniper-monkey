package game.sniper_monkey.model.player.fighter.attack.attack_object;

import com.badlogic.gdx.math.Vector2;
import game.sniper_monkey.model.collision.CollisionEngine;
import game.sniper_monkey.model.world.GameObject;
import game.sniper_monkey.utils.time.CallbackTimer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * An abstract AttackObject class. Is a generic object in the world that can collide with a player and do something depending on the concrete implementation of the object.
 * I.E. a EvilMagicSwing object damages the player it colldes with.
 *
 * @author Dadi Andrason
 * @author Kevin Jeryd
 *
 */
public abstract class AttackObject extends GameObject {

    private final float damage;
    private final float timeToLive;
    private CallbackTimer timeToLiveTimer;
    private Vector2 velocity;

    private final Map<Class<? extends GameObject>, Consumer<GameObject>> objectCollidedDispatch;


    /**
     * Creates an attackobject. Is used by the concrete attack object classes that have a projectile.
     *
     * @param damage a float 0..n. Is how much damage the AttackObject is going to do.
     * @param timeToLive a float 0..n. Determines for how long the object exists for in seconds.
     * @param spawnPos a Vector2. A coordinate of the position where the hitbox is supposed to spawn.
     * @param collisionMask an int 0..n. A collision mask to prevent the hitbox from colliding with the attacker.
     * @param velocity a Vector2. The velocity that the object has.
     */
    protected AttackObject(float damage, float timeToLive, Vector2 spawnPos, int collisionMask, boolean lookingRight, Vector2 velocity, Vector2 attackHitboxSize) {
        super(spawnPos, true);
        timeToLiveTimer = new CallbackTimer(timeToLive, this::delete);
        timeToLiveTimer.reset();
        timeToLiveTimer.start();
        setHitboxMask(collisionMask);

        this.damage = damage;
        this.timeToLive = timeToLive;
        this.velocity = velocity;

        Vector2 attackHitboxPos = spawnPos.add(lookingRight ? 0 : -attackHitboxSize.x, 0);
        setHitboxPos(attackHitboxPos);
        setHitboxSize(attackHitboxSize);

        if (velocity.x < 0 && lookingRight) {
            velocity.scl(-1, 1);
        } else if (velocity.x > 0 && !lookingRight) {
            velocity.scl(-1, 1);
        }

        objectCollidedDispatch = new HashMap<>();
    }

    /**
     * The same as the constructor above.
     * However, this one spawns an object that has no velocity and therefore doesnt move.
     *
     * @param damage a float 0..n. Is how much damage the AttackObject is going to do.
     * @param timeToLive a float 0..n. Determines for how long the object exists for in seconds.
     * @param spawnPos a Vector2. A coordinate of the position where the hitbox is supposed to spawn.
     * @param collisionMask an int 0..n. A collision mask to prevent the hitbox from colliding with the attacker.
     */
    protected AttackObject(float damage, float timeToLive, Vector2 spawnPos, int collisionMask, boolean lookingRight, Vector2 attackHitboxSize) {
        this(damage, timeToLive, spawnPos, collisionMask, lookingRight, new Vector2(0,0), attackHitboxSize);
    }


    private void checkCollision() {
        List<GameObject> collidedObjects = CollisionEngine.getCollidingObjects(this.getHitbox(), new Vector2(0,0),getHitboxMask());
        for (GameObject gameObject : collidedObjects) {
            try {
                objectCollidedDispatch.get(gameObject.getClass()).accept(gameObject);
            } catch (NullPointerException ignored) {}
        }
    }

    /**
     * Adds a "response" to what happens when an object collides with a player.
     *
     * @param gameObjectType the type of the object. i.e. Player.class
     * @param response       the action that happens when the object collides with a player. I.e. player takes damage.
     */
    protected void addHitResponse(Class<? extends GameObject> gameObjectType, Consumer<GameObject> response) {
        objectCollidedDispatch.put(gameObjectType, response);
    }

    @Override
    public void update(float deltaTime) {
        checkCollision();
        if (!velocity.isZero()) {
            setPosition(getHitbox().getPosition().add(velocity.cpy().scl(deltaTime)));
        }
    }
}
