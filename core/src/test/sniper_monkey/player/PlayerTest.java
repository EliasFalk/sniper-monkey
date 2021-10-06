package sniper_monkey.player;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import com.badlogic.gdx.math.Vector2;
import game.sniper_monkey.model.Config;
import game.sniper_monkey.model.player.Player;
import game.sniper_monkey.model.player.PlayerFactory;
import game.sniper_monkey.model.player.PlayerInputAction;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class PlayerTest {

    private static Player player;
    private static String values;
    private static final float spawnX = 100;
    private static final float spawnY = 100;
    private static final float deltaTime = 1 / 60f;

    @BeforeClass
    public static void initHeadless() {
        final HeadlessApplicationConfiguration config = new HeadlessApplicationConfiguration();
        new HeadlessApplication(new ApplicationAdapter() {
        }, config);
        values = "cfg/player_values.cfg";
        Config.readConfigFile(values);
    }

    @Before
    public void initPlayer() {
        player = PlayerFactory.createPlayer(new Vector2(spawnX, spawnY));
    }

    @Test
    public void testInputActions() {

    }

    // TODO do movement tests when movement is finalized.
//    @Test
//    public void testMoveLeftWhileGroundState() {
//        player.setInputAction(PlayerInputAction.MOVE_LEFT);
//        for (int i = 0; i < 60; i++) {
//            player.update(deltaTime);
//        }
//        System.out.println(player.getPos().y);
//        float velGain = Config.getNumber(values, "VEL_GAIN");
//        Assert.assertEquals(-velGain + spawnX, player.getPos().x, 0.0);
//    }
//
//    @Test
//    public void testMoveRightWhileGroundState() {
//        player.setInputAction(PlayerInputAction.MOVE_RIGHT);
//        player.update(1f);
//        float velGain = Config.getNumber(values, "VEL_GAIN");
//        Assert.assertEquals(velGain + spawnX, player.getPos().x, 0.0);
//    }
//
//    @Test
//    public void testMaxXVelocityLeft() {
//        for (int i = 0; i < 100; i++) {
//            player.update(1 / 60f);
//            player.setInputAction(PlayerInputAction.MOVE_LEFT);
//        }
//        float oldX = player.getPos().x;
//        player.setInputAction(PlayerInputAction.MOVE_LEFT);
//        player.update(1);
//        float newX = player.getPos().x;
//        float maxSpeed = Config.getNumber(values, "MAX_X_VEL");
//        Assert.assertEquals(-maxSpeed, newX - oldX, 0.0);
//    }
//
//    @Test
//    public void testMaxXVelocityRight() {
//        for (int i = 0; i < 100; i++) {
//            player.update(1 / 60f);
//            player.setInputAction(PlayerInputAction.MOVE_RIGHT);
//        }
//        float oldX = player.getPos().x;
//        player.setInputAction(PlayerInputAction.MOVE_RIGHT);
//        player.update(1);
//        float newX = player.getPos().x;
//        float maxSpeed = Config.getNumber(values, "MAX_X_VEL");
//        Assert.assertEquals(maxSpeed, newX - oldX, 0.0);
//    }

    // TODO test jump

    @Test
    public void testJumpWhileGroundedState() {
        // update until hits the ground, 5 seconds should be enough.
        for (int i = 0; i < 300; i++) {
            player.update(deltaTime);
        }
        float prevY = player.getPos().y;
        player.setInputAction(PlayerInputAction.JUMP);
        player.update(deltaTime);
        Assert.assertNotEquals(prevY, player.getPos().y);
    }

    @Test
    public void testJumpWhileInAirState() {
        // update until hits the ground, 5 seconds should be enough.
        for (int i = 0; i < 300; i++) {
            player.update(deltaTime);
        }

        player.setInputAction(PlayerInputAction.JUMP);
        player.update(deltaTime);
        float prevY = player.getPos().y;
        player.setInputAction(PlayerInputAction.JUMP);
        player.update(deltaTime);
        Assert.assertTrue(prevY > player.getPos().y);
    }


    @Test
    public void testNoMovementWhileBlock() {
        float startX = player.getPos().x;
        for (int i = 0; i < 60; i++) {
            player.setInputAction(PlayerInputAction.BLOCK);
            player.setInputAction(PlayerInputAction.MOVE_LEFT);
            player.update(deltaTime);
        }

        Assert.assertEquals(startX, player.getPos().x, 0);

        startX = player.getPos().x;
        for (int i = 0; i < 60; i++) {
            player.setInputAction(PlayerInputAction.BLOCK);
            player.setInputAction(PlayerInputAction.MOVE_RIGHT);
            player.update(deltaTime);
        }

        Assert.assertEquals(startX, player.getPos().x, 0);

        startX = player.getPos().x;
        for (int i = 0; i < 60; i++) {
            player.setInputAction(PlayerInputAction.BLOCK);
            player.setInputAction(PlayerInputAction.JUMP);
            player.update(deltaTime);
        }

        Assert.assertEquals(startX, player.getPos().x, 0);

        startX = player.getPos().x;
        for (int i = 0; i < 60; i++) {
            player.setInputAction(PlayerInputAction.BLOCK);
            player.setInputAction(PlayerInputAction.DROP);
            player.update(deltaTime);
        }

        Assert.assertEquals(startX, player.getPos().x, 0);
    }

    // States
    // swap fighter
    // block - can't test until attacks have been implemented
    // attack - can't test until attacks have been implemented

}
