package game.sniper_monkey.player;

public class Stamina {

    final int maxAmount; // oklart om denna ska assignas i constructorn eller ha en hårdkodad max.
    float currentAmount; // oklart om denna assignas i constructorn eller ha en hårdkodad currentamount.
    float regenerationFactor;
    // float staminaDrainFactor;

    /**
     * Creates a player object with a regenFactor and max stamina amount.
     * @param regenerationFactor a float variable that determines how much stamina is regained every frame
     * @param maxAmount a maximum amount, the stamina can't go over this amount
     */
    public Stamina(float regenerationFactor, int maxAmount) {
        currentAmount = 100;
        this.regenerationFactor = regenerationFactor;
        this.maxAmount = maxAmount;
        // this.staminaDrainFactor = staminaDrainFactor;
    }

    /**
     * Getter for the current stamina amount.
     * @return the current stamina amount.
     */
    public float getCurrentAmount() {
        return currentAmount;
    }

    /**
     * Decreases the players stamina based on a parameter. Mostly used on attack or something likewise
     * @param decreaseAmount the amount of stamina that is decreased
     */
    public void decrease(float decreaseAmount) {
        if (currentAmount - decreaseAmount < 0) {
            currentAmount = 0;
        } else {
            currentAmount -= decreaseAmount;
        }
    }

    /*public void staminaDrainOnAttack(float staminaDrainAmount) { // TODO bättre namn för denna
        decrease(staminaDrainAmount); // ska man ha en konstant stamina drain?
    }*/ // oklart om jag vill ha denna kvar

    /**
     * Increases the players stamina based on a parameter. Gets called every update.
     * @param increaseAmount the amount that stamina increases
     */
    private void increase(float increaseAmount) {
        if (currentAmount + increaseAmount > 100) {
            currentAmount = 100;
        } else {
            currentAmount += increaseAmount;
        }
    }

    /**
     * Regenerates the players stamina.
     * @param deltaTime is the time between frames. Is used so that the regeneration is constant.
     */
    private void regenerateStamina(float deltaTime) {
        if (currentAmount < 100) {
           increase(regenerationFactor * deltaTime);
        }
    }

    /**
     * Regenerates stamina by calling the regenerateStamina method. Gets called every frame by the Player class.
     * @param deltaTime is the time between frames.
     */
    public void update(float deltaTime) {
        regenerateStamina(deltaTime);
    }
}