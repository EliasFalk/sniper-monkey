package sniper_monkey.world;
import org.junit.*;

import game.sniper_monkey.model.world.Timer;

import java.util.concurrent.TimeUnit;

public class TimerTest {
    Timer timer = new Timer(2);

    @Test
    public void testStartTimer() throws InterruptedException {
        timer.start();
        TimeUnit.SECONDS.sleep(1);
        Assert.assertTrue("Time has passed", timer.getTimePassed()>0);
    }

    @Test
    public void testEndTimer() {
        timer.start();
        timer.endTimer();
        Assert.assertTrue("Timer has ended", timer.isDone());
    }

    @Test
    public void testGetTimePassed() throws InterruptedException {
        timer.start();
        TimeUnit.SECONDS.sleep(1);
        Assert.assertEquals(1, timer.getTimePassed());
    }

    @Test
    public void testTimerIsDone() throws InterruptedException {
        timer.start();
        TimeUnit.SECONDS.sleep(2);
        Assert.assertTrue("Timer has ended", timer.isDone());
    }

}
