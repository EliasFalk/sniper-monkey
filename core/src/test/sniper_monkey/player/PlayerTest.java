package sniper_monkey.player;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import com.badlogic.gdx.math.Vector2;
import game.sniper_monkey.model.player.*;
import game.sniper_monkey.model.player.fighter.EvilWizard;
import game.sniper_monkey.model.player.fighter.Fighter;
import game.sniper_monkey.model.player.fighter.FighterFactory;
import game.sniper_monkey.model.player.fighter.HuntressBow;
import game.sniper_monkey.model.player.fighter.attack.IAttack;
import game.sniper_monkey.model.time.TimerBank;
import game.sniper_monkey.model.world.World;
import game.sniper_monkey.model.world_brick.WorldBrick;
import game.sniper_monkey.utils.Config;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class PlayerTest {

    private static Player player;
    private static String playerValues;
    private static String physicsValues;
    private static final Class<? extends Fighter> primaryFighterClass = EvilWizard.class;
    private static final Class<? extends Fighter> secondaryFighterClass = HuntressBow.class;
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
        TimerBank.clear();
        player = PlayerFactory.createPlayer1(new Vector2(spawnX, spawnY), primaryFighterClass, secondaryFighterClass);
        player = PlayerFactory.createPlayer2(new Vector2(spawnX, spawnY), primaryFighterClass, secondaryFighterClass);
    }

    // TODO do movement tests when movement is finalized.
    @Test
    public void testMoveLeftWhileGroundState() {
        updatePlayerAndTimerBank(5);
        float prevX = player.getPos().x;
        updatePlayerAndTimerBank(1);
        assertEquals(prevX, player.getPos().x, 0.0);
        player.setInputAction(PlayerInputAction.MOVE_LEFT);
        updatePlayerAndTimerBank(1);
        assertTrue(prevX > player.getPos().x);
    }

    private Vector2 getVelocity() {
        float smallDt = 1 / 1000f;
        Vector2 prePos = player.getPos();
        player.update(smallDt);
        Vector2 afterPos = player.getPos();
        return afterPos.sub(prePos).scl(1 / smallDt);
    }

    private void updatePlayerWithAction(float seconds, PlayerInputAction action) {
        for (int i = 0; i < seconds / deltaTime; i++) {
            player.setInputAction(action);
            player.update(deltaTime);
        }
    }

    private void updatePlayerAndTimerBank(float seconds) {
        for (int i = 0; i < seconds / deltaTime; i++) {
            player.update(deltaTime);
            TimerBank.updateTimers(deltaTime);
        }
    }

    @Test
    public void testMoveRightWhileGroundState() {
        updatePlayerAndTimerBank(5);
        float prevX = player.getPos().x;
        updatePlayerAndTimerBank(1);
        assertEquals(prevX, player.getPos().x, 0.0);
        player.setInputAction(PlayerInputAction.MOVE_RIGHT);
        updatePlayerAndTimerBank(1);
        assertTrue(prevX < player.getPos().x);
    }

    @Test
    public void testMaxXVelocityLeft() {
        assertEquals(0, getVelocity().x, 0);
        updatePlayerWithAction(5, PlayerInputAction.MOVE_LEFT);
        player.setInputAction(PlayerInputAction.MOVE_LEFT);
        Vector2 playerVel = getVelocity();
        float maxSpeed = Config.getNumber(playerValues, "MAX_X_VEL");
        assertTrue(Math.abs(playerVel.x) <= maxSpeed);
    }

    @Test
    public void testMaxXVelocityRight() {
        assertEquals(0, getVelocity().x, 0);
        updatePlayerWithAction(5, PlayerInputAction.MOVE_RIGHT);
        player.setInputAction(PlayerInputAction.MOVE_RIGHT);
        Vector2 playerVel = getVelocity();
        float maxSpeed = Config.getNumber(playerValues, "MAX_X_VEL");
        assertTrue(Math.abs(playerVel.x) <= maxSpeed);
    }

    @Test
    public void testJumpWhileGroundedState() {
        updatePlayerAndTimerBank(5);
        float prevY = player.getPos().y;
        float prevYVelocity = getVelocity().y;
        player.setInputAction(PlayerInputAction.JUMP);
        player.update(deltaTime);
        assertTrue(prevY < player.getPos().y);
        assertTrue(prevYVelocity < getVelocity().y);
    }

    @Test
    public void testJumpWhileInAirState() {
        updatePlayerAndTimerBank(5);
        player.setInputAction(PlayerInputAction.JUMP);
        player.update(deltaTime);

        float jumpGain = Config.getNumber(playerValues, "JUMP_GAIN");
        float gravity = Config.getNumber(physicsValues, "GRAVITY");
        float secondsToZeroYVelocity = jumpGain / Math.abs(gravity);

        updatePlayerAndTimerBank(secondsToZeroYVelocity);

        float prevY = player.getPos().y;
        player.setInputAction(PlayerInputAction.JUMP);

        player.update(deltaTime);
        assertTrue(prevY > player.getPos().y);
    }

    @Test
    public void testNoMovementWhileBlock() {
        float startX = player.getPos().x;
        for (int i = 0; i < 60; i++) {
            player.setInputAction(PlayerInputAction.BLOCK);
            player.setInputAction(PlayerInputAction.MOVE_LEFT);
            player.update(deltaTime);
        }

        assertEquals(startX, player.getPos().x, 0);

        startX = player.getPos().x;
        for (int i = 0; i < 60; i++) {
            player.setInputAction(PlayerInputAction.BLOCK);
            player.setInputAction(PlayerInputAction.MOVE_RIGHT);
            player.update(deltaTime);
        }

        assertEquals(startX, player.getPos().x, 0);

        startX = player.getPos().x;
        for (int i = 0; i < 60; i++) {
            player.setInputAction(PlayerInputAction.BLOCK);
            player.setInputAction(PlayerInputAction.JUMP);
            player.update(deltaTime);
        }

        assertEquals(startX, player.getPos().x, 0);

        startX = player.getPos().x;
        for (int i = 0; i < 60; i++) {
            player.setInputAction(PlayerInputAction.BLOCK);
            player.setInputAction(PlayerInputAction.DROP);
            player.update(deltaTime);
        }

        assertEquals(startX, player.getPos().x, 0);
    }

    @Test
    public void testActiveFighterChangedAfterSwap() {
        Class<? extends Fighter> beforeSwappedFighterClass = player.getActiveFighterClass();
        player.setInputAction(PlayerInputAction.SWAP_FIGHTER);
        player.update(deltaTime);
        Class<? extends Fighter> afterSwappedFighterClass = player.getActiveFighterClass();
        assertNotEquals(beforeSwappedFighterClass, afterSwappedFighterClass);
    }

    @Test
    public void testTeleportToSpawnAfterSwap() {
        updatePlayerAndTimerBank(5);
        Vector2 groundPos = player.getPos();

        player.setInputAction(PlayerInputAction.SWAP_FIGHTER);
        player.update(deltaTime);
        Vector2 respawnPos = player.getPos();

        assertEquals(spawnX, respawnPos.x, 1f);
        assertEquals(spawnY, respawnPos.y, 1f);
        assertNotEquals(groundPos, respawnPos);
    }

    @Test
    public void testResetVelocityAfterSwap() {
        updatePlayerWithAction(2, PlayerInputAction.MOVE_RIGHT);

        Vector2 velocityBeforeSwap = getVelocity();

        player.setInputAction(PlayerInputAction.SWAP_FIGHTER);
        player.update(0);
        Vector2 velocityAfterSwap = getVelocity();
        assertNotEquals(velocityBeforeSwap, velocityAfterSwap);
        assertEquals(0, velocityAfterSwap.x, 0);
        assertEquals(0, velocityAfterSwap.y, 1f);
    }

    @Test
    public void testCannotSwapUntilSwapTimerOver() {
        float swapCooldown = Config.getNumber(playerValues, "SWAP_COOLDOWN");

        player.setInputAction(PlayerInputAction.SWAP_FIGHTER);
        updatePlayerAndTimerBank(deltaTime);
        Class<? extends Fighter> secondaryFighter = player.getActiveFighterClass();

        assertTrue(deltaTime < swapCooldown);
        player.setInputAction(PlayerInputAction.SWAP_FIGHTER);
        updatePlayerAndTimerBank(deltaTime);

        Class<? extends Fighter> fighterAfterIllegalSwap = player.getActiveFighterClass();
        assertEquals(secondaryFighter, fighterAfterIllegalSwap);
    }

    @Test
    public void testSwapAgainAfterSwapCooldown() {
        float swapCooldown = Config.getNumber(playerValues, "SWAP_COOLDOWN");

        Class<? extends Fighter> primaryFighter = player.getActiveFighterClass();
        player.setInputAction(PlayerInputAction.SWAP_FIGHTER);
        player.update(0);

        Class<? extends Fighter> secondaryFighter = player.getActiveFighterClass();
        updatePlayerAndTimerBank(swapCooldown + deltaTime);
        player.setInputAction(PlayerInputAction.SWAP_FIGHTER);
        player.update(0);

        Class<? extends Fighter> fighterAfterLegalSwap = player.getActiveFighterClass();
        assertEquals(primaryFighter, fighterAfterLegalSwap);
        assertEquals(secondaryFighterClass, secondaryFighter);
    }


    @Test
    public void testGetAttackLength() {
        float evilWizardAttack1Length = FighterFactory.createEvilWizard().getAttackLength(0);
        float evilWizardAttack2Length = FighterFactory.createEvilWizard().getAttackLength(1);
        assertEquals(evilWizardAttack1Length, player.getAttackLength(0), 0);
        assertEquals(evilWizardAttack2Length, player.getAttackLength(1), 0);
    }

    @Test
    public void testGetInactiveFighterClassWithPrimaryAsActive() {
        assertEquals(secondaryFighterClass, player.getInactiveFighterClass());
    }

    @Test
    public void testGetInactiveFighterClassWithSecondaryAsActive() {
        player.setInputAction(PlayerInputAction.SWAP_FIGHTER);
        player.update(0);
        assertEquals(primaryFighterClass, player.getInactiveFighterClass());
    }

    @Test
    public void testGetAttackClass() {
        Class<? extends IAttack> evilWizardAttack1Class = FighterFactory.createEvilWizard().getAttackClass(0);
        Class<? extends IAttack> evilWizardAttack2Class = FighterFactory.createEvilWizard().getAttackClass(1);

        assertEquals(evilWizardAttack1Class, player.getAttackClass(0));
        assertEquals(evilWizardAttack2Class, player.getAttackClass(1));
    }

    @Test
    public void testTakeDamageNoBlocking() {
        float baseDmg = 10;
        float baseHealth = player.getHealth();
        player.takeDamage(baseDmg);
        Fighter fighter = FighterFactory.getFighter(player.getActiveFighterClass());
        float defenseFactor = fighter.DEFENSE_FACTOR;
        float HealthAfterTakenDmgNoBlocking = baseHealth - baseDmg * (1 - defenseFactor);
        assertEquals(HealthAfterTakenDmgNoBlocking, player.getHealth(), 0);
    }

    @Test
    public void testTakeDamageBlocking() {
        float baseDmg = 10;
        float baseHealth = player.getHealth();

        player.setInputAction(PlayerInputAction.BLOCK);
        player.update(0);
        player.setInputAction(PlayerInputAction.BLOCK);
        player.update(0);
        player.takeDamage(baseDmg);

        Fighter fighter = FighterFactory.getFighter(player.getActiveFighterClass());
        float defenseFactor = fighter.DEFENSE_FACTOR;
        float dmgTakenWithoutBlocking = baseDmg * (1 - defenseFactor);
        float dmgTaken = baseHealth - player.getHealth();
        assertTrue(dmgTaken < dmgTakenWithoutBlocking);
    }

    @Test
    public void testTakeDamageToDeath() {
        float baseHealth = player.getHealth();
        Fighter fighter = FighterFactory.getFighter(player.getActiveFighterClass());
        float defenseFactor = fighter.DEFENSE_FACTOR;
        player.takeDamage(baseHealth / (1 - defenseFactor));
        assertEquals(0, player.getHealth(), 0);
    }

    @Test
    public void testBlockDrain() {
        float baseDmg = 10f;
        float baseHealth = player.getHealth();
        player.setInputAction(PlayerInputAction.BLOCK);
        player.update(0);
        player.setInputAction(PlayerInputAction.BLOCK);
        player.update(0);
        player.takeDamage(baseDmg);
        float dmgTakenBlockingForShortTime = baseHealth - player.getHealth();


        float blockCooldown = Config.getNumber(playerValues, "BLOCK_COOLDOWN");
        TimerBank.updateTimers(blockCooldown);

        for (int i = 0; i < 600; i++) {
            player.setInputAction(PlayerInputAction.BLOCK);
            updatePlayerAndTimerBank(deltaTime);
        }

        baseHealth = player.getHealth();
        player.setInputAction(PlayerInputAction.BLOCK);
        updatePlayerAndTimerBank(0);
        player.setInputAction(PlayerInputAction.BLOCK);
        updatePlayerAndTimerBank(0);
        player.takeDamage(baseDmg);
        float dmgTakenBlockingForLongTime = baseHealth - player.getHealth();
        assertTrue(dmgTakenBlockingForLongTime > dmgTakenBlockingForShortTime);
    }

    @Test
    public void testBlockRegen() {
        float baseDmg = 10f;
        float baseHealth = player.getHealth();
        for (int i = 0; i < 600; i++) {
            player.setInputAction(PlayerInputAction.BLOCK);
            updatePlayerAndTimerBank(deltaTime);
        }
        player.setInputAction(PlayerInputAction.BLOCK);
        player.update(0);
        player.takeDamage(baseDmg);
        float dmgTakenBlockingForLongTime = baseHealth - player.getHealth();

        float blockCooldown = Config.getNumber(playerValues, "BLOCK_COOLDOWN");
        updatePlayerAndTimerBank(blockCooldown);

        // regen blockfactor
        updatePlayerAndTimerBank(5);

        baseHealth = player.getHealth();
        player.setInputAction(PlayerInputAction.BLOCK);
        player.update(0);
        player.setInputAction(PlayerInputAction.BLOCK);
        player.update(0);
        player.takeDamage(baseDmg);
        float dmgTakenBlockingAfterRegen = baseHealth - player.getHealth();
        assertTrue(dmgTakenBlockingAfterRegen < dmgTakenBlockingForLongTime);
    }

    @Test
    public void testInputsWhileAttacking() {
        player.setInputAction(PlayerInputAction.ATTACK1);
        updatePlayerAndTimerBank(deltaTime);
        for (int i = 0; i < 10; i++) {
            player.setInputAction(PlayerInputAction.MOVE_LEFT);
            player.update(deltaTime);
        }
        assertEquals(PhysicalState.ATTACKING1, player.getCurrentPhysicalState());
        assertEquals(0, getVelocity().x, 0);
    }

    @Test
    public void testStaminaObserver() {
        final float[] staminaValue = new float[1];
        FluctuatingAttributeObserver observer = (min, max, current) -> staminaValue[0] = current;
        player.registerStaminaObserver(observer);

        float previousStaminaValue = Config.getNumber(playerValues, "MAX_STAMINA");
        player.setInputAction(PlayerInputAction.ATTACK1);
        player.update(0);
        float afterAttackStamina = staminaValue[0];
        assertTrue(afterAttackStamina < previousStaminaValue);

        player.unregisterStaminaObserver(observer);
        player.update(1);
        float unObservedValueBeforeAttacks = staminaValue[0];
        for (int i = 0; i < 600; i++) {
            player.setInputAction(PlayerInputAction.ATTACK1);
            player.update(deltaTime);
            TimerBank.updateTimers(deltaTime);
        }

        float unObservedValueAfterAttacks = staminaValue[0];
        assertEquals(unObservedValueAfterAttacks, unObservedValueBeforeAttacks, 0);
    }

    @Test
    public void testHealthObserver() {
        final float[] currentHealth = new float[1];
        FluctuatingAttributeObserver observer = (min, max, current) -> currentHealth[0] = current;
        player.registerHealthObserver(observer);
        player.takeDamage(10);
        player.update(0);
        float afterDmgTaken = currentHealth[0];
        player.takeDamage(10);
        player.update(0);
        float afterDmgTaken2 = currentHealth[0];
        assertTrue(afterDmgTaken2 < afterDmgTaken);

        player.unregisterHealthObserver(observer);
        player.update(1);
        float unObservedHealthValueBeforeDmg = currentHealth[0];
        player.takeDamage(10);
        float unObservedHealthValueAfterDmg = currentHealth[0];
        assertEquals(unObservedHealthValueAfterDmg, unObservedHealthValueBeforeDmg, 0);
    }

    @Test
    public void testBlockObserver() {
        final float[] currentBlockFactor = new float[1];
        FluctuatingAttributeObserver observer = (min, max, current) -> currentBlockFactor[0] = current;
        player.registerBlockObserver(observer);
        player.setInputAction(PlayerInputAction.BLOCK);
        player.update(0);
        player.update(0);
        float initialBlockFactor = currentBlockFactor[0];
        float blockCooldown = Config.getNumber(playerValues, "BLOCK_COOLDOWN");
        updatePlayerAndTimerBank(blockCooldown + 1);
        updatePlayerWithAction(1, PlayerInputAction.BLOCK);
        float afterBlockingBlockFactor = currentBlockFactor[0];
        assertTrue(initialBlockFactor > afterBlockingBlockFactor);

        // somewhat reset the state of the player
        updatePlayerAndTimerBank(100);

        player.unregisterBlockObserver(observer);
        player.setInputAction(PlayerInputAction.BLOCK);
        player.update(0);
        initialBlockFactor = currentBlockFactor[0];
        updatePlayerAndTimerBank(blockCooldown + 1);
        updatePlayerWithAction(1, PlayerInputAction.BLOCK);
        afterBlockingBlockFactor = currentBlockFactor[0];
        assertEquals(initialBlockFactor, afterBlockingBlockFactor, 0);
    }
}
