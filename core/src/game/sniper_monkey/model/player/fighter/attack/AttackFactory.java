package game.sniper_monkey.model.player.fighter.attack;

/**
 * A factory that creates attacks for all different fighters.
 *
 * @author Dadi Andrason
 * @author Kevin Jeryd
 *
 * Used by EvilWizard
 * Used by FantasyWarrior
 * Used by HuntressBow
 * Used by Samurai
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

    /**
     * Creates an attack of type BowAttack. Is the first attack for the Huntress.
     *
     * @return a new BowAttack object.
     */
    public static IAttack createBowAttack() {
        return new BowAttack();
    }

    /**
     * Creates an attack of type BowTripleAttack. Is the second attack for the Huntress.
     *
     * @return a new BowTripleAttack object.
     */
    public static IAttack createBowTripleAttack() {
        return new BowTripleAttack();
    }

    /**
     * Creates an object of type SamuraiQuickAttack. Is the Samurai's first attack.
     * @return a new SamuraiQuickAttack object.
     */
    public static IAttack createSamuraiQuickAttack() {
        return new SamuraiQuickAttack();
    }

    /**
     * Creates an object of the type SamuraiShurikenAttack. Is the second attack for the Samurai.
     * @return a new SamuraiShuriken object.
     */
    public static IAttack createSamuraiShurikenAttack() {
        return new SamuraiShurikenAttack();
    }

    /**
     * Creates an object of the type ElectricalSlashAttack. Is the first attack for the Fantasy Warrior.
     * @return a new ElectricalSlashAttack object.
     */
    public static IAttack createElectricalSlashAttack() {
        return new ElectricalSlashAttack();
    }

    /**
     * Creates an object of the type ElectricalSmashAttack. Is the second attack for the Fantasy Warrior.
     * @return a new ElectricalSmashAttack object
     */
    public static IAttack createElectricalSmashAttack() {
        return new ElectricalSmashAttack();
    }
}
