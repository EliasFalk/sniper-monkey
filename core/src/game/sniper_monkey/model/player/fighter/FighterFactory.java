package game.sniper_monkey.model.player.fighter;

/**
 * @author Elias Falk
 */
public class FighterFactory {

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
     * @return A fighter with factors and attacks belonging to the Huntress.
     */
    public static Fighter createHuntressBow() {
        return new HuntressBow();
    }
}
