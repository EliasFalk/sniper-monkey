package game.sniper_monkey.model.player;

import com.badlogic.gdx.math.Vector2;
import game.sniper_monkey.model.player.fighter.Fighter;
import game.sniper_monkey.model.player.fighter.FighterFactory;
import game.sniper_monkey.utils.collision.CollisionMasks;

/**
 * @author Elias Falk
 */
public class PlayerFactory {

    //TODO documentation
    public static Player createPlayer(Vector2 spawnPos, Fighter primaryFighter, Fighter secondaryFighter, int collisionMasks) {
        // TODO change this method thanks
        return new Player(spawnPos, primaryFighter, secondaryFighter, collisionMasks);
    }

    //TODO documentation
    public static Player createPlayer1(Vector2 spawnPos, Fighter primaryFighter, Fighter secondaryFighter) {
        // TODO change this method thanks
        return createPlayer(spawnPos, primaryFighter, secondaryFighter, CollisionMasks.PLAYER_1);
    }

    //TODO documentation
    public static Player createPlayer2(Vector2 spawnPos, Fighter primaryFighter, Fighter secondaryFighter) {
        // TODO change this method thanks
        return createPlayer(spawnPos, primaryFighter, secondaryFighter, CollisionMasks.PLAYER_2);
    }
}
