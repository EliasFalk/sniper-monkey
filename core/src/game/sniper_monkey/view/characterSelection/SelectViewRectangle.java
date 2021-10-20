package game.sniper_monkey.view.characterSelection;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
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
    private Image previewImg;
    private Image player2PreviewImg;
    private final float x;
    private final float y;
    private final float width;
    private final float height;
    private static final float borderThickness = 6;
    private Color color;
    private boolean player1Select;
    private boolean player2Select;
    private final Label fighterLabel;
    private final Class<? extends Fighter> fighter;
    private final Stage stage;
    private Color outerRectangleColor;

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
        this.img = createFighterImage(HUDUtils.getCorrespondingTextureRegion(fighter), x, y, true, img);
        this.previewImg = createFighterImage(HUDUtils.getCorrespondingTextureRegion(fighter), (Gdx.graphics.getWidth()*2)/10f, (Gdx.graphics.getHeight()*6)/10f, true, previewImg);
        this.player2PreviewImg = createFighterImage(HUDUtils.getCorrespondingTextureRegion(fighter), (Gdx.graphics.getWidth()*10)/10f, (Gdx.graphics.getHeight()*6)/10f, true, player2PreviewImg);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.end();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        if (player1Select) {
            drawOuterRectangle();
            drawPreviewAnimation();
        } else {
            removePreviewAnimation();
        }

        if (player2Select) {
            drawOuterRectangle();
            drawPlayer2PreviewAnimation();
        } else {
            removePlayer2PreviewAnimation();
        }

        if (!player1Select && !player2Select) {
            removeOuterRectangle(color);
        }
        drawRectangle(color);
        shapeRenderer.end();
        batch.begin();
    }

    private Image createFighterImage(TextureRegion textureRegion, float x, float y, boolean flipX, Image img) {
        textureRegion.flip(flipX, false);
        img = new Image(textureRegion);
        img.setAlign(Align.center);
        img.setPosition(x, y);
        return img;
    }

    private void drawPreviewAnimation() {
        previewImg.setScale(2);
        stage.addActor(previewImg);
    }

    private void drawPlayer2PreviewAnimation() {
        player2PreviewImg.setScale(2);
        stage.addActor(player2PreviewImg);
    }

    private void removePreviewAnimation() {
        previewImg.remove();
    }

    private void removePlayer2PreviewAnimation() {
        player2PreviewImg.remove();
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
        shapeRenderer.setColor(outerRectangleColor);
        shapeRenderer.rect(x, y, width+borderThickness, height+borderThickness);
    }

    private void removeOuterRectangle(Color color) {
        shapeRenderer.setColor(color);
        shapeRenderer.rect(x, y, width+borderThickness, height+borderThickness);
    }

    public void setPlayer1Selected(boolean bool) {
        this.player1Select = bool;
        if (player1Select) {
            this.outerRectangleColor = Color.ORANGE;
        } else {
            this.outerRectangleColor = Color.BLUE;
        }
    }

    public void setPlayer2Selected(boolean bool) {
        this.player2Select = bool;
        if (player2Select) {
            this.outerRectangleColor = Color.GREEN;
        } else {
            this.outerRectangleColor = Color.BLUE;
        }
    }
}
