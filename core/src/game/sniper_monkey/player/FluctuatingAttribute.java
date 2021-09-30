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

    public FluctuatingAttribute(float minValue, float maxValue, float regenerationAmount, float drainAmount) {
        this.maxValue = maxValue;
        this.minValue = minValue;
        setDrainAmount(drainAmount);
        setRegenerationAmount(regenerationAmount);
        currentValue = maxValue;
    }

    public FluctuatingAttribute(float minValue, float maxValue, float regenerationAmount) {
        this(minValue, maxValue, regenerationAmount, 0);
    }

    public FluctuatingAttribute(float minValue, float maxValue) {
        this(minValue, maxValue, 0);
    }

    public FluctuatingAttribute(float maxValue) {
        this(0, maxValue);
    }


    public void setRegenerationAmount(float regenerationAmount) {
        if(regenerationAmount < 0) {
            throw new IllegalArgumentException("RegenerationAmount cannot be negative.");
        }
        this.regenerationAmount = regenerationAmount;
    }

    public void setDrainAmount(float drainAmount) {
        if(drainAmount < 0) {
            throw new IllegalArgumentException("DrainAmount cannot be negative.");
        }
        this.drainAmount = drainAmount;
    }

    public void update(float deltaTime) {
        if(isDraining && isRegenerating) {
            currentValue = Math.max(minValue, Math.min(maxValue, currentValue + (regenerationAmount - drainAmount)*deltaTime));
        } else if(isRegenerating) {
            currentValue = Math.min(currentValue + regenerationAmount *deltaTime, maxValue);
        } else if(isDraining) {
            currentValue = Math.min(currentValue - drainAmount *deltaTime, minValue);
        }
    }

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
    
    public void increase(float amount) {
        if(amount < 0) {
            throw new IllegalArgumentException("Amount cannot be negative.");
        }
        if (currentValue + amount > maxValue) {
            currentValue = maxValue;
        } else {
            currentValue += amount;
        }
    }

    public void decrease(float amount) {
        if(amount < 0) {
            throw new IllegalArgumentException("Amount cannot be negative.");
        }
        if (currentValue - amount < 0) {
            currentValue = 0;
        } else {
            setCurrentValue(currentValue - amount);
        }
    }

    public boolean isNone() { //TODO better method name
        return currentValue == 0;
    }
}


