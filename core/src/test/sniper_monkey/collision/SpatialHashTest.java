package sniper_monkey.collision;

import com.badlogic.gdx.math.Vector2;
import game.sniper_monkey.collision.CollisionPair;
import game.sniper_monkey.collision.Hitbox;
import game.sniper_monkey.collision.SpatialHash;
import game.sniper_monkey.world.GameObject;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class SpatialHashTest {
    SpatialHash sh;
    GameObject testObject;
    Hitbox testHitbox;

    @Before
    public void initHitbox() {
        sh = new SpatialHash(64, 64);
        testObject = new GameObject() { @Override public void update(float deltaTime) { }};
        testHitbox = new Hitbox(new Vector2(0, 0), new Vector2(10, 10));
    }

    @Test
    public void testInsertAndQuery() {
        sh.insert(testObject, testHitbox);
        List<CollisionPair> pairs = sh.query(testHitbox, new Vector2(0, 0));
        assertEquals(new CollisionPair(testObject, testHitbox), pairs.get(0));
    }

    @Test
    public void testRegenerate() {
        sh.insert(testObject, testHitbox);
        sh.regenerate();
        List<CollisionPair> pairs = sh.query(testHitbox, new Vector2(0, 0));
        assertEquals(new CollisionPair(testObject, testHitbox), pairs.get(0));
    }

    @Test
    public void testClear() {
        sh.insert(testObject, testHitbox);
        sh.clear();
        List<CollisionPair> pairs = sh.query(testHitbox, new Vector2(0, 0));
        assertEquals(0, pairs.size());
    }

    @Test
    public void testInsertIntoExistingPartition() {
        sh.insert(testObject, testHitbox);
        sh.insert(testObject, testHitbox);
        List<CollisionPair> pairs = sh.query(testHitbox, new Vector2(0, 0));
        assertEquals(2, pairs.size());
    }

}
