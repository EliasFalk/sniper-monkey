package game.sniper_monkey;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

public final class World
{
    private static World INSTANCE;

    private ArrayList<GameObject> gameObjects;

    private World()
    {
        gameObjects = new ArrayList<GameObject>();
    }

    //Singleton
    public static World getInstance()
    {
        if(INSTANCE == null) INSTANCE = new World();
        return INSTANCE;
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
    }

    /**
     * Removes a GameObject from the world if it exists
     * @param obj the object to remove
     */
    public void deleteGameObject(GameObject obj)
    {
        gameObjects.remove(obj);
    }
}
