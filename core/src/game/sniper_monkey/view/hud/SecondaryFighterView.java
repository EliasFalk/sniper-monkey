package game.sniper_monkey.view.hud;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;

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

    @Override
    public void addActors(Stage stage) {
        stage.addActor(img);
        stage.addActor(fighterName);
    }
}
