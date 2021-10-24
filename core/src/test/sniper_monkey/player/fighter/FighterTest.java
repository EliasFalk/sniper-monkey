package sniper_monkey.player.fighter;

import com.badlogic.gdx.math.Vector2;
import game.sniper_monkey.model.player.fighter.Fighter;
import game.sniper_monkey.model.player.fighter.FighterFactory;
import game.sniper_monkey.model.player.fighter.attack.EvilMagicSwingAttack;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class FighterTest {

    private static Fighter fighter;

    private static Fighter concreteFighter = FighterFactory.createEvilWizard();

    @BeforeClass
    public static void before() {
        fighter = new Fighter(1, 1, 1, new Vector2(10, 10)) {
            @Override
            public Vector2 getHitboxSize() {
                return super.getHitboxSize();
            }
        };
    }

    @Test
    public void testTotalAttacks() {
        assertEquals(0, fighter.getTotalAttacks());
    }

    @Test
    public void testGetHitboxSize() {
        assertEquals(fighter.getHitboxSize(), new Vector2(10, 10));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPerformAttack() {
        fighter.performAttack(0, new Vector2(1, 1), 0, false);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetStaminaDecrease() {
        fighter.getStaminaCost(0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetAttackLength() {
        fighter.getAttackLength(0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetHitStunLength() {
        fighter.getHitStunTime(0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetAttackClassWithException() {
        fighter.getAttackClass(0);
    }

    @Test
    public void testGetAttackClassWithActualFighter() {
        assertEquals(concreteFighter.getAttackClass(0), EvilMagicSwingAttack.class);

    }

}
