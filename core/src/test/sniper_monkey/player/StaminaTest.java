package sniper_monkey.player;

import game.sniper_monkey.player.Stamina;
import org.junit.Before;
import org.junit.Test;


import static org.junit.Assert.*;


public class StaminaTest {


    @Test
    public void increaseStaminaTest() {
        float regenerationFactor = 10f;
        int maxAmount = 100;
        float deltaTime = 1/60f; // equal to 1 frame if we consider that the game plays at 60fps
        Stamina stamina = new Stamina(regenerationFactor, maxAmount);

        stamina.decrease(50);
        for (int i = 0; i < 60; i++) { //
            stamina.update(deltaTime);
        }
        assertEquals(60, stamina.getCurrentAmount(), 0.001);

    }

    //TODO add a decrease stamina test when attacks are implemented

    @Test
    public void increaseStaminaWhenMaxTest() {
        float regenerationFactor = 10f;
        int maxAmount = 100;
        float deltaTime = 1/60f;
        Stamina stamina = new Stamina(regenerationFactor, maxAmount);

        stamina.decrease(5);

        for (int i = 0; i < 60; i++) { //
            stamina.update(deltaTime);
        }
        assertEquals(100, stamina.getCurrentAmount(), 0);
    }

}
