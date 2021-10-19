package game.sniper_monkey.view.hud;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import game.sniper_monkey.model.player.FluctuatingAttributeObserver;
import game.sniper_monkey.utils.view.FontUtils;

import java.text.DecimalFormat;


/**
 * A view that represents a fillable bar and a side text related to the fillable bar.
 *
 * @author Elias Falk
 * @author Vincent Hellner
 */
public class BarView implements FluctuatingAttributeObserver, HUDView {
    private Label barLabel;
    private FillableBar fillableBar;
    private float currentValue = 0;
    private final float sideTextMargin = 10f;

    /**
     * Creates a new bar view with a fillable bar and a side text representing the percentage filled.
     * The side text will always be positioned in the middle of the height of the bar.
     * The side text has a decimal format of XX.X. For example 33.3. Which represents that the bar is 33.3% filled.
     *
     * @param x             The x position of the fillable bar.
     * @param y             The y position of the fillable bar.
     * @param width         The width of the fillable bar.
     * @param height        The height of the fillable bar.
     * @param color         The color of the fillable bar.
     * @param fillDir       The direction of which the fillable bar will be filled.
     * @param textAlignment The alignment of the side text. Currently only accepts left, right or center.
     */
    public BarView(float x, float y, float width, float height, Color color, FillDirection fillDir, int textAlignment) {
        createBarLabel(x, y, width, height, textAlignment);
        fillableBar = new FillableBar(x, y, width, height, color, fillDir);
    }

    private void createBarLabel(float x, float y, float barWidth, float barHeight, int textAlignment) {
        barLabel = new Label(String.format(new DecimalFormat("#.##").format(100), 0f), FontUtils.robotoWhite(14, 2));
        barLabel.setFontScale(1, 1);
        float yLabelPos = y + barHeight / 2 - barLabel.getHeight() / 2;
        float xLabelPos = x;
        if (textAlignment == Align.left) {
            xLabelPos = x - sideTextMargin - barLabel.getWidth();
        } else if (textAlignment == Align.center) {
            xLabelPos = x + barWidth / 2 - barLabel.getWidth() / 2;
        } else if (textAlignment == Align.right) {
            xLabelPos = x + sideTextMargin + barWidth;
        }

        barLabel.setPosition(xLabelPos, yLabelPos);
        barLabel.setAlignment(Align.center);
    }

    /**
     * Creates a new bar view with a fillable bar and a side text representing the percentage filled.
     * The width is 150, height 20 and text alignment center.
     *
     * @param x       The x position of the fillable bar.
     * @param y       The y position of the fillable bar.
     * @param color   The color of the fillable bar.
     * @param fillDir The direction of which the fillable bar will be filled.
     */
    public BarView(float x, float y, Color color, FillDirection fillDir) {
        this(x, y, 150, 20, color, fillDir, Align.center);
    }

    /**
     * Creates a new bar view with a fillable bar and a side text representing the percentage filled. The width is 150, height 20, text alignment center and fill direction right.
     *
     * @param x     The x position of the fillable bar.
     * @param y     The y position of the fillable bar.
     * @param color The color of the fillable bar.
     */
    public BarView(float x, float y, Color color) {
        this(x, y, color, FillDirection.RIGHT);
    }

    @Override
    public void addActors(Stage stage) {
        stage.addActor(fillableBar);
        stage.addActor(barLabel);
    }

    @Override
    public void onPercentageChange(float newFraction) {
        currentValue = newFraction;
        barLabel.setText(String.format(new DecimalFormat("#.#").format(currentValue * 100), currentValue));
        fillableBar.update(newFraction);
    }
}
