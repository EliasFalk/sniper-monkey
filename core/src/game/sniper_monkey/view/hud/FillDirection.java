package game.sniper_monkey.view.hud;

/**
 * The direction of which a fillable bar will be filled.
 * For example the direction UP means a 1/3 filled bar is colored at the bottom 3rd part.
 * <p>
 * Used by GameController.
 * Used by BarView.
 * Used by Fillablebar.
 * Used by KeyInputView.
 *
 * @author Elias Falk
 */
public enum FillDirection {
    LEFT,
    RIGHT,
    UP,
    DOWN
}
