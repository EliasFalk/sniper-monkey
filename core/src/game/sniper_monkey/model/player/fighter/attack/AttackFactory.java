package game.sniper_monkey.model.player.fighter.attack;

public class AttackFactory {

    public static IAttack createSwordAttack() {
        return new SwordAttack();
    }

    public static IAttack createStrongSwordAttack() {
        return new StrongSwordAttack();
    }

    public static IAttack createBowAttack() {
        return new BowAttack();
    }

}
