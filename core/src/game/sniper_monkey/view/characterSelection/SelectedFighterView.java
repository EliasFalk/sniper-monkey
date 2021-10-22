package game.sniper_monkey.view.characterSelection;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
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

    //Make so you can pick secondary fighter first
    public void drawPlayer1PrimaryFighter(Fighter fighter) {
        TextureRegion textureRegion = HUDUtils.getCorrespondingTextureRegion(fighter.getClass());
        textureRegion.flip(true, false);
        float textureRegionHeight = textureRegion.getRegionHeight();
        float textureRegionWidth = textureRegion.getRegionWidth();
        TextureRegionDrawable fighterDrawable = new TextureRegionDrawable(textureRegion);
        player1SelectedImage.setDrawable(fighterDrawable);
        player1SelectedImage.setScale(2);
        player1SelectedImage.setPosition((Gdx.graphics.getWidth()*2)/10f, (Gdx.graphics.getHeight()*6)/10f);
        player1SelectedImage.setWidth(textureRegionWidth);
        player1SelectedImage.setHeight(textureRegionHeight);

        player1PrimarySelectedLabel.setText("Player 1 Primary: " + HUDUtils.getFighterDisplayName(fighter.getClass()));
        player1PrimarySelectedLabel.setPosition((Gdx.graphics.getWidth()*2)/10f, (Gdx.graphics.getHeight()*9.7f)/10f);

        stage.addActor(player1SelectedImage);
        stage.addActor(player1PrimarySelectedLabel);
    }

    public void drawPlayer1SecondaryFighter(Fighter fighter) {
        player1SecondarySelectedLabel.setText("Player 1 Secondary: " + HUDUtils.getFighterDisplayName(fighter.getClass()));
        player1SecondarySelectedLabel.setPosition((Gdx.graphics.getWidth()*2)/10f, (Gdx.graphics.getHeight()*9.3f)/10f);

        stage.addActor(player1SecondarySelectedLabel);
    }

    public void drawPlayer2PrimaryFighter(Fighter fighter) {
        TextureRegion textureRegion = HUDUtils.getCorrespondingTextureRegion(fighter.getClass());
        textureRegion.flip(true, false);
        float textureRegionHeight = textureRegion.getRegionHeight();
        float textureRegionWidth = textureRegion.getRegionWidth();
        TextureRegionDrawable fighterDrawable = new TextureRegionDrawable(textureRegion);
        player2SelectedImage.setDrawable(fighterDrawable);
        player2SelectedImage.setScale(2);
        player2SelectedImage.setPosition((Gdx.graphics.getWidth()*6)/10f, (Gdx.graphics.getHeight()*6)/10f);
        player2SelectedImage.setWidth(textureRegionWidth);
        player2SelectedImage.setHeight(textureRegionHeight);

        player2PrimarySelectedLabel.setText("Player 2 Primary: " + HUDUtils.getFighterDisplayName(fighter.getClass()));
        player2PrimarySelectedLabel.setPosition((Gdx.graphics.getWidth()*6)/10f, (Gdx.graphics.getHeight()*9.7f)/10f);

        stage.addActor(player2SelectedImage);
        stage.addActor(player2PrimarySelectedLabel);
    }

    public void drawPlayer2SecondaryFighter(Fighter fighter) {
        player2SecondarySelectedLabel.setText("Player 2 Secondary: " + HUDUtils.getFighterDisplayName(fighter.getClass()));
        player2SecondarySelectedLabel.setPosition((Gdx.graphics.getWidth()*6)/10f, (Gdx.graphics.getHeight()*9.3f)/10f);

        stage.addActor(player2SecondarySelectedLabel);
    }

}
