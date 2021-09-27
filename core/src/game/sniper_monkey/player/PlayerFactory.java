package game.sniper_monkey.player;

import game.sniper_monkey.player.fighter.EvilWizard;

public class PlayerFactory {
    public static Player createPlayer() {
        // TODO change this method thanks
        return new Player(new EvilWizard(), new EvilWizard());
    }
}
