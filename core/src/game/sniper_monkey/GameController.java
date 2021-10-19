package game.sniper_monkey;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;
import game.sniper_monkey.controller.PlayerController;
import game.sniper_monkey.model.player.Player;
import game.sniper_monkey.model.player.PlayerFactory;
import game.sniper_monkey.model.world.World;
import game.sniper_monkey.model.world_brick.WorldBrick;
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

        String[][] map = MapReader.readMapTiles("grass_map_2");
        Vector2 mapOffset = calculateMapOffset(map);
        addMapToWorld(map, mapOffset);
        Map<String, Vector2> spawnPoints = MapReader.readSpawnPoints("grass_map_2/grass_2.tmx");
        initPlayer(1, spawnPoints.get("player_1").cpy().add(mapOffset));
        initPlayer(2, spawnPoints.get("player_2").cpy().add(mapOffset));
    }

    private Vector2 calculateMapOffset(String[][] map) {
        return new Vector2((-map[0].length / 2.f) * 16, (-map.length / 2.f) * 16);
    }

    private void addMapToWorld(String[][] map, Vector2 mapOffset) {
        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[y].length; x++) {
                if (!map[y][x].equals("air"))
                    World.getInstance().queueAddGameObject(new WorldBrick(new Vector2(mapOffset.x + (x * 16), mapOffset.y + y * 16), map[y][x]));
            }
        }
    }

    private Player initPlayer(int playerNum, Vector2 spawnPoint) {
        Player player;
        if (playerNum == 1) {
            player = PlayerFactory.createPlayer1(spawnPoint);
            World.getInstance().queueAddGameObject(player);
            player1Controller = new PlayerController(player, "cfg/player1_keybinds.cfg");
            createBars(player, Placement.LEFT);
            BottomHUDController p1BottomHUD = new BottomHUDController(gameScreen, player, "cfg/player1_keybinds.cfg", Placement.LEFT);
            player.registerSwappedFighterObserver(p1BottomHUD);
            player.registerSwappedFighterObserver(gameScreen);
        } else if (playerNum == 2) {
            player = PlayerFactory.createPlayer2(spawnPoint);
            World.getInstance().queueAddGameObject(player);
            player2Controller = new PlayerController(player, "cfg/player2_keybinds.cfg");
            createBars(player, Placement.RIGHT);
            BottomHUDController p2BottomHUD = new BottomHUDController(gameScreen, player, "cfg/player2_keybinds.cfg", Placement.RIGHT);
            player.registerSwappedFighterObserver(p2BottomHUD);
            player.registerSwappedFighterObserver(gameScreen);
        } else {
            throw new IllegalArgumentException("THERE CAN ONLY BE 2 PLAYERS..."); // TODO
        }
        return player;
    }

    private void createBars(Player player, Placement placement) {
        float barWidth = 300f;
        float barHeight = 20f;
        BarView healthBar;
        BarView staminaBar;
        BarView blockBar;
        if (placement == Placement.LEFT) {
            healthBar = new BarView(100, Gdx.graphics.getHeight() - 50, barWidth, barHeight, Color.RED, FillDirection.LEFT, Align.left);
            staminaBar = new BarView(120, Gdx.graphics.getHeight() - 80, barWidth, barHeight, Color.YELLOW, FillDirection.LEFT, Align.left);
            blockBar = new BarView(140, Gdx.graphics.getHeight() - 110, barWidth, barHeight, Color.BLUE, FillDirection.LEFT, Align.left);
        } else {
            healthBar = new BarView(Gdx.graphics.getWidth() - 100 - barWidth, Gdx.graphics.getHeight() - 50, barWidth, barHeight, Color.RED, FillDirection.RIGHT, Align.right);
            staminaBar = new BarView(Gdx.graphics.getWidth() - 120 - barWidth, Gdx.graphics.getHeight() - 80, barWidth, barHeight, Color.YELLOW, FillDirection.RIGHT, Align.right);
            blockBar = new BarView(Gdx.graphics.getWidth() - 140 - barWidth, Gdx.graphics.getHeight() - 110, barWidth, barHeight, Color.BLUE, FillDirection.RIGHT, Align.right);
        }
        gameScreen.addHudView(healthBar);
        gameScreen.addHudView(staminaBar);
        gameScreen.addHudView(blockBar);
        player.registerHealthObserver(healthBar);
        player.registerStaminaObserver(staminaBar);
        player.registerBlockObserver(blockBar);
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
