package game.sniper_monkey.model.world;

import com.badlogic.gdx.math.Vector2;
import game.sniper_monkey.model.collision.Hitbox;
import game.sniper_monkey.utils.collision.CollisionMasks;

/**
 * A abstract GameObject used in the world. It has a position and hitbox as well as support for
 * being updated.
 *
 * @author Vincent Hellner
 */
public abstract class GameObject {
    private final Hitbox hitbox;
    private final boolean isDynamic;
    private Vector2 position;

    /**
     * Creates the GameObject at a custom position
     *
     * @param position the starting position
     * @param isDynamic Whether the GameObject will move around
     */
    public GameObject(Vector2 position, boolean isDynamic) {
        this.position = position;
        hitbox = new Hitbox(position, new Vector2(0, 0));
        this.isDynamic = isDynamic;
    }

    /**
     * Creates the GameObject at a 0, 0
     *
     * @param isDynamic Whether the GameObject will move around
     */
    public GameObject(boolean isDynamic) {
        this(new Vector2(0, 0), isDynamic);
    }

    /**
     * Is the GameObject dynamic
     * @return Whether or not it is dynamic.
     */
    public boolean isDynamic() {
        return isDynamic;
    }

    /**
     * Update the GameObject (handles logic, data, etc.)
     *
     * @param deltaTime The time between this and the last time update was called (in seconds).
     */
    public void update(float deltaTime) {}

    /**
     * Removes this GameObject from the world at the start of the next frame
     */
    protected final void delete() {
        World.getInstance().queueRemoveGameObject(this);
    }

    /**
     * Updates the position of this GameObject
     *
     * @param position the new position to use
     */
    protected void setPosition(Vector2 position) {
        this.position = position;
        this.hitbox.setPosition(position);
    }

    /**
     * @return the position of this GameObject
     */
    public Vector2 getPos() {
        return position.cpy();
    }

    /**
     * Set the size of the hitbox.
     *
     * @param size The new size to use.
     */
    protected void setHitboxSize(Vector2 size) {
        hitbox.setSize(size);
    }

    /**
     * Set the position of the hitbox.
     *
     * @param pos The new position to use.
     */
    protected void setHitboxPos(Vector2 pos) {
        hitbox.setPosition(pos);
    }

    /**
     * Returns the hitbox as a reference (not copy).
     *
     * @return The hitbox.
     */
    public Hitbox getHitbox() {
        return hitbox;
    }

    /**
     * Sets the mask of this GameObjects hitbox.
     *
     * @param mask The new mask to use
     */
    protected void setHitboxMask(int mask) {
        hitbox.setMask(mask);
    }

    /**
     * Adds to the mask of this GameObjects hitbox.
     *
     * @param mask The new mask to add onto the current mask.
     */
    protected void addHitboxMask(int mask) {
        hitbox.addMask(mask);
    }

    /**
     * Removes a part of the mask from this GameObjects Hitbox.
     *
     * @param mask The mask to remove from the current mask.
     */
    protected void removeHitboxMask(int mask) {
        hitbox.removeMask(mask);
    }

    /**
     * Sets or unsets the ghost mask of this GameObject making it either respond to collision or not.
     *
     * @param ghost Whether or not this GameObject will act as a ghost.
     */
    protected void setGhost(boolean ghost) {
        if(ghost) addHitboxMask(CollisionMasks.GHOST);
        else removeHitboxMask(CollisionMasks.GHOST);
    }

    /**
     * Returns the mask of this GameObject.
     *
     * @return The mask
     */
    protected int getHitboxMask() {
        return hitbox.getMask();
    }
}
