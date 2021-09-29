package game.sniper_monkey.view;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import game.sniper_monkey.world.GameObject;
import game.sniper_monkey.world.IWorldObserver;
import game.sniper_monkey.world.World;

import java.util.ArrayList;

public class GameRenderer implements IWorldObserver {
    SpriteBatch batch;
    ShapeRenderer sr;
    Texture img = new Texture("evil_wizard_2/Attack1.png");
    Texture platform = new Texture("platform.png");
    Stage stage;
    OrthographicCamera camera = new OrthographicCamera(1280 / 2f, 720 / 2f);

    private ArrayList<GameObjectView> gameObjectViews;
    RoundTimerView roundTimerView;

    public void updateCamera(int width, int height) {
        camera.viewportHeight = height;
        camera.viewportWidth = width;
        camera.update();
    }

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


    /**
     * Renders a background and then all of the views stored in the GameRenderer
     */
    public void render() {
        ScreenUtils.clear(1, 1, 1, 1);

        batch.begin();
        sr.begin(ShapeRenderer.ShapeType.Line);
        batch.setProjectionMatrix(camera.combined);
        sr.setProjectionMatrix(camera.combined);
        for (GameObjectView view : gameObjectViews) {
            view.updateSprite();
            view.render(sr, batch);
        }
        /*
        for (RoundTimerView timerView : timerViews) {
            timerView.onTimerChange();
        }
         */
        batch.end();
        sr.end();
        stage.draw();
    }

    /**
     * Disposes of the SpriteBatch
     */
    public void dispose() {
        batch.dispose();
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
