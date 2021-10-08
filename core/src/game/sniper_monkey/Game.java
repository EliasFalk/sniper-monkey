package game.sniper_monkey;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import game.sniper_monkey.controller.PlayerController;
import game.sniper_monkey.model.platform.Platform;
import game.sniper_monkey.model.player.Player;
import game.sniper_monkey.model.player.PlayerFactory;
import game.sniper_monkey.model.world.World;
import game.sniper_monkey.view.GameScreen;
import game.sniper_monkey.view.hud.BarView;
import game.sniper_monkey.view.hud.HUDView;

public class Game {
    GameScreen gameScreen;
    PlayerController player1Controller;
    PlayerController player2Controller;
    boolean pause = false;

    //TODO documentation
    public void create() {
        gameScreen = new GameScreen();
        World.getInstance().registerObserver(gameScreen);

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
        BarView healthBar = new BarView(100, Gdx.graphics.getHeight()-50, Color.RED);
        BarView staminaBar = new BarView(120, Gdx.graphics.getHeight()-80, Color.YELLOW);
        BarView blockBar = new BarView(140, Gdx.graphics.getHeight()-110, Color.BLUE);

        gameScreen.addHudView(healthBar);
        gameScreen.addHudView(staminaBar);
        gameScreen.addHudView(blockBar);


        player1.registerHealthObserver(healthBar);
        player1.registerStaminaObserver(staminaBar);
        player1.registerBlockObserver(blockBar);

        // player 2
        BarView healthBar2 = new BarView(Gdx.graphics.getWidth()-100-153, Gdx.graphics.getHeight()-50, Color.RED);
        BarView staminaBar2 = new BarView(Gdx.graphics.getWidth()-120-153, Gdx.graphics.getHeight()-80, Color.YELLOW);
        BarView blockBar2 = new BarView(Gdx.graphics.getWidth()-140-153, Gdx.graphics.getHeight()-110, Color.BLUE);

        gameScreen.addHudView(healthBar2);
        gameScreen.addHudView(staminaBar2);
        gameScreen.addHudView(blockBar2);

        player2.registerHealthObserver(healthBar2);
        player2.registerStaminaObserver(staminaBar2);
        player2.registerBlockObserver(blockBar2);
    }



    //TODO documentation
    public void tick(float deltaTime) {
        World.getInstance().update(deltaTime);
        player1Controller.handleKeyInputs();
        player2Controller.handleKeyInputs();
        gameScreen.render(deltaTime);
    }

    //TODO documentation
    public void dispose() {
        gameScreen.dispose();
    }
}
