package game.sniper_monkey.view.hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import game.sniper_monkey.utils.time.TimerObserver;
import game.sniper_monkey.utils.view.FontUtils;

/**
 * A view for the Round timer shown at the top of the screen
 * <p>
 * Uses HUDView.
 * Uses TimerObserver.
 * <p>
 * Used by GameController.
 *
 * @author Kevin Jeryd
 */
public class RoundTimerView implements HUDView, TimerObserver {

    private final Label countdownLabel;

    /**
     * Creates a view that displays a large timer at the top middle of the screen.
     */
    public RoundTimerView() {
        countdownLabel = new Label("0", FontUtils.robotoWhite(50));
        countdownLabel.setPosition(Gdx.graphics.getWidth() / 2f - countdownLabel.getPrefWidth() / 2, Gdx.graphics.getHeight() - 100);
        countdownLabel.setAlignment(Align.center);
    }

    @Override
    public void addActors(Stage stage) {
        stage.addActor(countdownLabel);
    }

    @Override
    public void removeActors() {
        countdownLabel.remove();
    }

    @Override
    public void onTimeUpdated(float timerLength, float timeLeft) {
        countdownLabel.setText((int) timeLeft);
    }
}
