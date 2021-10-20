package game.sniper_monkey.view.hud;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import game.sniper_monkey.model.player.fighter.Fighter;
import game.sniper_monkey.utils.view.FontUtils;
import game.sniper_monkey.utils.view.HUDUtils;

/**
 * A view that represents the secondary fighter, including a corresponding image of the idle animation and the fighter's display name.
 *
 * @author Elias
 */
public class SecondaryFighterView implements HUDView {
    private final Image img;
    private final float x;
    private final float y;
    private final Label fighterName;
    private final float yTextOffset = -5f;
    private final boolean flipX;

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
        this.flipX = flipX;
        this.img = createFighterImage(textureRegion);
        updateImage(textureRegion, x, y, flipX);
        this.fighterName = createFighterNameLabel(x, y, fighterName);
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

    private Image createFighterImage(TextureRegion textureRegion) {
        return new Image(textureRegion);
    }

    private Label createFighterNameLabel(float x, float y, String fighterName) {
        Label label = new Label(fighterName, FontUtils.robotoWhite(16));
        label.setPosition(x + img.getPrefWidth() / 2 - label.getPrefWidth() / 2, y + yTextOffset - label.getPrefHeight());
        return label;
    }

    /**
     * Updates the fighter name and fighter image based on the fighterClass.
     *
     * @param fighterClass The fighter class to be updated with.
     */
    public void updateFighterView(Class<? extends Fighter> fighterClass) {
        updateImage(HUDUtils.getCorrespondingTextureRegion(fighterClass), x, y, flipX);
        updateFighterName(HUDUtils.getFighterDisplayName(fighterClass));
    }

    /**
     * Updates the secondary fighter view with a new image.
     *
     * @param textureRegion The texture region to be updated with.
     */
    public void updateImage(TextureRegion textureRegion, float x, float y, boolean flipX) {
        textureRegion.flip(flipX, false);
        TextureRegionDrawable trd = new TextureRegionDrawable(textureRegion);
        float h = textureRegion.getRegionHeight();
        float w = textureRegion.getRegionWidth();
        img.setDrawable(trd);
        img.setWidth(w);
        img.setHeight(h);
        img.setPosition(x, y);
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

    @Override
    public void removeActors() {
        img.remove();
        fighterName.remove();
    }
}
