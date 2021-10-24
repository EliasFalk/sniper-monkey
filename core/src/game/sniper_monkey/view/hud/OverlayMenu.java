package game.sniper_monkey.view.hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import game.sniper_monkey.utils.view.ButtonUtils;
import game.sniper_monkey.utils.view.FontUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * A view for a general overlay that represents a menu with x amount of buttons and a title as well as a covering lightbox.
 * <p>
 * Uses HUDView.
 * <p>
 * Used by GameController.
 *
 * @author Elias Falk
 * @author Vincent Hellner
 */
public class OverlayMenu implements HUDView {
    private static final float yStart = Gdx.graphics.getHeight() - 200f;
    private static final float yButtonStart = yStart - 100f;
    private static final float buttonHeight = 40f;
    private static final float buttonWidth = 300f;
    private static final float buttonMargin = 5f;
    private final List<Button> buttons;
    private Label title;
    private final Image lightBox;

    /**
     * Creates an overlay menu with a lightbox and title and an empty set of buttons.
     *
     * @param titleText The title of the overlay menu.
     */
    public OverlayMenu(String titleText) {
        createTitleLabel(titleText);
        buttons = new ArrayList<>();
        lightBox = new Image(new Texture("images/lightbox.png"));
        lightBox.setFillParent(true);
    }

    private void createTitleLabel(String titleText) {
        this.title = new Label(titleText, FontUtils.robotoWhite(50));
        this.title.setPosition(Gdx.graphics.getWidth() / 2f - this.title.getPrefWidth() / 2, yStart);
        this.title.setAlignment(Align.center);
    }

    private void transformButtons() {
        float buttonMargin = OverlayMenu.buttonMargin;
        for (Button button : buttons) {
            button.setStyle(ButtonUtils.getExpeeButtonStyle());
            button.setWidth(buttonWidth);
            button.setHeight(buttonHeight);
            button.setPosition(Gdx.graphics.getWidth() / 2f - button.getWidth() / 2f, yButtonStart - buttonHeight - buttonMargin);
            buttonMargin += OverlayMenu.buttonMargin + buttonHeight;
        }
    }

    /**
     * Adds, styles and places the button to the overlay menu.
     *
     * @param button The button to be added to the overlay.
     */
    public void addButton(Button button) {
        buttons.add(button);
        transformButtons();
    }

    @Override
    public void addActors(Stage stage) {
        stage.addActor(lightBox);
        stage.addActor(title);
        for (Button button : buttons) {
            stage.addActor(button);
        }
    }

    @Override
    public void removeActors() {
        title.remove();
        lightBox.remove();
        for (Button button : buttons) {
            button.remove();
        }
    }

    /**
     * Updates the text of title.
     *
     * @param text The new title.
     */
    public void updateTitleText(String text) {
        title.setText(text);
    }

}
