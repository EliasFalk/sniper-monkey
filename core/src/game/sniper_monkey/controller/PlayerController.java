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


    // Send something to identify which config / part of config to read.
    public PlayerController(Player player) {
        this.player = player;
        this.moveLeftKeyCode = Input.Keys.A;
        this.moveRightKeyCode = Input.Keys.D;
        this.jumpKeyCode = Input.Keys.W;
        this.dropKeyCode = Input.Keys.S;
    }

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
        return keyUsed;
    }
}
