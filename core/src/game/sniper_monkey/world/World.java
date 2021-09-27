package game.sniper_monkey.world;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import game.sniper_monkey.collision.CollisionEngine;
import game.sniper_monkey.collision.Hitbox;

import java.util.ArrayList;

public final class World {
    private static World INSTANCE;

    private ArrayList<GameObject> gameObjects;
    private ArrayList<IWorldObserver> observers;

    private World() {
        gameObjects = new ArrayList<>();
        observers = new ArrayList<>();
    }

    //Singleton
    public static World getInstance() {
        if (INSTANCE == null) INSTANCE = new World();
        return INSTANCE;
    }

    public void registerObserver(IWorldObserver observer) {
        observers.add(observer);
    }

    public void unregisterObserver(IWorldObserver observer) {
        observers.remove(observer);
    }

    private void notifyObserversOfNewObject(GameObject obj) {
        for (IWorldObserver observer : observers) {
            observer.onObjectAddedToWorld(obj);
        }
    }

    private void notifyObserversOfRemovedObject(GameObject obj) {
        for (IWorldObserver observer : observers) {
            observer.onObjectRemovedFromWorld(obj);
        }
    }

    /**
     * Calls update on all GameObjects in the world
     */
    public void update(float deltaTime) {
        for (GameObject obj : gameObjects) {
            obj.update(deltaTime);
        }
    }

    /**
     * Adds a GameObject to the world
     *
     * @param obj the object to add
     */
    public void addGameObject(GameObject obj) {
        gameObjects.add(obj);
        notifyObserversOfNewObject(obj);
        CollisionEngine.insertIntoSpatialHash(obj, obj.getHitbox());
    }

    /**
     * Removes a GameObject from the world if it exists
     *
     * @param obj the object to remove
     */
    public void deleteGameObject(GameObject obj) {
        gameObjects.remove(obj);
        notifyObserversOfRemovedObject(obj);
    }
}
