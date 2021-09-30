package game.sniper_monkey.player;


// TODO find a better name for the class
public class FluctuatingAttribute {
    private float regenerationAmount;
    private float drainAmount;
    private float currentValue;

    private final float maxValue;
    private final float minValue;

    private boolean isDraining;
    private boolean isRegenerating;

    /**
     * Creates a fluctuatingAttribute object.
     * @param minValue The minimum value that the attribute can have.
     * @param maxValue The maximum value that the attribute can have.
     * @param regenerationAmount a float 0..n. Is the amount that the attribute regenerates by per second.
     * @param drainAmount a float 0..n. Is the amount that the attribute drain by per second.
     */
    public FluctuatingAttribute(float minValue, float maxValue, float regenerationAmount, float drainAmount) {
        this.maxValue = maxValue;
        this.minValue = minValue;
        setDrainAmount(drainAmount);
        setRegenerationAmount(regenerationAmount);
        currentValue = maxValue;
    }

    /**
     * Creates a fluctuatingAttribute object. Sets drainAmount to 0.
     * @param minValue The minimum value that the attribute can have.
     * @param maxValue The maximum value that the attribute can have.
     * @param regenerationAmount a float 0..n. Is the amount that the attribute regenerates by per second.
     */
    public FluctuatingAttribute(float minValue, float maxValue, float regenerationAmount) {
        this(minValue, maxValue, regenerationAmount, 0);
    }
    /**
     * Creates a fluctuatingAttribute object. Sets regenerationAmount to 0.
     * @param minValue The minimum value that the attribute can have.
     * @param maxValue The maximum value that the attribute can have.
     */
    public FluctuatingAttribute(float minValue, float maxValue) {
        this(minValue, maxValue, 0);
    }

    /**
     * Creates a fluctuatingAttribute object. Sets minValue to 0.
     * @param maxValue The maximum value that the attribute can have.
     */
    public FluctuatingAttribute(float maxValue) {
        this(0, maxValue);
    }


    /**
     * Sets the regenerationAmount to a new value. Must be >= 0.
     * @param regenerationAmount a float 0..n. Is the amount that the attribute regenerates by per second.
     */
    public void setRegenerationAmount(float regenerationAmount) {
        if(regenerationAmount < 0) {
            throw new IllegalArgumentException("RegenerationAmount cannot be negative.");
        }
        this.regenerationAmount = regenerationAmount;
    }

    /**
     * Sets the drainAmount to a new value. Must be >= 0.
     * @param drainAmount a float 0..n. Is the amount that the attribute regenerates by per second.
     */
    public void setDrainAmount(float drainAmount) {
        if(drainAmount < 0) {
            throw new IllegalArgumentException("DrainAmount cannot be negative.");
        }
        this.drainAmount = drainAmount;
    }

    /**
     * Updates the attributes' current value if the attribute is regenerating or draining.
     * @param deltaTime a float between 0..n. It's the time between each frame and is often very small.
     */
    public void update(float deltaTime) {
        if(isDraining && isRegenerating) {
            currentValue = Math.max(minValue, Math.min(maxValue, currentValue + (regenerationAmount - drainAmount) * deltaTime));
        } else if(isRegenerating) {
            currentValue = Math.min(currentValue + regenerationAmount * deltaTime, maxValue);
        } else if(isDraining) {
            currentValue = Math.max(currentValue - drainAmount * deltaTime, minValue);
        }
    }


    /**
     * Gets the current value.
     * @return The current value of the attribute.
     */
    public float getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(float currentValue) {
        this.currentValue = Math.max(minValue, Math.min(maxValue, currentValue));
    }

    public void setDraining(boolean draining, float drainAmount) {
        isDraining = draining;
        setDrainAmount(drainAmount);
    }

    public void setDraining(boolean draining) {
        setDraining(draining, drainAmount);
    }

    public void setRegenerating(boolean regenerating, float regenerationAmount) {
        isRegenerating = regenerating;
        setRegenerationAmount(regenerationAmount);
    }

    public void setRegenerating(boolean regenerating) {
        setRegenerating(regenerating, regenerationAmount);
    }

    /**
     * Increases the attributes' current value with a specified amount.
     * The currentValue + amount cannot be higher than the maxValue. Otherwise, the value is set to the maxValue.
     * @param amount a float 0..n. Is the amount that the current value is increased with. Must be >= 0.
     */
    public void increase(float amount) {
        if(amount < 0) {
            throw new IllegalArgumentException("Amount cannot be negative.");
        }
        setCurrentValue(currentValue + amount);
    }

    /**
     * Decreases the attributes' current value with a specified amount.
     * The currentValue - amount cannot be less than 0. Otherwise, the value is set to minValue.
     * @param amount a float 0..n. Is the amount that the current value is decreased with. Must be >= 0.
     */
    public void decrease(float amount) {
        if(amount < 0) {
            throw new IllegalArgumentException("Amount cannot be negative.");
        }
        setCurrentValue(currentValue - amount);
    }
}


