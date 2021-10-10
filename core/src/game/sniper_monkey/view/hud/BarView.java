package game.sniper_monkey.view.hud;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import game.sniper_monkey.model.player.FluctuatingAttributeObserver;

import java.text.DecimalFormat;

public class BarView extends HUDView implements FluctuatingAttributeObserver {
    Label barLabel;
    Bar bar;

    float currentValue = 0;

    public BarView(float x, float y, float width, float height, Color color, FillDirection fillDir) {
        barLabel = new Label(String.format(new DecimalFormat("#.##").format(0), 0f), new Label.LabelStyle(new BitmapFont(), Color.BLACK));
        barLabel.setFontScale(1, 1);
        barLabel.setPosition(x - 30, y);
        barLabel.setAlignment(Align.center);
        bar = new Bar(x, y, width, height, color, fillDir);
    }

    public BarView(float x, float y, Color color, FillDirection fillDir) {
        this(x, y, 150, 20, color, fillDir);
    }

    @Override
    public void addActors(Stage stage) {
        stage.addActor(bar);
        stage.addActor(barLabel);
    }

    @Override
    public void onPercentageChange(float newFraction) {
        currentValue = newFraction;
        barLabel.setText(String.format(new DecimalFormat("#.#").format(currentValue * 100), currentValue));
        bar.update(newFraction);
    }
}
