package game.sniper_monkey.utils.view;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class SpriteUtils {
    private static final Sprite defaultSprite;

    static {
        Texture missingTexture = new Texture("images/missing_texture_texture.png");
        defaultSprite = new Sprite(missingTexture);
    }

    public static Sprite getDefaultSprite() {
        return defaultSprite;
    }
}
