package game.sniper_monkey.utils.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;

public class FontUtils {

    public static LabelStyle getNormalFont(int fontSize, int borderWidth) {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Roboto/Roboto-Regular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
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

    public static LabelStyle getNormalFont(int fontSize) {
        return getNormalFont(fontSize, 3);
    }
}
