package game.sniper_monkey.view.hud;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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

    public Bar(float x, float y, float width, float height, Color color) {
        shapeRenderer = new ShapeRenderer();
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
    }

    @Override
    public void draw(Batch batch, float alpha){
        batch.end();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.BLACK);
        shapeRenderer.rect(x - borderThickness/2, y - borderThickness/2, width + borderThickness, height + borderThickness);
        shapeRenderer.setColor(color);
        shapeRenderer.rect(x, y, width*fraction, height);
        shapeRenderer.end();
        batch.begin();
    }

    public void update(float fraction) {
        this.fraction = fraction;
    }
}