package sniper_monkey.utils.collision;

import com.badlogic.gdx.math.Vector2;
import game.sniper_monkey.model.collision.CollisionEngine;
import game.sniper_monkey.model.collision.CollisionPair;
import game.sniper_monkey.model.collision.Hitbox;
import game.sniper_monkey.model.world.GameObject;
import game.sniper_monkey.model.world_brick.WorldBrick;
import game.sniper_monkey.utils.collision.CollisionMasks;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class CollisionMaskTest {

    @Before
    public void init() {

    }

    @Test
    public void testOverlap() {
        assertTrue(CollisionMasks.areMasksOverlapping(CollisionMasks.PLAYER_1, CollisionMasks.PLAYER_1));
    }

    @Test
    public void testNoOverlap() {
        assertFalse(CollisionMasks.areMasksOverlapping(CollisionMasks.PLAYER_1, CollisionMasks.PLAYER_2));
    }

    @Test
    public void testCustomMaskOverlap() {
        assertTrue(CollisionMasks.areMasksOverlapping(0b10000000, 0b10000000));
    }

    @Test
    public void testCustomMaskNoOverlap() {
        assertFalse(CollisionMasks.areMasksOverlapping(0b10000000, 0b01000000));
    }
}
