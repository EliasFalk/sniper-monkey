package game.sniper_monkey.model.player;

/**
 * An observer interface that is implemented by classes that wants to observe when a player swaps fighter.
 *
 * @author Elias Falk
 */
public interface SwappedFighterObserver {
    /**
     * The method that will be called when the player swaps fighter.
     *
     * @param player The player that swapped fighter.
     */
    void onFighterSwap(Player player);
}
