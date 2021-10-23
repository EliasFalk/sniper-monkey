package game.sniper_monkey.model.collision;

import game.sniper_monkey.model.world.GameObject;

import java.util.Objects;

/**
 * A tuple storing both a GameObject and hitbox for use in the collision engine
 * as a way of associating a hitbox to its GameObject
 *
 * <p>
 *     Used by CollisionEngine
 *     Used by CollisionPair
 *     Used by SpatialHash
 *     Uses GameObject
 * </p>
 *
 * @author Vincent Hellner
 */
public final class CollisionPair {

    /**
     * The GameObject in the pair
     */
    public final GameObject gameObject;

    /**
     * The hitbox in the pair
     */
    public final Hitbox hitbox;

    /**
     * Create a collision pair
     * @param gameObject The GameObject to be used in the pair
     * @param hitbox The Hitbox to be used in the pair
     */
    public CollisionPair(GameObject gameObject, Hitbox hitbox) {
        this.gameObject = gameObject;
        this.hitbox = hitbox;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CollisionPair that = (CollisionPair) o;
        return Objects.equals(gameObject, that.gameObject) && Objects.equals(hitbox, that.hitbox);
    }
}
