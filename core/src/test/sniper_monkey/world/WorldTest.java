package sniper_monkey.world;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import com.badlogic.gdx.math.Vector2;
import game.sniper_monkey.model.Config;
import game.sniper_monkey.model.player.PlayerFactory;
import game.sniper_monkey.model.player.fighter.FighterFactory;
import game.sniper_monkey.model.world.GameObject;
import game.sniper_monkey.model.world.IWorldObserver;
import game.sniper_monkey.model.world.World;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class WorldTest {

    private final World world = World.getInstance();

    private static boolean observerCalled;

    @BeforeClass
    public static void init() {
        final HeadlessApplicationConfiguration config = new HeadlessApplicationConfiguration();
        new HeadlessApplication(new ApplicationAdapter() {
        }, config);
        World.getInstance().resetWorld();
    }

    @Test
    public void testAddGameObject() {
        GameObject obj = PlayerFactory.createPlayer(new Vector2(50, 50), FighterFactory.createEvilWizard(), FighterFactory.createHuntressBow(), 0);
        world.queueAddGameObject(obj);
        world.update(1);
        Assert.assertNotEquals(new Vector2(50, 50), obj.getPos());
    }

    @Test
    public void testObserverAdd() {
        observerCalled = false;
        IWorldObserver observer = new IWorldObserver() {
            @Override
            public void onObjectAddedToWorld(GameObject obj) {
                observerCalled = true;
            }

            @Override
            public void onObjectRemovedFromWorld(GameObject obj) {

            }
        };
        world.registerObserver(observer);
        GameObject testObj = new GameObject(false) {
            @Override
            public void update(float deltaTime) {
                super.update(deltaTime);
            }
        };
        world.queueAddGameObject(testObj);
        world.update(0);
        Assert.assertTrue(observerCalled);
        world.queueRemoveGameObject(testObj);
        world.update(0);
        world.unregisterObserver(observer);
    }

    @Test
    public void testObserverRemove() {
        observerCalled = false;
        IWorldObserver observer = new IWorldObserver() {
            @Override
            public void onObjectAddedToWorld(GameObject obj) {

            }

            @Override
            public void onObjectRemovedFromWorld(GameObject obj) {
                observerCalled = true;
            }
        };
        world.registerObserver(observer);
        GameObject testObj = new GameObject(false) {
            @Override
            public void update(float deltaTime) {
                super.update(deltaTime);
            }
        };
        world.queueAddGameObject(testObj);
        world.update(0);
        world.queueRemoveGameObject(testObj);
        world.update(0);
        Assert.assertTrue(observerCalled);
        world.unregisterObserver(observer);
    }

    @Test
    public void testObserverUnregister() {
        observerCalled = false;
        IWorldObserver observer = new IWorldObserver() {
            @Override
            public void onObjectAddedToWorld(GameObject obj) {
                observerCalled = true;
            }

            @Override
            public void onObjectRemovedFromWorld(GameObject obj) {
                observerCalled = true;
            }
        };
        world.registerObserver(observer);
        GameObject testObj = new GameObject(false) {
            @Override
            public void update(float deltaTime) {
                super.update(deltaTime);
            }
        };
        world.unregisterObserver(observer);
        world.queueAddGameObject(testObj);
        world.update(0);
        world.queueRemoveGameObject(testObj);
        world.update(0);
        Assert.assertFalse(observerCalled);
    }
}
