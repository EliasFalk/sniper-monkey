package game.sniper_monkey.view.hud;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import game.sniper_monkey.model.world.TimerObserver;
import game.sniper_monkey.utils.view.FontUtils;

/**
 * A view that represents a pressable key on the keyboard.
 *
 * @author Elias Falk
 */
public class KeyInputView implements TimerObserver, HUDView {

    private final float x;
    private final float y;
    private String key;
    private String text;
    private final FillableBar fillableBar;
    private final Label keyLabel;
    private final Label sideTextLabel;
    private static final float width = 30f;
    private static final float height = 30f;
    private float sideLabelOffset = 10f;
    private Placement textPlacement;
    private final float sideTextWidth = 150f;


    /**
     * Creates a new key input view with a default color of light gray.
     *
     * @param x             The x position of the key input view. 0 is the left most pixel on the screen.
     * @param y             The y position of the key input view. 0 is the bottom pixel of the screen.
     * @param key           The text that will be shown within the square representing the key.
     * @param text          The text that will be shown beside the square.
     * @param textPlacement The text placement where the side text will be shown.
     */
    public KeyInputView(float x, float y, String key, String text, Placement textPlacement) {
        this.x = x;
        this.y = y;
        this.key = key;
        this.text = text;
        this.textPlacement = textPlacement;
        fillableBar = new FillableBar(x, y, width, height, Color.LIGHT_GRAY, FillDirection.UP);
        keyLabel = createKeyLabel(x, y, key);
        sideTextLabel = new Label(text, FontUtils.getNormalFont(14));
        updateSideLabel();
    }

    private void updateSideLabel() {
        sideTextLabel.setText(text);
        if (textPlacement == Placement.RIGHT) {
            sideTextLabel.setPosition(x + width + sideLabelOffset, y + (height / 2 - sideTextLabel.getHeight() / 2));
        } else if (textPlacement == Placement.LEFT) {
            sideTextLabel.setPosition(x - sideLabelOffset - sideTextLabel.getPrefWidth(), y + (height / 2 - sideTextLabel.getHeight() / 2));
        }
    }

    private Label createKeyLabel(float x, float y, String key) {
        final Label keyLabel;
        keyLabel = new Label(key, new Label.LabelStyle(new BitmapFont(), Color.BLACK));
        keyLabel.setPosition(x + width / 2, y + height / 2, Align.center);
        return keyLabel;
    }

    /**
     * Returns the width of the part of the key input view that represents the key cap.
     *
     * @return The width of the part of the key input view that represents the key cap.
     */
    public static float getWidth() {
        return width;
    }

    /**
     * Returns the height of the part of the key input view that represents the key cap.
     *
     * @return The height of the part of the key input view that represents the key cap.
     */
    public static float getHeight() {
        return height;
    }

    /**
     * Sets the side text of the visual key to a new text.
     *
     * @param text The new side text.
     */
    public void setText(String text) {
        this.text = text;
        updateSideLabel();
    }

    @Override
    public void addActors(Stage stage) {
        stage.addActor(fillableBar);
        stage.addActor(keyLabel);
        stage.addActor(sideTextLabel);
    }

    @Override
    public void onTimeUpdated(float timerLength, float timeLeft) {
        // could update some label with the actual time left.
        fillableBar.update(1 - timeLeft / timerLength);
    }
}
