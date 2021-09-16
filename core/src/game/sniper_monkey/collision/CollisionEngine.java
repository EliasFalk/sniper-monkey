package game.sniper_monkey.collision;

import com.badlogic.gdx.math.Vector2;
import game.sniper_monkey.*;

import java.util.ArrayList;

public class CollisionEngine
{
    static SpatialHash spatialHash = new SpatialHash(64, 64);

    public static void insertIntoSpatialHash(GameObject gameObject, Hitbox hitbox)
    {
        spatialHash.insert(gameObject, hitbox);
    }

    public static void updateSpatialHash()
    {

    }

    public ArrayList<GameObject> getCollision(Hitbox hitbox, Vector2 offset)
    {
        ArrayList<GameObject> hits = new ArrayList<GameObject>();
        ArrayList<CollisionPair> potentialHits = spatialHash.query(hitbox.getPosition());
        for(CollisionPair pair : potentialHits)
        {
            if(hitbox.isOverlapping(pair.hitbox, offset))
            {
                hits.add(pair.gameObject);
            }
        }
        return hits;
    }
}
