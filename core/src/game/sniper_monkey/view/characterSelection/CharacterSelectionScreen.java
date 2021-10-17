package game.sniper_monkey.view.characterSelection;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import game.sniper_monkey.view.GameObjectView;

public class CharacterSelectionScreen extends ScreenAdapter  {

    SpriteBatch batch;
    ShapeRenderer sr;
    Stage stage;

    private final Label testLabel;

    public CharacterSelectionScreen() {
        stage = new Stage();
        batch = new SpriteBatch();

        sr = new ShapeRenderer();

        testLabel = new Label("test", new Label.LabelStyle(new BitmapFont(), Color.BLACK));
        testLabel.setFontScale(4,4);
        testLabel.setPosition(Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f);
        testLabel.setAlignment(Align.center);

        stage.addActor(testLabel);
    }



    @Override
    public void render(float deltaTime) {
        ScreenUtils.clear(1, 1, 1, 1);

        batch.begin();
        sr.begin(ShapeRenderer.ShapeType.Line);




        batch.end();
        sr.end();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
        batch.dispose();
        sr.dispose();
    }


    public void addSelectView(SelectView selectView) {
        selectView.addActors(stage);
    }



}
