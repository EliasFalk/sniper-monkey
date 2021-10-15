package sniper_monkey.player.fighter.attack;

import com.badlogic.gdx.math.Vector2;
import game.sniper_monkey.model.player.Player;
import game.sniper_monkey.model.player.PlayerFactory;
import game.sniper_monkey.model.player.fighter.attack.AttackFactory;
import game.sniper_monkey.model.player.fighter.attack.IAttack;
import game.sniper_monkey.model.player.fighter.attack.SwordAttack;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class AttackFactoryTest {

    @Test
    public void testCreateSwordAttack() {
        IAttack attack = AttackFactory.createSwordAttack();
        assertNotNull(attack);
    }

}
