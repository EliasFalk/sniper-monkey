package game.sniper_monkey.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import game.sniper_monkey.model.player.Player;
import game.sniper_monkey.model.player.SwappedFighterObserver;
import game.sniper_monkey.utils.Config;
import game.sniper_monkey.utils.view.HUDUtils;
import game.sniper_monkey.view.GameScreen;
import game.sniper_monkey.view.hud.KeyInputView;
import game.sniper_monkey.view.hud.Placement;
import game.sniper_monkey.view.hud.SecondaryFighterView;

/**
 * Controls the bottom part of the HUD of a player including the key inputs and secondary fighter.
 * <p>
 * Uses SwappedFighterObserver.
 * Uses Player. // TODO discuss: sad dependency
 * Uses GameScreen.
 * Uses SecondaryFighterView.
 * Uses KeyInputView.
 * Uses Placement.
 * Uses Config.
 * <p>
 * Used by GameController.
 *
 * @author Elias Falk
 */
public class BottomHUDController implements SwappedFighterObserver {
    private final GameScreen gameScreen;
    private final Player player;
    private static final float keyInputMargin = 6f;
    private static final float fighterXMargin = 75f;
    private static final float fighterYMargin = 50f;
    private static final float keyInputsXPosition = 200f;
    private static final float keyInputsYPosition = 25;
    private SecondaryFighterView secondaryFighterView;
    private KeyInputView attack1;
    private KeyInputView attack2;
    private KeyInputView block;
    private KeyInputView swapFighter;
    private final String keybindsFilePath;
    private final Placement placement;
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
        init();
    }

    private void init() {
        Config.readConfigFile(keybindsFilePath);
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

        attack1 = new KeyInputView(x, y,
                Input.Keys.toString((int) Config.getNumber(keybindsFilePath, "ATTACK1")),
                HUDUtils.getAttackDisplayName(player.getAttackClass(0)), textPlacement);
        attack2 = new KeyInputView(x, y + KeyInputView.getHeight() + keyInputMargin,
                Input.Keys.toString((int) Config.getNumber(keybindsFilePath, "ATTACK2")),
                HUDUtils.getAttackDisplayName(player.getAttackClass(1)), textPlacement);
        block = new KeyInputView(x, y + (KeyInputView.getHeight() + keyInputMargin) * 2, Input.Keys.toString((int) Config.getNumber(keybindsFilePath, "BLOCK")), "Block", textPlacement);
        swapFighter = new KeyInputView(x, y + (KeyInputView.getHeight() + keyInputMargin) * 3, Input.Keys.toString((int) Config.getNumber(keybindsFilePath, "SWAP_FIGHTER")), "Swap fighter", textPlacement);

        gameScreen.addHudView(attack1);
        gameScreen.addHudView(attack2);
        gameScreen.addHudView(block);
        gameScreen.addHudView(swapFighter);

        player.registerHitStunObserver(attack1);
        player.registerHitStunObserver(attack2);
        player.registerBlockCooldownObserver(block);
        player.registerSwapCooldownObserver(swapFighter);
    }

    private void createSecondaryFighterView() {
        TextureRegion inactiveFighter = HUDUtils.getCorrespondingTextureRegion(player.getInactiveFighterClass());

        float relativeMarginOfFighter;
        boolean flip;
        if (placement == Placement.RIGHT) {
            relativeMarginOfFighter = KeyInputView.getWidth() + fighterXMargin;
            flip = true;
        } else {
            relativeMarginOfFighter = -fighterXMargin - inactiveFighter.getRegionWidth();
            flip = false;
        }
        secondaryFighterView = new SecondaryFighterView(player.getInactiveFighterClass(), x + relativeMarginOfFighter, y + fighterYMargin, flip);
        gameScreen.addHudView(secondaryFighterView);
    }

    @Override
    public void onFighterSwap(Player player) {
        secondaryFighterView.updateFighterView(player.getInactiveFighterClass());
        attack1.setText(HUDUtils.getAttackDisplayName(player.getAttackClass(0)));
        attack2.setText(HUDUtils.getAttackDisplayName(player.getAttackClass(1)));
    }
}
