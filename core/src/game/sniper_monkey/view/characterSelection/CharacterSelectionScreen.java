package game.sniper_monkey.view.characterSelection;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
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

    private final SpriteBatch batch;
    private final ShapeRenderer sr;
    private final Stage stage;

    private final CharacterSelectionScreenController characterSelectionScreenController;
    public SelectedFighterView selectedFighterView;

    private final Label player1HowToChooseFighter;
    private final Label player2HowToChooseFighter;


    public final List<Class<? extends Fighter>> fighterList = new ArrayList<>();

    public CharacterSelectionScreen(CharacterSelectionScreenController characterSelectionController) {
        stage = new Stage();
        batch = new SpriteBatch();

        sr = new ShapeRenderer();

        this.characterSelectionScreenController = characterSelectionController;
        this.selectedFighterView = new SelectedFighterView(stage);

        fighterList.add(EvilWizard.class);
        fighterList.add(Samurai.class);
        fighterList.add(HuntressBow.class);
        fighterList.add(EvilWizard.class);

        characterSelectionController.amountOfFighters = fighterList.size();

        this.player1HowToChooseFighter = new Label("Player1 move with WASD\nPlayer1 Primary press P\nPlayer1 Secondary press T\n", new Label.LabelStyle(new BitmapFont(), Color.BLACK));
        this.player2HowToChooseFighter = new Label("Player2 move with arrow keys\nPlayer2 Primary press ENTER\nPlayer2 Secondary press SHIFT\n", new Label.LabelStyle(new BitmapFont(), Color.BLACK));
        this.player2HowToChooseFighter.setPosition(Gdx.graphics.getWidth()-player2HowToChooseFighter.getWidth(), 0);

        createRectangles();
    }

    Map<Integer, SelectViewRectangle> rectangleMap = new HashMap<Integer, SelectViewRectangle>();

    private void createRectangles() {
        for (int i = 0; i < (characterSelectionScreenController.amountOfFighters); i++) {
            SelectViewRectangle rect;

            //Fix maths
            if ( i < (characterSelectionScreenController.amountOfFighters/2)) {
                rect = new SelectViewRectangle(fighterList.get(i), ((i % (characterSelectionScreenController.amountOfFighters / 2f))) * (Gdx.graphics.getWidth() / ((float) characterSelectionScreenController.amountOfFighters / 2))+270, Gdx.graphics.getHeight() / 3.5f, 150f, 150f, Color.BLUE, stage);
            } else {
                rect = new SelectViewRectangle(fighterList.get(i),((i % (characterSelectionScreenController.amountOfFighters / 2f))) * Gdx.graphics.getWidth() / ((float) characterSelectionScreenController.amountOfFighters / 2)+270, Gdx.graphics.getHeight() / (3.5f * 5f), 150f, 150f, Color.BLUE, stage);
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

    private void staticContent() {
        stage.addActor(player1HowToChooseFighter);
        stage.addActor(player2HowToChooseFighter);
    }

    @Override
    public void render(float deltaTime) {
        ScreenUtils.clear(1, 1, 1, 1);

        staticContent();

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
    public void dispose() {
        batch.dispose();
        sr.dispose();
    }
}
