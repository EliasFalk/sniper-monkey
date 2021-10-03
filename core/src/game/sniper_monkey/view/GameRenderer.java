package game.sniper_monkey.view;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import game.sniper_monkey.world.GameObject;
import game.sniper_monkey.world.IWorldObserver;
import game.sniper_monkey.world.World;

import java.util.ArrayList;

public class GameRenderer implements IWorldObserver {
    private final ArrayList<GameObjectView> gameObjectViews;
    SpriteBatch batch;
    ShapeRenderer sr;
    Stage stage;
    OrthographicCamera camera = new OrthographicCamera(1280 / 2f, 720 / 2f);
    boolean debugMode = (Math.random() > 0.5); // TODO epic.
    RoundTimerView roundTimerView;

    public GameRenderer() {
        stage = new Stage();
        batch = new SpriteBatch();
        gameObjectViews = new ArrayList<>();

        //TODO: REFACTOR OBSERVER
        roundTimerView = new RoundTimerView(World.getInstance());
        World.getInstance().registerTimerObserver(roundTimerView);

        sr = new ShapeRenderer();
        roundTimerView.addActors(stage);
    }

    public void updateCamera(int width, int height) {
        camera.viewportHeight = height;
        camera.viewportWidth = width;
        camera.update();
    }

    /**
     * Renders a background and then all of the views stored in the GameRenderer
     */
    public void render() {
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

    /**
     * Disposes of the SpriteBatch and ShapeRenderer
     */
    public void dispose() {
        batch.dispose();
        sr.dispose();
    }

    @Override
    public void onObjectAddedToWorld(GameObject obj) {
        GameObjectView view = GameObjectViewFactory.viewFromGameObject(obj);
        if (view != null) gameObjectViews.add(view);
    }

    @Override
    public void onObjectRemovedFromWorld(GameObject obj) {
        for (int i = 0; i < gameObjectViews.size(); i++) {
            if (gameObjectViews.get(i).hasModel(obj)) {
                gameObjectViews.remove(i);
                return;
            }
        }
    }
}
