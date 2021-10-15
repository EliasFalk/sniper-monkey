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

public class MapReader {
    private static final DocumentBuilder db = createDocumentBuilder();

    public static String[][] readMap() {
        Document mapDoc;
        Document tilesetDoc;

        mapDoc = readDocument("core/assets/map/grass_map/untitled.tmx");
        tilesetDoc = readDocument("core/assets/map/grass_map/Tileset.tsx");

        return translateMapData(mapDoc, tilesetDoc);
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
        for(Node node : XmlUtil.asList(tileData)) {
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
