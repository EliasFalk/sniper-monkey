package sniper_monkey.player.fighter.attack;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import com.badlogic.gdx.math.Vector2;
import game.sniper_monkey.model.Config;
import game.sniper_monkey.model.player.Player;
import game.sniper_monkey.model.player.PlayerFactory;
import game.sniper_monkey.model.player.PlayerInputAction;
import game.sniper_monkey.model.player.fighter.FighterFactory;
import game.sniper_monkey.model.player.fighter.attack.AttackFactory;
import game.sniper_monkey.model.player.fighter.attack.EvilMagicSwingAttack;
import game.sniper_monkey.model.player.fighter.attack.IAttack;
import game.sniper_monkey.model.world.World;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestEvilMagicSwingAttack {

    private static World world = World.getInstance();
    private static String cfg;
    private static float roundTime;

    @BeforeClass
    public static void init() {
        cfg = "cfg/game.cfg";
        final HeadlessApplicationConfiguration config = new HeadlessApplicationConfiguration();
        new HeadlessApplication(new ApplicationAdapter() {}, config);
        Config.readConfigFile(cfg);
        roundTime = Config.getNumber(cfg, "ROUND_TIME");
        world.resetWorld();
    }

    @Test
    public void testEvilMagicSwingAttack() {
        Player player1 = PlayerFactory.createPlayer1(new Vector2(70, 0), FighterFactory.createEvilWizard(), FighterFactory.createEvilWizard());
        Player player2 = PlayerFactory.createPlayer2(new Vector2(0, 0), FighterFactory.createEvilWizard(), FighterFactory.createEvilWizard());
        player1.setInputAction(PlayerInputAction.ATTACK1);
        world.queueAddGameObject(player1);
        world.queueAddGameObject(player2);
        world.update(0.1f);
        world.update(0.1f);
        Assert.assertNotEquals(100f, player2.getHealth());
    }

    @Test
    public void testEvilMagicSwingAttackMiss() {
        Player player1 = PlayerFactory.createPlayer1(new Vector2(700, 0), FighterFactory.createEvilWizard(), FighterFactory.createEvilWizard());
        Player player2 = PlayerFactory.createPlayer2(new Vector2(0, 0), FighterFactory.createEvilWizard(), FighterFactory.createEvilWizard());
        player1.setInputAction(PlayerInputAction.ATTACK1);
        world.queueAddGameObject(player1);
        world.queueAddGameObject(player2);
        world.update(0.1f);
        world.update(0.1f);
        Assert.assertEquals(100f, player2.getHealth(), 0);
    }

    @Test
    public void testGetAttackLength() {
        IAttack evilMagicSwingAttack = AttackFactory.createEvilMagicSwingAttack();
        Assert.assertEquals(0.8f, evilMagicSwingAttack.getAttackLength(), 0);
    }

    @Test
    public void testGetHitStun() {
        IAttack evilMagicSwingAttack = AttackFactory.createEvilMagicSwingAttack();
        Assert.assertEquals(0.2f, evilMagicSwingAttack.getHitStunLength(), 0);
    }

}
