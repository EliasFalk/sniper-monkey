package game.sniper_monkey;

import com.badlogic.gdx.math.Vector2;

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
        Config.readConfigFile("cfg/physics.cfg");
        GRAVITY = Config.getNumber("cfg/physics.cfg", "GRAVITY");
        DRAG = Config.getNumber("cfg/physics.cfg", "DRAG");
    }

    public PhysicsPosition(Vector2 position, Vector2 velocity, Vector2 acceleration) {
        this.position = position;
        this.velocity = velocity;
        this.acceleration = acceleration;
    }


    public PhysicsPosition(Vector2 position, Vector2 velocity) {
        this.position = position;
        this.velocity = velocity;
    }

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

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public Vector2 getVelocity() {
        return velocity.cpy();
    }

    public void setVelocity(Vector2 velocity) {
        this.velocity = velocity;
    }

    public Vector2 getAcceleration() {
        return acceleration.cpy();
    }

    public void setAcceleration(Vector2 acceleration) {
        this.acceleration = acceleration;
    }

    public PhysicsPosition(PhysicsPosition physicsPosition) {
        this.position = physicsPosition.getPosition();
        this.velocity = physicsPosition.getVelocity();
        this.acceleration = physicsPosition.getAcceleration();
    }

    public String toString() {
        return "Position: " + position.x + ", " + position.y + "\n" +
                "Velocity: " + velocity.x + ", " + velocity.y + "\n" +
                "Acceleration: " + acceleration.x + ", " + acceleration.y + "\n";
    }
}
