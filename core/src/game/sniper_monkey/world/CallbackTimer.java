package game.sniper_monkey.world;

import game.sniper_monkey.Callback;

public class CallbackTimer {

    private final float timerLength;
    private float timeLeft;

    private boolean running;
    private final Callback callback;
    private boolean looping;

    public CallbackTimer(float timerLength, boolean loop, Callback callback) {
        this.callback = callback;
        this.timerLength = timerLength;
        timeLeft = timerLength;
        this.looping = loop;
    }

    public CallbackTimer(float timerLength, Callback callback) {
        this(timerLength, false, callback);
    }

    public void start() {
        running = true;
    }

    public void update(float deltaTime) {
        if(!running) return;
        timeLeft -= deltaTime;
        if (timeLeft <= 0) {
            running = false;
            if(looping) {
                reset();
                start();
            }
            callback.call();
        }
    }

    public void reset() {
        timeLeft = timerLength;
        running = false;
    }

    public void stop() {
        running = false;
    }

    public void setLooping(boolean loop) {
        this.looping = loop;
    }

    public float getTimeLeft() {
        return timeLeft;
    }
}
