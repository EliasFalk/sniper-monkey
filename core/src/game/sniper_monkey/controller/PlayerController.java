package game.sniper_monkey.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import game.sniper_monkey.player.Player;
import game.sniper_monkey.player.PlayerInputAction;

public class PlayerController {
    private final Player player;

    // TODO read keycodes from config file
    private final int moveLeftKeyCode;
    private final int moveRightKeyCode;
    private final int jumpKeyCode;
    private final int dropKeyCode;


    /**
     * Creates a player controller with input keys.
     * @param player a player object. Is used to know which player object the controller controls.
     */
    // Send something to identify which config / part of config to read.
    public PlayerController(Player player) {
        this.player = player;
        this.moveLeftKeyCode = Input.Keys.A;
        this.moveRightKeyCode = Input.Keys.D;
        this.jumpKeyCode = Input.Keys.W;
        this.dropKeyCode = Input.Keys.S;
    }

    /**
     * Handles key inputs from the keyboard. Can handle several at the same time.
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
        } // player only moves right if both keys are pressed
        if (Gdx.input.isKeyPressed(jumpKeyCode)) {
            player.setInputAction(PlayerInputAction.JUMP);
            keyUsed = true;
        }
        if (Gdx.input.isKeyPressed(dropKeyCode)) {
            player.setInputAction(PlayerInputAction.DROP);
            keyUsed = true;
        }
        return keyUsed;
    }
}
