package game.sniper_monkey;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class SniperMonkey extends ApplicationAdapter {
	GameRenderer gameRenderer;
	
	@Override
	public void create () {
		gameRenderer = new GameRenderer();
	}

	@Override
	public void render () {
		World.getInstance().update(Gdx.graphics.getDeltaTime());
		gameRenderer.render();
	}
	
	@Override
	public void dispose () {
		gameRenderer.dispose();
	}
}
