package game.sniper_monkey.view.hud;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * A bar that can be "filled" meaning a bar that can represent a fluctuating value.
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
        this.width = width - borderThickness / 2;
        this.height = height - borderThickness / 2;
        this.color = color;
        this.fillDir = fillDir;
    }

    @Override
    public void draw(Batch batch, float alpha) {
        batch.end();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.DARK_GRAY);
        shapeRenderer.rect(x - borderThickness / 2, y - borderThickness / 2, width + borderThickness, height + borderThickness);
        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.rect(x, y, width, height);

        shapeRenderer.setColor(color);
        if (fillDir == FillDirection.RIGHT) {
            shapeRenderer.rect(x, y, width * fraction, height);
        } else if (fillDir == FillDirection.LEFT) {
            shapeRenderer.rect(x + width * (1 - fraction), y, width * fraction, height);
        } else if (fillDir == FillDirection.UP) {
            shapeRenderer.rect(x, y, width, height * fraction);
        } else if (fillDir == FillDirection.DOWN) {
            shapeRenderer.rect(x, y + height * (1 - fraction), width, height * fraction);
        }
        shapeRenderer.end();
        batch.begin();
    }

    public void update(float fraction) {
        this.fraction = fraction;
    }
}