package sniper_monkey.collision;

import com.badlogic.gdx.math.Vector2;
import game.sniper_monkey.collision.CollisionPair;
import game.sniper_monkey.collision.Hitbox;
import game.sniper_monkey.world.GameObject;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CollisionPairTest {
    Hitbox hitbox;
    GameObject object;

    @Before
    public void initHitbox() {
        hitbox = new Hitbox(new Vector2(0, 0), new Vector2(10, 10));
        object = new GameObject() { @Override public void update(float deltaTime) { } };
    }

    /**
     * Colliders touching each other (no overlap)
     */

    @Test
    public void testEquals() {
        CollisionPair one = new CollisionPair(object, hitbox);
        CollisionPair two = new CollisionPair(object, hitbox);
        assertEquals(true, one.equals(two));
        assertEquals(false, one == two);
    }
}

