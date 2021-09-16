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

    public static World getInstance()
    {
        if(INSTANCE == null) INSTANCE = new World();
        return INSTANCE;
    }

    public void update(float deltaTime)
    {
        for(GameObject obj : gameObjects)
        {
            obj.update(deltaTime);
        }
    }

    public void render(SpriteBatch batch)
    {
        for(GameObject obj : gameObjects)
        {
            obj.render(batch);
        }
    }

    public void addGameObject(GameObject obj)
    {
        gameObjects.add(obj);
    }

    public void deleteGameObject(GameObject obj)
    {
        gameObjects.remove(obj);
    }
}
