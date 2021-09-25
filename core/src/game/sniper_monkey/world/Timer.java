package game.sniper_monkey.world;

public class Timer {
    private final long NANO = 1000000000; // Number of nanoseconds in a second

    //For tracking timer start, end and duration
    long start;
    long end;
    long seconds;

    /**
     * Creates the timer object
     * @param seconds
     */
    public Timer(int seconds)
    {
        this.seconds = seconds * NANO;
        start = end = -1;
    }

    /**
     * Activates the timer
     */
    public void start()
    {
        start = System.nanoTime();
        end  = start + seconds;
    }

    public boolean end()
    {
        start = end = -1;
        System.out.println("Timer ended");
        return true;
    }

    public int getTimePassed() {
        return (int) (((System.nanoTime() - start)/NANO) % 60);
    }

    /**
     * Checks if the timer is done and deactivates it, if it is.
     */
    public boolean isDone()
    {
        if (start > 0 && end > 0)
            if (System.nanoTime() >= end) {
                start = end = -1;
                return true;
            }

        return false;
    }
}
