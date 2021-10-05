package sniper_monkey;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import game.sniper_monkey.Config;
import static org.junit.Assert.*;

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
        assertEquals(123, num);

    }

    @Test
    public void testInvalidNumber() {
        try {
            int num = (int) Config.getNumber("assets/cfg/test.cfg", "key20");
        } catch (RuntimeException e) {
            if (e.getMessage().equals("The key " + "key20" + " does not exist.")) {
                assertTrue(true);
            } else {
                fail();
            }
        }
    }

    @Test
    public void testInvalidText() {
        try {
           String str = Config.getText("assets/cfg/test.cfg", "key20");
        } catch (RuntimeException e) {
            if (e.getMessage().equals("The key " + "key20" + " does not exist.")) {
                assertTrue(true);
            } else {
                fail();
            }
        }
    }


    @Test
    public void testTextValue() {
        String str = Config.getText("assets/cfg/test.cfg", "key2");
        assertEquals("hejsan", str);


    }

}
