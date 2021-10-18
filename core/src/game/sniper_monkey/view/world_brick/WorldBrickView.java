package game.sniper_monkey.view.world_brick;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import game.sniper_monkey.model.world_brick.WorldBrick;
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
    private static final Map<String, Sprite> brickSprites = new HashMap<>();

    static {
        brickSprites.put("mid_grass", new Sprite(new Texture("images/CuteForest/Tileset.png"), 16, 0, 16, 16));
        brickSprites.put("left_grass", new Sprite(new Texture("images/CuteForest/Tileset.png"), 0, 0, 16, 16));
        brickSprites.put("right_grass", new Sprite(new Texture("images/CuteForest/Tileset.png"), 32, 0, 16, 16));
        brickSprites.put("dirt", new Sprite(new Texture("images/CuteForest/Tileset.png"), 16, 16, 16, 16));

        brickSprites.put("float_grass", new Sprite(new Texture("images/CuteForest/Tileset.png"), 80, 32, 16, 16));

        brickSprites.put("inner_house", new Sprite(new Texture("images/CuteForest/Tileset.png"), 96, 64, 16, 16));
        brickSprites.put("top_left_house", new Sprite(new Texture("images/CuteForest/Tileset.png"), 80, 48, 16, 16));
        brickSprites.put("roof", new Sprite(new Texture("images/CuteForest/Tileset.png"), 96, 48, 16, 16));
        brickSprites.put("top_right_house", new Sprite(new Texture("images/CuteForest/Tileset.png"), 112, 48, 16, 16));
        brickSprites.put("right_house", new Sprite(new Texture("images/CuteForest/Tileset.png"), 112, 64, 16, 16));
        brickSprites.put("left_house", new Sprite(new Texture("images/CuteForest/Tileset.png"), 80, 64, 16, 16));
    }

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
