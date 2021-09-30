package game.sniper_monkey.player.fighter;

import com.badlogic.gdx.math.Vector2;
import game.sniper_monkey.collision.Hitbox;
import game.sniper_monkey.player.fighter.attack.IAttack;

import java.util.ArrayList;
import java.util.List;

public class EvilWizard extends Fighter {

    /**
     * Creates an EvilWizard fighter.
     */
    public EvilWizard() {
        super(1, 1, 1, new Vector2(40, 55));
        // TODO attacks.add(SomeAttack);
    }

}