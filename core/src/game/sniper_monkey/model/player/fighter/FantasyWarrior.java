package game.sniper_monkey.model.player.fighter;

import com.badlogic.gdx.math.Vector2;

public class FantasyWarrior extends Fighter {

    private static final float attackFactor = 1;
    private static final float defenseFactor = 0.2f;
    private static final float speedFactor = 1;
    private static final Vector2 hitboxSize = new Vector2(44, 51);

    /**
     * Creates a Fantasy Warrior, with specified attributes.
     */
    public FantasyWarrior() {
        super(attackFactor, defenseFactor, speedFactor, hitboxSize);
    }
}
