package game.sniper_monkey.world;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

import javax.swing.*;

public class GameRenderer
{
    SpriteBatch batch;
    Texture img = new Texture("evil_wizard_2/Attack1.png");
    Texture platform = new Texture("platform.png");

    public GameRenderer()
    {
        batch = new SpriteBatch();
    }

    /**
     * Renders a background and then all the objects in the world singleton using a SpriteBatch
     */

    OrthographicCamera camera = new OrthographicCamera(400, 400);
    public void render()
    {
        ScreenUtils.clear(1, 1, 1, 1);

        batch.begin();
        batch.setProjectionMatrix(camera.combined);
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
