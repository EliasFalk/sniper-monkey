package sniper_monkey.collision;

import com.badlogic.gdx.math.Vector2;
import game.sniper_monkey.collision.CollisionEngine;
import game.sniper_monkey.collision.CollisionPair;
import game.sniper_monkey.collision.Hitbox;
import game.sniper_monkey.player.Player;
import game.sniper_monkey.player.PlayerFactory;
import game.sniper_monkey.world.GameObject;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CollisionEngineTest {

    @Before
    public void InsertObj() {
        CollisionEngine.registerObject(PlayerFactory.createPlayer(), false);
    }

    @Test
    public void testNoCollision() {
        assertEquals(false, CollisionEngine.getCollision(new Hitbox(new Vector2(100, 100), new Vector2(10, 10)), new Vector2(0 ,0)));
    }

    @Test
    public void testInsertIntoDynamic() {
        CollisionEngine.registerObject(PlayerFactory.createPlayer(), true);
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
