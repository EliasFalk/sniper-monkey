package sniper_monkey.collision;

import com.badlogic.gdx.math.Vector2;
import game.sniper_monkey.collision.CollisionEngine;
import game.sniper_monkey.collision.Hitbox;
import game.sniper_monkey.player.PlayerFactory;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CollisionEngineTest {

    @Before
    public void InsertObj() {
        CollisionEngine.registerGameObject(PlayerFactory.createPlayer(), false);
    }

    @Test
    public void testNoCollision() {
        assertEquals(false, CollisionEngine.getCollision(new Hitbox(new Vector2(100, 100), new Vector2(10, 10)), new Vector2(0 ,0)));
    }

    @Test
    public void testInsertIntoDynamic() {
        CollisionEngine.registerGameObject(PlayerFactory.createPlayer(), true);
        assertEquals(true, CollisionEngine.getCollision(new Hitbox(new Vector2(0, 0), new Vector2(10, 10)), new Vector2(0 ,0)));
    }

    @Test
    public void testCollision() {
        assertEquals( true, CollisionEngine.getCollision(new Hitbox(new Vector2(0, 0), new Vector2(10, 10)), new Vector2(0 ,0)));
    }

    @Test
    public void testGetObjects() {
        assertEquals( true, CollisionEngine.getCollidingObjects(new Hitbox(new Vector2(5, 5), new Vector2(10, 10)), new Vector2(0 ,0)).size() > 0);
    }

    @Test
    public void testRegen() {
        CollisionEngine.regenerateSpatialHash();
        assertEquals( true, CollisionEngine.getCollision(new Hitbox(new Vector2(5, 5), new Vector2(10, 10)), new Vector2(0 ,0)));
    }
}
