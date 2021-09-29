package game.sniper_monkey.player;

public class Health {

    private float currentHealth;

    /**
     * Creates a health object that is used by the player.
     * @param health a float 0..n. Chooses how much health the player has from the start.
     */
    public Health(float health) {
        this.currentHealth = health;
    }

    private void increase(float increaseAmount) {
        currentHealth += increaseAmount;
    }

    private void decrease(float decreaseAmount) {
        if (currentHealth - decreaseAmount < 0) {
            currentHealth = 0;
        } else {
            currentHealth =- decreaseAmount;
        }
    }

    /**
     * Gets called when damage has been done to the player. Decreases the players' health accordingly.
     * @param damageAmount a float 0..n. Is the amount of damage done to the player. Players health gets removed with damageAmount.
     */
    public void onDamage(float damageAmount) {
        decrease(damageAmount);
    }

    /**
     * Checks if the player is dead.
     * Player is dead if health is less or equal to 0.
     * @return false if currentHealth is > 0, true if currentHealth is == 0.
     */
    public boolean isDead() {
        return currentHealth == 0;
    }

}
