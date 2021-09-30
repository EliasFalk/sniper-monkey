package game.sniper_monkey;

import java.util.ArrayList;
import java.util.List;

public class TimerBank {
    private static final List<UpdatableTimer> timers = new ArrayList<>();

    public static boolean addTimer(UpdatableTimer timer) {
        if(timer == null) throw new IllegalArgumentException("Timer cannot be null.");
        if(timers.contains(timer)) return false;
        timers.add(timer);
        return true;
    }

    public static boolean removeTimer(UpdatableTimer timer) {
        if(timer == null) throw new IllegalArgumentException("Timer cannot be null.");
        if(!timers.contains(timer)) return false;
        timers.remove(timer);
        return true;
    }

    public static void updateTimers(float deltaTime) {
        for(UpdatableTimer timer: timers) {
            timer.update(deltaTime);
        }
    }
}
