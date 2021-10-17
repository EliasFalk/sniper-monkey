package game.sniper_monkey.view.characterSelection;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;

import java.util.*;

public class SelectView {

    private com.badlogic.gdx.scenes.scene2d.ui.Label fighterNameLabel;
    private SelectViewRectangle selectViewRectangle;
    private Map<Label, SelectViewRectangle> rectangles;
    private float marginBetweenRectangles = 20;
    private float startingX = 100;
    private float startingY = 100;
    private int amountOfFighters = 8;

    public SelectView() {
        rectangles = new HashMap<>();
        for (int i = 0; i < amountOfFighters; i++) {
            createFighterNameLabel("Fighter " + (i+1), (startingX + startingX*i + marginBetweenRectangles*i), startingY - 20);
            selectViewRectangle = new SelectViewRectangle(fighterNameLabel, startingX + startingX*i + marginBetweenRectangles*i, startingY, 100, 100, Color.BLACK);
            rectangles.put(fighterNameLabel, selectViewRectangle);
        }
        rectangles.get(fighterNameLabel).setSelected(true);
    }


    public void addActors(Stage stage) {

    }

    public void createFighterNameLabel(String nameOfFighter, float xPosition, float yPosition) {
        fighterNameLabel = new Label(nameOfFighter, new Label.LabelStyle(new BitmapFont(), Color.BLACK));
        fighterNameLabel.setFontScale(1,1);
        fighterNameLabel.setPosition(xPosition, yPosition);
        fighterNameLabel.setAlignment(Align.center);
    }

}
