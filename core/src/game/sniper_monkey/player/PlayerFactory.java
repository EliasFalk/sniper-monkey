package game.sniper_monkey.player;

import com.badlogic.gdx.math.Vector2;
import game.sniper_monkey.player.fighter.FighterFactory;

public class PlayerFactory {

    /**
     * Creates a player with two fighters
     *
     * @return a player object with two fighters.
     */
    public static Player createPlayer() {
        // TODO change this method thanks
        return new Player(FighterFactory.createEvilWizard(), FighterFactory.createEvilWizard());
    }

    public static Player createPlayer(Vector2 spawnPos) {
        // TODO change this method thanks
        return new Player(spawnPos, FighterFactory.createEvilWizard(), FighterFactory.createEvilWizard());
    }
}
