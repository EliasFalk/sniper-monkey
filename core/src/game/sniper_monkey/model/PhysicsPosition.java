package game.sniper_monkey.model;

import com.badlogic.gdx.math.Vector2;
import game.sniper_monkey.utils.Config;

/**
 * Represents an object used for handling of physics based movement using
 * gravity, velocity, acceleration, etc.
 *
 * @author Vincent Hellner
 * @author Elias Falk
 */
public class PhysicsPosition {
    private static final float GRAVITY;
    private static final float DRAG;
    private Vector2 position;
    private Vector2 velocity;
    private Vector2 acceleration = new Vector2(0, GRAVITY);

    static {
        String cfgPath = "cfg/physics.cfg";
        Config.readConfigFile(cfgPath);
        GRAVITY = Config.getNumber(cfgPath, "GRAVITY");
        DRAG = Config.getNumber(cfgPath, "DRAG");
    }

    /**
     * Creates a new physics position with a start position, velocity and acceleration.
     *
     * @param position     The start position.
     * @param velocity     The start velocity.
     * @param acceleration The start acceleration.
     */
    public PhysicsPosition(Vector2 position, Vector2 velocity, Vector2 acceleration) {
        this.position = position;
        this.velocity = velocity;
        this.acceleration = acceleration;
    }

    /**
     * Creates a new physics position with a start position and velocity. Acceleration is (0, gravity)
     *
     * @param position The start position.
     * @param velocity The start velocity.
     */
    public PhysicsPosition(Vector2 position, Vector2 velocity) {
        this.position = position;
        this.velocity = velocity;
    }

    /**
     * Creates a new physics position with a start position. Velocity is 0. Acceleration is (0, gravity).
     *
     * @param position The start position.
     */
    public PhysicsPosition(Vector2 position) {
        this(position, new Vector2(0, 0));
    }

    /**
     * Updates the velocity and position based on current velocity and acceleration;
     *
     * @param deltaTime The time between frames
     */
    public void update(float deltaTime) {
        position.add(velocity.cpy().scl(deltaTime));
        velocity.add(acceleration.cpy().scl(deltaTime));
        if (Math.abs(velocity.x) - DRAG * deltaTime < 0) {
            velocity.x = 0;
        } else {
            velocity.x -= Math.signum(velocity.x) * DRAG * deltaTime;
        }
        acceleration.x = 0;
    }

    /**
     * Gets a copy of the current position vector.
     *
     * @return A copy of the position vector.
     */
    public Vector2 getPosition() {
        return position.cpy();
    }

    /**
     * Sets a new position.
     *
     * @param position The new position.
     */
    public void setPosition(Vector2 position) {
        this.position = position;
    }

    /**
     * Returns a copy of the velocity vector.
     *
     * @return A copy of the velocity vector.
     */
    public Vector2 getVelocity() {
        return velocity.cpy();
    }

    /**
     * Sets a new velocity.
     *
     * @param velocity The new velocity.
     */
    public void setVelocity(Vector2 velocity) {
        this.velocity = velocity;
    }

    /**
     * Returns a copy of the acceleration vector.
     *
     * @return A copy of the acceleration vector.
     */
    public Vector2 getAcceleration() {
        return acceleration.cpy();
    }

    /**
     * Sets a new acceleration.
     *
     * @param acceleration The new acceleration.
     */
    public void setAcceleration(Vector2 acceleration) {
        this.acceleration = acceleration;
    }

    /**
     * Creates a new physics position with values copied from the physics position sent into the constructor.
     *
     * @param physicsPosition The physics position with the values being copied from.
     */
    public PhysicsPosition(PhysicsPosition physicsPosition) {
        this.position = physicsPosition.getPosition();
        this.velocity = physicsPosition.getVelocity();
        this.acceleration = physicsPosition.getAcceleration();
    }

    /**
     * Returns a formatted string that presents the three vectors, position, velocity and acceleration in way suited for printing.
     *
     * @return A formatted string that presents the three vectors, position, velocity and acceleration in way suited for printing.
     */
    public String toString() {
        return "Position: " + position.x + ", " + position.y + "\n" +
                "Velocity: " + velocity.x + ", " + velocity.y + "\n" +
                "Acceleration: " + acceleration.x + ", " + acceleration.y + "\n";
    }
}
