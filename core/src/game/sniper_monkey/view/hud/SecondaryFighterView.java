package game.sniper_monkey.view.hud;

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

/**
 * A view that represents the secondary fighter, including a corresponding image of the idle animation and the fighter's display name.
 */
public class SecondaryFighterView implements HUDView {
    private Image img;
    private float x;
    private float y;
    private Label fighterName;

    /**
     * Creates a view that represents the secondary fighter.
     *
     * @param textureRegion The texture region to be used to represent the fighter.
     * @param x             The x position of the texture region.
     * @param y             The y position of the texture region.
     * @param fighterName   The display name of the fighter.
     */
    public SecondaryFighterView(TextureRegion textureRegion, float x, float y, String fighterName) {
        this.img = new Image(textureRegion);
        img.setAlign(Align.center);
        img.setPosition(x, y);
        this.fighterName = new Label(fighterName, new Label.LabelStyle(new BitmapFont(), Color.BLACK));
        this.fighterName.setPosition(x + img.getWidth() / 2 - this.fighterName.getWidth() / 2, y - 15);
        this.fighterName.setAlignment(Align.center);
        this.x = x;
        this.y = y;
    }

    /**
     * Creates a secondary fighter view based on the fighter class.
     *
     * @param fighterClass The class of the fighter.
     * @param x            The x position of the view.
     * @param y            The y position of the view.
     */
    public SecondaryFighterView(Class<? extends Fighter> fighterClass, float x, float y) {
        this(HUDUtils.getCorrespondingTextureRegion(fighterClass), x, y, HUDUtils.getFighterDisplayName(fighterClass));
    }

    /**
     * Updates the fighter name and fighter image based on the fighterClass.
     *
     * @param fighterClass The fighter class to be updated with.
     */
    public void updateFighterView(Class<? extends Fighter> fighterClass) {
        updateImage(HUDUtils.getCorrespondingTextureRegion(fighterClass));
        updateFighterName(HUDUtils.getFighterDisplayName(fighterClass));
    }

    /**
     * Updates the secondary fighter view with a new image.
     *
     * @param textureRegion The texture region to be updated with.
     */
    public void updateImage(TextureRegion textureRegion) {
        TextureRegionDrawable trd = new TextureRegionDrawable(textureRegion);
        img.setDrawable(trd);
    }


    /**
     * Updates the name under the displayed fighter.
     *
     * @param name The name of the displayed fighter.
     */
    public void updateFighterName(String name) {
        fighterName.setText(name);
    }

    @Override
    public void addActors(Stage stage) {
        stage.addActor(img);
        stage.addActor(fighterName);
    }
}
