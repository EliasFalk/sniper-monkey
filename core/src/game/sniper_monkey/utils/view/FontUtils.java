package game.sniper_monkey.utils.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;

/**
 * A utility class which returns different label styles with different parameters.
 *
 * @author Elias Falk
 */
public class FontUtils {
    private static final FileHandle roboto = Gdx.files.internal("fonts/Roboto/Roboto-Regular.ttf");

    private FontUtils() {
    }

    /**
     * Returns the label style of a roboto font with a white inner color and black outline.
     *
     * @param fontSize    The size of the font.
     * @param borderWidth The borderWidth of the font.
     * @return A label style of a roboto font with a white inner color and black outline.
     */
    public static LabelStyle robotoWhite(int fontSize, int borderWidth) {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(roboto);
        FreeTypeFontParameter parameter = new FreeTypeFontParameter();
        parameter.size = fontSize;
        parameter.borderWidth = borderWidth;
        parameter.color = Color.WHITE;
        parameter.borderColor = Color.BLACK;
        BitmapFont font = generator.generateFont(parameter);
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = font;
        generator.dispose();
        return labelStyle;
    }

    /**
     * Returns the label style of a roboto font with a white inner color and black outline and a predetermined border width of 3.
     *
     * @param fontSize The size of the font.
     * @return A label style of a roboto font with a white inner color and black outline.
     */
    public static LabelStyle robotoWhite(int fontSize) {
        return robotoWhite(fontSize, 3);
    }
}
