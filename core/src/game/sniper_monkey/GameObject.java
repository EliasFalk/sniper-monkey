package game.sniper_monkey;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public abstract class GameObject
{
    private Vector2 position;
    private Texture sprite;

    public abstract void update(float deltaTime);

    public void render(SpriteBatch batch)
    {
        batch.draw(sprite, position.x, position.y);
    }

    protected final void delete()
    {
        World.getInstance().deleteGameObject(this);
    }

    protected void setSprite(Texture sprite)
    {
        this.sprite = sprite;
    }

    protected void setPos(Vector2 position)
    {
        this.position = position;
    }

    public Vector2 getPos()
    {
        return position;
    }
}
