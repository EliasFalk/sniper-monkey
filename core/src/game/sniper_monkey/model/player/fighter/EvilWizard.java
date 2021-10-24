package game.sniper_monkey.model.player.fighter;

import com.badlogic.gdx.math.Vector2;
import game.sniper_monkey.model.player.fighter.attack.AttackFactory;

/**
 * An Evil Wizard (spooky)
 *
 * <p>
 *     Used by FighterFactory
 *     Used by HUDUtils
 *     Used by GameObjectViewFactory
 *     Used by CharacterSelectionScreen
 *
 *     Uses AttackFactory
 * </p>
 *
 * @author Elias Falk
 * @author Kevin Jeryd
 * @author Dadi Andrason
 */
public class EvilWizard extends Fighter {
    private static final float attack = 1;
    private static final float defense = 0.2f;
    private static final float speed = 1;
    private static final Vector2 hitboxSize = new Vector2(40, 55);

    /**
     * Creates an EvilWizard fighter.
     */
    protected EvilWizard() {
        super(attack, defense, speed, hitboxSize);
        attacks.add(AttackFactory.createEvilMagicSwingAttack());
        attacks.add(AttackFactory.createEvilMagicHammerAttack());
    }
}
