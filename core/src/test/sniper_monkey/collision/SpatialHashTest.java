package sniper_monkey.collision;

import com.badlogic.gdx.math.Vector2;
import game.sniper_monkey.model.collision.CollisionPair;
import game.sniper_monkey.model.collision.SpatialHash;
import game.sniper_monkey.model.world_brick.WorldBrick;
import game.sniper_monkey.model.world.GameObject;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class SpatialHashTest {
    SpatialHash sh;
    GameObject testObject;

    @Before
    public void initHitbox() {
        sh = new SpatialHash(64, 64);
        testObject = new WorldBrick(new Vector2(0, 0),"test");
    }

    @Test
    public void testInsertAndQuery() {
        sh.insert(testObject);
        List<CollisionPair> pairs = sh.query(testObject.getHitbox(), new Vector2(0, 0));
        assertEquals(new CollisionPair(testObject, testObject.getHitbox()), pairs.get(0));
    }

    @Test
    public void testRegenerate() {
        sh.insert(testObject);
        sh.regenerate();
        List<CollisionPair> pairs = sh.query(testObject.getHitbox(), new Vector2(0, 0));
        assertEquals(new CollisionPair(testObject, testObject.getHitbox()), pairs.get(0));
    }

    @Test
    public void testClear() {
        sh.insert(testObject);
        sh.clear();
        List<CollisionPair> pairs = sh.query(testObject.getHitbox(), new Vector2(0, 0));
        assertEquals(0, pairs.size());
    }

    @Test
    public void testInsertIntoExistingPartition() {
        sh.insert(testObject);
        sh.insert(testObject);
        List<CollisionPair> pairs = sh.query(testObject.getHitbox(), new Vector2(0, 0));
        assertEquals(2, pairs.size());
    }

    @Test
    public void testRemoveFromExistingPartition() {
        sh.remove(testObject);
        List<CollisionPair> pairs = sh.query(testObject.getHitbox(), new Vector2(0, 0));
        assertEquals(0, pairs.size());
    }
}
