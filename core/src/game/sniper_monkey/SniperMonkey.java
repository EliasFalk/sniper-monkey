package game.sniper_monkey;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import game.sniper_monkey.player.Player;
import game.sniper_monkey.platform.Platform;
import game.sniper_monkey.world.*;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import game.sniper_monkey.world.*;

import java.util.Vector;

public class SniperMonkey extends ApplicationAdapter {
	GameRenderer gameRenderer;

	@Override
	public void create () {
		Texture img = new Texture("evil_wizard_2/Attack1.png");
		Texture platform = new Texture("platform.png");
		gameRenderer = new GameRenderer();
		World.getInstance().addGameObject(new Player(new Vector2(50, 50), new Sprite(img,75, 100, 55, 65)));
		World.getInstance().addGameObject(new Platform(new Vector2(-400, -200), new Sprite(platform)));
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
