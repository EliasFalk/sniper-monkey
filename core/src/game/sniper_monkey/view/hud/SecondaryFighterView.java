package game.sniper_monkey.view.hud;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import game.sniper_monkey.model.player.fighter.Fighter;
import game.sniper_monkey.utils.view.FontUtils;
import game.sniper_monkey.utils.view.HUDUtils;

/**
 * A view that represents the secondary fighter, including a corresponding image of the idle animation and the fighter's display name.
 */
public class SecondaryFighterView implements HUDView {
    private Image img;
    private final float x;
    private final float y;
    private Label fighterName;
    private final float yTextOffset = -5f;

    /**
     * Creates a view that represents the secondary fighter.
     *
     * @param textureRegion The texture region to be used to represent the fighter.
     * @param x             The x position of the texture region.
     * @param y             The y position of the texture region.
     * @param fighterName   The display name of the fighter.
     * @param flipX         Whether to flip the image horizontally.
     */
    public SecondaryFighterView(TextureRegion textureRegion, float x, float y, String fighterName, boolean flipX) {
        this.x = x;
        this.y = y;
        createFighterImage(textureRegion, x, y, flipX);
        createFighterNameLabel(x, y, fighterName);
    }

    /**
     * Creates a view that represents the secondary fighter.
     *
     * @param textureRegion The texture region to be used to represent the fighter.
     * @param x             The x position of the texture region.
     * @param y             The y position of the texture region.
     * @param fighterName   The display name of the fighter.
     */
    public SecondaryFighterView(TextureRegion textureRegion, float x, float y, String fighterName) {
        this(textureRegion, x, y, fighterName, false);
    }

    /**
     * Creates a secondary fighter view based on the fighter class.
     *
     * @param fighterClass The class of the fighter.
     * @param x            The x position of the view.
     * @param y            The y position of the view.
     * @param flipX        Whether to flip the image horizontally.
     */
    public SecondaryFighterView(Class<? extends Fighter> fighterClass, float x, float y, boolean flipX) {
        this(HUDUtils.getCorrespondingTextureRegion(fighterClass), x, y, HUDUtils.getFighterDisplayName(fighterClass), flipX);
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

    private void createFighterImage(TextureRegion textureRegion, float x, float y, boolean flipX) {
        textureRegion.flip(flipX, false);
        this.img = new Image(textureRegion);
        img.setAlign(Align.center);
        img.setPosition(x, y);
    }

    private void createFighterNameLabel(float x, float y, String fighterName) {
        this.fighterName = new Label(fighterName, FontUtils.getNormalFont(16));
        this.fighterName.setPosition(x + img.getPrefWidth() / 2 - this.fighterName.getPrefWidth() / 2, y + yTextOffset - this.fighterName.getPrefHeight());
        this.fighterName.setAlignment(Align.center);
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
        float h = textureRegion.getRegionHeight();
        float w = textureRegion.getRegionWidth();
        img.setDrawable(trd);
        img.setWidth(w);
        img.setHeight(h);
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
