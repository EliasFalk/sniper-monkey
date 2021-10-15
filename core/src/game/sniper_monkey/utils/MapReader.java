package game.sniper_monkey.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
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

    private static final DocumentBuilder db = createDocumentBuilder();
    private static final String MAPS_DIR = "core/assets/map/";

    /**
     * Reads a tmx map along with a tileset and creates a map of Strings containing the names of each tile.
     *
     * @param mapPath     The path relative to the map directory of the map file.
     * @param tilesetPath The path relative to the map directory of the tileset file.
     * @return            A 2D array of Strings representing the map where each entry is the name of the tile or "air" if there is no tile.
     */
    public static String[][] readMap(String mapPath, String tilesetPath) {
        Document mapDoc;
        Document tilesetDoc;

        mapDoc = readDocument(MAPS_DIR + mapPath);
        tilesetDoc = readDocument(MAPS_DIR + tilesetPath);

        return translateMapData(mapDoc, tilesetDoc);
    }

    /**
     * Reads a tmx map along with a tileset and creates a map of Strings containing the names of each tile.
     *
     * @param mapFolder The path relative to the map directory of a folder containing both a map and a tileset file.
     *                  If there are multiple map or tileset files the last files in the directory will be read.
     * @return          A 2D array of Strings representing the map where each entry is the name of the tile or "air" if there is no tile.
     */
    public static String[][] readMap(String mapFolder) {
        FileHandle dirHandle = Gdx.files.internal(MAPS_DIR + mapFolder);

        String tileSet = "";
        String tileMap = "";

        for (FileHandle entry : dirHandle.list()) {
           if(entry.extension().equals("tmx")) tileMap = entry.name();
           else if(entry.extension().equals("tsx")) tileSet = entry.name();
        }

        return readMap(mapFolder + "/" + tileMap, mapFolder + "/" + tileSet);
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

    private static Document readDocument(String s) {
        Document document;
        FileHandle mapHandle = Gdx.files.internal(s); //TODO fix path
        try {
            document = db.parse(mapHandle.file());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        document.getDocumentElement().normalize();
        return document;
    }

    private static DocumentBuilder createDocumentBuilder() {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db;
        try {
            db = dbf.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        }
        return db;
    }
}
