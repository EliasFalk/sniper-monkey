package game.sniper_monkey.view.hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import game.sniper_monkey.model.player.FluctuatingAttributeObserver;

public class BarView extends HUDView implements FluctuatingAttributeObserver {
    Label barLabel;
    Bar bar;

    float currentValue = 0;

    public BarView(float x, float y, Color color, FillDirection fillDir) {
        barLabel = new Label(String.format("%03f", 0f), new Label.LabelStyle(new BitmapFont(), Color.BLACK));
        barLabel.setFontScale(4, 4);
        barLabel.setPosition(x, y, Gdx.graphics.getHeight() - 100);
        barLabel.setAlignment(Align.center);
        bar = new Bar(x, y, 150, 20, color, fillDir);
    }

    @Override
    public void addActors(Stage stage) {
        stage.addActor(bar);
    }

    @Override
    public void onPercentageChange(float newFraction) {
        currentValue = newFraction;
        barLabel.setText(String.format("%03f", currentValue));
        bar.update(newFraction);
    }
}
