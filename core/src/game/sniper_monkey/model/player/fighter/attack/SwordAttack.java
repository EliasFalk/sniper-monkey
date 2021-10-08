package game.sniper_monkey.model.player.fighter.attack;

public class SwordAttack implements IAttack {

    private final float staminaCost = 10;
    private final float damage = 15;
    private final float cooldownDuration = 0.2f;



    @Override
    public void performAttack(float attackFactor) {

    }

    @Override
    public float getStaminaCost() {
        return staminaCost;
    }

    @Override
    public float getCooldown() {
        return cooldownDuration;
    }
}
