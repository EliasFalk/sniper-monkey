package game.sniper_monkey.physics;

import com.badlogic.gdx.math.Vector2;

public class PhysicsAttribute {
    // pos
    // vel
    // acc
    // mass
    // my (friction coefficient) sadge.

    public Vector2 position;
    public Vector2 velocity;
    public Vector2 acceleration;
    public float mass;
    public float frictionCoefficient;


    public PhysicsAttribute(Vector2 position, Vector2 velocity, Vector2 acceleration, float mass, float frictionCoefficient) {
        this.position = position;
        this.velocity = velocity;
        this.acceleration = acceleration;
        this.mass = mass;
        this.frictionCoefficient = frictionCoefficient;
    }
}
