package game.sniper_monkey.model.player.fighter.attack;

public class AttackFactory {

    public static IAttack createSwordAttack() {
        return new SwordAttack();
    }

    public static IAttack createFireBoltAttack() {
        return new StrongSwordAttack();
    }

}
