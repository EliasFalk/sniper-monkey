package game.sniper_monkey.view.characterSelection;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import game.sniper_monkey.model.player.fighter.Fighter;
import game.sniper_monkey.utils.view.HUDUtils;

public class SelectedFighterView {

    private Stage stage;
    private Image player1SelectedImage;
    private Image player2SelectedImage;

    public SelectedFighterView(Stage stage) {
        this.stage = stage;
        this.player1SelectedImage = new Image();
        this.player2SelectedImage = new Image();
    }

    private Image createFighterImage(TextureRegion textureRegion, float x, float y, boolean flipX, Image img) {
        textureRegion.flip(flipX, false);
        img = new Image(textureRegion);
        img.setAlign(Align.center);
        img.setPosition(x, y);
        return img;
    }

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
        stage.addActor(player1SelectedImage);
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
        stage.addActor(player2SelectedImage);
    }

}
