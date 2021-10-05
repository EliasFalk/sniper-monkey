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

    private static String cfg;
  
    @BeforeClass
    public static void initHeadless() {
        cfg = "assets/cfg/test.cfg";
        final HeadlessApplicationConfiguration config = new HeadlessApplicationConfiguration();
        new HeadlessApplication(new ApplicationAdapter() {
        }, config);
    }

    @Before
    public void readConfig() {
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
        System.out.println(str);
        assertEquals("hejsan", str); // "//" is considered to be a comment. it's meant to disappear.
    }

    @Test
    public void testDoubleSlashNumber() { // kinda same as previous test but maybe need to test it for getNumber() as well.
        int num = (int) Config.getNumber(cfg, "key4");
        assertEquals(123, num);
    }

    /*@Test // TODO fix this test
    public void testKeyWithoutValue() {
        String str = Config.getText(cfg, "key7");
        System.out.println(str);
    }*/

}
