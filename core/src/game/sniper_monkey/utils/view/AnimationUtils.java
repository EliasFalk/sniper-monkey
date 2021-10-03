package game.sniper_monkey.utils.view;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Array;

public final class AnimationUtils {

    private AnimationUtils() {
    }

    /**
     * Creates an array of sprites from a horizontal sprite sheet.
     *
     * @param texture The Sprite sheet to cut.
     * @param sprites The amount of sprites in the sheet.
     * @param mirror  Should the sprites be mirrored?
     * @return The array of sprites
     */
    public static Array<Sprite> cutSpriteSheet(Texture texture, int sprites, boolean mirror) {
        Array<Sprite> cutSprites = new Array<>();
        int spriteWidth = texture.getWidth() / sprites;
        int spriteHeight = texture.getHeight();
        int i = 0;
        if (mirror) {
            spriteWidth *= -1;
            sprites++;
            i++;
        }

        for (; i < sprites; i++) {
            cutSprites.add(new Sprite(texture, Math.abs(spriteWidth * i), 0, spriteWidth, spriteHeight));
        }
        return cutSprites;
    }

    /**
     * Creates an array of sprites from a horizontal sprite sheet.
     *
     * @param texture The Sprite sheet to cut.
     * @param sprites The amount of sprites in the sheet.
     * @return The array of sprites
     */
    public static Array<Sprite> cutSpriteSheet(Texture texture, int sprites) {
        return cutSpriteSheet(texture, sprites, false);
    }
}
