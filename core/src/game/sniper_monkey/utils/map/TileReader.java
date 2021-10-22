package game.sniper_monkey.utils.map;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import game.sniper_monkey.utils.view.SpriteUtils;
import game.sniper_monkey.utils.xml.NodeListUtil;
import game.sniper_monkey.utils.xml.XMLUtils;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.HashMap;
import java.util.Map;

/**
 * A utility class to read tile sets.
 *
 * @author Elias Falk
 * @author Vincent Hellner
 */
public class TileReader {
    private static final String MAPS_DIR = "core/assets/map/";

    private TileReader() {}

    /**
     * Reads a tileset from the program Tiled (.tsx extension) and returns a map containing the type for each tile together with the correct sprite.
     * Note: the tileset image must exist within the assets folder.
     *
     * @param filepath The filepath to the .tsx tileset file.
     *                 The program reads from core/assets/map/ meaning a filepath of "tileset.tsx" will read the file at "core/assets/map/tileset.tsx".
     * @return A map containing the type for each tile together with the correct sprite extracted from the tileset image.
     */
    public static Map<String, Sprite> readTileSet(String filepath) {
        Document tileSet = XMLUtils.readDocument(MAPS_DIR + filepath);
        Map<String, Sprite> tileMap = new HashMap<>();
        NamedNodeMap tileSetAttrbiutes = tileSet.getElementsByTagName("tileset").item(0).getAttributes();
        int tileHeight = Integer.parseInt(tileSetAttrbiutes.getNamedItem("tileheight").getTextContent());
        int tileWidth = Integer.parseInt(tileSetAttrbiutes.getNamedItem("tilewidth").getTextContent());
        int tileCount = Integer.parseInt(tileSetAttrbiutes.getNamedItem("tilecount").getTextContent());
        int columns = Integer.parseInt(tileSetAttrbiutes.getNamedItem("columns").getTextContent());
        String imagePath = tileSet.getElementsByTagName("image").item(0).getAttributes().getNamedItem("source").getTextContent();
        imagePath = imagePath.replaceAll("^\\p{P}+|\\p{P}+$", "");

        NodeList tiles = tileSet.getElementsByTagName("tile");

        for(Node tile : NodeListUtil.asList(tiles)) {
            int id = Integer.parseInt(tile.getAttributes().getNamedItem("id").getTextContent());
            String type = tile.getAttributes().getNamedItem("type").getTextContent();
            if(type.startsWith("ghost-")) {
                type = type.split("-")[1];
            }
            tileMap.put(type, SpriteUtils.getTile(new Texture(imagePath), tileHeight, id % columns, id / columns));
        }

        return tileMap;
    }
}
