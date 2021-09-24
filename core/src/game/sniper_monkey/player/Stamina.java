package game.sniper_monkey.player;

public class Stamina {

    final int maxAmount; // oklart om denna ska assignas i constructorn eller ha en hårdkodad max.
    int currentAmount;
    float regenerationFactor;

    public Stamina(float regenerationFactor, int maxAmount) {
        currentAmount = 100;
        this.regenerationFactor = regenerationFactor;
        this.maxAmount = maxAmount;

    }

    public void decrease(int decreaseAmount) {
        if (currentAmount - decreaseAmount < 0) {
            currentAmount = 0;
        } else {
            currentAmount -= decreaseAmount;
        }
    }

    public void increase(int increaseAmount) {
        if (currentAmount + increaseAmount > 100) {
            currentAmount = 100;
        } else {
            currentAmount += increaseAmount;
        }
    }

    public void regenerateStamina(float deltaTime) {
        if (currentAmount < 100) {
            currentAmount += regenerationFactor * deltaTime;
        }
    }

    public void update(float deltaTime) {
        regenerateStamina(deltaTime);
    }

}
