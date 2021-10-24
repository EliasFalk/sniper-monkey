package game.sniper_monkey.view.hud;

import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 * A view for HUD element.
 * <p>
 * Used by GameScreen.
 * Used by BarView.
 * Used by KeyInputView.
 * Used by OverLayMenu.
 * Used by RoundTimerView.
 * Used by SecondaryFighterView.
 *
 * @author Kevin Jeryd
 * @author Elias Falk
 * @author Vincent Hellner
 */
public interface HUDView {

    /**
     * Adds the actors that the HUD view uses to the given stage.
     *
     * @param stage The stage that the actors should be added to.
     */
    void addActors(Stage stage);

    /**
     * Removes the actors that the HUD view uses from its parent.
     */
    void removeActors();
}
