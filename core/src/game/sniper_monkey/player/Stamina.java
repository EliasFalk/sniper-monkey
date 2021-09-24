package game.sniper_monkey.player;

public class Stamina {

    final int maxAmount; // oklart om denna ska assignas i constructorn eller ha en hårdkodad max.
    float currentAmount; // oklart om denna assignas i constructorn eller ha en hårdkodad currentamount.
    float regenerationFactor;

    public Stamina(float regenerationFactor, int maxAmount) {
        currentAmount = 100;
        this.regenerationFactor = regenerationFactor;
        this.maxAmount = maxAmount;

    }

    public float getCurrentAmount() {
        return currentAmount;
    }

    public void decrease(float decreaseAmount) {
        if (currentAmount - decreaseAmount < 0) {
            currentAmount = 0;
        } else {
            currentAmount -= decreaseAmount;
        }
    }

    private void increase(float increaseAmount) {
        if (currentAmount + increaseAmount > 100) {
            currentAmount = 100;
        } else {
            currentAmount += increaseAmount;
        }
    }

    private void regenerateStamina(float deltaTime) {
        if (currentAmount < 100) {
           increase(regenerationFactor * deltaTime);
        }
    }

    public void update(float deltaTime) {
        regenerateStamina(deltaTime);
    }
}