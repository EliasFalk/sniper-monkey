package game.sniper_monkey.view;

import com.badlogic.gdx.scenes.scene2d.Stage;

public abstract class HUDView {

    private final Object model;

    public HUDView(Object model) {
        this.model = model;
    }

    public abstract void addActors(Stage stage);
}
