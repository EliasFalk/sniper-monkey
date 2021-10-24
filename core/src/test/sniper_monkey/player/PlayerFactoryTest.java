package sniper_monkey.player;

import static org.junit.Assert.*;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import com.badlogic.gdx.math.Vector2;
import game.sniper_monkey.model.player.Player;
import game.sniper_monkey.model.player.PlayerFactory;
import game.sniper_monkey.model.player.fighter.FighterFactory;
import org.junit.BeforeClass;
import org.junit.Test;


public class PlayerFactoryTest {

    @BeforeClass
    public static void initHeadless() {
        final HeadlessApplicationConfiguration config = new HeadlessApplicationConfiguration();
        new HeadlessApplication(new ApplicationAdapter() {
        }, config);
    }

    @Test
    public void testCreatePlayer() {

        Player player = PlayerFactory.createPlayer(new Vector2(50,50), FighterFactory.createSamurai(), FighterFactory.createHuntressBow(), 0);

        assertEquals(new Vector2(50,50), player.getPos());

    }
}
