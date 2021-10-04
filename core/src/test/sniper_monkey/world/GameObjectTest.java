package sniper_monkey.world;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import game.sniper_monkey.collision.Hitbox;
import game.sniper_monkey.player.Player;
import game.sniper_monkey.world.GameObject;
import game.sniper_monkey.world.World;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;


public class GameObjectTest {

    private static GameObject object;

    @BeforeClass
    public static void before() {
        object = new GameObject(false) {
            @Override
            public void update(float deltaTime) {

            }
        };
    }

    @Test
    public void testGetPosition() {
        assertEquals(object.getPos(), new Vector2(0, 0));
    }

    @Test
    public void testIsDynamic() {
        assertFalse(object.isDynamic());
    }

    @Test
    public void testGetHitbox() {
        assertNotNull(object.getHitbox());
    }

}
