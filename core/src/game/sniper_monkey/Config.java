package game.sniper_monkey;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import java.util.HashMap;

public class Config {

    private static final HashMap<String, HashMap<String, Object>> fileMap = new HashMap<>();

    /**
     * Read a config file and add it's data to the internal config hash.
     * The data can then be accessed using getText() and getNumber().
     *
     * @param file The filename to read data from.
     */
    public static void readConfigFile(String file) {
        HashMap<String, Object> config = new HashMap<>();
        if (fileMap.containsKey(file)) return;
        fileMap.put(file, config);
        FileHandle handle = Gdx.files.local(file);
        String text = handle.readString();
        String[] lines = text.split("\\r?\\n");
        for (String line : lines) {
            String[] words = line.split("(\\/\\/)|=");

            for (int i = 0; i < words.length; i++) {
                words[i] = words[i].strip();
            }

            try {
                config.put(words[0], Float.parseFloat(words[1]));
            } catch (NumberFormatException e) {
                config.put(words[0], words[1]);
            }
        }
    }

    /**
     * Gets config data as a float
     *
     * @param file The file containing the data
     * @param key  The name of the datapoint
     * @return The data as a float.
     */
    public static float getNumber(String file, String key) {
        if (fileMap.get(file).containsKey(key))
            return (float) fileMap.get(file).get(key);
        else throw new RuntimeException("The key " + key + " does not exist.");
    }

    /**
     * Gets config data as a String
     *
     * @param file The file containing the data
     * @param key  The name of the datapoint
     * @return The data as a String.
     */
    public static String getText(String file, String key) {
        if (fileMap.get(file).containsKey(key))
            return (String) fileMap.get(file).get(key);
        else throw new RuntimeException("The key " + key + " does not exist.");
    }
}
