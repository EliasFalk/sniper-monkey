package game.sniper_monkey.view.characterSelection;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import game.sniper_monkey.model.player.fighter.Fighter;
import game.sniper_monkey.utils.view.HUDUtils;

public class SelectViewRectangle extends Actor {

    private final ShapeRenderer shapeRenderer;

    private Image img;
    private final float x;
    private final float y;
    private final float width;
    private final float height;
    private static final float borderThickness = 6;
    private Color color;
    private boolean selected;
    private final Label fighterLabel;
    private final Class<? extends Fighter> fighter;
    private final Stage stage;

    public SelectViewRectangle(Class<? extends Fighter> fighter, float x, float y, float width, float height, Color color, Stage stage) {
        shapeRenderer = new ShapeRenderer();
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
        addLabel();
        this.fighter = fighter;
        this.stage = stage;
        fighterLabel = new Label(HUDUtils.getFighterDisplayName(fighter), new Label.LabelStyle(new BitmapFont(), Color.BLACK));
        createFighterImage(HUDUtils.getCorrespondingTextureRegion(fighter), x, y, true);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.end();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        if (selected) {
            drawOuterRectangle();
        } else {
            removeOuterRectangle(color);
            drawPreviewAnimation();
        }
        drawRectangle(color);
        shapeRenderer.end();
        batch.begin();
    }

    private void createFighterImage(TextureRegion textureRegion, float x, float y, boolean flipX) {
        textureRegion.flip(flipX, false);
        this.img = new Image(textureRegion);
        img.setAlign(Align.center);
        img.setPosition(x, y);
    }

    private void drawPreviewAnimation() {

    }

    public void addLabel() {
        //stage.addActor(fighterLabel);
    }

    private void drawRectangle(Color color) {
        shapeRenderer.setColor(color);
        stage.addActor(img);
        shapeRenderer.rect(x+borderThickness , y+borderThickness, width-borderThickness, height-borderThickness);
    }

    private void drawOuterRectangle() {
        shapeRenderer.setColor(Color.ORANGE);
        shapeRenderer.rect(x, y, width+borderThickness, height+borderThickness);
    }

    private void removeOuterRectangle(Color color) {
        shapeRenderer.setColor(color);
        shapeRenderer.rect(x, y, width+borderThickness, height+borderThickness);
    }

    public void setSelected(boolean bool) {
        this.selected = bool;
    }

}
