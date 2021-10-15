package game.sniper_monkey.model.player.fighter;

import com.badlogic.gdx.math.Vector2;
import game.sniper_monkey.model.player.fighter.attack.AttackFactory;

/**
 * An Evil Wizard (spooky)
 *
 * @author Elias Falk
 */
public class EvilWizard extends Fighter {
    private static final float attack = 1;
    private static final float defense = 0.2f;
    private static final float speed = 1;
    private static final Vector2 hitboxSize = new Vector2(40, 55);

    /**
     * Creates an EvilWizard fighter.
     */
    public EvilWizard() {
        super(attack, defense, speed, hitboxSize);
        attacks.add(AttackFactory.createSwordAttack());
    }
}
