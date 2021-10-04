package sniper_monkey.player.fighter;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import game.sniper_monkey.collision.Hitbox;
import game.sniper_monkey.player.Player;
import game.sniper_monkey.player.fighter.Fighter;
import game.sniper_monkey.player.fighter.FighterFactory;
import game.sniper_monkey.world.GameObject;
import game.sniper_monkey.world.World;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import static org.junit.Assert.*;

public class FighterTest {

    private static Fighter fighter;

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
    public void testTotalAttacks(){
        assertEquals(0, fighter.getTotalAttacks());
    }

    @Test
    public void testGetHitboxSize(){
        assertEquals(fighter.getHitboxSize(), new Vector2(10, 10));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPerformAttack() {
        fighter.performAttack(0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetStaminaDecrease() {
        fighter.getStaminaDecrease(0);
    }

}
