package game.sniper_monkey.world;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import game.sniper_monkey.collision.Hitbox;

public abstract class GameObject {
    private Vector2 position;
    private Hitbox hitbox;

    /**
     * Creates the GameObject at a custom position
     *
     * @param position the starting position
     */
    public GameObject(Vector2 position) {
        this.position = position;
        hitbox = new Hitbox(position, new Vector2(0, 0));
    }

    /**
     * Creates the GameObject at a 0, 0
     */
    public GameObject() {
        this(new Vector2(0,0));
    }

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
    protected void setPos(Vector2 position) {
        this.position = position;
    }

    /**
     * @return the position of this GameObject
     */
    public Vector2 getPos() {
        return position.cpy();
    }

    protected void setHitboxSize(Vector2 size) {
        hitbox.setSize(size);
    }
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
