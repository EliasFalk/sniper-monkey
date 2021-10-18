package game.sniper_monkey;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;
import game.sniper_monkey.controller.PlayerController;
import game.sniper_monkey.model.world_brick.WorldBrick;
import game.sniper_monkey.model.player.Player;
import game.sniper_monkey.model.player.PlayerFactory;
import game.sniper_monkey.model.world.World;
import game.sniper_monkey.utils.MapReader;
import game.sniper_monkey.view.GameScreen;
import game.sniper_monkey.view.hud.BarView;
import game.sniper_monkey.view.hud.BottomHUDController;
import game.sniper_monkey.view.hud.FillDirection;
import game.sniper_monkey.view.hud.Placement;

import java.util.Map;

public class GameController {
    GameScreen gameScreen;
    PlayerController player1Controller;
    PlayerController player2Controller;
    boolean pause = false;

    //TODO documentation
    public void create() {
        gameScreen = new GameScreen();
        World.getInstance().registerObserver(gameScreen);

        String[][] map = MapReader.readMapTiles("grass_map");
        Vector2 mapOffset = new Vector2((-map[0].length / 2.f) * 16, (-map.length / 2.f) * 16);
        for(int y = 0; y < map.length; y++) {
            for(int x = 0; x < map[y].length; x++) {
                if(!map[y][x].equals("air"))
                    World.getInstance().queueAddGameObject(new WorldBrick(new Vector2(mapOffset.x + (x * 16), mapOffset.y + y * 16), map[y][x]));
            }
        }

        Map<String, Vector2> spawnPoints = MapReader.readSpawnPoints("grass_map/untitled.tmx");

        Player player1 = PlayerFactory.createPlayer1(spawnPoints.get("player_1").cpy().add(mapOffset));
        Player player2 = PlayerFactory.createPlayer2(spawnPoints.get("player_2").cpy().add(mapOffset));
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

        player1.registerSwappedFighterObserver(gameScreen);
        player2.registerSwappedFighterObserver(gameScreen);
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
