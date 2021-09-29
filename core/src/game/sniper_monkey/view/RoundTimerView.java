package game.sniper_monkey.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import game.sniper_monkey.world.World;

public class RoundTimerView extends HUDView {

    World model;

    private Label countdownLabel;

    public RoundTimerView(World model) {
        super(model);
        this.model = model;
        countdownLabel = new Label(String.format("%03d", model.getRoundDuration()), new Label.LabelStyle(new BitmapFont(), Color.BLACK));
        countdownLabel.setFontScale(4, 4);
        countdownLabel.setPosition(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()-100);
        countdownLabel.setAlignment(Align.center);
    }

    @Override
    public void readModel() {
        countdownLabel.setText(model.getRoundDuration());
    }

    @Override
    public void addActors(Stage stage) {
        stage.addActor(countdownLabel);
    }
}
