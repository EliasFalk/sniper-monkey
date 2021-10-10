package game.sniper_monkey.view.hud;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Bar extends Actor {

    private final ShapeRenderer shapeRenderer;

    private final float x;
    private final float y;
    private final float width;
    private final float height;
    private static final float borderThickness = 6;
    private float fraction = 1;
    private Color color;
    private FillDirection fillDir;

    public Bar(float x, float y, float width, float height, Color color, FillDirection fillDir) {
        shapeRenderer = new ShapeRenderer();
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
        this.fillDir = fillDir;
    }

    @Override
    public void draw(Batch batch, float alpha) {
        batch.end();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.DARK_GRAY);
        shapeRenderer.rect(x - borderThickness / 2, y - borderThickness / 2, width + borderThickness, height + borderThickness);
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