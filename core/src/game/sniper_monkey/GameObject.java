package game.sniper_monkey;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public abstract class GameObject
{
    private Vector2 position;
    private Texture sprite;

    public abstract void update(float deltaTime);

    /**
     * Renders the GameObjects sprite (must be used inside a SpriteBatch begin and end statement)
     * @param batch the SpriteBatch to use
     */
    public void render(SpriteBatch batch)
    {
        batch.draw(sprite, position.x, position.y);
    }

    /**
     * Removes this GameObject from the world
     */
    protected final void delete()
    {
        World.getInstance().deleteGameObject(this);
    }

    /**
     * Updates the sprite of this GameObject
     * @param sprite the new sprite to use
     */
    protected void setSprite(Texture sprite)
    {
        this.sprite = sprite;
    }

    /**
     * Updates the position of this GameObject
     * @param position the new position to use
     */
    protected void setPos(Vector2 position)
    {
        this.position = position;
    }

    /**
     * @return the position of this GameObject
     */
    public Vector2 getPos()
    {
        return position;
    }
}
