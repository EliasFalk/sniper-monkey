package game.sniper_monkey.model.player.fighter;

import com.badlogic.gdx.math.Vector2;
import game.sniper_monkey.model.player.fighter.attack.AttackFactory;


/**
 * A fantasy warrior fighter.
 */
public class FantasyWarrior extends Fighter {

    private static final float attackFactor = 1;
    private static final float defenseFactor = 0.2f;
    private static final float speedFactor = 1;
    private static final Vector2 hitboxSize = new Vector2(33, 55);

    /**
     * Creates a Fantasy Warrior, with specified attributes.
     */
    public FantasyWarrior() {
        super(attackFactor, defenseFactor, speedFactor, hitboxSize);
        attacks.add(AttackFactory.createElectricalSlashAttack());
        attacks.add(AttackFactory.createElectricalSmashAttack());
    }
}
