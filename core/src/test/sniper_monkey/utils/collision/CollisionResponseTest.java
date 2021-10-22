package sniper_monkey.utils.collision;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import com.badlogic.gdx.math.Vector2;
import game.sniper_monkey.model.PhysicsPosition;
import game.sniper_monkey.model.collision.CollisionEngine;
import game.sniper_monkey.model.collision.Hitbox;
import game.sniper_monkey.model.world.World;
import game.sniper_monkey.model.world_brick.WorldBrick;
import game.sniper_monkey.utils.collision.CollisionResponse;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CollisionResponseTest {

    private static WorldBrick obstacle;
    private static Hitbox hitbox;
    private static PhysicsPosition pp;

    @BeforeClass
    public static void init() {
        final HeadlessApplicationConfiguration config = new HeadlessApplicationConfiguration();
        new HeadlessApplication(new ApplicationAdapter() {
        }, config);
        World.getInstance().resetWorld();
        obstacle = new WorldBrick(new Vector2(0, 0), "unspecified");
        //Dynamic for faster cleanup
        CollisionEngine.registerGameObject(obstacle, true);
        hitbox = new Hitbox(new Vector2(0,0), new Vector2(16, 16));
        pp = new PhysicsPosition(new Vector2(0,0));
    }

    @AfterClass
    public static void cleanup() {
        CollisionEngine.unregisterGameObject(obstacle);
    }

    @Test
    public void testCollisionFromLeft() {
        hitbox.setPosition(new Vector2(-32, 0));
        pp = new PhysicsPosition(new Vector2(-32, 0));
        pp.setVelocity(new Vector2(32, 0));

        assertEquals(CollisionResponse.handleCollision(1, hitbox, 0, pp, () -> {}, () -> {}).getPosition(), new Vector2(-16, 0));
    }

    @Test
    public void testCollisionFromRight() {
        hitbox.setPosition(new Vector2(32, 0));
        pp = new PhysicsPosition(new Vector2(32, 0));
        pp.setVelocity(new Vector2(-32, 0));

        assertEquals(CollisionResponse.handleCollision(1, hitbox, 0, pp, () -> {}, () -> {}).getPosition(), new Vector2(16, 0));
    }

    @Test
    public void testCollisionFromTop() {
        hitbox.setPosition(new Vector2(0, 32));
        pp = new PhysicsPosition(new Vector2(0, 32));
        pp.setVelocity(new Vector2(0, -32));

        assertEquals(CollisionResponse.handleCollision(1, hitbox, 0, pp, () -> {}, () -> {}).getPosition(), new Vector2(0, 16));
    }

    @Test
    public void testCollisionFromBottom() {
        hitbox.setPosition(new Vector2(0, -32));
        pp = new PhysicsPosition(new Vector2(0, -32));
        pp.setVelocity(new Vector2(0, 32));

        assertEquals(CollisionResponse.handleCollision(1, hitbox, 0, pp, () -> {}, () -> {}).getPosition(), new Vector2(0, -16));
    }

    @Test
    public void testXCallback() {
        hitbox.setPosition(new Vector2(-32, 0));
        pp = new PhysicsPosition(new Vector2(-32, 0));
        pp.setVelocity(new Vector2(32, 0));

        CollisionResponse.handleCollision(1, hitbox, 0, pp, () -> assertTrue(true), Assert::fail);
    }

    @Test
    public void testYCallback() {
        hitbox.setPosition(new Vector2(0, 32));
        pp = new PhysicsPosition(new Vector2(0, 32));
        pp.setVelocity(new Vector2(0, -32));

        CollisionResponse.handleCollision(1, hitbox, 0, pp, Assert::fail, () -> assertTrue(true));
    }
}
