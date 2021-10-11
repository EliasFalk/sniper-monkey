package game.sniper_monkey.model.collision;

import com.badlogic.gdx.math.Vector2;

/**
 * An Axis Aligned Bounding Box used for hit detection.
 *
 * @author Vincent Hellner
 */
public final class Hitbox {
    private Vector2 position;
    private Vector2 size;
    private int mask;

    /**
     * Creates an AABB Hitbox
     *
     * @param position The position of the hitbox.
     * @param size     The size of the hitbox.
     * @param mask     The bitmask CollisionMask of this Hitbox.
     */
    public Hitbox(Vector2 position, Vector2 size, int mask) {
        this.position = position;
        this.size = size;
        this.mask = mask;
    }

    /**
     * Creates an AABB Hitbox
     *
     * @param position The position of the hitbox.
     * @param size     The size of the hitbox.
     */
    public Hitbox(Vector2 position, Vector2 size) {
        this(position, size, 0);
    }

    /**
     * Sets the mask of this Hitbox.
     *
     * @param mask The new mask to use
     */
    public void setMask(int mask) {
        this.mask = mask;
    }

    /**
     * Adds to the mask of this Hitbox.
     *
     * @param mask The new mask to add onto the current mask.
     */
    public void addMask(int mask) {
        this.mask &= mask;
    }

    /**
     * Returns the mask of this Hitbox.
     *
     * @return The mask
     */
    public int getMask() {
        return mask;
    }

    /**
     * @param other  The hitbox to check overlapping with.
     * @param offset The offset + the hitbox's position will be the position where the check of overlapping will be done.
     * @return True if the hitbox and the other hitbox overlaps when hitbox is at hitbox initial position + offset position, false if no overlapping.
     */
    public boolean isOverlapping(Hitbox other, Vector2 offset) {
        Vector2 offsetPosition = position.cpy();
        offsetPosition.add(offset);

        return (offsetPosition.x < other.position.x + other.size.x &&
                offsetPosition.x + size.x > other.position.x &&
                offsetPosition.y < other.position.y + other.size.y &&
                offsetPosition.y + size.y > other.position.y);
    }

    /**
     * Returns a copy of the size of the hitbox.
     *
     * @return A Vector2 representing the width and height of the hitbox, x=width, y=height.
     */
    public Vector2 getSize() {
        return size.cpy();
    }

    /**
     * Sets the hitbox's size.
     *
     * @param newSize The new size to use.
     */
    public void setSize(Vector2 newSize) {
        size = newSize;
    }

    /**
     * Returns a copy of the hitbox's position.
     *
     * @return A vector2 representing the x and y coordinates of the hitbox. The position is based in the lower left corner relative to the hitbox's size.
     */
    public Vector2 getPosition() {
        return position.cpy();
    }

    /**
     * Sets the hitbox's position.
     *
     * @param newPos The new position to use.
     */
    public void setPosition(Vector2 newPos) {
        position = newPos;
    }
}
