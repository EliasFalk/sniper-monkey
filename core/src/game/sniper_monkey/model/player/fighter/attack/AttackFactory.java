package game.sniper_monkey.model.player.fighter.attack;

/**
 * A factory that creates attacks for all different fighters.
 *
 * @author Dadi Andrason
 * @author Kevin Jeryd
 *
 * Uses IAttack
 * Uses SwordAttack
 * Uses StrongSwordAttack
 */
public class AttackFactory {

    /**
     * Creates an attack of type SwordAttack
     * @return a new SwordAttack object.
     */
    public static IAttack createSwordAttack() {
        return new SwordAttack();
    }

    /**
     * Creates an attack of type StrongSwordAttack
     * @return a new StrongSwordAttack object.
     */
    public static IAttack createStrongSwordAttack() {
        return new StrongSwordAttack();
    }

}
