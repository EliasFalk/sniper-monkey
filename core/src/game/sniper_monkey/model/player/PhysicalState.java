package game.sniper_monkey.model.player;

/**
 * Enum representing the different physical states a player can have.
 * <p>
 * Used by Player.
 * Used by concrete FighterViews, such as EvilWizardView.
 *
 * @author Elias Falk
 * @author Vincent Hellner
 * @author Kevin Jeryd
 */
public enum PhysicalState {
    ATTACKING1,
    ATTACKING2,
    JUMPING,
    FALLING,
    MOVING,
    BLOCKING,
    TAKING_DAMAGE,
    DYING,
    IDLING,
}
