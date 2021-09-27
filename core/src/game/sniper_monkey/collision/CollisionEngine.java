package game.sniper_monkey.collision;

import com.badlogic.gdx.math.Vector2;
import game.sniper_monkey.world.GameObject;

import java.util.ArrayList;

public class CollisionEngine {
    static SpatialHash spatialHash = new SpatialHash(64, 64);

    /**
     * Add a game object to the world's spatial hash so it can be handled by the engine
     * @param gameObject The object to add
     * @param hitbox The objects corresponding hitbox
     */
    public static void insertIntoSpatialHash(GameObject gameObject, Hitbox hitbox) {
        spatialHash.insert(gameObject, hitbox);
    }

    public static void updateSpatialHash() {

    }

    /**
     * Check hitbox collision and returns a list of all objects the hitbox collided with
     * @param hitbox The hitbox to check
     * @param offset An offset of the hitbox position that can be used to predict future collision
     * @return The list of GameObjects that was collided with
     */
    public static ArrayList<GameObject> getCollidingObjects(Hitbox hitbox, Vector2 offset) {
        ArrayList<GameObject> hits = new ArrayList<GameObject>();
        ArrayList<CollisionPair> potentialHits = spatialHash.query(hitbox.getPosition());
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
        ArrayList<CollisionPair> potentialHits = spatialHash.query(hitbox.getPosition());
        for (CollisionPair pair : potentialHits) {
            //Doesn't collide with itself
            if (pair.hitbox != hitbox && hitbox.isOverlapping(pair.hitbox, offset)) {
                return true;
            }
        }
        return false;
    }
}
