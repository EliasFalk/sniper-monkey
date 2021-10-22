package sniper_monkey.player.fighter.attack;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import com.badlogic.gdx.math.Vector2;
import game.sniper_monkey.model.Config;
import game.sniper_monkey.model.TimerBank;
import game.sniper_monkey.model.player.Player;
import game.sniper_monkey.model.player.PlayerFactory;
import game.sniper_monkey.model.player.fighter.HuntressBow;
import game.sniper_monkey.model.player.fighter.attack.attack_object.AttackObjectSpawner;
import game.sniper_monkey.model.world.World;
import game.sniper_monkey.model.world_brick.WorldBrick;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class AttackObjectSpawnerTest {

    static String cfg;
    static float roundTime;
    private static final float deltaTime = 1 / 60f;


    @BeforeClass
    public static void init() {
        cfg = "cfg/game.cfg";
        final HeadlessApplicationConfiguration config = new HeadlessApplicationConfiguration();
        new HeadlessApplication(new ApplicationAdapter() {}, config);
        Config.readConfigFile(cfg);
        roundTime = Config.getNumber(cfg, "ROUND_TIME");
        World.getInstance().resetWorld();
        for (int i = -400; i < 400; i += 16) {
            World.getInstance().queueAddGameObject(new WorldBrick(new Vector2(i, -100), "1"));
        }
        World.getInstance().update(deltaTime);
    }

    private void updateWorld(int seconds) {
        for (int i = 0; i < seconds / deltaTime; i++) {
            World.getInstance().update(deltaTime);
            TimerBank.updateTimers(deltaTime);
        }
    }

    @Test
    public void testSpawningOfAllAttackObjects() {
        float damage = 5;
        float timeToLive = 2;
        Vector2 spawnPos = new Vector2(0,-80);
        int collisionMask = 0;
        boolean lookingRight = true;
        Vector2 velocity = new Vector2(5*60, 0);

        Player player = PlayerFactory.createPlayer1(new Vector2(20,0), HuntressBow.class, HuntressBow.class);
        float playerBaseHealth = player.getHealth();
        World.getInstance().queueAddGameObject(player);
        updateWorld(2);


        AttackObjectSpawner.spawnElectricalSmash(damage, timeToLive, spawnPos, collisionMask, lookingRight);
        updateWorld(2);
        assertTrue(playerBaseHealth > player.getHealth());

        playerBaseHealth = player.getHealth();

        AttackObjectSpawner.spawnElectricalSlash(damage, timeToLive, spawnPos, collisionMask, lookingRight);
        updateWorld(2);
        assertTrue(playerBaseHealth > player.getHealth());

        playerBaseHealth = player.getHealth();

        AttackObjectSpawner.spawnSamuraiQuickSwing(damage, timeToLive, spawnPos, collisionMask, lookingRight);
        updateWorld(2);
        assertTrue(playerBaseHealth > player.getHealth());

        playerBaseHealth = player.getHealth();

        AttackObjectSpawner.spawnSamuraiShuriken(damage, timeToLive, spawnPos, collisionMask, lookingRight, velocity);
        updateWorld(2);
        assertTrue(playerBaseHealth > player.getHealth());

        playerBaseHealth = player.getHealth();

        AttackObjectSpawner.spawnEvilMagicHammer(damage, timeToLive, spawnPos, collisionMask, lookingRight);
        updateWorld(2);
        assertTrue(playerBaseHealth > player.getHealth());

        playerBaseHealth = player.getHealth();

        AttackObjectSpawner.spawnEvilMagicSwing(damage, timeToLive, spawnPos, collisionMask, lookingRight);
        updateWorld(2);
        assertTrue(playerBaseHealth > player.getHealth());

        playerBaseHealth = player.getHealth();

        AttackObjectSpawner.spawnHuntressArrowShot(damage, timeToLive, spawnPos, collisionMask, lookingRight, velocity);
        updateWorld(2);
        assertTrue(playerBaseHealth > player.getHealth());
    }
}
