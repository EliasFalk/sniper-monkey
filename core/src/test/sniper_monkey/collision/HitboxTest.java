package sniper_monkey.collision;

import com.badlogic.gdx.math.Vector2;
import game.sniper_monkey.model.collision.Hitbox;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

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
        assertFalse(hitbox.isOverlapping(other, new Vector2(0, 0)));
    }

    @Test
    public void testRightTouching() {
        Hitbox other = new Hitbox(new Vector2(-10, 0), new Vector2(10, 10));
        assertFalse(hitbox.isOverlapping(other, new Vector2(0, 0)));
    }

    @Test
    public void testUpTouching() {
        Hitbox other = new Hitbox(new Vector2(0, 10), new Vector2(10, 10));
        assertFalse(hitbox.isOverlapping(other, new Vector2(0, 0)));
    }

    @Test
    public void testDownTouching() {
        Hitbox other = new Hitbox(new Vector2(0, -10), new Vector2(10, 10));
        assertFalse(hitbox.isOverlapping(other, new Vector2(0, 0)));
    }

    /**
     * Colliders overlapping
     */

    @Test
    public void testLeftOverlap() {
        Hitbox other = new Hitbox(new Vector2(9, 0), new Vector2(10, 10));
        assertTrue(hitbox.isOverlapping(other, new Vector2(0, 0)));
    }

    @Test
    public void testRightOverlap() {
        Hitbox other = new Hitbox(new Vector2(-9, 0), new Vector2(10, 10));
        assertTrue(hitbox.isOverlapping(other, new Vector2(0, 0)));
    }

    @Test
    public void testUpOverlap() {
        Hitbox other = new Hitbox(new Vector2(0, 9), new Vector2(10, 10));
        assertTrue(hitbox.isOverlapping(other, new Vector2(0, 0)));
    }

    @Test
    public void testDownOverlap() {
        Hitbox other = new Hitbox(new Vector2(0, -9), new Vector2(10, 10));
        assertTrue(hitbox.isOverlapping(other, new Vector2(0, 0)));
    }

    /**
     * With offset causing no overlap
     */

    @Test
    public void testLeftOffsetOverlap() {
        Hitbox other = new Hitbox(new Vector2(9, 0), new Vector2(10, 10));
        assertFalse(hitbox.isOverlapping(other, new Vector2(-1, 0)));
    }

    @Test
    public void testRightOffsetOverlap() {
        Hitbox other = new Hitbox(new Vector2(-9, 0), new Vector2(10, 10));
        assertFalse(hitbox.isOverlapping(other, new Vector2(1, 0)));
    }

    @Test
    public void testUpOffsetOverlap() {
        Hitbox other = new Hitbox(new Vector2(0, 9), new Vector2(10, 10));
        assertFalse(hitbox.isOverlapping(other, new Vector2(0, -1)));
    }

    @Test
    public void testDownOffsetOverlap() {
        Hitbox other = new Hitbox(new Vector2(0, -9), new Vector2(10, 10));
        assertFalse(hitbox.isOverlapping(other, new Vector2(0, 1)));
    }

    @Test
    public void testGetPosition() {
        assertEquals(new Vector2(0, 0), hitbox.getPosition());
    }

    @Test
    public void testGetSize() {
        assertEquals(new Vector2(10, 10), hitbox.getSize());
    }

    @Test
    public void testSetSize() {
        hitbox.setSize(new Vector2(20, 20));
        assertEquals(new Vector2(20, 20), hitbox.getSize());
    }

    @Test
    public void testSetPosition() {
        hitbox.setPosition(new Vector2(10, 10));
        assertEquals(new Vector2(10, 10), hitbox.getPosition());
    }

    @Test
    public void addMask() {
        hitbox.setMask(0b001);
        hitbox.addMask(0b100);
        assertEquals(0b101, hitbox.getMask());
    }
}
