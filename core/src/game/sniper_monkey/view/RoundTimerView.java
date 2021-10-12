package game.sniper_monkey.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import game.sniper_monkey.model.world.ITimerObserver;
import game.sniper_monkey.model.world.World;
import game.sniper_monkey.view.hud.HUDView;

/**
 * A view for the Round timer shown at the top of the screen
 *
 * @author Kevin Jeryd
 */
public class RoundTimerView implements HUDView, ITimerObserver {

    private final Label countdownLabel;
    World model;

    //TODO documentation
    public RoundTimerView(World model) {
        this.model = model;
        countdownLabel = new Label(String.format("%03d", model.getRoundDuration()), new Label.LabelStyle(new BitmapFont(), Color.BLACK));
        countdownLabel.setFontScale(4, 4);
        countdownLabel.setPosition(Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() - 100);
        countdownLabel.setAlignment(Align.center);
    }

    //TODO documentation
    public void addActors(Stage stage) {
        stage.addActor(countdownLabel);
    }

    //TODO documentation
    @Override
    public void onTimerChange(int time) {
        countdownLabel.setText(time);
    }
}
