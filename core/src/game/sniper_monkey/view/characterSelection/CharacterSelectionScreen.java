package game.sniper_monkey.view.characterSelection;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import game.sniper_monkey.model.player.fighter.EvilWizard;
import game.sniper_monkey.model.player.fighter.Fighter;
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
    private int player1SelectedRectangleIndex = 0;
    private int player2SelectedRectangleIndex = 0;

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
        fighterList.add(HuntressBow.class);
        fighterList.add(EvilWizard.class);

        createRectangles();
    }

    Map<Integer, SelectViewRectangle> rectangleMap = new HashMap<Integer, SelectViewRectangle>();

    private void createRectangles() {
        for (int i = 0; i < (amountOfFighters); i++) {
            SelectViewRectangle rect;

            if ( i < (amountOfFighters/2)) {
                rect = new SelectViewRectangle(fighterList.get(i), ((i % (amountOfFighters / 2f))) * Gdx.graphics.getWidth() / ((float) amountOfFighters / 2)+100, Gdx.graphics.getHeight() / 4f, 100f, 100f, Color.BLUE, stage);
            } else {
                rect = new SelectViewRectangle(fighterList.get(i),((i % (amountOfFighters / 2f))) * Gdx.graphics.getWidth() / ((float) amountOfFighters / 2)+100, Gdx.graphics.getHeight() / (3 * 4f), 100f, 100f, Color.BLUE, stage);
            }
            rectangleMap.put(i, rect);
            stage.addActor(rect);
        }
    }

    //Instead of doing like this, maybe do rectangleMap.get(selectedRectangleIndex-1).setSelected(false); inside setSelectedRectangle
    //Will break if you go to the left though. Find solution.
    private void unSelectedRectangles() {
        for (int i = 0; i < rectangleMap.size(); i++) {
            if (i != player1SelectedRectangleIndex) {
                rectangleMap.get(i).setPlayer1Selected(false);
            }
        }
    }

    private void player2UnselectedRectangles() {
        for (int i = 0; i < rectangleMap.size(); i++) {
            if (i != player2SelectedRectangleIndex) {
                rectangleMap.get(i).setPlayer2Selected(false);
            }
        }
    }

    private void setPlayer1SelectedRectangle() {
        SelectViewRectangle player1SelectedRectangle = rectangleMap.get(player1SelectedRectangleIndex);
        player1SelectedRectangle.setPlayer1Selected(true);
    }

    private void setPlayer2SelectedRectangle() {
        SelectViewRectangle player2SelectedRectangle = rectangleMap.get(player2SelectedRectangleIndex);
        player2SelectedRectangle.setPlayer2Selected(true);
    }


    //Use T and P for player 1 select fighters. P primary and T secondary
    private void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.D)){
            setPlayer1SelectedRectangleIndex(1);
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.A)) {
            setPlayer1SelectedRectangleIndex(-1);
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.W)) {
            setPlayer1SelectedRectangleIndex(amountOfFighters/2);
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.S)) {
            setPlayer1SelectedRectangleIndex(-amountOfFighters/2);
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            chooseFighter();
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
            setPlayer2SelectedRectangleIndex(1);
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
            setPlayer2SelectedRectangleIndex(-1);
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            setPlayer2SelectedRectangleIndex(amountOfFighters/2);
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
            setPlayer2SelectedRectangleIndex(-amountOfFighters/2);
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            chooseFighter();
        }
    }

    private void setPlayer1SelectedRectangleIndex(int i) {
        if (((player1SelectedRectangleIndex +i)%amountOfFighters) < 0) {
            player1SelectedRectangleIndex = ((player1SelectedRectangleIndex +i)%amountOfFighters)+amountOfFighters;
        } else {
            player1SelectedRectangleIndex = ((player1SelectedRectangleIndex +i)%amountOfFighters);
        }
    }

    private void setPlayer2SelectedRectangleIndex(int i) {
        if (((player2SelectedRectangleIndex +i)%amountOfFighters) < 0) {
            player2SelectedRectangleIndex = ((player2SelectedRectangleIndex +i)%amountOfFighters)+amountOfFighters;
        } else {
            player2SelectedRectangleIndex = ((player2SelectedRectangleIndex +i)%amountOfFighters);
        }
    }

    private void chooseFighter() {

    }

    @Override
    public void render(float deltaTime) {
        ScreenUtils.clear(1, 1, 1, 1);

        handleInput();
        System.out.println("PLAYER 1 INDEX : " + player1SelectedRectangleIndex);
        System.out.println("PLAYER 2 INDEX : " + player2SelectedRectangleIndex);
        setPlayer1SelectedRectangle();
        setPlayer2SelectedRectangle();
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
