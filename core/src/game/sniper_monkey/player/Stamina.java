package game.sniper_monkey.player;

public class Stamina {

    final int maxAmount;
    float currentAmount;
    float regenerationFactor;
    // float staminaDrainFactor;

    /**
     * Creates a stamina object with a regenerationFactor and maxAmount
     *
     * @param regenerationFactor a float between 0..n. This determines how fast the stamina is regenerated per second. For example a factor of 10 would mean 10 stamina points are regenerated per second.
     * @param maxAmount an integer between 0..n. This is the maximum value that stamina can be.
     */
    public Stamina(float regenerationFactor, int maxAmount) {
        currentAmount = maxAmount;
        this.regenerationFactor = regenerationFactor;
        this.maxAmount = maxAmount;
        // this.staminaDrainFactor = staminaDrainFactor;
    }

    public Stamina(int maxAmount) {
        this(0, maxAmount);
    }

    public void setRegenerationFactor(float regenerationFactor) {
        this.regenerationFactor = regenerationFactor;
    }

    /**
     * Returns the current amount of Stamina a player has.
     *
     * @return a float between 0..maxAmount.
     */
    public float getCurrentAmount() {
        return currentAmount;
    }

    /**
     * Decreases players stamina with the parameter decreaseAmount. Stamina cannot go under 0.
     *
     * @param decreaseAmount a float determined by which attack is used.
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
     * Increases the players stamina with the parameter increaseAmount. Stamina cannot go over maxAmount.
     *
     * @param increaseAmount a float between 0..n. Depends on how long each frame is.
     */
    private void increase(float increaseAmount) {
        if (currentAmount + increaseAmount > maxAmount) {
            currentAmount = maxAmount;
        } else {
            currentAmount += increaseAmount;
        }
    }

    /**
     * Regenerates the players stamina.
     *
     * @param deltaTime a float between 0..n. It's the time between each frame and is often very small.
     */
    private void regenerateStamina(float deltaTime) {
        if (currentAmount < 100) {
           increase(regenerationFactor * deltaTime);
        }
    }

    /**
     * An update method that gets called every frame by the Player class.
     *
     * @param deltaTime a float between 0..n. It's the time between each frame and is often very small.
     */
    public void update(float deltaTime) {
        regenerateStamina(deltaTime);
    }
}