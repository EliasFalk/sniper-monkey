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

    public void render()
    {
        ScreenUtils.clear(0, 0, 0, 1);

        batch.begin();
        World.getInstance().render(batch);
        batch.end();
    }

    public void dispose()
    {
        batch.dispose();
    }
}
