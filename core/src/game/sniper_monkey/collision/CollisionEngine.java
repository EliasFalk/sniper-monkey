package game.sniper_monkey.collision;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.math.Vector2;
import game.sniper_monkey.world.GameObject;

import java.util.ArrayList;
import java.util.List;

public class CollisionEngine {
    private static final SpatialHash spatialHash = new SpatialHash(64, 64);
    private static final List<CollisionPair> dynamicObjects = new ArrayList<>();

    /**
     * Registers an object so it can be handled by the engine
     * @param gameObject The object to add
     * @param dynamicObject Whether the will be moving or not (if a non dynamicObject moves the engine's internal spatial hash needs to be regenerated)
     */
    public static void registerObject(GameObject gameObject, boolean dynamicObject) {
        if(dynamicObject) dynamicObjects.add(new CollisionPair(gameObject, gameObject.getHitbox()));
        else spatialHash.insert(gameObject);
    }

    /**
     * Regenerates the entire spatial hash taking into account the updated movement of hitboxes in it.
     */
    public static void regenerateSpatialHash() {
        spatialHash.regenerate();
    }

    /**
     * Check hitbox collision and returns a list of all objects the hitbox collided with
     * @param hitbox The hitbox to check
     * @param offset An offset of the hitbox position that can be used to predict future collision
     * @return The list of GameObjects that was collided with
     */
    public static List<GameObject> getCollidingObjects(Hitbox hitbox, Vector2 offset) {
        ArrayList<GameObject> hits = new ArrayList<>();
        List<CollisionPair> potentialHits = spatialHash.query(hitbox, offset);
        potentialHits.addAll(dynamicObjects);
        for (CollisionPair pair : potentialHits) {
            //Doesn't collide with itself
            if (pair.hitbox != hitbox && hitbox.isOverlapping(pair.hitbox, offset)) {
                hits.add(pair.gameObject);
            }
        }
        return hits;
    }

    /**
     * Check hitbox collision and returns a boolean that's true if there is a collision
     * @param hitbox The hitbox to check
     * @param offset An offset of the hitbox position that can be used to predict future collision
     * @return Was there a collision?
     */
    public static boolean getCollision(Hitbox hitbox, Vector2 offset) {
        List<CollisionPair> potentialHits = spatialHash.query(hitbox, offset);
        potentialHits.addAll(dynamicObjects);
        for (CollisionPair pair : potentialHits) {
            //Doesn't collide with itself
            if (pair.hitbox != hitbox && hitbox.isOverlapping(pair.hitbox, offset)) {
                return true;
            }
        }
        return false;
    }
}
