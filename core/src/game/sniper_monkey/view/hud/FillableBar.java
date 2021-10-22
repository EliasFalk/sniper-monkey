package game.sniper_monkey.view.hud;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * A bar that can be "filled" meaning a bar that can represent a fluctuating value.
 * <p>
 * Uses FillDirection.
 * <p>
 * Used by BarView.
 * Used by KeyInputView.
 *
 * @author Elias Falk
 * @author Vincent Hellner
 */
public class FillableBar extends Actor {

    private final ShapeRenderer shapeRenderer;

    private final float x;
    private final float y;
    private final float width;
    private final float height;
    private static final float borderThickness = 6;
    private float fraction = 1;
    private Color color;
    private FillDirection fillDir;

    /**
     * Creates a fillable bar. Is "filled" by default.
     *
     * @param x       The x position of the bar. Defined in shape render's coordinate system.
     * @param y       The y position of the bar. Defined in shape render's coordinate system.
     * @param width   The width of the bar including the border. Defined in shape render's coordinate system.
     * @param height  The height of the bar including the border. Defined in shape render's coordinate system.
     * @param color   The color of the part that fluctuates.
     * @param fillDir The direction of which the bar will be filled.
     */
    public FillableBar(float x, float y, float width, float height, Color color, FillDirection fillDir) {
        shapeRenderer = new ShapeRenderer();
        this.x = x;
        this.y = y;
        this.width = width - borderThickness;
        this.height = height - borderThickness;
        this.color = color;
        this.fillDir = fillDir;
    }

    @Override
    public void draw(Batch batch, float alpha) {
        batch.end();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        drawBorder(Color.BLACK);
        drawInnerBar(Color.WHITE);
        drawChangingBar();
        shapeRenderer.end();
        batch.begin();
    }

    private void drawChangingBar() {
        float drawOffset = borderThickness / 2;
        shapeRenderer.setColor(color);
        if (fillDir == FillDirection.RIGHT) {
            shapeRenderer.rect(x + drawOffset, y + drawOffset, width * fraction, height);
        } else if (fillDir == FillDirection.LEFT) {
            shapeRenderer.rect(x + drawOffset + width * (1 - fraction), y + drawOffset, width * fraction, height);
        } else if (fillDir == FillDirection.UP) {
            shapeRenderer.rect(x + drawOffset, y + drawOffset, width, height * fraction);
        } else if (fillDir == FillDirection.DOWN) {
            shapeRenderer.rect(x + drawOffset, y + drawOffset + height * (1 - fraction), width, height * fraction);
        }
    }

    private void drawInnerBar(Color color) {
        float drawOffset = borderThickness / 2;
        shapeRenderer.setColor(color);
        shapeRenderer.rect(x + drawOffset, y + drawOffset, width, height);
    }

    private void drawBorder(Color color) {
        shapeRenderer.setColor(color);
        shapeRenderer.rect(x, y, width + borderThickness, height + borderThickness);
    }

    /**
     * Updates the fillable bar with a new fraction. The fraction determines how much of the bar is filled.
     *
     * @param fraction The new fraction of how much the bar will be filled.
     */
    public void update(float fraction) {
        this.fraction = fraction;
    }
}