package game.sniper_monkey.model.player.fighter;

import com.badlogic.gdx.math.Vector2;
import game.sniper_monkey.model.player.fighter.attack.AttackFactory;

/**
 *  A samurai fighter.
 *
 *  <p>
 *    Used by FighterFactory
 *    Used by HUDUtils
 *    Used by GameObjectViewFactory
 *    Used by CharacterSelectionScreen
 *
 *    Uses AttackFactory
 *  </p>
 *
 * @author Dadi Andrason
 */
public class Samurai extends Fighter {

    private static final float attackFactor = 1;
    private static final float defenseFactor = 0.2f;
    private static final float speedFactor = 1;
    private static final Vector2 hitboxSize = new Vector2(33, 55);

    /**
     * Creates a Samurai fighter with some specific attributes.
     *
     */
    protected Samurai() {
        super(attackFactor, defenseFactor, speedFactor, hitboxSize);
        attacks.add(AttackFactory.createSamuraiQuickAttack());
        attacks.add(AttackFactory.createSamuraiShurikenAttack());
    }
}