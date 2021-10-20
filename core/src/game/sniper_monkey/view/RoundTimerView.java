package game.sniper_monkey.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import game.sniper_monkey.GameController;
import game.sniper_monkey.model.world.CallbackTimer;
import game.sniper_monkey.model.world.TimerObserver;
import game.sniper_monkey.model.world.World;
import game.sniper_monkey.utils.view.FontUtils;
import game.sniper_monkey.view.hud.HUDView;

/**
 * A view for the Round timer shown at the top of the screen
 *
 * @author Kevin Jeryd
 */
public class RoundTimerView implements HUDView, TimerObserver {

    private final Label countdownLabel;

    //TODO documentation
    public RoundTimerView() {
        countdownLabel = new Label("0", FontUtils.robotoWhite(50));
        countdownLabel.setPosition(Gdx.graphics.getWidth() / 2f - countdownLabel.getPrefWidth() / 2, Gdx.graphics.getHeight() - 100);
        countdownLabel.setAlignment(Align.center);
    }

    //TODO documentation
    public void addActors(Stage stage) {
        stage.addActor(countdownLabel);
    }


    @Override
    public void onTimeUpdated(float timerLength, float timeLeft) {
        countdownLabel.setText((int) timeLeft);
    }
}
