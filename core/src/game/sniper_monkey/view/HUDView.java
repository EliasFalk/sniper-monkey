package game.sniper_monkey.view;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import game.sniper_monkey.world.GameObject;
import com.badlogic.gdx.scenes.scene2d.Stage;

public abstract class HUDView {

    private Object model;

    public HUDView(Object model) {
        this.model = model;
    }

    public abstract void addActors(Stage stage);
}
