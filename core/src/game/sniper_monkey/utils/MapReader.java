package game.sniper_monkey.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Vector2;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.HashMap;
import java.util.Map;

/**
 * A static utility that can read and convert Tiled map files along with tileset files.
 *
 * @author Vincent Hellner
 * @author Elias Falk
 */
public final class MapReader {
    private MapReader() {}

    private static final String MAPS_DIR = "core/assets/map/";

    /**
     * Reads the spawn points in the map and returns them as a Map
     * @param mapPath The path to the map relative to the maps directory
     * @return        A Map with the spawn points
     */
    public static Map<String, Vector2> readSpawnPoints(String mapPath) {
        Document mapDoc = XMLUtils.readDocument(MAPS_DIR + mapPath);
        Node spawnGroup = getSpawnGroup(mapDoc);
        return getSpawnPoints(spawnGroup);
    }

    private static Map<String, Vector2> getSpawnPoints(Node spawnGroup) {
        Map<String, Vector2> spawnPoints = new HashMap<>();
        for(Node node : NodeListUtil.asList(spawnGroup.getChildNodes())) {
            if(!node.getNodeName().equals("object")) continue;
            if(node.getAttributes().getNamedItem("type").getTextContent().equals("spawn_point")) {
                float xSpawn = Float.parseFloat(node.getAttributes().getNamedItem("x").getTextContent());
                float ySpawn = Float.parseFloat(node.getAttributes().getNamedItem("y").getTextContent());
                spawnPoints.put(node.getAttributes().getNamedItem("name").getTextContent(), new Vector2(xSpawn, ySpawn));
            }
        }
        return spawnPoints;
    }

    private static Node getSpawnGroup(Document mapDoc) {
        Node spawnGroup = null;
        for(Node node : NodeListUtil.asList(mapDoc.getElementsByTagName("objectgroup"))) {
            Node nameAttribute = node.getAttributes().getNamedItem("name");
            if(nameAttribute.getTextContent().equals("spawns")) {
                spawnGroup = node;
                break;
            }
        }
        if(spawnGroup == null) throw new IllegalArgumentException("Supplied map does not contain spawn points");
        return spawnGroup;
    }

    /**
     * Reads a tmx map along with a tileset and creates a map of Strings containing the names of each tile.
     *
     * @param mapPath     The path relative to the map directory of the map file.
     * @param tilesetPath The path relative to the map directory of the tileset file.
     * @return            A 2D array of Strings representing the map where each entry is the name of the tile or "air" if there is no tile.
     */
    public static String[][] readMapTiles(String mapPath, String tilesetPath) {
        Document mapDoc = XMLUtils.readDocument(MAPS_DIR + mapPath);
        Document tilesetDoc = XMLUtils.readDocument(MAPS_DIR + tilesetPath);

        return translateMapData(mapDoc, tilesetDoc);
    }

    /**
     * Reads a tmx map along with a tileset and creates a map of Strings containing the names of each tile.
     *
     * @param mapFolder The path relative to the map directory of a folder containing both a map and a tileset file.
     *                  If there are multiple map or tileset files the last files in the directory will be read.
     * @return          A 2D array of Strings representing the map where each entry is the name of the tile or "air" if there is no tile.
     */
    public static String[][] readMapTiles(String mapFolder) {
        FileHandle dirHandle = Gdx.files.internal(MAPS_DIR + mapFolder);

        String tileSet = "";
        String tileMap = "";

        for (FileHandle entry : dirHandle.list()) {
            if(entry.extension().equals("tmx")) tileMap = entry.name();
            else if(entry.extension().equals("tsx")) tileSet = entry.name();
        }

        return readMapTiles(mapFolder + "/" + tileMap, mapFolder + "/" + tileSet);
    }

    private static String[][] translateMapData(Document mapDoc, Document tilesetDoc) {
        Map<Integer, String> tileNames = readTileNames(tilesetDoc);

        int[][] mapData = convertMapData(mapDoc);

        String[][] translatedMap = new String[mapData.length][mapData[0].length];

        for(int y = 0; y < mapData.length; y++) {
            for(int x = 0; x < mapData[y].length; x++) {
                translatedMap[y][x] = tileNames.getOrDefault(mapData[y][x], "air");
            }
        }

        return translatedMap;
    }

    private static int[][] convertMapData(Document mapDoc) {
        NodeList mapData = mapDoc.getElementsByTagName("data");
        NamedNodeMap layerAttributes = mapDoc.getElementsByTagName("layer").item(0).getAttributes();

        String[] rows = mapData.item(0).getTextContent().trim().split("\n");

        int width = Integer.parseInt(layerAttributes.getNamedItem("width").getTextContent());
        int height = Integer.parseInt(layerAttributes.getNamedItem("height").getTextContent());

        int[][] map = new int[height][width];
        for(int y = height-1; y >= 0; y--) {
            for(int x = 0; x < width; x++) {
                map[height-y-1][x] = Integer.parseInt(rows[y].split(",")[x])-1;
            }
        }
        return map;
    }

    private static Map<Integer, String> readTileNames(Document tilesetDoc) {
        Map<Integer, String> tileNames = new HashMap<>();
        NodeList tileData = tilesetDoc.getElementsByTagName("tile");
        for(Node node : NodeListUtil.asList(tileData)) {
            int id = Integer.parseInt(node.getAttributes().item(0).getTextContent());
            String name = node.getAttributes().item(1).getTextContent();
            tileNames.put(id, name);
        }
        return tileNames;
    }
}
