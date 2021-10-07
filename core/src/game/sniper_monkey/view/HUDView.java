package game.sniper_monkey.view;

import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 * A view for HUD
 *
 * @author Kevin Jeryd
 * @author Elias Falk
 * @author Vincent Hellner
 */
public abstract class HUDView {

    private final Object model;

    //TODO documentation
    public HUDView(Object model) {
        this.model = model;
    }

    //TODO documentation
    public abstract void addActors(Stage stage);
}
