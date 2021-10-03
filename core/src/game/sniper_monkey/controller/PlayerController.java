package game.sniper_monkey.controller;

import com.badlogic.gdx.Gdx;
import game.sniper_monkey.Config;
import game.sniper_monkey.player.Player;
import game.sniper_monkey.player.PlayerInputAction;

public class PlayerController {
    private final Player player;

    // TODO read keycodes from config file
    private final int moveLeftKeyCode;
    private final int moveRightKeyCode;
    private final int jumpKeyCode;
    private final int dropKeyCode;
    private final int attack1KeyCode;
    private final int attack2KeyCode;
    private final int swapFighterKeyCode;
    private final int blockKeyCode;


    /**
     * Creates a player controller with input keys.
     *
     * @param player a player object. Is used to know which player object the controller controls.
     */
    // Send something to identify which config / part of config to read.
    public PlayerController(Player player, String filepath) {
        this.player = player;
        Config.readConfigFile(filepath);
        this.moveLeftKeyCode = (int) Config.getNumber(filepath, "MOVE_LEFT");
        this.moveRightKeyCode = (int) Config.getNumber(filepath, "MOVE_RIGHT");
        this.jumpKeyCode = (int) Config.getNumber(filepath, "JUMP");
        this.dropKeyCode = (int) Config.getNumber(filepath, "DROP");
        this.attack1KeyCode = (int) Config.getNumber(filepath, "ATTACK1");
        this.attack2KeyCode = (int) Config.getNumber(filepath, "ATTACK2");
        this.swapFighterKeyCode = (int) Config.getNumber(filepath, "SWAP_FIGHTER");
        this.blockKeyCode = (int) Config.getNumber(filepath, "BLOCK");
    }

    /**
     * Handles key inputs from the keyboard. Can handle several at the same time.
     *
     * @return a bool if a key is pressed.
     */
    public boolean handleKeyInputs() {
        boolean keyUsed = false;

        if (Gdx.input.isKeyPressed(moveLeftKeyCode)) {
            player.setInputAction(PlayerInputAction.MOVE_LEFT);
            keyUsed = true;
        }
        if (Gdx.input.isKeyPressed(moveRightKeyCode)) {
            player.setInputAction(PlayerInputAction.MOVE_RIGHT);
            keyUsed = true;
        }
        if (Gdx.input.isKeyPressed(jumpKeyCode)) {
            player.setInputAction(PlayerInputAction.JUMP);
            keyUsed = true;
        }
        if (Gdx.input.isKeyPressed(dropKeyCode)) {
            player.setInputAction(PlayerInputAction.DROP);
            keyUsed = true;
        }
        if (Gdx.input.isKeyPressed(attack1KeyCode)) {
            player.setInputAction(PlayerInputAction.ATTACK1);
            keyUsed = true;
        }
        if (Gdx.input.isKeyPressed(attack2KeyCode)) {
            player.setInputAction(PlayerInputAction.ATTACK2);
            keyUsed = true;
        }
        if (Gdx.input.isKeyPressed(blockKeyCode)) {
            player.setInputAction(PlayerInputAction.BLOCK);
            keyUsed = true;
        }
        if (Gdx.input.isKeyPressed(swapFighterKeyCode)) {
            player.setInputAction(PlayerInputAction.SWAP_FIGHTER);
            keyUsed = true;
        }


        return keyUsed;
    }
}
