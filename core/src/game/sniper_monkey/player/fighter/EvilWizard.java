package game.sniper_monkey.player.fighter;

import com.badlogic.gdx.math.Vector2;

/**
 * An Evil Wizard (spooky)
 *
 * @author Elias Falk
 */
public class EvilWizard extends Fighter {
    private static final float attack = 1;
    private static final float defense = 1;
    private static final float speed = 1;
    private static final Vector2 hitboxSize = new Vector2(40, 55);

    /**
     * Creates an EvilWizard fighter.
     */
    public EvilWizard() {
        super(attack, defense, speed, hitboxSize);
        // TODO attacks.add(SomeAttack);
    }
}
