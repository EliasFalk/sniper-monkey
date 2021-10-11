package game.sniper_monkey.model.collision;

import com.badlogic.gdx.math.Vector2;
import game.sniper_monkey.model.world.GameObject;
import game.sniper_monkey.utils.collision.CollisionMasks;

import java.util.ArrayList;
import java.util.List;

/**
 * A static class used for checking collision between hitboxes and
 * optimizes this by storing static (Not moving) GameObjects in a spatial hash.
 *
 * @author Vincent Hellner
 */
public final class CollisionEngine {
    private static final SpatialHash spatialHash = new SpatialHash(64, 64);
    private static final List<CollisionPair> dynamicObjects = new ArrayList<>();

    private CollisionEngine() {
    }

    /**
     * Registers a GameObject so it can be handled by the engine
     *
     * @param gameObject    The GameObject to add
     * @param dynamicObject Whether the will be moving or not (if a non dynamicObject moves the engine's internal spatial hash needs to be regenerated)
     */
    public static void registerGameObject(GameObject gameObject, boolean dynamicObject) {
        if (dynamicObject) dynamicObjects.add(new CollisionPair(gameObject, gameObject.getHitbox()));
        else spatialHash.insert(gameObject);
    }

    /**
     * Removes a GameObject so it is no longer be handled by the engine.
     *
     * @param gameObject The GameObject to remove
     */
    public static void unregisterGameObject(GameObject gameObject) {
        dynamicObjects.removeIf(pair -> pair.gameObject == gameObject);
        spatialHash.remove(gameObject);
    }

    /**
     * Regenerates the entire spatial hash taking into account the updated movement of hitboxes in it.
     */
    public static void regenerateSpatialHash() {
        spatialHash.regenerate();
    }

    private static List<CollisionPair> getPotentialCollisions(Hitbox hitbox, Vector2 offset, int mask) {
        List<CollisionPair> potentialCollisions = spatialHash.query(hitbox, offset);
        potentialCollisions.addAll(dynamicObjects);

        //ignored if mask is 0 for performance reasons
        if(mask != 0) {
            potentialCollisions.removeIf(pair -> {
                int objectMask = pair.gameObject.getHitbox().getMask();
                return CollisionMasks.areMasksOverlapping(objectMask, mask);
            });
        }

        return potentialCollisions;
    }

    /**
     * Check hitbox collision and returns a list of all objects the hitbox collided with
     *
     * @param hitbox The hitbox to check
     * @param offset An offset of the hitbox position that can be used to predict future collision
     * @param mask   A bitmask used to ignore hitboxes
     * @return The list of GameObjects that was collided with
     */
    public static List<GameObject> getCollidingObjects(Hitbox hitbox, Vector2 offset, int mask) {
        List<GameObject> hits = new ArrayList<>();
        for (CollisionPair pair : getPotentialCollisions(hitbox, offset, mask)) {
            //Doesn't collide with itself
            if (pair.hitbox != hitbox && hitbox.isOverlapping(pair.hitbox, offset)) {
                hits.add(pair.gameObject);
            }
        }
        return hits;
    }

    /**
     * Check hitbox collision and returns a list of all objects the hitbox collided with
     *
     * @param hitbox The hitbox to check
     * @param offset An offset of the hitbox position that can be used to predict future collision
     * @return The list of GameObjects that was collided with
     */
    public static List<GameObject> getCollidingObjects(Hitbox hitbox, Vector2 offset) {
        return getCollidingObjects(hitbox, offset, 0);
    }

    /**
     * Check hitbox collision and returns a boolean that's true if there is a collision
     *
     * @param hitbox The hitbox to check
     * @param offset An offset of the hitbox position that can be used to predict future collision
     * @param mask   A bitmask used to ignore hitboxes
     * @return Was there a collision?
     */
    public static boolean getCollision(Hitbox hitbox, Vector2 offset, int mask) {
        for (CollisionPair pair : getPotentialCollisions(hitbox, offset, mask)) {
            //Doesn't collide with itself
            if (pair.hitbox != hitbox && hitbox.isOverlapping(pair.hitbox, offset)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Check hitbox collision and returns a boolean that's true if there is a collision
     *
     * @param hitbox The hitbox to check
     * @param offset An offset of the hitbox position that can be used to predict future collision
     * @return Was there a collision?
     */
    public static boolean getCollision(Hitbox hitbox, Vector2 offset) {
        return getCollision(hitbox, offset, 0);
    }
}
