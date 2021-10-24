package sniper_monkey;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import game.sniper_monkey.utils.Config;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class ConfigTest {

    private static String cfg;

    @BeforeClass
    public static void initHeadless() {
        cfg = "cfg/test.cfg";
        final HeadlessApplicationConfiguration config = new HeadlessApplicationConfiguration();
        new HeadlessApplication(new ApplicationAdapter() {
        }, config);
        Config.readConfigFile(cfg);
    }

    @Test
    public void testNumberVal() {
        int num = (int) Config.getNumber(cfg, "key1");
        assertEquals(123, num);
    }

    @Test
    public void testInvalidNumber() {
        try {
            int num = (int) Config.getNumber(cfg, "key20");
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
            String str = Config.getText(cfg, "key20");
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
        String str = Config.getText(cfg, "key1");
        assertEquals("123", str);
    }

    @Test(expected = NumberFormatException.class)
    public void testTextAsNumberValue() {
        Config.getNumber(cfg, "key2");
    }

    @Test
    public void testNumberAsTextValue() {
        String str = Config.getText(cfg, "key2");
        assertEquals("hejsan", str);
    }

    @Test
    public void testMultiWordText() {
        String str = Config.getText(cfg, "key3");
        assertEquals("hello there", str);
    }

    @Test
    public void testDoubleSlashText() {
        String str = Config.getText(cfg, "key5");
        assertEquals("hejsan", str); // "//" is considered to be a comment. it's meant to disappear.
    }

    @Test
    public void testDoubleSlashNumber() { // kinda same as previous test but maybe need to test it for getNumber() as well.
        int num = (int) Config.getNumber(cfg, "key4");
        assertEquals(123, num);
    }

    @Test
    public void testKeyWithoutValueAsText() {
        String str = Config.getText(cfg, "key7");
        assertEquals("", str);
    }

    @Test(expected = NumberFormatException.class)
    public void testKeyWithoutValueAsNumber() {
        int num = (int) Config.getNumber(cfg, "key7");
    }
}
