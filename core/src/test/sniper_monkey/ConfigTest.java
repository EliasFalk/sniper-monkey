package sniper_monkey;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import game.sniper_monkey.Config;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ConfigTest {

    @BeforeClass
    public static void initHeadless() {
        final HeadlessApplicationConfiguration config = new HeadlessApplicationConfiguration();
        new HeadlessApplication(new ApplicationAdapter() {
        }, config);
    }

    @Before
    public void readConfig() {
        Config.readConfigFile("assets/cfg/test.cfg");
    }

    @Test
    public void testNumberVal() {
        int num = (int) Config.getNumber("assets/cfg/test.cfg", "key1");
        Assert.assertEquals(123, num);
    }
}
