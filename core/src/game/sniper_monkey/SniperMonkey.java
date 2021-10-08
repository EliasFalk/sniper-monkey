package game.sniper_monkey;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import game.sniper_monkey.controller.PlayerController;
import game.sniper_monkey.model.platform.Platform;
import game.sniper_monkey.model.player.Player;
import game.sniper_monkey.model.player.PlayerFactory;
import game.sniper_monkey.model.player.fighter.attack.SwordAttack;
import game.sniper_monkey.view.GameRenderer;
import game.sniper_monkey.model.world.World;

public class SniperMonkey extends ApplicationAdapter {
    GameRenderer gameRenderer;
    PlayerController player1Controller;
    PlayerController player2Controller;
    boolean pause = false;

    //TODO documentation
    @Override
    public void create() {
        Texture img = new Texture("images/evil_wizard_2/Attack1.png");
        gameRenderer = new GameRenderer();
        World.getInstance().registerObserver(gameRenderer);

        for (int i = 0; i < 400 / 16; i++)
            World.getInstance().addGameObject(new Platform(new Vector2(-200 + i * 16, -100)));

        //TODO use an external tool to create the map and create a utility to read it
        World.getInstance().addGameObject(new Platform(new Vector2(16, -100 + 16)));
        World.getInstance().addGameObject(new Platform(new Vector2(16, -100 + 16 * 2)));
        World.getInstance().addGameObject(new Platform(new Vector2(16, -100 + 16 * 3)));
        World.getInstance().addGameObject(new Platform(new Vector2(16, -100 + 16 * 4)));
        World.getInstance().addGameObject(new Platform(new Vector2(32, -100 + 16 * 4)));
        World.getInstance().addGameObject(new Platform(new Vector2(32 + 16, -100 + 16 * 4)));
        World.getInstance().addGameObject(new Platform(new Vector2(32 * 2, -100 + 16 * 4)));

        World.getInstance().addGameObject(new Platform(new Vector2(-32 * 4, -100 + 16 * 5)));
        World.getInstance().addGameObject(new Platform(new Vector2((-32 - 16) * 4, -100 + 16 * 5)));
        World.getInstance().addGameObject(new Platform(new Vector2(-32 * 2 * 4, -100 + 16 * 5)));
        Player player1 = PlayerFactory.createPlayer(new Vector2(50, 50));
        Player player2 = PlayerFactory.createPlayer(new Vector2(-50, 50));
        World.getInstance().addGameObject(player1);
        World.getInstance().addGameObject(player2);
        player1Controller = new PlayerController(player1, "cfg/player1_keybinds.cfg");
        player2Controller = new PlayerController(player2, "cfg/player2_keybinds.cfg");
    }

    //TODO documentation
    @Override
    public void render() {
        float deltaTime = Math.min(1 / 10f, Gdx.graphics.getDeltaTime());
        if (Gdx.graphics.getDeltaTime() > 1) {
            return;
        }
        World.getInstance().update(deltaTime);
        player1Controller.handleKeyInputs();
        player2Controller.handleKeyInputs();
        gameRenderer.render();
    }

    //TODO documentation
    @Override
    public void dispose() {
        gameRenderer.dispose();
    }

    //TODO documentation
    @Override
    public void resize(int width, int height) {
        gameRenderer.updateCamera(width / 2, height / 2);
    }
}
