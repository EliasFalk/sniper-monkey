package game.sniper_monkey.view;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import game.sniper_monkey.world.GameObject;
import game.sniper_monkey.world.IWorldObserver;

import java.util.ArrayList;

public class GameRenderer implements IWorldObserver {
    SpriteBatch batch;
    Texture img = new Texture("evil_wizard_2/Attack1.png");
    Texture platform = new Texture("platform.png");

    private ArrayList<GameObjectView> gameObjectViews;

    public GameRenderer() {
        batch = new SpriteBatch();
        gameObjectViews = new ArrayList<>();
    }

    /**
     * Renders a background and then all the objects in the world singleton using a SpriteBatch
     */

    OrthographicCamera camera = new OrthographicCamera(1280 / 2, 720 / 2);

    public void render() {
        ScreenUtils.clear(1, 1, 1, 1);

        batch.begin();
        batch.setProjectionMatrix(camera.combined);
        for (GameObjectView view : gameObjectViews) {
            view.render(batch);
        }
        batch.end();
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
