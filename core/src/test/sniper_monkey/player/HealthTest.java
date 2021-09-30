package sniper_monkey.player;

import game.sniper_monkey.player.Health;
import org.junit.Test;

public class HealthTest {

    @Test
    public void testHealthDecrease() {
        Health playerHealth = new Health(100);

        playerHealth.onDamage(10);

    }


}
