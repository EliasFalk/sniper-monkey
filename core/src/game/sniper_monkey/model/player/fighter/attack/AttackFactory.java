package game.sniper_monkey.model.player.fighter.attack;

/**
 * A factory that creates attacks for all different fighters.
 *
 * @author Dadi Andrason
 * @author Kevin Jeryd
 *
 * Uses IAttack
 * Uses EvilMagicSwingAttack
 * Uses EvilMagicHammerAttack
 */
public class AttackFactory {

    /**
     * Creates an attack of type evil magic swing attack.
     *
     * @return a new SwordAttack object.
     */
    public static IAttack createEvilMagicSwingAttack() {
        return new EvilMagicSwingAttack();
    }

    /**
     * Creates an attack of type evil magic hammer attack.
     *
     * @return a new StrongSwordAttack object.
     */
    public static IAttack createEvilMagicHammerAttack() {
        return new EvilMagicHammerAttack();
    }

}
