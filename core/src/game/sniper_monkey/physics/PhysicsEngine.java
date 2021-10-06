package game.sniper_monkey.physics;

import com.badlogic.gdx.math.Vector2;

public class PhysicsEngine {
    // air density
    // gravity
    private static final Vector2 GRAVITY = new Vector2(0, -500f);
    private static final float airDensity = 2.223f;
    private static final float dragCoefficient = 1f;

    private static Vector2 calculateForce(PhysicsAttribute physicsAttribute, float deltaTime) {
        Vector2 gravityF = GRAVITY.cpy().scl(physicsAttribute.getMass());

        Vector2 inverseDir = physicsAttribute.getVelocity().nor().scl(-1);
        Vector2 frictionF = inverseDir.scl(gravityF.cpy().scl(-physicsAttribute.getFrictionCoefficient()).len());
        Vector2 movementF = physicsAttribute.getAcceleration().scl(physicsAttribute.getMass());

//        frictionF.clamp(0, physicsAttribute.getVelocity().len()*(1/deltaTime));

//        (1/2*airDensity*velocity^2*dragCoeff)
        Vector2 dragF = physicsAttribute.getVelocity().nor().scl((float) Math.pow(physicsAttribute.getVelocity().len(), 2) * airDensity * dragCoefficient / 2).scl(-1);
//        dragF.clamp(0, physicsAttribute.getVelocity().len()*(1/deltaTime));

        Vector2 attackingF = dragF.add(frictionF).clamp(0, physicsAttribute.getVelocity().len()*(1/deltaTime));
        // drag
        return gravityF.add(attackingF).add(movementF);
    }

    public static PhysicsAttribute getNextState(PhysicsAttribute physicsAttribute, float deltaTime) {
        Vector2 resultantAcc = calculateForce(physicsAttribute, deltaTime).scl(deltaTime).scl(1 / physicsAttribute.getMass());
        Vector2 deltaTimedVel = physicsAttribute.getVelocity().scl(deltaTime);
        Vector2 newPos = physicsAttribute.getPosition().add(deltaTimedVel);
        Vector2 newVel = physicsAttribute.getVelocity().add(resultantAcc);
        Vector2 newAcc = new Vector2(0, 0);

        return new PhysicsAttribute(newPos, newVel, newAcc, physicsAttribute.getMass(), physicsAttribute.getFrictionCoefficient());
    }
}
