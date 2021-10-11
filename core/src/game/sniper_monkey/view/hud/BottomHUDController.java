package game.sniper_monkey.view.hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import game.sniper_monkey.model.Config;
import game.sniper_monkey.model.player.Player;
import game.sniper_monkey.model.player.SwappedFighterObserver;
import game.sniper_monkey.view.GameScreen;

/**
 * Controls the bottom part of the HUD of a player including the key inputs and secondary fighter.
 *
 * @author Elias Falk
 */
public class BottomHUDController implements SwappedFighterObserver {
    private KeyInputView attack1;
    private KeyInputView attack2;
    private KeyInputView block;
    private KeyInputView swapFighter;
    private final GameScreen gameScreen;
    private final Player player;
    private float x;
    private float y;

    /**
     * Creates a controller for the HUD that presents the key inputs such as attacks and which secondary fighter the player has chosen.
     *
     * @param gameScreen       The gameScreen which the controller will place its actors within.
     * @param player           The player that the UI elements will subscribe to.
     * @param keybindsFilePath The file which contains the player's key binds.
     * @param placement        The placement of the side text relative to the key box.
     */
    public BottomHUDController(GameScreen gameScreen, Player player, String keybindsFilePath, Placement placement) {
        this.gameScreen = gameScreen;
        this.player = player;
        Placement textPlacement = Placement.RIGHT;
        if (placement == Placement.LEFT) {
            x = 200;
            y = 25;
            textPlacement = Placement.RIGHT;
        } else if (placement == Placement.RIGHT) {
            x = Gdx.graphics.getWidth() - 200;
            y = 25;
            textPlacement = Placement.LEFT;
        }

        Config.readConfigFile(keybindsFilePath);
        attack1 = new KeyInputView(x, y, Input.Keys.toString((int) Config.getNumber(keybindsFilePath, "ATTACK1")), "player.getAttack1Name()", textPlacement); //TODO
        attack2 = new KeyInputView(x, y + 40, Input.Keys.toString((int) Config.getNumber(keybindsFilePath, "ATTACK2")), "player.getAttack2Name()", textPlacement); // TODO
        block = new KeyInputView(x, y + 80, Input.Keys.toString((int) Config.getNumber(keybindsFilePath, "BLOCK")), "Block", textPlacement);
        swapFighter = new KeyInputView(x, y + 120, Input.Keys.toString((int) Config.getNumber(keybindsFilePath, "SWAP_FIGHTER")), "Swap fighter", textPlacement);

        gameScreen.addHudView(attack1);
        gameScreen.addHudView(attack2);
        gameScreen.addHudView(block);
        gameScreen.addHudView(swapFighter);

        player.registerAttackCooldownObserver(attack1);
        player.registerAttackCooldownObserver(attack2);
        player.registerBlockCooldownObserver(block);
        player.registerSwapCooldownObserver(swapFighter);
    }

    @Override
    public void onFighterSwap(Player player) {
        // TODO
        // set new attack names
        // set new secondary fighter name & sprite
    }
}
