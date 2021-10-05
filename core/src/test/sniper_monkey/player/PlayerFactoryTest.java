package sniper_monkey.player;

import static org.junit.Assert.*;

import com.badlogic.gdx.math.Vector2;
import game.sniper_monkey.player.Player;
import game.sniper_monkey.player.PlayerFactory;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


public class PlayerFactoryTest {
    @Test
    public void testCreatePlayer() {

        Player player = PlayerFactory.createPlayer(new Vector2(50,50));

        assertEquals(new Vector2(50,50), player.getPos());

    }
}
