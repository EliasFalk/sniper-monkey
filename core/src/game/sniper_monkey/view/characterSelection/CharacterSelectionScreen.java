package game.sniper_monkey.view.characterSelection;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.HashMap;
import java.util.Map;

public class CharacterSelectionScreen extends ScreenAdapter  {

    SpriteBatch batch;
    ShapeRenderer sr;
    Stage stage;

    private final Label testLabel;
    private int amountOfFighters = 8;
    private int selectedRectangleIndex = 1;

    public CharacterSelectionScreen() {
        stage = new Stage();
        batch = new SpriteBatch();

        sr = new ShapeRenderer();

        testLabel = new Label("test", new Label.LabelStyle(new BitmapFont(), Color.BLACK));
        testLabel.setFontScale(4,4);
        testLabel.setPosition(Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f);
        testLabel.setAlignment(Align.center);

        stage.addActor(testLabel);

        createRectangles();
    }

    Map<Integer, SelectViewRectangle> rectangleMap = new HashMap<Integer, SelectViewRectangle>();

    public void createRectangles() {
        for (int i = 0; i < (amountOfFighters); i++) {
            SelectViewRectangle rect;
            if ( i < (amountOfFighters/2)) {
                rect = new SelectViewRectangle(new Label("Fighter " + i, new Label.LabelStyle(new BitmapFont(), Color.BLACK)), ((i % (amountOfFighters / 2f)+0)) * Gdx.graphics.getWidth() / ((float) amountOfFighters / 2), Gdx.graphics.getHeight() / 4f, 100f, 100f, Color.BLUE);
            } else {
                rect = new SelectViewRectangle(new Label("Fighter " + i, new Label.LabelStyle(new BitmapFont(), Color.BLACK)), ((i % (amountOfFighters / 2f)+0)) * Gdx.graphics.getWidth() / ((float) amountOfFighters / 2), Gdx.graphics.getHeight() / (3 * 4f), 100f, 100f, Color.BLUE);
            }
            rectangleMap.put(i+1, rect);
            stage.addActor(rect);
        }
    }


    public void unSelectedRectangles() {
        for (int i = 1; i <= rectangleMap.size(); i++) {
            if (i != selectedRectangleIndex) {
                rectangleMap.get(i).setSelected(false);
            }
        }
    }

    public void setSelectedRectangle() {
        SelectViewRectangle selectedRectangle = rectangleMap.get(selectedRectangleIndex);
        selectedRectangle.setSelected(true);
    }

    public void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.D)){
            setSelectedRectangleIndex(1);
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.A)) {
            setSelectedRectangleIndex(-1);
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.W)) {
            setSelectedRectangleIndex(amountOfFighters/2);
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.S)) {
            setSelectedRectangleIndex(-amountOfFighters/2);
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {

        }
    }

    public void setSelectedRectangleIndex(int i) {
        //Fix maths so it works when selectedRectangleIndex == amountOfFighters on -1
        if ((selectedRectangleIndex%amountOfFighters)+i <= 0) {
            selectedRectangleIndex = amountOfFighters;
        } else {
            selectedRectangleIndex = (selectedRectangleIndex%amountOfFighters)+i;
        }
    }

    @Override
    public void render(float deltaTime) {
        ScreenUtils.clear(1, 1, 1, 1);

        handleInput();
        setSelectedRectangle();
        unSelectedRectangles();

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
