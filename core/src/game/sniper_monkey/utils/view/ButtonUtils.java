package game.sniper_monkey.utils.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

/**
 * A utility class to get button styles.
 * <p>
 * Used by GameController.
 *
 * @author Elias Falk
 */
public final class ButtonUtils {
    private final static Skin expeeSkin = new Skin();
    private static final BitmapFont expeeFont = new BitmapFont(Gdx.files.local("skins/expee/font-export.fnt"));

    static {
        TextureAtlas expeeAtlas = new TextureAtlas(Gdx.files.local("skins/expee/expee-ui.atlas"));
        expeeSkin.addRegions(expeeAtlas);
    }

    private ButtonUtils() {

    }

    /**
     * Returns a text button style with a default bitmap font.
     *
     * @return a text button style with a default bitmap font.
     */
    public static TextButton.TextButtonStyle getDefaultButtonFont() {
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = new BitmapFont();
        return textButtonStyle;
    }

    public static TextButton.TextButtonStyle getExpeeButtonStyle() {
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = expeeSkin.getDrawable("button");
        textButtonStyle.fontColor = Color.BLACK;
        textButtonStyle.down = expeeSkin.getDrawable("button-pressed");
        textButtonStyle.font = expeeFont;
        return textButtonStyle;
    }
}
