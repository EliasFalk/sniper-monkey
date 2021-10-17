package game.sniper_monkey.view.characterSelection;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class SelectViewRectangle extends Actor {

    private final ShapeRenderer shapeRenderer;

    private final float x;
    private final float y;
    private final float width;
    private final float height;
    private static final float borderThickness = 6;
    private Color color;
    private boolean selected;
    private final Label fighterLabel;

    public SelectViewRectangle(Label fighterLabel, float x, float y, float width, float height, Color color) {
        shapeRenderer = new ShapeRenderer();
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
        this.fighterLabel = fighterLabel;
        addLabel();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.end();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        if (selected) {
            drawOuterRectangle();
        }
        drawRectangle(Color.BLUE);
        shapeRenderer.end();
        batch.begin();
    }

    public void addLabel() {
    }

    private void drawRectangle(Color color) {
        shapeRenderer.setColor(color);
        shapeRenderer.rect(x+borderThickness , y+borderThickness, width-borderThickness, height-borderThickness);
    }

    private void drawOuterRectangle() {
        shapeRenderer.setColor(Color.ORANGE);
        shapeRenderer.rect(x, y, width+borderThickness, height+borderThickness);
    }

    public void setSelected(boolean bool) {
        this.selected = bool;
    }

}
