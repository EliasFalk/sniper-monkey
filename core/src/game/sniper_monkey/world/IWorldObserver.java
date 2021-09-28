package game.sniper_monkey.world;

public interface IWorldObserver {
    /**
     * Called when an object is added to the world
     * @param obj The removed added.
     */
    void onObjectAddedToWorld(GameObject obj);
    /**
     * Called when an object is removed from the world.
     * @param obj The removed object.
     */
    void onObjectRemovedFromWorld(GameObject obj);
}
