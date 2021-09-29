package game.sniper_monkey.utils.time;

public class Time {

    public static final long START_TIME = System.currentTimeMillis();

    public static float getElapsedTime() {
        return (System.currentTimeMillis() - START_TIME) / 1000f;
    }
}
