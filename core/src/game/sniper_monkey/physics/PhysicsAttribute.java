package game.sniper_monkey.physics;

import com.badlogic.gdx.math.Vector2;

public class PhysicsAttribute {
    // pos
    // vel
    // acc
    // mass
    // my (friction coefficient) sadge.



    private final Vector2 position;
    private final Vector2 velocity;
    private final Vector2 acceleration;
    private float mass;
    private float frictionCoefficient;


    public PhysicsAttribute(Vector2 position, Vector2 velocity, Vector2 acceleration, float mass, float frictionCoefficient) {
        this.position = position;
        this.velocity = velocity;
        this.acceleration = acceleration;
        this.mass = mass;
        this.frictionCoefficient = frictionCoefficient;
    }


    public Vector2 getPosition() {
        return position.cpy();
    }

    public Vector2 getVelocity() {
        return velocity.cpy();
    }

    public Vector2 getAcceleration() {
        return acceleration.cpy();
    }

    public float getMass() {
        return mass;
    }

    public float getFrictionCoefficient() {
        return frictionCoefficient;
    }

    public void setMass(float mass) {
        this.mass = mass;
    }

    public void setFrictionCoefficient(float frictionCoefficient) {
        this.frictionCoefficient = frictionCoefficient;
    }

    public void addAcceleration(float x, float y) {
        acceleration.x += x;
        acceleration.y += y;
    }

    public void addAcceleration(Vector2 acceleration) {
        addAcceleration(acceleration.x, acceleration.y);
    }

    public void setPosition(Vector2 position) {
        this.position.x = position.x;
        this.position.y = position.y;
    }

    public void setVelocity(Vector2 velocity) {
        this.velocity.x = position.x;
        this.velocity.y = position.y;
    }
}
