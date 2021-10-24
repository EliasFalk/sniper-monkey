package game.sniper_monkey.model.player.fighter;

import java.util.HashMap;
import java.util.Map;

/**
 * A factory that serves as an interface for creating all the different fighters.
 *
 * <p>
 *     Used by CharacterSelectionScreenController
 *     Used by PlayerFactory
 *     Used by FighterFactory
 * </p>
 *
 * @author Elias Falk
 * @author Dadi Andrason
 */
public final class FighterFactory {

    private FighterFactory() {
    }

    private static final Map<Class<? extends Fighter>, FighterCreator> correspondingFighters = new HashMap<>();

    static {
        correspondingFighters.put(EvilWizard.class, FighterFactory::createEvilWizard);
        correspondingFighters.put(Samurai.class, FighterFactory::createSamurai);
        correspondingFighters.put(HuntressBow.class, FighterFactory::createHuntressBow);
        correspondingFighters.put(FantasyWarrior.class, FighterFactory::createFantasyWarrior);
    }

    /**
     * Creates an Evil Wizard fighter.
     *
     * @return A generic Fighter with factors and attacks belonging to Evil Wizard.
     */
    public static Fighter createEvilWizard() {
        return new EvilWizard();
    }

    /**
     * Creates a Huntress fighter with a bow.
     *
     * @return A fighter with factors and attacks belonging to the Huntress.
     */
    public static Fighter createHuntressBow() {
        return new HuntressBow();
    }

    /**
     * Creates a Samurai fighter.
     *
     * @return A fighter with factors and attacks belonging to the Samurai.
     */
    public static Fighter createSamurai() {
        return new Samurai();
    }

    /**
     * Creates a Fantasy warrior fighter.
     * @return a fighter with factors and attacks belonging to the Fantasy warrior.
     */
    public static Fighter createFantasyWarrior() {
        return new FantasyWarrior();
    }

    /**
     * Returns a fighter object corresponding to the given fighter class.
     *
     * @param fighterClass The class of the fighter.
     * @return A fighter object corresponding to the fighter class.
     */
    public static Fighter getFighter(Class<? extends Fighter> fighterClass) {
        return correspondingFighters.get(fighterClass).getFighter();
    }

    @FunctionalInterface
    private interface FighterCreator {
        Fighter getFighter();
    }
}
