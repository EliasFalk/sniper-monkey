package game.sniper_monkey;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;
import game.sniper_monkey.controller.PlayerController;
import game.sniper_monkey.model.platform.Platform;
import game.sniper_monkey.model.player.Player;
import game.sniper_monkey.model.player.PlayerFactory;
import game.sniper_monkey.model.world.World;
import game.sniper_monkey.view.GameScreen;
import game.sniper_monkey.view.hud.BarView;
import game.sniper_monkey.view.hud.BottomHUDController;
import game.sniper_monkey.view.hud.FillDirection;
import game.sniper_monkey.view.hud.Placement;

public class GameController {
    GameScreen gameScreen;
    PlayerController player1Controller;
    PlayerController player2Controller;
    boolean pause = false;

    //TODO documentation
    public void create() {
        gameScreen = new GameScreen();
        World.getInstance().registerObserver(gameScreen);

        for (int i = 0; i < 400 / 8; i++)
            World.getInstance().queueAddGameObject(new Platform(new Vector2(-200 + i * 16, -100)));

        //TODO use an external tool to create the map and create a utility to read it
        World.getInstance().queueAddGameObject(new Platform(new Vector2(16, -100 + 16)));
        World.getInstance().queueAddGameObject(new Platform(new Vector2(16, -100 + 16 * 2)));
        World.getInstance().queueAddGameObject(new Platform(new Vector2(16, -100 + 16 * 3)));
        World.getInstance().queueAddGameObject(new Platform(new Vector2(16, -100 + 16 * 4)));
        World.getInstance().queueAddGameObject(new Platform(new Vector2(32, -100 + 16 * 4)));
        World.getInstance().queueAddGameObject(new Platform(new Vector2(32 + 16, -100 + 16 * 4)));
        World.getInstance().queueAddGameObject(new Platform(new Vector2(32 * 2, -100 + 16 * 4)));

        World.getInstance().queueAddGameObject(new Platform(new Vector2(-32 * 4, -100 + 16 * 5)));
        World.getInstance().queueAddGameObject(new Platform(new Vector2((-32 - 16) * 4, -100 + 16 * 5)));
        World.getInstance().queueAddGameObject(new Platform(new Vector2(-32 * 2 * 4, -100 + 16 * 5)));
        Player player1 = PlayerFactory.createPlayer1(new Vector2(50, 50));
        Player player2 = PlayerFactory.createPlayer2(new Vector2(-50, 50));
        World.getInstance().queueAddGameObject(player1);
        World.getInstance().queueAddGameObject(player2);
        player1Controller = new PlayerController(player1, "cfg/player1_keybinds.cfg");
        player2Controller = new PlayerController(player2, "cfg/player2_keybinds.cfg");

        // Player health, stamina & block bars.
        float barWidth = 300f;
        float barHeight = 20f;

        BarView healthBar = new BarView(100, Gdx.graphics.getHeight() - 50, barWidth, barHeight, Color.RED, FillDirection.LEFT, Align.left);
        BarView staminaBar = new BarView(120, Gdx.graphics.getHeight() - 80, barWidth, barHeight, Color.YELLOW, FillDirection.LEFT, Align.left);
        BarView blockBar = new BarView(140, Gdx.graphics.getHeight() - 110, barWidth, barHeight, Color.BLUE, FillDirection.LEFT, Align.left);

        gameScreen.addHudView(healthBar);
        gameScreen.addHudView(staminaBar);
        gameScreen.addHudView(blockBar);


        player1.registerHealthObserver(healthBar);
        player1.registerStaminaObserver(staminaBar);
        player1.registerBlockObserver(blockBar);

        // player 2
        BarView healthBar2 = new BarView(Gdx.graphics.getWidth() - 100 - barWidth, Gdx.graphics.getHeight() - 50, barWidth, barHeight, Color.RED, FillDirection.RIGHT, Align.right);
        BarView staminaBar2 = new BarView(Gdx.graphics.getWidth() - 120 - barWidth, Gdx.graphics.getHeight() - 80, barWidth, barHeight, Color.YELLOW, FillDirection.RIGHT, Align.right);
        BarView blockBar2 = new BarView(Gdx.graphics.getWidth() - 140 - barWidth, Gdx.graphics.getHeight() - 110, barWidth, barHeight, Color.BLUE, FillDirection.RIGHT, Align.right);

        gameScreen.addHudView(healthBar2);
        gameScreen.addHudView(staminaBar2);
        gameScreen.addHudView(blockBar2);

        player2.registerHealthObserver(healthBar2);
        player2.registerStaminaObserver(staminaBar2);
        player2.registerBlockObserver(blockBar2);

        BottomHUDController p1BottomHUD = new BottomHUDController(gameScreen, player1, "cfg/player1_keybinds.cfg", Placement.LEFT);
        BottomHUDController p2BottomHUD = new BottomHUDController(gameScreen, player2, "cfg/player2_keybinds.cfg", Placement.RIGHT);

        player1.registerSwappedFighterObserver(p1BottomHUD);
        player2.registerSwappedFighterObserver(p2BottomHUD);
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
