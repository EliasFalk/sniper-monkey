package sniper_monkey.player;

import com.badlogic.gdx.graphics.g2d.Sprite;
import game.sniper_monkey.player.Player;
import game.sniper_monkey.player.PlayerFactory;
import game.sniper_monkey.player.PlayerInputAction;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class PlayerTest {

    Player player;

    @Before
    public void initPlayer() {
        player = PlayerFactory.createPlayer();
    }

    @Test
    public void testInputActions() {

    }

    @Test
    public void testMoveLeftWhileGroundState() {
        player.setInputAction(PlayerInputAction.MOVE_LEFT);
        player.update(1);
        Assert.assertEquals(-40f, player.getPos().x, 0.0);
    }

    @Test
    public void testMoveRightWhileGroundState() {
        player.setInputAction(PlayerInputAction.MOVE_RIGHT);
        player.update(1);
        Assert.assertEquals(40f, player.getPos().x, 0.0);
    }

    @Test
    public void testMaxXVelocityLeft() {
        for (int i = 0; i < 100; i++) {
            player.update(1 / 60f);
            player.setInputAction(PlayerInputAction.MOVE_LEFT);
        }
        float oldX = player.getPos().x;
        player.setInputAction(PlayerInputAction.MOVE_LEFT);
        player.update(1);
        float newX = player.getPos().x;
        Assert.assertEquals(-300f, newX - oldX, 0.0);
    }

    @Test
    public void testMaxXVelocityRight() {
        for (int i = 0; i < 100; i++) {
            player.update(1 / 60f);
            player.setInputAction(PlayerInputAction.MOVE_RIGHT);
        }
        float oldX = player.getPos().x;
        player.setInputAction(PlayerInputAction.MOVE_RIGHT);
        player.update(1);
        float newX = player.getPos().x;
        Assert.assertEquals(300f, newX - oldX, 0.0);
    }

    // TODO test jump
}
