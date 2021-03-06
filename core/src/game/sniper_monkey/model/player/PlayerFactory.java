package game.sniper_monkey.model.player;

import com.badlogic.gdx.math.Vector2;
import game.sniper_monkey.model.player.fighter.Fighter;
import game.sniper_monkey.model.player.fighter.FighterFactory;
import game.sniper_monkey.utils.collision.CollisionMasks;

/**
 * A factory that serves as the interface to creating players.
 * <p>
 * Uses FighterFactory.
 * Uses Fighter
 * Uses CollisionMasks
 * <p>
 * Used by GameController.
 *
 * @author Elias Falk
 */
public final class PlayerFactory {
    private PlayerFactory() {
    }

    /**
     * Returns a player with a given spawn position, primary and secondary fighter as well as collision mask.
     *
     * @param spawnPos         The spawn position of where the player will spawn and respawn when swapping fighter.
     * @param primaryFighter   The primary fighter that the player will use, will be the active fighter until swap.
     * @param secondaryFighter The secondary fighter that the player will use.
     * @param collisionMask    The collision mask that the player will have.
     * @return A player.
     */
    public static Player createPlayer(Vector2 spawnPos, Fighter primaryFighter, Fighter secondaryFighter, int collisionMask) {
        return new Player(spawnPos, primaryFighter, secondaryFighter, collisionMask);
    }

    /**
     * Returns a player with a given spawn position, primary and secondary fighter.
     * The player has a predetermined collision mask of PLAYER_1.
     *
     * @param spawnPos         The spawn position of where the player will spawn and respawn when swapping fighter.
     * @param primaryFighter   The primary fighter that the player will use, will be the active fighter until swap.
     * @param secondaryFighter The secondary fighter that the player will use.
     * @return A player with a collision mask of player 1.
     */
    public static Player createPlayer1(Vector2 spawnPos, Fighter primaryFighter, Fighter secondaryFighter) {
        return createPlayer(spawnPos, primaryFighter, secondaryFighter, CollisionMasks.PLAYER_1);
    }

    /**
     * Returns a player with a given spawn position, primary and secondary fighter.
     * The player has a predetermined collision mask of PLAYER_2.
     *
     * @param spawnPos         The spawn position of where the player will spawn and respawn when swapping fighter.
     * @param primaryFighter   The primary fighter that the player will use, will be the active fighter until swap.
     * @param secondaryFighter The secondary fighter that the player will use.
     * @return A player with a collision mask of player 2.
     */
    public static Player createPlayer2(Vector2 spawnPos, Fighter primaryFighter, Fighter secondaryFighter) {
        return createPlayer(spawnPos, primaryFighter, secondaryFighter, CollisionMasks.PLAYER_2);
    }


    /**
     * Returns a player with a given spawn position, primary and secondary fighter as well as collision mask.
     *
     * @param spawnPos         The spawn position of where the player will spawn and respawn when swapping fighter.
     * @param primaryFighter   The class of the primary fighter that the player will use, will be the active fighter until swap.
     * @param secondaryFighter The class of the secondary fighter that the player will use.
     * @param collisionMask    The collision mask that the player will have.
     * @return A player.
     */
    public static Player createPlayer(Vector2 spawnPos, Class<? extends Fighter> primaryFighter, Class<? extends Fighter> secondaryFighter, int collisionMask) {
        Fighter f1 = FighterFactory.getFighter(primaryFighter);
        Fighter f2 = FighterFactory.getFighter(secondaryFighter);
        return new Player(spawnPos, f1, f2, collisionMask);
    }

    /**
     * Returns a player with a player 1 collision mask.
     *
     * @param spawnPos         The spawn position of where the player will spawn and respawn when swapping fighter.
     * @param primaryFighter   The class of the primary fighter that the player will use, will be the active fighter until swap.
     * @param secondaryFighter The class of the secondary fighter that the player will use.
     * @return A player with a player 1 collision mask.
     */
    public static Player createPlayer1(Vector2 spawnPos, Class<? extends Fighter> primaryFighter, Class<? extends Fighter> secondaryFighter) {
        return createPlayer(spawnPos, primaryFighter, secondaryFighter, CollisionMasks.PLAYER_1);
    }

    /**
     * Returns a player with a player 1 collision mask.
     *
     * @param spawnPos         The spawn position of where the player will spawn and respawn when swapping fighter.
     * @param primaryFighter   The class of the primary fighter that the player will use, will be the active fighter until swap.
     * @param secondaryFighter The class of the secondary fighter that the player will use.
     * @return A player with a player 1 collision mask.
     */
    public static Player createPlayer2(Vector2 spawnPos, Class<? extends Fighter> primaryFighter, Class<? extends Fighter> secondaryFighter) {
        return createPlayer(spawnPos, primaryFighter, secondaryFighter, CollisionMasks.PLAYER_2);
    }
}
