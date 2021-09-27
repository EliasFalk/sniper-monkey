package game.sniper_monkey;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class GameRenderer
{
    SpriteBatch batch;

    public GameRenderer()
    {
        batch = new SpriteBatch();
    }

    /**
     * Renders a background and then all the objects in the world singleton using a SpriteBatch
     */
    public void render()
    {
        ScreenUtils.clear(0, 0, 0, 1);

        batch.begin();
        World.getInstance().render(batch);
        batch.end();
    }

    /**
     * Disposes of the SpriteBatch
     */
    public void dispose()
    {
        batch.dispose();
    }
}