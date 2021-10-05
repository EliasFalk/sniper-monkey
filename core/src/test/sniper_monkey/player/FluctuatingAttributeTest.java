package sniper_monkey.player;

import game.sniper_monkey.player.FluctuatingAttribute;
import org.junit.Assert;
import org.junit.Test;

public class FluctuatingAttributeTest {
    FluctuatingAttribute fluctuatingAttribute = new FluctuatingAttribute(1);


    @Test
    public void testRegeneration() {
        fluctuatingAttribute.setCurrentValue(0);
        fluctuatingAttribute.setRegenerationAmount(0.25f);
        fluctuatingAttribute.setRegenerating(true);
        fluctuatingAttribute.update(1);
        Assert.assertEquals(fluctuatingAttribute.getCurrentValue(), 0.25,0.0001);
    }

    @Test
    public void testRegenerationHigherLimit() {
        fluctuatingAttribute.setCurrentValue(0.9f);
        fluctuatingAttribute.setRegenerationAmount(0.25f);
        fluctuatingAttribute.setRegenerating(true);
        fluctuatingAttribute.update(1);
        Assert.assertEquals(fluctuatingAttribute.getCurrentValue(), 1,0.0);
    }

    @Test
    public void testRegenerationAmountNegative() {
        fluctuatingAttribute.setCurrentValue(0.9f);
        try {
            fluctuatingAttribute.setRegenerationAmount(-0.25f);
        } catch (IllegalArgumentException e) {

        }
    }

    @Test
    public void testDrain() {
        fluctuatingAttribute.setCurrentValue(0.30f);
        fluctuatingAttribute.setDrainAmount(0.25f);
        fluctuatingAttribute.setDraining(true);
        fluctuatingAttribute.update(1);
        Assert.assertEquals(fluctuatingAttribute.getCurrentValue(), 0.05f,0.0001);
    }

    @Test
    public void testDrainLowerLimit() {
        fluctuatingAttribute.setCurrentValue(0.30f);
        fluctuatingAttribute.setDrainAmount(0.45f);
        fluctuatingAttribute.setDraining(true);
        fluctuatingAttribute.update(1);
        Assert.assertEquals(fluctuatingAttribute.getCurrentValue(), 0,0.0);
    }

    @Test
    public void testDrainAmountNegative() {
        fluctuatingAttribute.setCurrentValue(0.9f);
        try {
            fluctuatingAttribute.setDrainAmount(-0.25f);
        } catch (IllegalArgumentException e) {

        }
    }

    @Test
    public void testDrainAndRegenerationSimultaneously() {
        fluctuatingAttribute.setCurrentValue(0.9f);
        fluctuatingAttribute.setDraining(true, 0.5f);
        fluctuatingAttribute.setRegenerating(true,0.3f);
        fluctuatingAttribute.update(1);
        Assert.assertEquals(fluctuatingAttribute.getCurrentValue(), 0.7f, 0.0001);
    }

    @Test
    public void testIncrease() {
        fluctuatingAttribute.setCurrentValue(0.30f);
        fluctuatingAttribute.increase(0.3f);
        Assert.assertEquals(fluctuatingAttribute.getCurrentValue(), 0.6f,0.0001);
    }

    @Test
    public void testIncreaseHigherLimit() {
        fluctuatingAttribute.setCurrentValue(0.30f);
        fluctuatingAttribute.increase(0.8f);
        Assert.assertEquals(fluctuatingAttribute.getCurrentValue(), 1,0.0);
    }

    @Test
    public void testIncreaseNegativeNumber() {
        fluctuatingAttribute.setCurrentValue(0.30f);
        try {
            fluctuatingAttribute.increase(-0.8f);
        } catch (IllegalArgumentException e) {

        }
    }

    @Test
    public void testDecrease() {
        fluctuatingAttribute.setCurrentValue(0.35f);
        fluctuatingAttribute.decrease(0.3f);
        Assert.assertEquals(fluctuatingAttribute.getCurrentValue(), 0.05f,0.0001);
    }

    @Test
    public void testDecreaseLowerLimit() {
        fluctuatingAttribute.setCurrentValue(0.30f);
        fluctuatingAttribute.decrease(0.8f);
        Assert.assertEquals(fluctuatingAttribute.getCurrentValue(), 0,0.0);
    }

    @Test
    public void testDecreaseNegativeNumber() {
        fluctuatingAttribute.setCurrentValue(0.30f);
        try {
            fluctuatingAttribute.decrease(-0.8f);
        } catch (IllegalArgumentException e) {

        }
    }

    //TODO add decrease test on attack

}
