package game.sniper_monkey.utils.view;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Utility used for sprites
 * <p>
 * Used by TileReader.
 * Used by all fighter views.
 * Used by WorldBrickView.
 *
 * @author Elias Falk
 * @author Vincent Hellner
 */
public final class SpriteUtils {
    private static final Sprite defaultSprite;

    static {
        Texture missingTexture = new Texture("images/missing_texture_texture.png");
        defaultSprite = new Sprite(missingTexture);
    }

    private SpriteUtils() {
    }

    /**
     * Returns a missing texture sprite.
     *
     * @return A sprite with a "missing texture" texture.
     */
    public static Sprite getDefaultSprite() {
        return defaultSprite;
    }

    /**
     * Returns the sprite of the tile specified by the tile set, tileSize and position of the tile within the tilseSet.
     *
     * @param tileSet  The tile set to be read from.
     * @param tileSize The size of 1 tile within the tile set.
     * @param x        The x position of the tile within the tile set. 0 is left most tile.
     * @param y        The y position of the tile within the tile set. 0 is uppermost tile.
     * @return The sprite of the tile specified by the tile set, tileSize and position of the tile within the tileset.
     */
    public static Sprite getTile(Texture tileSet, int tileSize, int x, int y) {
        return new Sprite(tileSet, x * tileSize, y * tileSize, tileSize, tileSize);
    }
}
