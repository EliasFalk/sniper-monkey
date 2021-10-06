package game.sniper_monkey.physics;

import com.badlogic.gdx.math.Vector2;

public class PhysicsEngine {
    // air density
    // gravity
    private static final Vector2 GRAVITY = new Vector2(0, -9.82f);
    private static final float airDensity = 2.223f;
    private static final float dragCoefficient = 1f;

    private static Vector2 calculateForce(PhysicsAttribute physicsAttribute) {
        Vector2 gravityF = GRAVITY.cpy().scl(physicsAttribute.mass);

        Vector2 inverseDir = physicsAttribute.velocity.cpy().nor().scl(-1);
//        Vector2 frictionF = inverseDir.scl(gravityF.cpy().scl(-physicsAttribute.frictionCoefficient).len());
        Vector2 movementF = physicsAttribute.acceleration.cpy().scl(physicsAttribute.mass);

//        (1/2*airDensity*velocity^2*dragCoeff)
        Vector2 dragF = physicsAttribute.velocity.cpy().nor().scl((float) Math.pow(physicsAttribute.velocity.len(), 2) * airDensity * dragCoefficient / 2).scl(-1);

        // drag
        return gravityF.add(dragF).add(movementF);
    }

    public static PhysicsAttribute getNextState(PhysicsAttribute physicsAttribute, float deltaTime) {
        Vector2 resultantAcc = calculateForce(physicsAttribute).scl(deltaTime).scl(1 / physicsAttribute.mass);
        Vector2 deltaTimedVel = physicsAttribute.velocity.cpy().scl(deltaTime);
        Vector2 newPos = physicsAttribute.position.cpy().add(deltaTimedVel);
        Vector2 newVel = physicsAttribute.velocity.cpy().add(resultantAcc);
        Vector2 newAcc = new Vector2(0, 0);

        return new PhysicsAttribute(newPos, newVel, newAcc, physicsAttribute.mass, physicsAttribute.frictionCoefficient);
    }
}
