package game.sniper_monkey.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import game.sniper_monkey.utils.view.SpriteUtils;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

public class TileReader {
    private static final DocumentBuilder db = createDocumentBuilder();
    private static final String MAPS_DIR = "core/assets/map/";

    public static Map<String, Sprite> readTileSet(String filepath) {
        Document tileSet = readDocument(MAPS_DIR + filepath);
        Map<String, Sprite> tileMap = new HashMap<>();
        NamedNodeMap tileSetAttrbiutes = tileSet.getElementsByTagName("tileset").item(0).getAttributes();
        int tileHeight = Integer.parseInt(tileSetAttrbiutes.getNamedItem("tileheight").getTextContent());
        int tileWidth = Integer.parseInt(tileSetAttrbiutes.getNamedItem("tilewidth").getTextContent());
        int tileCount = Integer.parseInt(tileSetAttrbiutes.getNamedItem("tilecount").getTextContent());
        int columns = Integer.parseInt(tileSetAttrbiutes.getNamedItem("columns").getTextContent());
        String imagePath = tileSet.getElementsByTagName("image").item(0).getAttributes().getNamedItem("source").getTextContent();

        NodeList tiles = tileSet.getElementsByTagName("tile");

        for(Node tile : NodeListUtil.asList(tiles)) {
            int id = Integer.parseInt(tile.getAttributes().getNamedItem("id").getTextContent());
            String type = tile.getAttributes().getNamedItem("type").getTextContent();
            if(type.startsWith("ghost-")) {
                type = type.split("-")[1];
            }
            tileMap.put(type, SpriteUtils.getTile(new Texture("images/CuteForest/Tileset.png"), tileHeight, id % columns, id / columns));
        }

        return tileMap;
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
