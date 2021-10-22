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

public class SelectedFighterView  {

    private final Stage stage;
    private final Image player1SelectedImage;
    private final Image player2SelectedImage;
    private final Label player1PrimarySelectedLabel;
    private final Label player1SecondarySelectedLabel;
    private final Label player2PrimarySelectedLabel;
    private final Label player2SecondarySelectedLabel;

    public SelectedFighterView(Stage stage) {
        this.stage = stage;
        this.player1SelectedImage = new Image();
        this.player2SelectedImage = new Image();
        this.player1PrimarySelectedLabel = new Label("", new Label.LabelStyle(new BitmapFont(), Color.BLACK));
        this.player1SecondarySelectedLabel = new Label("", new Label.LabelStyle(new BitmapFont(), Color.BLACK));
        this.player2PrimarySelectedLabel = new Label("", new Label.LabelStyle(new BitmapFont(), Color.BLACK));
        this.player2SecondarySelectedLabel = new Label("", new Label.LabelStyle(new BitmapFont(), Color.BLACK));
    }

    private void createSelectedImage(Fighter fighter, Image playerSelectedImage, float width, float height) {
        TextureRegion textureRegion = HUDUtils.getCorrespondingTextureRegion(fighter.getClass());
        textureRegion.flip(true, false);
        float textureRegionHeight = textureRegion.getRegionHeight();
        float textureRegionWidth = textureRegion.getRegionWidth();
        TextureRegionDrawable fighterDrawable = new TextureRegionDrawable(textureRegion);
        playerSelectedImage.setDrawable(fighterDrawable);
        playerSelectedImage.setScale(2);
        playerSelectedImage.setPosition(width, height);
        playerSelectedImage.setWidth(textureRegionWidth);
        playerSelectedImage.setHeight(textureRegionHeight);
    }

    private void setChosenFighterLabel(Label label, String selectedText, Fighter fighter, float xPos, float yPos) {
        label.setText(selectedText + HUDUtils.getFighterDisplayName(fighter.getClass()));
        label.setPosition(xPos, yPos);
    }

    //Make so you can pick secondary fighter first
    public void drawPlayer1PrimaryFighter(Fighter fighter) {
        createSelectedImage(fighter, player1SelectedImage, (Gdx.graphics.getWidth()*2)/10f, (Gdx.graphics.getHeight()*6)/10f);
        setChosenFighterLabel(player1PrimarySelectedLabel, "Player 1 Primary: ", fighter, (Gdx.graphics.getWidth()*2)/10f, (Gdx.graphics.getHeight()*9.7f)/10f);
        stage.addActor(player1SelectedImage);
        stage.addActor(player1PrimarySelectedLabel);
    }

    public void drawPlayer1SecondaryFighter(Fighter fighter) {
        setChosenFighterLabel(player1SecondarySelectedLabel, "Player 1 Secondary: ", fighter,(Gdx.graphics.getWidth()*2)/10f, (Gdx.graphics.getHeight()*9.3f)/10f);
        stage.addActor(player1SecondarySelectedLabel);
    }

    public void drawPlayer2PrimaryFighter(Fighter fighter) {
        createSelectedImage(fighter, player2SelectedImage, (Gdx.graphics.getWidth()*6)/10f, (Gdx.graphics.getHeight()*6)/10f);
        setChosenFighterLabel(player2PrimarySelectedLabel, "Player 2 Primary: ", fighter, (Gdx.graphics.getWidth()*6)/10f, (Gdx.graphics.getHeight()*9.7f)/10f);
        stage.addActor(player2SelectedImage);
        stage.addActor(player2PrimarySelectedLabel);
    }

    public void drawPlayer2SecondaryFighter(Fighter fighter) {
        setChosenFighterLabel(player2SecondarySelectedLabel, "Player 2 Secondary: ", fighter, (Gdx.graphics.getWidth()*6)/10f, (Gdx.graphics.getHeight()*9.3f)/10f);
        stage.addActor(player2SecondarySelectedLabel);
    }

}
