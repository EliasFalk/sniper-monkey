package sniper_monkey.player;

import game.sniper_monkey.player.FluctuatingAttribute;
import game.sniper_monkey.player.Health;
import org.junit.Test;
import static org.junit.Assert.*;

public class HealthTest {

    @Test
    public void testHealthDecrease() {
        FluctuatingAttribute playerHealth = new FluctuatingAttribute(100);

        playerHealth.decrease(10);

        playerHealth.update(1/60f); // simulating one frame, game runs at 60fps
        assertEquals(90, playerHealth.getCurrentValue(), 0);

        playerHealth.decrease(200);
        playerHealth.update(1/60f);

        assertEquals(0, playerHealth.getCurrentValue(), 0);

        try {
            playerHealth.decrease(-10);
        } catch (IllegalArgumentException e) {
            if (e.getMessage().equals("Amount cannot be negative.")) {
                assertTrue(true);
            } else {
                fail();
            }
        }
    }

    @Test
    public void testHealthIsNone() {
        FluctuatingAttribute playerHealth = new FluctuatingAttribute(100);

        playerHealth.decrease(50);
        playerHealth.update(1/60f); //simulate one frame

        assertFalse(playerHealth.isNone());


        playerHealth.decrease(200);
        playerHealth.update(1/60f); //simulate one frame
        assertTrue(playerHealth.isNone());
    }

    // TODO make test to decrease health on attack


}
