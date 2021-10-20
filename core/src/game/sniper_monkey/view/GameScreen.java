package game.sniper_monkey.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import game.sniper_monkey.model.player.Player;
import game.sniper_monkey.model.player.SwappedFighterObserver;
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
public class GameScreen extends ScreenAdapter implements IWorldObserver, SwappedFighterObserver {
    private final List<GameObjectView> gameObjectViews;
    SpriteBatch batch;
    ShapeRenderer PartitionDebugRenderer;
    ShapeRenderer ObjectDebugRenderer;
    Stage stage;
    OrthographicCamera camera = new OrthographicCamera(1920 / 2f, 1080 / 2f);
    boolean debugMode = false;

    Sprite bg1;
    Sprite bg2;

    /**
     * Creates a GameRenderer
     */
    public GameScreen() {
        stage = new Stage();
        batch = new SpriteBatch();
        gameObjectViews = new ArrayList<>();

        PartitionDebugRenderer = new ShapeRenderer();
        ObjectDebugRenderer = new ShapeRenderer();
        loadBackground();
    }

    private void loadBackground() {
        Texture bg1T = new Texture("images/Taiga-Asset-Pack_v2_vnitti/PNG/Background.png");
        Texture bg2T = new Texture("images/Taiga-Asset-Pack_v2_vnitti/PNG/Middleground.png");

        bg1 = new Sprite(bg1T);
        bg2 = new Sprite(bg2T);
    }

    private void renderBackground(SpriteBatch batch) {
        batch.draw(bg1, -camera.viewportWidth / 2f, -camera.viewportHeight / 2f, camera.viewportWidth, camera.viewportHeight);
        batch.draw(bg2, -camera.viewportWidth / 2f, -camera.viewportHeight / 2f, camera.viewportWidth, camera.viewportHeight);
    }

    /**
     * Updates the projection matrix using a new size
     *
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
        batch.setProjectionMatrix(camera.combined);
        PartitionDebugRenderer.begin(ShapeRenderer.ShapeType.Line);
        PartitionDebugRenderer.setProjectionMatrix(camera.combined);
        ObjectDebugRenderer.begin(ShapeRenderer.ShapeType.Line);
        ObjectDebugRenderer.setProjectionMatrix(camera.combined);
        renderBackground(batch);
        if (debugMode) {
            PartitionDebugRenderer.setColor(1, 0, 0, 1);
            int partitionSize = 64; // hard coded based on spatialhash
            for (int x = -10 * partitionSize; x < 10 * partitionSize; x += partitionSize) {
                for (int y = -10 * partitionSize; y < 10 * partitionSize; y += partitionSize) {
                    PartitionDebugRenderer.rect(x, y, partitionSize, partitionSize);
                }
            }
        }

        for (GameObjectView view : gameObjectViews) {
            view.updateSprite();
            view.render(ObjectDebugRenderer, batch, debugMode);
        }

        batch.end();
        PartitionDebugRenderer.end();
        ObjectDebugRenderer.end();
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
        PartitionDebugRenderer.dispose();
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

    @Override
    public void onFighterSwap(Player player) {
        // TODO refactor
        onObjectRemovedFromWorld(player);
        onObjectAddedToWorld(player);
    }
}
