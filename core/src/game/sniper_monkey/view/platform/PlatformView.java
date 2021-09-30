package game.sniper_monkey.view.platform;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import game.sniper_monkey.platform.Platform;
import game.sniper_monkey.view.GameObjectView;
import game.sniper_monkey.world.GameObject;

public class PlatformView extends GameObjectView {
    Platform model;
    public PlatformView(Platform model) {
        super(new Vector2(0,0), new Sprite(new Texture("CuteForest/Tileset.png"), 16, 0, 16, 16), model);
        this.model = model;
    }
}