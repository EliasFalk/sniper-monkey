package game.sniper_monkey.view.characterSelection;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import game.sniper_monkey.model.player.fighter.EvilWizard;
import game.sniper_monkey.model.player.fighter.Fighter;
import game.sniper_monkey.model.player.fighter.HuntressBow;
import game.sniper_monkey.model.player.fighter.Samurai;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Kevin Jeryd
 * @author Dadi Andrason
 */
public class CharacterSelectionScreen extends ScreenAdapter {

    SpriteBatch batch;
    ShapeRenderer sr;
    Stage stage;

    CharacterSelectionScreenController characterSelectionScreenController;


    //Create a list with potential fighters to choose from? Should it be the sprite of each fighter?
    public final List<Class<? extends Fighter>> fighterList = new ArrayList<>();

    public CharacterSelectionScreen(CharacterSelectionScreenController characterSelectionController) {
        stage = new Stage();
        batch = new SpriteBatch();

        sr = new ShapeRenderer();

        this.characterSelectionScreenController = characterSelectionController;

        fighterList.add(EvilWizard.class);
        fighterList.add(Samurai.class);
        fighterList.add(HuntressBow.class);
        fighterList.add(EvilWizard.class);
        fighterList.add(HuntressBow.class);
        fighterList.add(Samurai.class);
        fighterList.add(EvilWizard.class);
        fighterList.add(HuntressBow.class);

        characterSelectionController.amountOfFighters = fighterList.size();

        createRectangles();
    }

    Map<Integer, SelectViewRectangle> rectangleMap = new HashMap<Integer, SelectViewRectangle>();

    private void createRectangles() {
        for (int i = 0; i < (characterSelectionScreenController.amountOfFighters); i++) {
            SelectViewRectangle rect;

            //Fix maths
            if ( i < (characterSelectionScreenController.amountOfFighters/2)) {
                rect = new SelectViewRectangle(fighterList.get(i), ((i % (characterSelectionScreenController.amountOfFighters / 2f))) * Gdx.graphics.getWidth() / ((float) characterSelectionScreenController.amountOfFighters / 2)+100, Gdx.graphics.getHeight() / 4f, 100f, 100f, Color.BLUE, stage);
            } else {
                rect = new SelectViewRectangle(fighterList.get(i),((i % (characterSelectionScreenController.amountOfFighters / 2f))) * Gdx.graphics.getWidth() / ((float) characterSelectionScreenController.amountOfFighters / 2)+100, Gdx.graphics.getHeight() / (3 * 4f), 100f, 100f, Color.BLUE, stage);
            }
            characterSelectionScreenController.registerObserver(rect);
            rectangleMap.put(i, rect);
            stage.addActor(rect);
        }
    }

    private void setPlayer1SelectedRectangle() {
        SelectViewRectangle player1SelectedRectangle = rectangleMap.get(characterSelectionScreenController.player1SelectedRectangleIndex);
        player1SelectedRectangle.setPlayer1Selected(true);
    }

    private void setPlayer2SelectedRectangle() {
        SelectViewRectangle player2SelectedRectangle = rectangleMap.get(characterSelectionScreenController.player2SelectedRectangleIndex);
        player2SelectedRectangle.setPlayer2Selected(true);
    }

    private void unSelectedRectangles() {
        for (int i = 0; i < rectangleMap.size(); i++) {
            rectangleMap.get(i).setPlayer1Selected(false);
            rectangleMap.get(i).setPlayer2Selected(false);
        }
    }

    @Override
    public void render(float deltaTime) {
        ScreenUtils.clear(1, 1, 1, 1);

        characterSelectionScreenController.handleInput();
        unSelectedRectangles();
        setPlayer1SelectedRectangle();
        setPlayer2SelectedRectangle();

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
}
