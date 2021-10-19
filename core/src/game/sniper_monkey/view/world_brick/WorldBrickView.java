package game.sniper_monkey.view.world_brick;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import game.sniper_monkey.model.world_brick.WorldBrick;
import game.sniper_monkey.utils.TileReader;
import game.sniper_monkey.utils.view.SpriteUtils;
import game.sniper_monkey.view.GameObjectView;

import java.util.HashMap;
import java.util.Map;

/**
 * A GameObjectView for a WorldBrick
 *
 * @author Vincent Hellner
 * @author Elias Falk
 */
public class WorldBrickView extends GameObjectView {
    private static final Map<String, Sprite> brickSprites = TileReader.readTileSet("grass_map_2/Tileset2.tsx");

    private WorldBrick model;

    //TODO documentation
    public WorldBrickView(WorldBrick model) {
        super(new Vector2(0, 0), chooseSprite(model.type), model);
        this.model = model;
    }

    private static Sprite chooseSprite(String type) {
        if(type.startsWith("ghost-")) {
            type = type.split("-")[1];
        }
        System.out.println(type);
        return brickSprites.getOrDefault(type, SpriteUtils.getDefaultSprite());
    }
}
