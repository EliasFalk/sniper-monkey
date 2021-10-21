package sniper_monkey.player;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import com.badlogic.gdx.math.Vector2;
import game.sniper_monkey.model.Config;
import game.sniper_monkey.model.TimerBank;
import game.sniper_monkey.model.player.Player;
import game.sniper_monkey.model.player.PlayerFactory;
import game.sniper_monkey.model.player.PlayerInputAction;
import game.sniper_monkey.model.player.fighter.Fighter;
import game.sniper_monkey.model.world.World;
import game.sniper_monkey.model.world_brick.WorldBrick;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class PlayerTest {

    private static Player player;
    private static String playerValues;
    private static String physicsValues;
    private static final float spawnX = 100;
    private static final float spawnY = 100;
    private static final float deltaTime = 1 / 60f;

    @BeforeClass
    public static void initHeadless() {
        final HeadlessApplicationConfiguration config = new HeadlessApplicationConfiguration();
        new HeadlessApplication(new ApplicationAdapter() {
        }, config);
        playerValues = "cfg/player_values.cfg";
        physicsValues = "cfg/physics.cfg";
        Config.readConfigFile(playerValues);
        Config.readConfigFile(physicsValues);
        World.getInstance().resetWorld();
        TimerBank.clear();
        // create a ~800 pixels long platform.
        for (int i = -400; i < 400; i += 16) {
            World.getInstance().queueAddGameObject(new WorldBrick(new Vector2(i, -100), "1"));
        }
        World.getInstance().update(deltaTime);
    }

    @Before
    public void initPlayer() {
        player = PlayerFactory.createPlayer2(new Vector2(spawnX, spawnY));
    }

    @Test
    public void testInputActions() {

    }

    // TODO do movement tests when movement is finalized.
//    @Test
//    public void testMoveLeftWhileGroundState() {
//        for (int i = 0; i < 300; i++) {
//            player.update(deltaTime);
//        }
//        player.setInputAction(PlayerInputAction.MOVE_LEFT);
//        float velGain = Config.getNumber(values, "VEL_GAIN");
//        Assert.assertEquals(-velGain + spawnX, player.getPos().x, 0.0);
//    }
//
//    @Test
//    public void testMoveRightWhileGroundState() {
//        player.setInputAction(PlayerInputAction.MOVE_RIGHT);
//        for(int i = 0; i < 60; i++) {
//            player.update(deltaTime);
//        }
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
        updatePlayer(5);
        float prevY = player.getPos().y;
        player.setInputAction(PlayerInputAction.JUMP);
        player.update(deltaTime);
        Assert.assertTrue(prevY < player.getPos().y);
    }

    @Test
    public void testJumpWhileInAirState() { //TODO make this less ugly.
        updatePlayer(5);
        player.setInputAction(PlayerInputAction.JUMP);
        player.update(deltaTime);

        float jumpGain = Config.getNumber(playerValues, "JUMP_GAIN");
        float gravity = Config.getNumber(physicsValues, "GRAVITY");
        float secondsToZeroYVelocity = jumpGain / Math.abs(gravity);
        float framesToZeroYVelocity = secondsToZeroYVelocity / deltaTime;

        for (int i = 0; i < framesToZeroYVelocity; i++) {
            player.update(deltaTime);
        }

        float prevY = player.getPos().y;
        player.setInputAction(PlayerInputAction.JUMP);

        player.update(deltaTime);
        Assert.assertTrue(prevY > player.getPos().y);
    }

    private void updatePlayer(float seconds) {
        for (int i = 0; i < seconds / deltaTime; i++) {
            player.update(deltaTime);
        }
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

    @Test
    public void testActiveFighterChangedAfterSwap() {
        Class<? extends Fighter> beforeSwappedFighterClass = player.getActiveFighterClass();
        System.out.println(beforeSwappedFighterClass);
        player.setInputAction(PlayerInputAction.SWAP_FIGHTER);
        player.update(deltaTime);
        Class<? extends Fighter> afterSwappedFighterClass = player.getActiveFighterClass();
        Assert.assertNotEquals(beforeSwappedFighterClass, afterSwappedFighterClass);
    }

    @Test
    public void testTeleportToSpawnAfterSwap() {
        updatePlayer(5);
        Vector2 groundPos = player.getPos();

        player.setInputAction(PlayerInputAction.SWAP_FIGHTER);
        player.update(deltaTime);
        Vector2 respawnPos = player.getPos();

        Vector2 originalSpawnPos = new Vector2(spawnX, spawnY);

        Assert.assertEquals(spawnX, respawnPos.x, 1f);
        Assert.assertEquals(spawnY, respawnPos.y, 1f);
        Assert.assertNotEquals(groundPos, respawnPos);
    }

    @Test
    public void testResetVelocityAfterSwap() {
        for (int i = 0; i < 200; i++) {
            player.setInputAction(PlayerInputAction.MOVE_RIGHT);
            player.update(deltaTime);
        }

        float verySmallDeltaTime = 1 / 10000f;

        Vector2 pos1 = player.getPos();
        player.setInputAction(PlayerInputAction.MOVE_RIGHT);
        player.update(verySmallDeltaTime);
        Vector2 pos2 = player.getPos();
        Vector2 velocityBeforeSwap = pos2.cpy().sub(pos1).cpy().scl(1 / verySmallDeltaTime);

        player.setInputAction(PlayerInputAction.SWAP_FIGHTER);
        player.update(verySmallDeltaTime);
        Vector2 pos3 = player.getPos();
        player.update(verySmallDeltaTime);
        Vector2 pos4 = player.getPos();
        Vector2 velocityAfterSwap = pos4.cpy().sub(pos3).cpy().scl(1 / verySmallDeltaTime);
        Assert.assertNotEquals(velocityBeforeSwap, velocityAfterSwap);
        Assert.assertEquals(0, velocityAfterSwap.x, 0);
        Assert.assertEquals(0, velocityAfterSwap.y, 0.5f);
    }

    @Test
    public void testCannotSwapUntilSwapTimerOver() {
        player.setInputAction(PlayerInputAction.SWAP_FIGHTER);
        player.update(deltaTime);
        float swapCooldown = Config.getNumber(playerValues, "SWAP_COOLDOWN");
        Class<? extends Fighter> secondaryFighter = player.getActiveFighterClass();
        player.setInputAction(PlayerInputAction.SWAP_FIGHTER);
        player.update(deltaTime);
        Class<? extends Fighter> fighterAfterIllegalSwap = player.getActiveFighterClass();
        Assert.assertEquals(secondaryFighter, fighterAfterIllegalSwap);
    }

    @Test
    public void testSwapAgainAfterSwapCooldown() {
        Class<? extends Fighter> primaryFighter = player.getActiveFighterClass();
        player.setInputAction(PlayerInputAction.SWAP_FIGHTER);
        player.update(deltaTime);
        float swapCooldown = Config.getNumber(playerValues, "SWAP_COOLDOWN");
        Class<? extends Fighter> secondaryFighter = player.getActiveFighterClass();
        TimerBank.updateTimers(swapCooldown);
        player.update(deltaTime);
        player.setInputAction(PlayerInputAction.SWAP_FIGHTER);
        player.update(deltaTime);
        Class<? extends Fighter> fighterAfterLegalSwap = player.getActiveFighterClass();
        Assert.assertEquals(primaryFighter, fighterAfterLegalSwap);
    }


    // States
    // block - can't test until attacks have been implemented
    // attack - can't test until attacks have been implemented
}
