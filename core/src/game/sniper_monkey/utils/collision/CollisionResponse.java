package game.sniper_monkey.utils.collision;

import com.badlogic.gdx.math.Vector2;
import game.sniper_monkey.model.Callback;
import game.sniper_monkey.model.PhysicsPosition;
import game.sniper_monkey.model.collision.CollisionEngine;
import game.sniper_monkey.model.collision.Hitbox;

public final class CollisionResponse {
    private CollisionResponse() {}

    public static PhysicsPosition handleCollision(float deltaTime, Hitbox hitbox, int collisionMask, PhysicsPosition physicsPosition, Callback onXCollision, Callback onYCollision) {
        Vector2 vel = physicsPosition.getVelocity();
        Vector2 hitboxStartingPos = hitbox.getPosition();

        if (CollisionEngine.getCollision(hitbox, new Vector2(vel.x, 0).scl(deltaTime), collisionMask)) {
            while (!CollisionEngine.getCollision(hitbox, new Vector2(Math.signum(vel.x) / 2f, 0), collisionMask)) {
                hitbox.setPosition(new Vector2(hitbox.getPosition().x + Math.signum(vel.x) / 2f, hitbox.getPosition().y));
            }
            vel.x = 0;
            onXCollision.call();
        }
        hitbox.setPosition(hitbox.getPosition().add(vel.x * deltaTime, 0));

        if (CollisionEngine.getCollision(hitbox, new Vector2(0, vel.y).scl(deltaTime), collisionMask)) {
            while (!CollisionEngine.getCollision(hitbox, new Vector2(0, Math.signum(vel.y) / 2f), collisionMask)) {
                hitbox.setPosition(new Vector2(hitbox.getPosition().x, hitbox.getPosition().y + Math.signum(vel.y) / 2f));
            }
            vel.y = 0;
            onYCollision.call();
        }
        hitbox.setPosition(hitbox.getPosition().add(0, vel.y * deltaTime));
        Vector2 newPos = hitbox.getPosition();

        hitbox.setPosition(hitboxStartingPos);

        return new PhysicsPosition(newPos, vel, physicsPosition.getAcceleration());
    }
}
