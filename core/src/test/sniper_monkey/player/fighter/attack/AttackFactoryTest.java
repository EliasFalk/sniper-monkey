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

    @Test
    public void testCreateEvilMagicHammerAttack() {
        IAttack attack = AttackFactory.createEvilMagicHammerAttack();
        assertNotNull(attack);
    }
    @Test
    public void testCreateElectricalSmashAttack() {
        IAttack attack = AttackFactory.createElectricalSmashAttack();
        assertNotNull(attack);
    }
    @Test
    public void testCreateElectricalSlashAttack() {
        IAttack attack = AttackFactory.createElectricalSlashAttack();
        assertNotNull(attack);
    }
    @Test
    public void testCreateBowAttack() {
        IAttack attack = AttackFactory.createBowAttack();
        assertNotNull(attack);
    }
    @Test
    public void testCreateBowTripleAttack() {
        IAttack attack = AttackFactory.createBowTripleAttack();
        assertNotNull(attack);
    }
    @Test
    public void testCreateSamuraiQuickAttack() {
        IAttack attack = AttackFactory.createSamuraiQuickAttack();
        assertNotNull(attack);
    }
    @Test
    public void testCreateSamuraiShurikenAttack() {
        IAttack attack = AttackFactory.createSamuraiShurikenAttack();
        assertNotNull(attack);
    }

}
