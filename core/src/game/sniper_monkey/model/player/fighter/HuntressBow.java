package game.sniper_monkey.model.player.fighter;

import com.badlogic.gdx.math.Vector2;
import game.sniper_monkey.model.player.fighter.attack.AttackFactory;

public class HuntressBow extends Fighter {
    private static final float attack = 1;
    private static final float defense = 0.2f;
    private static final float speed = 1;
    private static final Vector2 hitboxSize = new Vector2(44, 51);

    /**
     * Creates a specific fighter, such as an Evil Wizard with x Factors.
     *
     */
    public HuntressBow() {
        super(attack, defense, speed, hitboxSize);
        attacks.add(AttackFactory.createBowAttack());
        attacks.add(AttackFactory.createEvilMagicHammerAttack());
    }
}
