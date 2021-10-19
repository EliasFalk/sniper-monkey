package game.sniper_monkey.utils.collision;

import com.badlogic.gdx.math.Vector2;
import game.sniper_monkey.model.Callback;
import game.sniper_monkey.model.PhysicsPosition;
import game.sniper_monkey.model.collision.CollisionEngine;
import game.sniper_monkey.model.collision.Hitbox;

/**
 * Static utility for responding to collision (i.e preventing
 * movement from occurring if a hitbox is traveling towards another
 * hitbox which is not masked out of the collision
 *
 * @author Vincent Hellner
 * @author Elias Falk
 */
public final class CollisionResponse {
    private CollisionResponse() {}

    /**
     * Prevents a hitbox from intersecting with other hitboxes in the world it is set to collide with.
     *
     * @param deltaTime The time since the last frame.
     * @param hitbox    The hitbox of the moving object.
     * @param collisionMask A bitmask representing objects that will be masked out of the collision check and not collided with.
     * @param physicsPosition The physics position of the moving object.
     * @param onXCollision A callback that is called when a horizontal collision occurs.
     * @param onYCollision A callback that is called when a vertical collision occurs.
     *
     * @return A resulting new physics position that represents the objects new position.
     */
    public static PhysicsPosition handleCollision(float deltaTime, Hitbox hitbox, int collisionMask, PhysicsPosition physicsPosition, Callback onXCollision, Callback onYCollision) {
        collisionMask |= CollisionMasks.GHOST;

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
