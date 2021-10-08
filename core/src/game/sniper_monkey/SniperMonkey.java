package game.sniper_monkey;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import game.sniper_monkey.controller.PlayerController;
import game.sniper_monkey.model.platform.Platform;
import game.sniper_monkey.model.player.Player;
import game.sniper_monkey.model.player.PlayerFactory;
import game.sniper_monkey.view.GameScreen;
import game.sniper_monkey.model.world.World;

public class SniperMonkey extends ApplicationAdapter {
    private Game game;
    //TODO documentation
    @Override
    public void create() {
        game = new Game();
        game.create();
    }

    //TODO documentation
    @Override
    public void render() {
        float deltaTime = Math.min(1 / 10f, Gdx.graphics.getDeltaTime());
        if (Gdx.graphics.getDeltaTime() > 1) {
            return;
        }
        game.tick(deltaTime);
    }

    //TODO documentation
    @Override
    public void dispose() {
        game.dispose();
    }
}
