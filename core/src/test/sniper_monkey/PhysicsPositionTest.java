package sniper_monkey;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import com.badlogic.gdx.math.Vector2;
import game.sniper_monkey.model.PhysicsPosition;
import game.sniper_monkey.utils.Config;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

// TODO create better tests that use config values.
public class PhysicsPositionTest {

    private static final float deltaTime = 1 / 60f;
    private static PhysicsPosition physicsPos;
    private static String physicsConsts;
    private static float DRAG;
    private static float GRAVITY;

    @BeforeClass
    public static void initHeadless() {
        final HeadlessApplicationConfiguration config = new HeadlessApplicationConfiguration();
        new HeadlessApplication(new ApplicationAdapter() {
        }, config);
    }

    @BeforeClass
    public static void readConfig() {
        physicsConsts = "cfg/physics.cfg";
        Config.readConfigFile(physicsConsts);
        DRAG = Config.getNumber(physicsConsts, "DRAG");
        GRAVITY = Config.getNumber(physicsConsts, "GRAVITY");
    }


    @Before
    public void initPhysicsPos() {
        physicsPos = new PhysicsPosition(new Vector2(0, 0));
    }

    @Test
    public void testUpdate() {
        physicsPos.setPosition(new Vector2(0, 0));
        physicsPos.setVelocity(new Vector2(10, 0));
        physicsPos.update(deltaTime);
        assertEquals(10 * deltaTime, physicsPos.getPosition().x, 0);
        assertEquals(0, physicsPos.getPosition().y, 0);
        assertEquals(0, physicsPos.getVelocity().x, 0);
        assertEquals(deltaTime * GRAVITY, physicsPos.getVelocity().y, 0);
    }

    @Test
    public void testUpdateBigVelocity() {
        float xVel = 10000;
        physicsPos.setPosition(new Vector2(0, 0));
        physicsPos.setVelocity(new Vector2(xVel, 0));

        physicsPos.update(deltaTime);
        assertEquals(xVel - (deltaTime * DRAG), physicsPos.getVelocity().x, 0);
        assertEquals(deltaTime * GRAVITY, physicsPos.getVelocity().y, 0);
    }


    @Test
    public void testCustomAccel() {
        Vector2 pos = new Vector2(0, 0);
        Vector2 vel = new Vector2(0, 0);
        Vector2 accel = new Vector2(1000, 100);
        physicsPos = new PhysicsPosition(pos, vel, accel);
        physicsPos.update(deltaTime);
        vel.cpy().add(1000, 100).scl(deltaTime);
        assertEquals(1000 * deltaTime - Math.signum(vel.x) * DRAG * deltaTime, physicsPos.getVelocity().x, 0);
    }

    @Test
    public void testSetAcc() {
        physicsPos.setAcceleration(new Vector2(15, 27));
        assertEquals(15, physicsPos.getAcceleration().x, 0);
        assertEquals(27, physicsPos.getAcceleration().y, 0);
    }

    @Test
    public void testCopyPhysicsPosition() {
        physicsPos = new PhysicsPosition(new Vector2(1, 2), new Vector2(3, 4), new Vector2(5, 6));
        PhysicsPosition physicsPos2 = new PhysicsPosition(physicsPos);
        assertEquals(physicsPos.getPosition(), physicsPos2.getPosition());
        assertEquals(physicsPos.getVelocity(), physicsPos2.getVelocity());
        assertEquals(physicsPos.getAcceleration(), physicsPos2.getAcceleration());
    }

    @Test
    public void testToString() {
        PhysicsPosition pp = new PhysicsPosition(new Vector2(1, 2), new Vector2(3, 4), new Vector2(5, 6));
        assertEquals("Position: " + pp.getPosition().x + ", " + pp.getPosition().y + "\n" +
                "Velocity: " + pp.getVelocity().x + ", " + pp.getVelocity().y + "\n" +
                "Acceleration: " + pp.getAcceleration().x + ", " + pp.getAcceleration().y + "\n", pp.toString());
    }

}
