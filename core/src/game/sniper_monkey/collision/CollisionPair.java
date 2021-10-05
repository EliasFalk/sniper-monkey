package game.sniper_monkey.collision;

import game.sniper_monkey.world.GameObject;

import java.util.Objects;

/**
 * A tuple storing both a GameObject and hitbox for use in the collision engine
 * as a way of associating a hitbox to its GameObject
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
