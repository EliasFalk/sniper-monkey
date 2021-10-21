package game.sniper_monkey.model.player;


import java.util.ArrayList;
import java.util.List;

/**
 * Representing a range of float values which can fluctuate over time by regenerating or draining slowly
 * or by instantaneously increasing or decreasing.
 * <p>
 * Uses FluctuatingAttributeObserver.
 * <p>
 * Used by Player.
 *
 * @author Elias Falk
 * @author Vincent Hellner
 * @author Dadi Andrason
 */
public class FluctuatingAttribute {

    private final List<FluctuatingAttributeObserver> observers;
    private final float maxValue;
    private final float minValue;
    private float regenerationAmount;
    private float drainAmount;
    private float currentValue;
    private boolean isDraining;
    private boolean isRegenerating;

    /**
     * Creates a fluctuatingAttribute object.
     *
     * @param minValue           The minimum value that the attribute can have.
     * @param maxValue           The maximum value that the attribute can have.
     * @param regenerationAmount a float 0..n. Is the amount that the attribute regenerates by per second.
     * @param drainAmount        a float 0..n. Is the amount that the attribute drain by per second.
     */
    public FluctuatingAttribute(float minValue, float maxValue, float regenerationAmount, float drainAmount) {
        this.maxValue = maxValue;
        this.minValue = minValue;
        setDrainAmount(drainAmount);
        setRegenerationAmount(regenerationAmount);
        currentValue = maxValue;
        observers = new ArrayList<>();
    }

    /**
     * Creates a fluctuatingAttribute object. Sets drainAmount to 0.
     *
     * @param minValue           The minimum value that the attribute can have.
     * @param maxValue           The maximum value that the attribute can have.
     * @param regenerationAmount a float 0..n. Is the amount that the attribute regenerates by per second.
     */
    public FluctuatingAttribute(float minValue, float maxValue, float regenerationAmount) {
        this(minValue, maxValue, regenerationAmount, 0);
    }

    /**
     * Creates a fluctuatingAttribute object. Sets regenerationAmount to 0.
     *
     * @param minValue The minimum value that the attribute can have.
     * @param maxValue The maximum value that the attribute can have.
     */
    public FluctuatingAttribute(float minValue, float maxValue) {
        this(minValue, maxValue, 0);
    }

    /**
     * Creates a fluctuatingAttribute object. Sets minValue to 0.
     *
     * @param maxValue The maximum value that the attribute can have.
     */
    public FluctuatingAttribute(float maxValue) {
        this(0, maxValue);
    }


    /**
     * Sets the regenerationAmount to a new value. Must be >= 0.
     *
     * @param regenerationAmount a float 0..n. Is the amount that the attribute regenerates by per second.
     */
    public void setRegenerationAmount(float regenerationAmount) {
        if (regenerationAmount < 0) {
            throw new IllegalArgumentException("RegenerationAmount cannot be negative.");
        }
        this.regenerationAmount = regenerationAmount;
    }

    /**
     * Sets the drainAmount to a new value. Must be >= 0.
     *
     * @param drainAmount a float 0..n. Is the amount that the attribute regenerates by per second.
     */
    public void setDrainAmount(float drainAmount) {
        if (drainAmount < 0) {
            throw new IllegalArgumentException("DrainAmount cannot be negative.");
        }
        this.drainAmount = drainAmount;
    }

    /**
     * Updates the attributes' current value if the attribute is regenerating or draining.
     *
     * @param deltaTime a float between 0..n. It's the time between each frame and is often very small.
     */
    public void update(float deltaTime) {
        if (isDraining && isRegenerating) {
            setCurrentValue(currentValue + (regenerationAmount - drainAmount) * deltaTime);
        } else if (isRegenerating) {
            setCurrentValue(currentValue + regenerationAmount * deltaTime);
        } else if (isDraining) {
            setCurrentValue(currentValue - drainAmount * deltaTime);
        }
    }


    /**
     * Gets the current value.
     *
     * @return The current value of the attribute.
     */
    public float getCurrentValue() {
        return currentValue;
    }

    /**
     * Sets the current value of the attribute to a new value.
     * If the value is greater than the attributes max value the current value will become the max value.
     * If the value is less than the attributes minimum value the current value will become the min value.
     *
     * @param newValue The new value to set the current value of the attribute to.
     */
    public void setCurrentValue(float newValue) {
        this.currentValue = Math.max(minValue, Math.min(maxValue, newValue));
        for (FluctuatingAttributeObserver observer : observers) {
            observer.onValueChange(minValue, maxValue, this.currentValue);
        }
    }

    /**
     * Sets whether to decrease the attributes value with a new drain amount every second based on the time between frames (deltaTime).
     *
     * @param draining    Whether to drain every frame.
     * @param drainAmount The amount to decrease the current amount with every second based on the time between frames.
     */
    public void setDraining(boolean draining, float drainAmount) {
        isDraining = draining;
        setDrainAmount(drainAmount);
    }

    /**
     * Sets whether to decrease the attributes value every second based on the time between frames (deltaTime).
     *
     * @param draining Whether to drain every frame.
     */
    public void setDraining(boolean draining) {
        setDraining(draining, drainAmount);
    }

    /**
     * Sets whether to increase the attributes value with a new regeneration amount every second based on the time between frames (deltaTime).
     *
     * @param regenerating       Whether to regenerate the attribute every frame.
     * @param regenerationAmount The amount to increase the current amount with every second based on the time between frames.
     */
    public void setRegenerating(boolean regenerating, float regenerationAmount) {
        isRegenerating = regenerating;
        setRegenerationAmount(regenerationAmount);
    }

    /**
     * Sets whether to increase the attributes value every second based on the time between frames (deltaTime).
     *
     * @param regenerating Whether to regenerate every frame.
     */
    public void setRegenerating(boolean regenerating) {
        setRegenerating(regenerating, regenerationAmount);
    }

    /**
     * Increases the attributes' current value with a specified amount.
     * The currentValue + amount cannot be higher than the maxValue. Otherwise, the value is set to the maxValue.
     *
     * @param amount a float 0..n. Is the amount that the current value is increased with. Must be >= 0.
     */
    public void increase(float amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Amount cannot be negative.");
        }
        setCurrentValue(currentValue + amount);
    }

    /**
     * Decreases the attributes' current value with a specified amount.
     * The currentValue - amount cannot be less than 0. Otherwise, the value is set to minValue.
     *
     * @param amount a float 0..n. Is the amount that the current value is decreased with. Must be >= 0.
     */
    public void decrease(float amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Amount cannot be negative.");
        }
        setCurrentValue(currentValue - amount);
    }

    /**
     * Registers an observer that will be notified every time a new value of the attribute is set.
     *
     * @param observer The observer to be notified.
     */
    public void registerObserver(FluctuatingAttributeObserver observer) {
        observers.add(observer);
    }

    /**
     * Unregisters an observer that will no longer be notified every time a new value of the attribute is set.
     *
     * @param observer The observer to be no longer notified.
     */
    public void unRegisterObserver(FluctuatingAttributeObserver observer) {
        observers.remove(observer);
    }
}


