package game.sniper_monkey.player.fighter;

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
}
