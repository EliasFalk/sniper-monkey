package game.sniper_monkey.player.fighter.attack;

public interface IAttack {
    void performAttack(float attackFactor);

    int getStaminaCost();
}
