package game.sniper_monkey.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import game.sniper_monkey.SniperMonkey;
import game.sniper_monkey.model.player.FluctuatingAttributeObserver;
import game.sniper_monkey.model.player.Player;
import game.sniper_monkey.model.player.PlayerFactory;
import game.sniper_monkey.model.player.fighter.Fighter;
import game.sniper_monkey.model.world.World;
import game.sniper_monkey.model.world_brick.WorldBrick;
import game.sniper_monkey.utils.Config;
import game.sniper_monkey.utils.map.MapReader;
import game.sniper_monkey.utils.time.CallbackTimer;
import game.sniper_monkey.utils.time.TimerBank;
import game.sniper_monkey.view.GameScreen;
import game.sniper_monkey.view.hud.*;

import java.util.Map;

/**
 * Controller that controls the Game itself
 *
 * <p>
 *     Used by CharacterSelectionScreenController
 *
 *     Uses FluctuatingAttributeObserver
 *     Uses IScreenController
 *     Uses SniperMonkey
 *     Uses Player
 *     Uses PlayerFactory
 *     Uses Fighter
 *     Uses World
 *     Uses WorldBrick
 *     Uses Config
 *     Uses MapReader
 *     Uses CallbackTimer
 *     Uses TimerBank
 *     Uses GameScreen
 *     Uses HUD
 * </p>
 *
 * @author Elias Falk
 * @author Vincent Hellner
 * @author Kevin Jeryd
 */
public class GameController implements FluctuatingAttributeObserver, IScreenController {
    private final CallbackTimer roundTimer;
    private final Map<String, Fighter> chosenFighters;
    private GameScreen gameScreen;
    private PlayerController player1Controller, player2Controller;
    private GameState currentState;
    private Player player1, player2;
    private final OverlayMenu pauseMenu;
    private final OverlayMenu endMenu;
    private final OverlayMenu startOverlay;
    private int startStage = 0;
    private final CallbackTimer startStageTime = new CallbackTimer(1, true, () -> startStage++);

    /**
     * Create a GameController that controls the game screen.
     *
     * @param chosenFighters The fighters that the players that the controller creates will use.
     * @see CharacterSelectionScreenController
     */
    public GameController(Map<String, Fighter> chosenFighters) {
        this.chosenFighters = chosenFighters;
        Config.readConfigFile("cfg/game.cfg");
        roundTimer = createRoundTimer();
        pauseMenu = createPauseMenu();
        startOverlay = createStartOverlay();
        endMenu = createEndMenu();
        World.getInstance().resetWorld();
        TimerBank.clear();
        create();
    }

    private OverlayMenu createStartOverlay() {
        return new OverlayMenu("3");
    }

    private CallbackTimer createRoundTimer() {
        final CallbackTimer roundTimer;
        roundTimer = new CallbackTimer(Config.getNumber("cfg/game.cfg", "ROUND_TIME"), () -> {
            currentState = this::gameOverState;
            addEndMenuToScreen();
        });
        return roundTimer;
    }

    @Override
    public void onValueChange(float min, float max, float health) {
        if (health <= min) {
            currentState = this::gameOverState;
            addEndMenuToScreen();
        }
    }

    private void create() {
        gameScreen = new GameScreen();
        World.getInstance().registerObserver(gameScreen);

        String[][] map = MapReader.readMapTiles("grass_map_2");
        Vector2 mapOffset = calculateMapOffset(map);
        addMapToWorld(map, mapOffset);
        Map<String, Vector2> spawnPoints = MapReader.readSpawnPoints("grass_map_2/grass_2.tmx");
        initPlayer(1, spawnPoints.get("player_1").cpy().add(mapOffset));
        initPlayer(2, spawnPoints.get("player_2").cpy().add(mapOffset));

        createRoundTimerView();

        World.getInstance().update(0);
        initStartState();
        currentState = this::startState;
    }

    private void createRoundTimerView() {
        RoundTimerView roundTimerView = new RoundTimerView();
        roundTimer.registerTimerObserver(roundTimerView);
        gameScreen.addHudView(roundTimerView);
    }

    private int determineWinner() {
        if (player1.getHealth() > player2.getHealth()) return 1;
        else if (player2.getHealth() > player1.getHealth()) return 2;
        else return 0;
    }

