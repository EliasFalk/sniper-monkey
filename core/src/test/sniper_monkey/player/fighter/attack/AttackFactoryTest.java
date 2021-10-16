package sniper_monkey.player.fighter.attack;

import game.sniper_monkey.model.player.fighter.attack.AttackFactory;
import game.sniper_monkey.model.player.fighter.attack.IAttack;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class AttackFactoryTest {

    @Test
    public void testCreateEvilMagicSwingAttack() {
        IAttack attack = AttackFactory.createEvilMagicSwingAttack();
        assertNotNull(attack);
    }

}
