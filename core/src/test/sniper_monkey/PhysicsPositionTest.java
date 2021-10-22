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

    private static PhysicsPosition physicsPos;
    private static String physicsConsts;
    private static float deltaTime = 1 / 10f;

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
        assertEquals(-60, physicsPos.getVelocity().y, 0); // gravity, better to read from config
    }

    @Test
    public void testUpdateBigVelocity() {
        physicsPos.setPosition(new Vector2(0, 0));
        physicsPos.setVelocity(new Vector2(10000, 0));

        physicsPos.update(deltaTime);
        assertEquals(9920, physicsPos.getVelocity().x, 0); // drag
        assertEquals(-60, physicsPos.getVelocity().y, 0); // gravity
    }


    @Test
    public void testCustomAccel() {
        physicsPos = new PhysicsPosition(new Vector2(0, 0), new Vector2(0, 0), new Vector2(1000, 0));
        physicsPos.update(deltaTime);
        assertEquals(20, physicsPos.getVelocity().x, 0); // accel - drag
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
