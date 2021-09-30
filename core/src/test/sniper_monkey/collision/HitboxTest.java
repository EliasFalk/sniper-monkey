package sniper_monkey.collision;

import com.badlogic.gdx.math.Vector2;
import game.sniper_monkey.collision.Hitbox;
import game.sniper_monkey.player.PlayerInputAction;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class HitboxTest {
    Hitbox hitbox;

    @Before
    public void initHitbox() {
        hitbox = new Hitbox(new Vector2(0, 0), new Vector2(10, 10));
    }

    /**
     * Colliders touching each other (no overlap)
     */

    @Test
    public void testLeftTouching() {
        Hitbox other = new Hitbox(new Vector2(10, 0), new Vector2(10, 10));
        assertEquals(false, hitbox.isOverlapping(other, new Vector2(0, 0)));
    }

    @Test
    public void testRightTouching() {
        Hitbox other = new Hitbox(new Vector2(-10, 0), new Vector2(10, 10));
        assertEquals(false, hitbox.isOverlapping(other, new Vector2(0, 0)));
    }

    @Test
    public void testUpTouching() {
        Hitbox other = new Hitbox(new Vector2(0, 10), new Vector2(10, 10));
        assertEquals(false, hitbox.isOverlapping(other, new Vector2(0, 0)));
    }

    @Test
    public void testDownTouching() {
        Hitbox other = new Hitbox(new Vector2(0, -10), new Vector2(10, 10));
        assertEquals(false, hitbox.isOverlapping(other, new Vector2(0, 0)));
    }

    /**
     * Colliders overlapping
     */

    @Test
    public void testLeftOverlap() {
        Hitbox other = new Hitbox(new Vector2(9, 0), new Vector2(10, 10));
        assertEquals(true, hitbox.isOverlapping(other, new Vector2(0, 0)));
    }

    @Test
    public void testRightOverlap() {
        Hitbox other = new Hitbox(new Vector2(-9, 0), new Vector2(10, 10));
        assertEquals(true, hitbox.isOverlapping(other, new Vector2(0, 0)));
    }

    @Test
    public void testUpOverlap() {
        Hitbox other = new Hitbox(new Vector2(0, 9), new Vector2(10, 10));
        assertEquals(true, hitbox.isOverlapping(other, new Vector2(0, 0)));
    }

    @Test
    public void testDownOverlap() {
        Hitbox other = new Hitbox(new Vector2(0, -9), new Vector2(10, 10));
        assertEquals(true, hitbox.isOverlapping(other, new Vector2(0, 0)));
    }

    /**
     * With offset causing no overlap
     */

    @Test
    public void testLeftOffsetOverlap() {
        Hitbox other = new Hitbox(new Vector2(9, 0), new Vector2(10, 10));
        assertEquals(false, hitbox.isOverlapping(other, new Vector2(-1, 0)));
    }

    @Test
    public void testRightOffsetOverlap() {
        Hitbox other = new Hitbox(new Vector2(-9, 0), new Vector2(10, 10));
        assertEquals(false, hitbox.isOverlapping(other, new Vector2(1, 0)));
    }

    @Test
    public void testUpOffsetOverlap() {
        Hitbox other = new Hitbox(new Vector2(0, 9), new Vector2(10, 10));
        assertEquals(false, hitbox.isOverlapping(other, new Vector2(0, -1)));
    }

    @Test
    public void testDownOffsetOverlap() {
        Hitbox other = new Hitbox(new Vector2(0, -9), new Vector2(10, 10));
        assertEquals(false, hitbox.isOverlapping(other, new Vector2(0, 1)));
    }
}
