package game.sniper_monkey.world;

public interface IWorldObserver
{
    void onObjectAddedToWorld(GameObject obj);
    void onObjectRemovedWorld(GameObject obj);
}
