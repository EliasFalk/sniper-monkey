package game.sniper_monkey;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import game.sniper_monkey.controller.PlayerController;
import game.sniper_monkey.platform.Platform;
import game.sniper_monkey.player.Player;
import game.sniper_monkey.player.PlayerFactory;
import game.sniper_monkey.view.GameRenderer;
import game.sniper_monkey.world.World;

public class SniperMonkey extends ApplicationAdapter {
    GameRenderer gameRenderer;
    PlayerController playerController;
    @Override
    public void create() {
        Texture img = new Texture("evil_wizard_2/Attack1.png");
        gameRenderer = new GameRenderer();
        World.getInstance().registerObserver(gameRenderer);
        Player player = PlayerFactory.createPlayer();
        World.getInstance().addGameObject(player);
        playerController = new PlayerController(player, "cfg/player1_keybinds.cfg");
        for (int i = 0; i < 400 / 16; i++)
            World.getInstance().addGameObject(new Platform(new Vector2(-200 + i * 16, -100)));
    }

    @Override
    public void render() {
        World.getInstance().update(Gdx.graphics.getDeltaTime());
        playerController.handleKeyInputs();
        gameRenderer.render();
    }

    @Override
    public void dispose() {
        gameRenderer.dispose();
    }
}
