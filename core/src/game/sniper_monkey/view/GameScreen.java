package game.sniper_monkey.view;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import game.sniper_monkey.model.world.GameObject;
import game.sniper_monkey.model.world.IWorldObserver;
import game.sniper_monkey.model.world.World;
import game.sniper_monkey.view.hud.HUDView;

import java.util.ArrayList;
import java.util.List;

/**
 * A class storing view data (all HUDViews and GameObjectViews) as well as renders these using
 * a projection matrix. It also provides visual debug functionality.
 *
 * @author Vincent Hellner
 * @author Elias Falk
 * @author Kevin Jeryd
 */
public class GameScreen extends ScreenAdapter implements IWorldObserver  {
    private final List<GameObjectView> gameObjectViews;
    SpriteBatch batch;
    ShapeRenderer sr;
    Stage stage;
    OrthographicCamera camera = new OrthographicCamera(1920 / 2f, 1080 / 2f);
    boolean debugMode = true;
    RoundTimerView roundTimerView;

    /**
     * Creates a GameRenderer
     */
    public GameScreen() {
        stage = new Stage();
        batch = new SpriteBatch();
        gameObjectViews = new ArrayList<>();

        //TODO: REFACTOR OBSERVER
        roundTimerView = new RoundTimerView(World.getInstance());
        World.getInstance().registerTimerObserver(roundTimerView);

        sr = new ShapeRenderer();
        roundTimerView.addActors(stage);
    }

    /**
     * Updates the projection matrix using a new size
     * @param width  The new viewport width
     * @param height The new viewport height
     */
    public void updateCamera(int width, int height) {
        camera.viewportHeight = height;
        camera.viewportWidth = width;
        camera.update();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float deltaTime) {
        ScreenUtils.clear(1, 1, 1, 1);

        batch.begin();
        sr.begin(ShapeRenderer.ShapeType.Line);
        batch.setProjectionMatrix(camera.combined);
        sr.setProjectionMatrix(camera.combined);

        if (debugMode) {
            sr.setColor(1, 0, 0, 1);
            int partitionSize = 64; // hard coded based on spatialhash
            for (int x = -10 * partitionSize; x < 10 * partitionSize; x += partitionSize) {
                for (int y = -10 * partitionSize; y < 10 * partitionSize; y += partitionSize) {
                    sr.rect(x, y, partitionSize, partitionSize);
                }
            }
        }

        for (GameObjectView view : gameObjectViews) {
            view.updateSprite();
            view.render(sr, batch, debugMode);
        }

        batch.end();
        sr.end();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    /**
     * Disposes of the SpriteBatch and ShapeRenderer
     */
    @Override
    public void dispose() {
        batch.dispose();
        sr.dispose();
    }

    //TODO documentation
    @Override
    public void onObjectAddedToWorld(GameObject obj) {
        GameObjectView view = GameObjectViewFactory.viewFromGameObject(obj);
        if (view != null) gameObjectViews.add(view);
    }

    //TODO documentation
    @Override
    public void onObjectRemovedFromWorld(GameObject obj) {
        for (int i = 0; i < gameObjectViews.size(); i++) {
            if (gameObjectViews.get(i).hasModel(obj)) {
                gameObjectViews.remove(i);
                return;
            }
        }
    }

    public void addHudView(HUDView hudView) {
        hudView.addActors(stage);
    }

    public void removeHudView(HUDView hudView) {
//        hudView.addActors(stage);
    }
}
