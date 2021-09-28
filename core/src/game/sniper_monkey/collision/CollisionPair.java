package game.sniper_monkey.collision;

import game.sniper_monkey.world.GameObject;

/**
 * A tuple storing both a GameObject and hitbox for use in the collision engine
 * as a way of associating a hitbox to its GameObject
 */
public class CollisionPair {
    public final GameObject gameObject;
    public final Hitbox hitbox;

    public CollisionPair(GameObject gameObject, Hitbox hitbox) {
        this.gameObject = gameObject;
        this.hitbox = hitbox;
    }
}
