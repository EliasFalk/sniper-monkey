package game.sniper_monkey.world;

import game.sniper_monkey.Callback;
import game.sniper_monkey.TimerBank;
import game.sniper_monkey.UpdatableTimer;

public class CallbackTimer implements UpdatableTimer {

    private final float timerLength;
    private float timeLeft;

    private boolean running;
    private final Callback callback;
    private boolean looping;

    /**
     * Creates a timer with a callback method which will be called upon when the timer finishes.
     * The timer does not start automatically, need to call start() for the timer to start.
     *
     * @param timerLength The length of the timer in seconds.
     * @param loop        Set true if timer should restart each time it finishes.
     * @param callback    The method that will be called each time the timer finishes.
     */
    public CallbackTimer(float timerLength, boolean loop, Callback callback) {
        this.callback = callback;
        this.timerLength = timerLength;
        timeLeft = timerLength;
        this.looping = loop;
        TimerBank.addTimer(this);
    }

    /**
     * Set whether the TimerBank should handle the updates, or handled manually by calling the update method somewhere else.
     * @param autoUpdate True will add this timer to the TimerBank and will update when TimerBank's update is called.
     *                   False will remove this timer from the TimerBank and will require this timer's update to be called manually somewhere else.
     */
    public void setAutoUpdate(boolean autoUpdate) {
        if(autoUpdate) {
            TimerBank.addTimer(this);
        } else {
            TimerBank.removeTimer(this);
        }
    }

    /**
     * Creates a timer with a callback method which will be called upon when the timer finishes.
     * The timer does not start automatically, need to call start() for the timer to start.
     * Will default to looping = false.
     *
     * @param timerLength The length of the timer in seconds.
     * @param callback    The method that will be called each time the timer finishes.
     */
    public CallbackTimer(float timerLength, Callback callback) {
        this(timerLength, false, callback);
    }

    /**
     * Sets the running state of the timer to true.
     * Does not reset the timer, will only make the timer update with deltaTime on update.
     */
    public void start() {
        running = true;
    }

    /**
     * Updates the timer by decreasing the timeLeft by deltaTime.
     * The timer finishes when the time left <= 0.
     * If the timer is done it will call the callback method set when creating the timer and set the time left to 0 and stop running.
     * If the timer is set to looping it will reset and start the timer and call the callback method each time the timer finishes.
     *
     * @param deltaTime The time between frames.
     */
    public void update(float deltaTime) {
        if (!running) return;
        timeLeft -= deltaTime;
        if (timeLeft <= 0) {
            running = false;
            timeLeft = 0;
            if (looping) {
                reset();
                start();
            }
            callback.call();
        }
    }

    /**
     * Resets the time left to the initial starting time.
     * Stops the timer.
     */
    public void reset() {
        timeLeft = timerLength;
        running = false;
    }

    /**
     * Stops the timer.
     */
    public void stop() {
        running = false;
    }

    /**
     * Set if the timer will restart each time it finishes.
     *
     * @param loop True will set timer to restart each time it finishes, false will make timer stop running on finish.
     */
    public void setLooping(boolean loop) {
        this.looping = loop;
    }

    /**
     * The time left of the timer in seconds, returns 0 if the timer has finished.
     *
     * @return The time left of the timer in seconds.
     */
    public float getTimeLeft() {
        return timeLeft;
    }

    /**
     * Returns the state of the timer, if it's finished or not.
     * If looping this will always return false.
     * If running it will return false.
     * Returns true only if the timer is not running, no time left and is not looping.
     *
     * @return The done-state of the timer.
     */
    public boolean isDone() {
        return !running && timeLeft == 0 && !looping;
    }

    /**
     * Returns the running-state of the timer.
     *
     * @return True if the timer is running, false if not running.
     */
    public boolean isRunning() {
        return running;
    }
}
