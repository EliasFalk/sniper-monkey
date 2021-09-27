package game.sniper_monkey;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;

public class PhysicsPosition {
    private final float GRAVITY = -20f;
    private float DRAG = 150f;
    private Vector2 position;
    private Vector2 velocity;
    private Vector2 acceleration = new Vector2(0, GRAVITY);


    /**
     * Updates the velocity and position based on current velocity and acceleration;
     *
     * @param deltaTime The time between frames
     */
    public void update(float deltaTime) {
        position.add(velocity.cpy().scl(deltaTime));
        velocity.add(acceleration.cpy().scl(deltaTime));
        if(Math.abs(velocity.x) - DRAG*deltaTime < 0) {
            velocity.x = 0;
        } else {
            velocity.x -= Math.signum(velocity.x)*DRAG*deltaTime;
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
        this.position = position;
        this.velocity = new Vector2(0,0);
    }


}
