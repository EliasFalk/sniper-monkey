package sniper_monkey.collision;

import com.badlogic.gdx.math.Vector2;
import game.sniper_monkey.model.collision.CollisionEngine;
import game.sniper_monkey.model.collision.Hitbox;
import game.sniper_monkey.model.platform.Platform;
import game.sniper_monkey.model.world.GameObject;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class CollisionEngineTest {

    static GameObject obj;
    static GameObject objDynamic;

    @BeforeClass
    public static void InsertObj() {
        obj = new Platform(new Vector2(0, 0), false);
        objDynamic = new Platform(new Vector2(0, 0), false);
        CollisionEngine.registerGameObject(obj, false);
    }

    @Test
    public void testNoCollision() {
        assertFalse(CollisionEngine.getCollision(new Hitbox(new Vector2(100, 100), new Vector2(10, 10)), new Vector2(0, 0)));
    }

    @Test
    public void testInsertIntoDynamic() {
        CollisionEngine.registerGameObject(objDynamic, true);
        assertTrue(CollisionEngine.getCollision(new Hitbox(new Vector2(0, 0), new Vector2(10, 10)), new Vector2(0, 0)));
    }

    @Test
    public void testCollision() {
        assertTrue(CollisionEngine.getCollision(new Hitbox(new Vector2(0, 0), new Vector2(10, 10)), new Vector2(0, 0)));
    }

    @Test
    public void testGetObjects() {
        assertTrue(CollisionEngine.getCollidingObjects(new Hitbox(new Vector2(5, 5), new Vector2(10, 10)), new Vector2(0, 0)).size() > 0);
    }

    @Test
    public void testRegen() {
        CollisionEngine.regenerateSpatialHash();
        assertTrue(CollisionEngine.getCollision(new Hitbox(new Vector2(5, 5), new Vector2(10, 10)), new Vector2(0, 0)));
    }

    @Test
    public void unregisterObjects() {
        CollisionEngine.registerGameObject(objDynamic, true);
        CollisionEngine.unregisterGameObject(objDynamic);
        CollisionEngine.unregisterGameObject(obj);
        assertFalse(CollisionEngine.getCollision(new Hitbox(new Vector2(0, 0), new Vector2(10, 10)), new Vector2(0, 0)));
    }

    @Test
    public void testSameHitbox() {
        CollisionEngine.unregisterGameObject(obj);
        CollisionEngine.registerGameObject(objDynamic, true);
        assertFalse(CollisionEngine.getCollision(objDynamic.getHitbox(), new Vector2(0, 0)));
    }

    @Test
    public void testMaskedHitbox() {
        //TODO Make this test once there are suitable predefined GameObjects which have masks.
        assertTrue(true);
    }
}
