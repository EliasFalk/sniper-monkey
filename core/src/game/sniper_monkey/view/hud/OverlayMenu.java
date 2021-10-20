package game.sniper_monkey.view.hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import game.sniper_monkey.utils.view.FontUtils;

import java.util.ArrayList;
import java.util.List;

public class OverlayMenu implements HUDView {
    private final float yStart = Gdx.graphics.getHeight() - 200f;
    private final float yButtonStart = yStart - 100f;
    private final float buttonHeight = 200f;
    private final float buttonWidth = 300f;
    private final float buttonMargin = 5f;
    private List<Button> buttons;
    private Label title;
    private Sprite darkBackground;

    public OverlayMenu(String titleText) {
        createTitleLabel(titleText);
        buttons = new ArrayList<>();
    }

    private void createTitleLabel(String titleText) {
        this.title = new Label(titleText, FontUtils.robotoWhite(50));
        this.title.setPosition(Gdx.graphics.getWidth() / 2f - this.title.getPrefWidth() / 2, yStart);
        this.title.setAlignment(Align.center);
    }

    private void transformButtons() {
        float buttonMargin = this.buttonMargin;
        for (Button button : buttons) {
            TextureAtlas textureAtlas = new TextureAtlas();
            button.setWidth(buttonWidth);
            button.setHeight(buttonHeight);
            button.setPosition(Gdx.graphics.getWidth() / 2f - button.getPrefWidth() / 2f, yButtonStart - buttonHeight - buttonMargin);
            buttonMargin += this.buttonMargin;
        }
    }

    public void addButton(Button button) {
        buttons.add(button);
        transformButtons();
    }

    @Override
    public void addActors(Stage stage) {
        stage.addActor(title);
        for (Button button : buttons) {
            stage.addActor(button);
        }
    }

    @Override
    public void removeActors() {
        title.remove();
        for (Button button : buttons) {
            button.remove();
        }
    }


}
