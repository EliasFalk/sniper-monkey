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

/**
 *
 * @author Kevin Jeryd
 */
public class SelectViewRectangle extends Actor implements ICharacterSelectedObserver {

    private final ShapeRenderer shapeRenderer;

    private Image img;
    private Image player1PreviewImage;
    private Image player2PreviewImage;
    private final float x;
    private final float y;
    private final float width;
    private final float height;
    private static final float borderThickness = 6;
    private final Color color;
    private boolean player1Select;
    private boolean player2Select;
    private final Label player1HoverFighterLabel;
    private final Label player2HoverFighterLabel;
    private final Stage stage;
    private Color player1OuterRectangleColor;
    private Color player2OuterRectangleColor;
    private float player1PreviewImageScaling = 2;
    private float player2PreviewImageScaling = 2;

    public SelectViewRectangle(Class<? extends Fighter> fighter, float x, float y, float width, float height, Color color, Stage stage) {
        shapeRenderer = new ShapeRenderer();
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
        addLabel();
        this.stage = stage;
        player1HoverFighterLabel = new Label(HUDUtils.getFighterDisplayName(fighter), new Label.LabelStyle(new BitmapFont(), Color.BLACK));
        player1HoverFighterLabel.setPosition((Gdx.graphics.getWidth()*2)/10f, (Gdx.graphics.getHeight()*5.5f)/10f);
        player2HoverFighterLabel = new Label(HUDUtils.getFighterDisplayName(fighter), new Label.LabelStyle(new BitmapFont(), Color.BLACK));
        player2HoverFighterLabel.setPosition((Gdx.graphics.getWidth()*6)/10f, (Gdx.graphics.getHeight()*5.5f)/10f);
        this.img = createFighterImage(HUDUtils.getCorrespondingTextureRegion(fighter), x, y, true, img);
        this.player1PreviewImage = createFighterImage(HUDUtils.getCorrespondingTextureRegion(fighter), (Gdx.graphics.getWidth()*2)/10f, (Gdx.graphics.getHeight()*6)/10f, true, player1PreviewImage);
        this.player2PreviewImage = createFighterImage(HUDUtils.getCorrespondingTextureRegion(fighter), (Gdx.graphics.getWidth()*6)/10f, (Gdx.graphics.getHeight()*6)/10f, true, player2PreviewImage);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.end();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        //Where both players are on the same rectangle
        if (player1Select && player2Select) {
            drawDoubleOuterRectangle();
            drawPlayer1PreviewAnimation();
            drawPlayer1HoverLabel();
            drawPlayer2PreviewAnimation();
            drawPlayer2HoverLabel();
        } else {
            if (player1Select) {
                drawPlayer1OuterRectangle();
                drawPlayer1PreviewAnimation();
                drawPlayer1HoverLabel();
            } else {
                removePlayer1PreviewAnimation();
                removePlayer1HoverLabel();
            }

            if (player2Select) {
                drawPlayer2OuterRectangle();
                drawPlayer2PreviewAnimation();
                drawPlayer2HoverLabel();
            } else {
                removePlayer2PreviewAnimation();
                removePlayer2HoverLabel();
            }

            //Where none of the players are on the rectangle just add blue to the outer rectangle
            if (!player1Select && !player2Select) {
                removeOuterRectangle(color);
            }
        }
        drawRectangle(color);
        shapeRenderer.end();
        batch.begin();
    }

    private void drawPlayer1HoverLabel() {
        stage.addActor(player1HoverFighterLabel);
    }

    private void drawPlayer2HoverLabel() {
        stage.addActor(player2HoverFighterLabel);
    }

    private void removePlayer1HoverLabel() {
        player1HoverFighterLabel.remove();
    }

    private void removePlayer2HoverLabel() {
        player2HoverFighterLabel.remove();
    }

    private void drawDoubleOuterRectangle() {
        shapeRenderer.setColor(player1OuterRectangleColor);
        shapeRenderer.rect(x, y, width+borderThickness, height+borderThickness);
        shapeRenderer.end();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(player2OuterRectangleColor);
        shapeRenderer.rect(x, y, width-borderThickness, height-borderThickness);
    }

    private Image createFighterImage(TextureRegion textureRegion, float x, float y, boolean flipX, Image img) {
        textureRegion.flip(flipX, false);
        img = new Image(textureRegion);
        img.setAlign(Align.center);
        img.setPosition(x, y);
        return img;
    }

    private void drawPlayer1PreviewAnimation() {
        player1PreviewImage.setScale(player1PreviewImageScaling);
        stage.addActor(player1PreviewImage);
    }

    private void drawPlayer2PreviewAnimation() {
        player2PreviewImage.setScale(player2PreviewImageScaling);
        stage.addActor(player2PreviewImage);
    }

    private void removePlayer1PreviewAnimation() {
        player1PreviewImage.remove();
    }

    private void removePlayer2PreviewAnimation() {
        player2PreviewImage.remove();
    }

    public void addLabel() {
        //stage.addActor(fighterLabel);
    }

    private void drawRectangle(Color color) {
        shapeRenderer.setColor(color);
        stage.addActor(img);
        shapeRenderer.rect(x+borderThickness , y+borderThickness, width-borderThickness, height-borderThickness);
    }

    private void drawPlayer1OuterRectangle() {
        shapeRenderer.setColor(player1OuterRectangleColor);
        shapeRenderer.rect(x, y, width+borderThickness, height+borderThickness);
    }

    private void drawPlayer2OuterRectangle() {
        shapeRenderer.setColor(player2OuterRectangleColor);
        shapeRenderer.rect(x, y, width+borderThickness, height+borderThickness);
    }

    private void removeOuterRectangle(Color color) {
        shapeRenderer.setColor(color);
        shapeRenderer.rect(x, y, width+borderThickness, height+borderThickness);
    }

    public void setPlayer1Selected(boolean bool) {
        this.player1Select = bool;
        if (player1Select) {
            this.player1OuterRectangleColor = Color.ORANGE;
        } else {
            this.player1OuterRectangleColor = Color.BLUE;
        }
    }

    public void setPlayer2Selected(boolean bool) {
        this.player2Select = bool;
        if (player2Select) {
            this.player2OuterRectangleColor = Color.GREEN;
        } else {
            this.player2OuterRectangleColor = Color.BLUE;
        }
    }


    @Override
    public void onPlayer1CharacterSelected() {
        player1PreviewImageScaling = 1.2f;
        player1PreviewImage.setPosition((Gdx.graphics.getWidth()*1.5f)/10f, (Gdx.graphics.getHeight()*6)/10f);
    }

    @Override
    public void onPlayer2CharacterSelected() {
        player2PreviewImageScaling = 1.2f;
        player2PreviewImage.setPosition((Gdx.graphics.getWidth()*5.5f)/10f, (Gdx.graphics.getHeight()*6)/10f);
    }
}
