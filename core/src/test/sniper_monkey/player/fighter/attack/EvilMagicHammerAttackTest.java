package sniper_monkey.player.fighter.attack;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import com.badlogic.gdx.math.Vector2;
import game.sniper_monkey.model.Config;
import game.sniper_monkey.model.TimerBank;
import game.sniper_monkey.model.player.Player;
import game.sniper_monkey.model.player.PlayerFactory;
import game.sniper_monkey.model.player.PlayerInputAction;
import game.sniper_monkey.model.player.fighter.EvilWizard;
import game.sniper_monkey.model.player.fighter.HuntressBow;
import game.sniper_monkey.model.player.fighter.Samurai;
import game.sniper_monkey.model.player.fighter.attack.AttackFactory;
import game.sniper_monkey.model.player.fighter.attack.IAttack;
import game.sniper_monkey.model.world.World;
import game.sniper_monkey.model.world_brick.WorldBrick;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class EvilMagicHammerAttackTest {

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
    public void testEvilHammerPerformAttack() {
        Player player1 = PlayerFactory.createPlayer1(new Vector2(0, 0));
        Player player2 = PlayerFactory.createPlayer2(new Vector2(50, 0), EvilWizard.class, HuntressBow.class);
        float player1baseHealth = player1.getHealth();
        World.getInstance().queueAddGameObject(player1);
        World.getInstance().queueAddGameObject(player2);
        updateWorld(2);
        player2.setInputAction(PlayerInputAction.ATTACK2);
        updateWorld(1);
        assertTrue(player1baseHealth > player1.getHealth());
    }

    @Test
    public void testGetStaminaCost() {
        IAttack hammerAttack = AttackFactory.createEvilMagicHammerAttack();
        assertEquals(15, hammerAttack.getStaminaCost(), 0.001);
    }

    @Test
    public void testAttackLength() {
        IAttack hammerAttack = AttackFactory.createEvilMagicHammerAttack();
        assertEquals(1.2f, hammerAttack.getAttackLength(), 0.001);
    }

    @Test
    public void testHitStunLength() {
        IAttack hammerAttack = AttackFactory.createEvilMagicHammerAttack();
        assertEquals(0.5f, hammerAttack.getHitStunLength(), 0.001);
    }

}
