package sniper_monkey.player.fighter.attack;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import com.badlogic.gdx.math.Vector2;
import game.sniper_monkey.model.Config;
import game.sniper_monkey.model.player.Player;
import game.sniper_monkey.model.player.PlayerFactory;
import game.sniper_monkey.model.player.PlayerInputAction;
import game.sniper_monkey.model.player.fighter.HuntressBow;
import game.sniper_monkey.model.player.fighter.Samurai;
import game.sniper_monkey.model.player.fighter.attack.AttackFactory;
import game.sniper_monkey.model.player.fighter.attack.IAttack;
import game.sniper_monkey.model.world.World;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class SamuraiShurikenAttackTest {

    World world = World.getInstance();
    static String cfg;
    static float roundTime;

    @BeforeClass
    public static void init() {
        cfg = "cfg/game.cfg";
        final HeadlessApplicationConfiguration config = new HeadlessApplicationConfiguration();
        new HeadlessApplication(new ApplicationAdapter() {}, config);
        Config.readConfigFile(cfg);
        roundTime = Config.getNumber(cfg, "ROUND_TIME");
    }


    @Test
    public void testShurikenAttack() {
        Player player1 = PlayerFactory.createPlayer1(new Vector2(0, 0));
        Player player2 = PlayerFactory.createPlayerWithFighters(new Vector2(70, 0), Samurai.class, HuntressBow.class);
        world.queueAddGameObject(player1);
        world.queueAddGameObject(player2);
        player2.setInputAction(PlayerInputAction.ATTACK2);
        world.update(0.1f);
        world.update(0.1f);
        assertNotEquals(100f, player1.getHealth(), 0);
    }

    @Test
    public void testGetStaminaCost() {
        IAttack shurikenAttack = AttackFactory.createSamuraiShurikenAttack();
        assertEquals(20, shurikenAttack.getStaminaCost(), 0.001);
    }

    @Test
    public void testAttackLength() {
        IAttack shurikenAttack = AttackFactory.createSamuraiShurikenAttack();
        assertEquals(1.2f, shurikenAttack.getAttackLength(), 0.001);
    }

    @Test
    public void testHitStunLength() {
        IAttack shurikenAttack = AttackFactory.createSamuraiShurikenAttack();
        assertEquals(1f, shurikenAttack.getHitStunLength(), 0.001);
    }

    @Test
    public void testBowAttackMiss() {
        Player player1 = PlayerFactory.createPlayer1(new Vector2(0, 0));
        Player player2 = PlayerFactory.createPlayerWithFighters(new Vector2(700, 0), Samurai.class, HuntressBow.class);
        player2.setInputAction(PlayerInputAction.ATTACK2);
        world.queueAddGameObject(player1);
        world.queueAddGameObject(player2);
        world.update(0.1f);
        world.update(0.1f);
        Assert.assertEquals(100f, player2.getHealth(), 0);
    }
}
