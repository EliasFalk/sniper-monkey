package sniper_monkey.player.fighter.attack;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import com.badlogic.gdx.math.Vector2;
import game.sniper_monkey.model.Config;
import game.sniper_monkey.model.player.Player;
import game.sniper_monkey.model.player.PlayerFactory;
import game.sniper_monkey.model.player.PlayerInputAction;
import game.sniper_monkey.model.player.fighter.attack.AttackFactory;
import game.sniper_monkey.model.player.fighter.attack.IAttack;
import game.sniper_monkey.model.world.World;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;

public class BowTripleAttackTest {

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
    public void testGetAttackLength() {
        IAttack bowTripleAttack = AttackFactory.createBowTripleAttack();
        assertEquals(1.5f, bowTripleAttack.getAttackLength(), 0);
    }

    @Test
    public void testGetHitStun() {
        IAttack bowTripleAttack = AttackFactory.createBowTripleAttack();
        assertEquals(1f, bowTripleAttack.getHitStunLength(), 0);
    }

    @Test
    public void testBowTripleAttackHit() {
        Player player1 = PlayerFactory.createPlayer1(new Vector2(0, 0));
        Player player2 = PlayerFactory.createPlayer2(new Vector2(70, 0));
        player2.setInputAction(PlayerInputAction.ATTACK2);
        world.queueAddGameObject(player1);
        world.queueAddGameObject(player2);
        world.update(0.1f);
        world.update(0.1f);
        assertNotEquals(100f, player1.getHealth(), 0);
    }

    @Test
    public void testBowTripleAttackMiss() {
        Player player1 = PlayerFactory.createPlayer1(new Vector2(0, 0));
        Player player2 = PlayerFactory.createPlayer2(new Vector2(700, 0));
        player2.setInputAction(PlayerInputAction.ATTACK2);
        world.queueAddGameObject(player1);
        world.queueAddGameObject(player2);
        world.update(0.1f);
        world.update(0.1f);
        assertEquals(100f, player1.getHealth(), 0);

    }

}
