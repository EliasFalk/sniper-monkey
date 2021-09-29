package game.sniper_monkey.player;

public class Health {

    private float currentHealth;
    private boolean isDead = false;

    public Health(float health) {
        this.currentHealth = health;
    }

    private void increase(float increaseAmount) {
        currentHealth += increaseAmount;
    }

    private void decrease(float decreaseAmount) {
        if (currentHealth - decreaseAmount < 0) {
            currentHealth = 0;
            isDead = true;
        } else {
            currentHealth =- decreaseAmount;
        }
    }

    public void onDamage(float damageAmount) {
        decrease(damageAmount);
    }


    public boolean isDead() {
        return isDead;
    }

}
