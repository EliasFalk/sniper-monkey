package sniper_monkey.world;

import game.sniper_monkey.TimerBank;
import game.sniper_monkey.world.CallbackTimer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class CallbackTimerTest {

    private CallbackTimer cbTimer;
    private boolean finished;
    private final float timerLength = 1f;

    @Before
    public void initTimer() {
        cbTimer = new CallbackTimer(timerLength, () -> finished = true);
        cbTimer.setAutoUpdate(false);
        finished = false;
    }

    @Test
    public void testStart() {
        boolean initialRunning = cbTimer.isRunning();
        cbTimer.start();
        boolean runningAfterStart = cbTimer.isRunning();

        Assert.assertFalse(initialRunning);
        Assert.assertTrue(runningAfterStart);
    }

    @Test
    public void testUpdateToFinish() {
        cbTimer.start();
        cbTimer.update(timerLength);
        Assert.assertTrue(finished);
    }

    @Test
    public void testUpdateToNotFinish() {
        cbTimer.start();
        cbTimer.update(0.5f);
        Assert.assertFalse(finished);
    }

    @Test
    public void testReset() {
        cbTimer.start();
        cbTimer.update(0.5f);
        float timeLeft = cbTimer.getTimeLeft();
        cbTimer.reset();
        cbTimer.update(timeLeft);
        Assert.assertEquals(1, cbTimer.getTimeLeft(), 0.0f);
        Assert.assertNotEquals(timeLeft, cbTimer.getTimeLeft());
    }

    @Test
    public void testStop() {
        cbTimer.start();
        cbTimer.stop();
        cbTimer.update(0.5f);
        Assert.assertEquals(timerLength, cbTimer.getTimeLeft(), 0.0f);
    }

    @Test
    public void testTimerFinished() {
        cbTimer.start();
        cbTimer.update(timerLength);
        Assert.assertTrue(cbTimer.isDone());
        Assert.assertEquals(0, cbTimer.getTimeLeft(), 0);
        Assert.assertTrue(finished);
    }

    @Test
    public void testLoopingTrue() {
        cbTimer.setLooping(true);
        cbTimer.start();
        cbTimer.update(timerLength);
        Assert.assertTrue(finished);
        finished = false;
        cbTimer.update(timerLength);
        Assert.assertTrue(finished);
    }

    @Test
    public void testLoopingFalse() {
        cbTimer.setLooping(false);
        cbTimer.start();
        cbTimer.update(timerLength);
        Assert.assertTrue(finished);
        finished = false;
        cbTimer.update(timerLength);
        Assert.assertFalse(finished);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNegativeTimeLength() {
        CallbackTimer cbTimer2 = new CallbackTimer(-100, () -> finished = true);
    }

    @Test
    public void testSettingAutoUpdate() {
        cbTimer.setAutoUpdate(true);
        Assert.assertTrue(TimerBank.contains(cbTimer));
        cbTimer.setAutoUpdate(false);
        Assert.assertFalse(TimerBank.contains(cbTimer));
    }

    @Test
    public void testNewTimerWithLoopingEnabled() {
        List<Integer> onLoopValues = new ArrayList<>();
        CallbackTimer loopingCbTimer = new CallbackTimer(timerLength, true, () -> onLoopValues.add(1));
        loopingCbTimer.start();
        loopingCbTimer.setAutoUpdate(false);
        loopingCbTimer.update(timerLength);
        loopingCbTimer.update(timerLength);
        Assert.assertEquals(2, onLoopValues.size());
    }

    @Test
    public void testTimerIsNeverDoneWhenLooping() {
        cbTimer.setLooping(true);
        cbTimer.start();
        cbTimer.update(timerLength);
        Assert.assertFalse(cbTimer.isDone());
    }

}
