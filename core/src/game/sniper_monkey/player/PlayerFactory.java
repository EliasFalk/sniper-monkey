package game.sniper_monkey.player;

import game.sniper_monkey.player.fighter.EvilWizard;
import game.sniper_monkey.player.fighter.FighterFactory;

public class PlayerFactory {

    /**
     * Creates a player with two fighters
     * @return a player object with two fighters.
     */
    public static Player createPlayer() {
        // TODO change this method thanks
        return new Player(FighterFactory.createEvilWizard(), FighterFactory.createEvilWizard());
    }
}