    private void initStartState() {
        gameScreen.addHudView(startOverlay);
        startStage = 0;
        startStageTime.setAutoUpdate(false);
        startStageTime.start();
    }

    private void startState(float deltaTime) {
        startStageTime.update(deltaTime);
        switch (startStage) {
            case 0 -> startOverlay.updateTitleText("3");
            case 1 -> startOverlay.updateTitleText("2");
            case 2 -> startOverlay.updateTitleText("1");
            case 3 -> startOverlay.updateTitleText("Fight!");
            default -> {
                roundTimer.start();
                currentState = this::fightingState;
                gameScreen.removeHudView(startOverlay);
                startStageTime.stop();
            }
        }
    }

    private void fightingState(float deltaTime) {
        player1Controller.handleKeyInputs();
        player2Controller.handleKeyInputs();
        World.getInstance().update(deltaTime);
        TimerBank.updateTimers(deltaTime);

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) { //TODO config for key bind
            gameScreen.addHudView(pauseMenu);
            currentState = this::pauseState;
        }
    }

    private void pauseState(float deltaTime) {
        //TODO add pause overlay
        roundTimer.stop();
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) { //TODO config for key bind
            initStartState();
            currentState = this::startState;
            gameScreen.removeHudView(pauseMenu);
        }
    }

    private OverlayMenu createPauseMenu() {
        OverlayMenu pauseMenu = new OverlayMenu("Game Paused");
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = new BitmapFont();
        Button resumeGame = new TextButton("Resume", textButtonStyle);
        resumeGame.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                initStartState();
                gameScreen.removeHudView(pauseMenu);
                currentState = GameController.this::startState;
            }
        });
        pauseMenu.addButton(resumeGame);
        return pauseMenu;
    }

    private OverlayMenu createEndMenu() {
        final OverlayMenu endMenu;
        endMenu = new OverlayMenu("");
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = new BitmapFont();
        Button goToStart = new TextButton("Return to character selection", textButtonStyle);
        goToStart.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                gameScreen.removeHudView(endMenu);
                SniperMonkey.activeController = new CharacterSelectionScreenController();
            }
        });

        endMenu.addButton(goToStart);
        return endMenu;
    }

    private void gameOverState(float deltaTime) {
        roundTimer.stop();
    }

    private void addEndMenuToScreen() {
        endMenu.updateTitleText("Player " + determineWinner() + " Wins!");
        gameScreen.addHudView(endMenu);
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

    private void initPlayer(int playerNum, Vector2 spawnPoint) {
        Player player;
        BottomHUDController bottomHUD;
        if (playerNum == 1) {
            player = PlayerFactory.createPlayer1(spawnPoint, chosenFighters.get("player1PrimaryFighter"), chosenFighters.get("player1SecondaryFighter"));
            player1 = player;
            player1Controller = new PlayerController(player, "cfg/player1_keybinds.cfg");
            createBars(player, Placement.LEFT);
            bottomHUD = new BottomHUDController(gameScreen, player, "cfg/player1_keybinds.cfg", Placement.LEFT);
        } else if (playerNum == 2) {
            player = PlayerFactory.createPlayer2(spawnPoint, chosenFighters.get("player2PrimaryFighter"), chosenFighters.get("player2SecondaryFighter"));
            player2 = player;
            player2Controller = new PlayerController(player, "cfg/player2_keybinds.cfg");
            createBars(player, Placement.RIGHT);
            bottomHUD = new BottomHUDController(gameScreen, player, "cfg/player2_keybinds.cfg", Placement.RIGHT);
        } else {
            throw new IllegalArgumentException("THERE CAN ONLY BE 2 PLAYERS..."); // TODO
        }
        World.getInstance().queueAddGameObject(player);
        player.registerSwappedFighterObserver(bottomHUD);
        player.registerSwappedFighterObserver(gameScreen);
        player.registerHealthObserver(this);
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

    @Override
    public void tick(float deltaTime) {
        currentState.perform(deltaTime);
        gameScreen.render(deltaTime);
    }

    @Override
    public void onResize(int w, int h) {
        gameScreen.resize(w, h);
    }

    @FunctionalInterface
    private interface GameState {
        /**
         * Perform the state
         *
         * @param deltaTime The time since last update
         */
        void perform(float deltaTime);
    }
}
