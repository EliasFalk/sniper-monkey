package game.sniper_monkey.view.characterSelection;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import game.sniper_monkey.model.player.fighter.Fighter;
import game.sniper_monkey.utils.view.HUDUtils;


/**
 * <p>
 *     Used by CharacterSelectionScreen
 * </p>
 * @author Kevin Jeryd
 */
public class SelectedFighterView  {

    private final Stage stage;
    private final Image player1SelectedImage;
    private final Image player2SelectedImage;
    private final Label player1PrimarySelectedLabel;
    private final Label player1SecondarySelectedLabel;
    private final Label player2PrimarySelectedLabel;
    private final Label player2SecondarySelectedLabel;
    private final Label errorLabel1;
    private final Label errorLabel2;

    /**
     * Creates the view for when a fighter has been chosen.
     * @param stage Is the stage that the view is supposed to draw to.
     */
    public SelectedFighterView(Stage stage) {
        this.stage = stage;
        this.player1SelectedImage = new Image();
        this.player2SelectedImage = new Image();
        this.player1PrimarySelectedLabel = new Label("", new Label.LabelStyle(new BitmapFont(), Color.BLACK));
        this.player1SecondarySelectedLabel = new Label("", new Label.LabelStyle(new BitmapFont(), Color.BLACK));
        this.player2PrimarySelectedLabel = new Label("", new Label.LabelStyle(new BitmapFont(), Color.BLACK));
        this.player2SecondarySelectedLabel = new Label("", new Label.LabelStyle(new BitmapFont(), Color.BLACK));
        this.errorLabel1 = new Label("Player1 need to select primary first!", new Label.LabelStyle(new BitmapFont(), Color.RED));
        this.errorLabel2 = new Label("Player2 need to select primary first!", new Label.LabelStyle(new BitmapFont(), Color.RED));
    }

    /**
     * Is a helper method for creating the image of the chosen fighter.
     * @param fighter Is the chosen fighter.
     * @param playerSelectedImage Is the image you want to set.
     * @param xPos Is the x position of the image.
     * @param yPos Is the y position of the image.
     */
    private void createSelectedImage(Fighter fighter, Image playerSelectedImage, float xPos, float yPos) {
        TextureRegion textureRegion = HUDUtils.getCorrespondingTextureRegion(fighter.getClass());
        textureRegion.flip(true, false);
        float textureRegionHeight = textureRegion.getRegionHeight();
        float textureRegionWidth = textureRegion.getRegionWidth();
        TextureRegionDrawable fighterDrawable = new TextureRegionDrawable(textureRegion);
        playerSelectedImage.setDrawable(fighterDrawable);
        playerSelectedImage.setScale(2);
        playerSelectedImage.setPosition(xPos, yPos);
        playerSelectedImage.setWidth(textureRegionWidth);
        playerSelectedImage.setHeight(textureRegionHeight);
    }

    public void errorChoosePrimaryFirst(int player) {
        if (player == 1) {
            errorLabel1.setPosition((Gdx.graphics.getWidth()*2)/10f, (Gdx.graphics.getHeight()*8.8f)/10f);
            stage.addActor(errorLabel1);
        } else {
            errorLabel2.setPosition((Gdx.graphics.getWidth()*6)/10f, (Gdx.graphics.getHeight()*8.8f)/10f);
            stage.addActor(errorLabel2);
        }
    }

    /**
     * A helper method that sets the label for the chosen fighter.
     * @param label Is the label you want to set.
     * @param selectedText Is the text you want the label to have.
     * @param fighter Is the fighter that has been chosen.
     * @param xPos Is the position in x direction.
     * @param yPos Is the position of the label in y direction.
     */
    private void setChosenFighterLabel(Label label, String selectedText, Fighter fighter, float xPos, float yPos) {
        label.setText(selectedText + HUDUtils.getFighterDisplayName(fighter.getClass()));
        label.setPosition(xPos, yPos);
    }

    //Can refactor these drawPlayer to one method that takes in the parameters fighter, selectedImage, imageWidth, imageHeight, selectedLabel, selectedLabelText, xPosLabel, yPosLabel
    //Or if drawSecondary take the same method but with different parameters
    /**
     * Draws the selected fighter sprite and the fighters name on the screen when chosen
     * @param fighter The fighter to draw
     */
    public void drawPlayer1PrimaryFighter(Fighter fighter) {
        createSelectedImage(fighter, player1SelectedImage, (Gdx.graphics.getWidth()*2)/10f, (Gdx.graphics.getHeight()*6)/10f);
        setChosenFighterLabel(player1PrimarySelectedLabel, "Player 1 Primary: ", fighter, (Gdx.graphics.getWidth()*2)/10f, (Gdx.graphics.getHeight()*9.7f)/10f);
        errorLabel1.setText("");
        stage.addActor(player1SelectedImage);
        stage.addActor(player1PrimarySelectedLabel);
    }

    /**
     * Draws the selected fighter sprite and the fighters name on the screen when chosen
     * @param fighter The fighter to draw
     */
    public void drawPlayer1SecondaryFighter(Fighter fighter) {
        setChosenFighterLabel(player1SecondarySelectedLabel, "Player 1 Secondary: ", fighter,(Gdx.graphics.getWidth()*2)/10f, (Gdx.graphics.getHeight()*9.3f)/10f);
        stage.addActor(player1SecondarySelectedLabel);
    }

    /**
     * Draws the selected fighter sprite and the fighters name on the screen when chosen
     * @param fighter The fighter to draw
     */
    public void drawPlayer2PrimaryFighter(Fighter fighter) {
        createSelectedImage(fighter, player2SelectedImage, (Gdx.graphics.getWidth()*6)/10f, (Gdx.graphics.getHeight()*6)/10f);
        setChosenFighterLabel(player2PrimarySelectedLabel, "Player 2 Primary: ", fighter, (Gdx.graphics.getWidth()*6)/10f, (Gdx.graphics.getHeight()*9.7f)/10f);
        errorLabel2.setText("");
        stage.addActor(player2SelectedImage);
        stage.addActor(player2PrimarySelectedLabel);
    }

    /**
     * Draws the selected fighter sprite and the fighters name on the screen when chosen
     * @param fighter The fighter to draw
     */
    public void drawPlayer2SecondaryFighter(Fighter fighter) {
        setChosenFighterLabel(player2SecondarySelectedLabel, "Player 2 Secondary: ", fighter, (Gdx.graphics.getWidth()*6)/10f, (Gdx.graphics.getHeight()*9.3f)/10f);
        stage.addActor(player2SecondarySelectedLabel);
    }

}
