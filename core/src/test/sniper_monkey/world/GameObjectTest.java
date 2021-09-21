package sniper_monkey.world;

import com.badlogic.gdx.graphics.g2d.Sprite;
import game.sniper_monkey.player.Player;
import game.sniper_monkey.world.GameObject;
import game.sniper_monkey.world.World;
import org.junit.Before;
import org.junit.Test;


public class GameObjectTest {

    @Before
    public void setUp() {
        GameObject player = new Player(new Sprite());
    }

    @Test
    public void testGameObjetDelete() {
    }
}
