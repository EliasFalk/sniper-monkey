package game.sniper_monkey.collision;

import com.badlogic.gdx.math.Vector2;

public class Hitbox {
    private Vector2 position;
    private Vector2 size;

    public Hitbox(Vector2 position, Vector2 size) {
        this.position = position;
        this.size = size;
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
     * Sets the hitbox's position at a new position.
     *
     * @param newPos The new position to be set.
     */
    public void setPosition(Vector2 newPos) {
        position = newPos;
    }

    public void setSize(Vector2 newSize) {
        size = newSize;
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
     * Returns a copy of the hitbox's position.
     *
     * @return A vector2 representing the x and y coordinates of the hitbox. The position is based in the lower left corner relative to the hitbox's size.
     */
    public Vector2 getPosition() {
        return position.cpy();
    }
}
