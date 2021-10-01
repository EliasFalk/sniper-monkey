package game.sniper_monkey.world;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import game.sniper_monkey.collision.Hitbox;

public abstract class GameObject {
    private Vector2 position;
    private Hitbox hitbox;
    private boolean isDynamic;

    /**
     * Creates the GameObject at a custom position
     *
     * @param position the starting position
     */
    public GameObject(Vector2 position, boolean isDynamic) {
        this.position = position;
        hitbox = new Hitbox(position, new Vector2(0, 0));
        this.isDynamic = isDynamic;
    }

    public boolean isDynamic() {
        return isDynamic;
    }

    /**
     * Creates the GameObject at a 0, 0
     */
    public GameObject(boolean isDynamic) {
        this(new Vector2(0,0), isDynamic);
    }

    /**
     * Update the GameObject (handles logic, data, etc.)
     * @param deltaTime The time between this and the last time update was called (in seconds).
     */
    public abstract void update(float deltaTime);

    /**
     * Removes this GameObject from the world
     */
    protected final void delete() {
        World.getInstance().deleteGameObject(this);
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
     * @param size The new size to use.
     */
    protected void setHitboxSize(Vector2 size) {
        hitbox.setSize(size);
    }

    /**
     * Set the position of the hitbox.
     * @param pos The new position to use.
     */
    protected void setHitboxPos(Vector2 pos) {
        hitbox.setPosition(pos);
    }

    /**
     * Returns the hitbox as a reference (not copy).
     * @return The hitbox.
     */
    public Hitbox getHitbox() {
        return hitbox;
    }
}
