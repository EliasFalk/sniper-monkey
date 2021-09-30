package sniper_monkey.player;

import game.sniper_monkey.player.FluctuatingAttribute;
import game.sniper_monkey.player.Stamina;
import org.junit.Before;
import org.junit.Test;


import java.awt.*;

import static org.junit.Assert.*;


public class StaminaTest {


    @Test
    public void increaseStaminaTest() {
        float regenerationFactor = 10f;
        int maxAmount = 100;
        float staminaDrain = 50f;
        float deltaTime = 1/60f; // equal to 1 frame if we consider that the game plays at 60fps
        FluctuatingAttribute stamina = new FluctuatingAttribute(0, maxAmount, regenerationFactor);
        stamina.setRegenerating(true);

        stamina.decrease(staminaDrain);
        System.out.println(stamina.getCurrentValue());
        for (int i = 0; i < 60; i++) { //
            stamina.update(deltaTime);
        }
        assertEquals(60, stamina.getCurrentValue(), 0.001);

    }

    //TODO add a decrease stamina test when attacks are implemented

    @Test
    public void increaseStaminaWhenMaxTest() {
        float regenerationFactor = 10f;
        int maxAmount = 100;
        float staminaDrain = 5f;
        float deltaTime = 1/60f; // equal to 1 frame if we consider that the game plays at 60fps
        FluctuatingAttribute stamina = new FluctuatingAttribute(0, maxAmount, regenerationFactor);

        stamina.setRegenerating(true);
        stamina.decrease(staminaDrain);
        for (int i = 0; i < 60; i++) { //
            stamina.update(deltaTime);
        }
        assertEquals(100, stamina.getCurrentValue(), 0);
    }



}
