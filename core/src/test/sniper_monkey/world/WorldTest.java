package sniper_monkey.world;

import com.badlogic.gdx.math.Vector2;
import game.sniper_monkey.model.player.PlayerFactory;
import game.sniper_monkey.model.world.GameObject;
import game.sniper_monkey.model.world.World;
import org.junit.Assert;
import org.junit.Test;

public class WorldTest {

    World world = World.getInstance();

    @Test
    public void testGetRoundDuration() {
        Assert.assertEquals(120, world.getRoundDuration());
    }

    @Test
    public void testAddGameObject() {
        GameObject obj = PlayerFactory.createPlayer(new Vector2(50, 50));
        world.addGameObject(obj);
        world.update(1);
        Assert.assertNotEquals(new Vector2(50, 50), obj.getPos());
    }

    @Test
    public void testUpdate() {
        world.update(2);
        Assert.assertEquals(117, world.getRoundDuration());
    }
}
