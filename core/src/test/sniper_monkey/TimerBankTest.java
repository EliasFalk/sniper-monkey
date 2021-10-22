package sniper_monkey;

import game.sniper_monkey.model.TimerBank;
import game.sniper_monkey.model.world.CallbackTimer;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class TimerBankTest {

    @BeforeClass
    public static void init() {
        TimerBank.clear();
    }

    @Test
    public void addTimerTest() {
        CallbackTimer timer = new CallbackTimer(10, false, null);

        CallbackTimer nullTimer = null;

        TimerBank.addTimer(timer);
        TimerBank.updateTimers(0);
        assertTrue(TimerBank.contains(timer));

        assertFalse(TimerBank.addTimer(timer));
    }

    @Test
    public void addIllegalTimerTest() {
        CallbackTimer nullTimer = null;
        try {
            TimerBank.addTimer(nullTimer);
        } catch (IllegalArgumentException e) {
            if (e.getMessage().equals("Timer cannot be null.")) {
                assertTrue(true);
            } else {
                fail();
            }
        }
    }

    @Test
    public void removeTimerTest() {
        CallbackTimer timer = new CallbackTimer(10, false, null);
        TimerBank.addTimer(timer);
        TimerBank.removeTimer(timer);

        assertFalse(TimerBank.contains(timer));
        assertFalse(TimerBank.removeTimer(timer));

    }

    @Test
    public void removeIllegalTimerTest() {
        CallbackTimer timer = new CallbackTimer(10, false, null);
        TimerBank.addTimer(timer);

        CallbackTimer nullTimer = null;

        try {
            TimerBank.addTimer(nullTimer);
        } catch (IllegalArgumentException e) {
            if (e.getMessage().equals("Timer cannot be null.")) {
                assertTrue(true);
            } else {
                fail();
            }
        }

    }

    @Test
    public void updateTimersTest() {
        CallbackTimer timer = new CallbackTimer(10, false, null);

        TimerBank.addTimer(timer);

        timer.start();

        TimerBank.updateTimers(1f); // simulates one second


        assertEquals(9, timer.getTimeLeft(), 0.001);

    }


}
