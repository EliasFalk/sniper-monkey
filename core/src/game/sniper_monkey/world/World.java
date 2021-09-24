package game.sniper_monkey.world;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.Observer;

public final class World
{
    private static World INSTANCE;

    private ArrayList<GameObject> gameObjects;
    private ArrayList<IWorldObserver> observers;

    private World()
    {
        gameObjects = new ArrayList<>();
        observers = new ArrayList<>();
    }

    //Singleton
    public static World getInstance()
    {
        if(INSTANCE == null) INSTANCE = new World();
        return INSTANCE;
    }

    public void registerObserver(IWorldObserver observer)
    {
        observers.add(observer);
    }

    public void unregisterObserver(IWorldObserver observer)
    {
        observers.remove(observer);
    }

    private void notifyObserversOfNewObject(GameObject obj)
    {
        for(IWorldObserver observer : observers)
        {
            observer.onObjectAddedToWorld(obj);
        }
    }

    private void notifyObserversOfRemovedObject(GameObject obj)
    {
        for(IWorldObserver observer : observers)
        {
            observer.onObjectRemovedFromWorld(obj);
        }
    }

    /**
     * Calls update on all GameObjects in the world
     */
    public void update(float deltaTime)
    {
        for(GameObject obj : gameObjects)
        {
            obj.update(deltaTime);
        }
    }

    /**
     * Renders all objects in the world
     */
    public void render(SpriteBatch batch)
    {
        for(GameObject obj : gameObjects)
        {
            obj.render(batch);
        }
    }

    /**
     * Adds a GameObject to the world
     * @param obj the object to add
     */
    public void addGameObject(GameObject obj)
    {
        gameObjects.add(obj);
        notifyObserversOfNewObject(obj);
    }

    /**
     * Removes a GameObject from the world if it exists
     * @param obj the object to remove
     */
    public void deleteGameObject(GameObject obj)
    {
        gameObjects.remove(obj);
        notifyObserversOfRemovedObject(obj);
    }
}
