package game.sniper_monkey.view.hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import game.sniper_monkey.model.Config;
import game.sniper_monkey.model.player.Player;
import game.sniper_monkey.model.player.SwappedFighterObserver;
import game.sniper_monkey.utils.view.HUDUtils;
import game.sniper_monkey.view.GameScreen;

/**
 * Controls the bottom part of the HUD of a player including the key inputs and secondary fighter.
 *
 * @author Elias Falk
 */
public class BottomHUDController implements SwappedFighterObserver {
    private final GameScreen gameScreen;
    private final Player player;
    private final float keyInputMargin = 6f;
    private final float fighterXMargin = 50f;
    private final float fighterYMargin = 50f;
    private final float keyInputsXPosition = 200f;
    private final float keyInputsYPosition = 25;
    private SecondaryFighterView secondaryFighterView;
    private KeyInputView attack1;
    private KeyInputView attack2;
    private KeyInputView block;
    private KeyInputView swapFighter;
    private String keybindsFilePath;
    private Placement placement;
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
        this.placement = placement;
        this.keybindsFilePath = keybindsFilePath;
        Config.readConfigFile(keybindsFilePath);
        init();
    }

    private void init() {
        createKeys();
        createSecondaryFighterView();
    }

    private void createKeys() {
        Placement textPlacement = placement;
        y = keyInputsYPosition;
        if (placement == Placement.LEFT) {
            x = keyInputsXPosition;
            textPlacement = Placement.RIGHT;
        } else if (placement == Placement.RIGHT) {
            x = Gdx.graphics.getWidth() - keyInputsXPosition - KeyInputView.getWidth();
            textPlacement = Placement.LEFT;
        }

        attack1 = new KeyInputView(x, y, Input.Keys.toString((int) Config.getNumber(keybindsFilePath, "ATTACK1")), "player.getAttack1Name()", textPlacement); //TODO get correct attack name
        attack2 = new KeyInputView(x, y + KeyInputView.getHeight() + keyInputMargin, Input.Keys.toString((int) Config.getNumber(keybindsFilePath, "ATTACK2")), "player.getAttack2Name()", textPlacement); // TODO get correct attack name
        block = new KeyInputView(x, y + (KeyInputView.getHeight() + keyInputMargin) * 2, Input.Keys.toString((int) Config.getNumber(keybindsFilePath, "BLOCK")), "Block", textPlacement);
        swapFighter = new KeyInputView(x, y + (KeyInputView.getHeight() + keyInputMargin) * 3, Input.Keys.toString((int) Config.getNumber(keybindsFilePath, "SWAP_FIGHTER")), "Swap fighter", textPlacement);

        gameScreen.addHudView(attack1);
        gameScreen.addHudView(attack2);
        gameScreen.addHudView(block);
        gameScreen.addHudView(swapFighter);

        player.registerAttackCooldownObserver(attack1);
        player.registerAttackCooldownObserver(attack2);
        player.registerBlockCooldownObserver(block);
        player.registerSwapCooldownObserver(swapFighter);
    }

    private void createSecondaryFighterView() {
        // TODO maybe move this into SecondaryFighterView since it's sort of view related?
        TextureRegion inactiveFighter = HUDUtils.getCorrespondingTextureRegion(player.getInactiveFighterClass());
        String inactiveFighterName = HUDUtils.getFighterDisplayName(player.getInactiveFighterClass());

        float relativeMarginOfFighter = 0;
        if (placement == Placement.RIGHT) {
            inactiveFighter.flip(true, false);
            relativeMarginOfFighter = KeyInputView.getWidth() + fighterXMargin;
        } else if (placement == Placement.LEFT) {
            relativeMarginOfFighter = -fighterXMargin - inactiveFighter.getRegionWidth();
        }
        secondaryFighterView = new SecondaryFighterView(inactiveFighter, x + relativeMarginOfFighter, y + fighterYMargin, inactiveFighterName);
        gameScreen.addHudView(secondaryFighterView);
    }

    @Override
    public void onFighterSwap(Player player) {
        // TODO
        // set new attack names
        // set new secondary fighter name & sprite
    }
}
