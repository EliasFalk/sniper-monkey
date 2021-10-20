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
    private final int amountOfFighters;
    private int player1SelectedRectangleIndex = 0;
    private int player2SelectedRectangleIndex = 0;

    private Fighter player1PrimaryFighter;
    private Fighter player1SecondaryFighter;
    private Fighter player2PrimaryFighter;
    private Fighter player2SecondaryFighter;


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

        this.amountOfFighters = fighterList.size();

        createRectangles();
    }

    Map<Integer, SelectViewRectangle> rectangleMap = new HashMap<Integer, SelectViewRectangle>();

    private void createRectangles() {
        for (int i = 0; i < (amountOfFighters); i++) {
            SelectViewRectangle rect;

            //Fix maths
            if ( i < (amountOfFighters/2)) {
                rect = new SelectViewRectangle(fighterList.get(i), ((i % (amountOfFighters / 2f))) * Gdx.graphics.getWidth() / ((float) amountOfFighters / 2)+100, Gdx.graphics.getHeight() / 4f, 100f, 100f, Color.BLUE, stage);
            } else {
                rect = new SelectViewRectangle(fighterList.get(i),((i % (amountOfFighters / 2f))) * Gdx.graphics.getWidth() / ((float) amountOfFighters / 2)+100, Gdx.graphics.getHeight() / (3 * 4f), 100f, 100f, Color.BLUE, stage);
            }
            rectangleMap.put(i, rect);
            stage.addActor(rect);
        }
    }

    private void unSelectedRectangles() {
        for (int i = 0; i < rectangleMap.size(); i++) {
            rectangleMap.get(i).setPlayer1Selected(false);
            rectangleMap.get(i).setPlayer2Selected(false);
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


    private void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.D)){
            setPlayer1SelectedRectangleIndex(1);
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.A)) {
            setPlayer1SelectedRectangleIndex(-1);
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.W)) {
            setPlayer1SelectedRectangleIndex(amountOfFighters/2);
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.S)) {
            setPlayer1SelectedRectangleIndex(-amountOfFighters/2);
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.P)) {
            choosePlayer1PrimaryFighter();
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.T)) {
            choosePlayer1SecondaryFighter();
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
            setPlayer2SelectedRectangleIndex(1);
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
            setPlayer2SelectedRectangleIndex(-1);
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            setPlayer2SelectedRectangleIndex(amountOfFighters/2);
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
            setPlayer2SelectedRectangleIndex(-amountOfFighters/2);
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            choosePlayer2PrimaryFighter();
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.SHIFT_RIGHT)) {
            choosePlayer2SecondaryFighter();
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

    private Fighter chooseFighterHelper(Class<? extends Fighter> fighter) {
        if (fighter == EvilWizard.class) {
            return FighterFactory.createEvilWizard();
        } else if (fighter == HuntressBow.class) {
            return FighterFactory.createHuntressBow();
        } else {
            throw new IllegalArgumentException("No fighter found with that class");
        }
    }

    private void choosePlayer1PrimaryFighter() {
        player1PrimaryFighter = chooseFighterHelper(fighterList.get(player1SelectedRectangleIndex));
    }

    private void choosePlayer1SecondaryFighter() {
        player1SecondaryFighter = chooseFighterHelper(fighterList.get(player1SelectedRectangleIndex));
    }

    private void choosePlayer2PrimaryFighter() {
        player2PrimaryFighter = chooseFighterHelper(fighterList.get(player2SelectedRectangleIndex));
    }

    private void choosePlayer2SecondaryFighter() {
        player2SecondaryFighter = chooseFighterHelper(fighterList.get(player2SelectedRectangleIndex));
    }

    private boolean fighterAreIndeedChosen() {
        if (player1PrimaryFighter != null && player1SecondaryFighter != null && player2PrimaryFighter != null && player2SecondaryFighter != null) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void render(float deltaTime) {
        ScreenUtils.clear(1, 1, 1, 1);

        handleInput();
        unSelectedRectangles();
        setPlayer1SelectedRectangle();
        setPlayer2SelectedRectangle();

        if (fighterAreIndeedChosen()) {
            System.out.println("Welcome my friend");
            //move on to next screen
        }

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
