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
import game.sniper_monkey.model.player.fighter.EvilWizard;
import game.sniper_monkey.model.player.fighter.Fighter;
import game.sniper_monkey.model.player.fighter.FighterFactory;
import game.sniper_monkey.model.player.fighter.HuntressBow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CharacterSelectionScreen extends ScreenAdapter  {

    SpriteBatch batch;
    ShapeRenderer sr;
    Stage stage;

    //Make it so amountOfFighters is the length of the fighterList
    private final int amountOfFighters = 8;
    private int selectedRectangleIndex = 0;

    //Create a list with potential fighters to choose from? Should it be the sprite of each fighter?
    private final List<Class<? extends Fighter>> fighterList = new ArrayList<>();

    public CharacterSelectionScreen() {
        stage = new Stage();
        batch = new SpriteBatch();

        sr = new ShapeRenderer();

        fighterList.add(EvilWizard.class);
        fighterList.add(EvilWizard.class);
        fighterList.add(HuntressBow.class);
        fighterList.add(EvilWizard.class);
        fighterList.add(HuntressBow.class);
        fighterList.add(EvilWizard.class);
        fighterList.add(EvilWizard.class);
        fighterList.add(EvilWizard.class);
        fighterList.add(EvilWizard.class);

        createRectangles();
    }

    Map<Integer, SelectViewRectangle> rectangleMap = new HashMap<Integer, SelectViewRectangle>();

    private void createRectangles() {
        for (int i = 0; i < (amountOfFighters); i++) {
            SelectViewRectangle rect;

            //Fix math for width
            if ( i < (amountOfFighters/2)) {
                rect = new SelectViewRectangle(fighterList.get(i), ((i % (amountOfFighters / 2f)+0)) * Gdx.graphics.getWidth() / ((float) amountOfFighters / 2), Gdx.graphics.getHeight() / 4f, 100f, 100f, Color.BLUE, stage);
            } else {
                rect = new SelectViewRectangle(fighterList.get(i),((i % (amountOfFighters / 2f)+0)) * Gdx.graphics.getWidth() / ((float) amountOfFighters / 2), Gdx.graphics.getHeight() / (3 * 4f), 100f, 100f, Color.BLUE, stage);
            }
            rectangleMap.put(i, rect);
            stage.addActor(rect);
        }
    }

    //Instead of doing like this, maybe do rectangleMap.get(selectedRectangleIndex-1).setSelected(false); inside setSelectedRectangle
    //Will break if you go to the left though. Find solution.
    private void unSelectedRectangles() {
        for (int i = 0; i < rectangleMap.size(); i++) {
            if (i != selectedRectangleIndex) {
                rectangleMap.get(i).setSelected(false);
            }
        }
    }

    private void setSelectedRectangle() {
        SelectViewRectangle selectedRectangle = rectangleMap.get(selectedRectangleIndex);
        selectedRectangle.setSelected(true);
    }

    private void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.D)){
            setSelectedRectangleIndex(1);
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.A)) {
            setSelectedRectangleIndex(-1);
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.W)) {
            setSelectedRectangleIndex(amountOfFighters/2);
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.S)) {
            setSelectedRectangleIndex(-amountOfFighters/2);
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            chooseFighter();
        }
    }

    private void setSelectedRectangleIndex(int i) {
        if (((selectedRectangleIndex+i)%amountOfFighters) < 0) {
            selectedRectangleIndex = ((selectedRectangleIndex+i)%amountOfFighters)+amountOfFighters;
        } else {
            selectedRectangleIndex = ((selectedRectangleIndex+i)%amountOfFighters);
        }
    }

    private void chooseFighter() {

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
