package game.sniper_monkey.world;

/**
 * An observer used for observing when the world has had a GameObject added or removed.
 *
 * @author Vincent Hellner
 */
public interface IWorldObserver {
    /**
     * Called when an object is added to the world
     *
     * @param obj The removed added.
     */
    void onObjectAddedToWorld(GameObject obj);

    /**
     * Called when an object is removed from the world.
     *
     * @param obj The removed object.
     */
    void onObjectRemovedFromWorld(GameObject obj);
}
