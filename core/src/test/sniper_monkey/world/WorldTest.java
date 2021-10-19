package sniper_monkey.world;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import com.badlogic.gdx.math.Vector2;
import game.sniper_monkey.model.Config;
import game.sniper_monkey.model.player.PlayerFactory;
import game.sniper_monkey.model.world.GameObject;
import game.sniper_monkey.model.world.World;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class WorldTest {

    World world = World.getInstance();
    static String cfg;
    static float roundTime;

    @BeforeClass
    public static void init() {
        cfg = "cfg/game.cfg";
        final HeadlessApplicationConfiguration config = new HeadlessApplicationConfiguration();
        new HeadlessApplication(new ApplicationAdapter() {
        }, config);
        Config.readConfigFile(cfg);
        roundTime = Config.getNumber(cfg, "ROUND_TIME");
        World.getInstance().resetWorld();
    }

    @Test
    public void testGetRoundDuration() {
        Assert.assertEquals(roundTime, world.getRoundDuration(), 0);
    }

    @Test
    public void testAddGameObject() {
        GameObject obj = PlayerFactory.createPlayer(new Vector2(50, 50));
        world.queueAddGameObject(obj);
        world.update(1);
        Assert.assertNotEquals(new Vector2(50, 50), obj.getPos());
    }

//    @Test
//    public void testUpdate() {
//        world.update(2);
//        Assert.assertEquals(roundTime-3, world.getRoundDuration(), 0);
//    }
}
