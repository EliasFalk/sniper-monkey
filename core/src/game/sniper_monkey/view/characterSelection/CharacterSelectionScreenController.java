package game.sniper_monkey.view.characterSelection;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import game.sniper_monkey.GameController;
import game.sniper_monkey.IController;
import game.sniper_monkey.SniperMonkey;
import game.sniper_monkey.model.player.fighter.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Used by SniperMonkey
 *  Used by CharacterSelectionScreen
 *  Uses CharacterSelectionScreen
 *  Uses IController
 * </p>
 * @author Kevin Jeryd
 */
public class CharacterSelectionScreenController implements IController {
    CharacterSelectionScreen characterSelectionScreen;

    public int player1SelectedRectangleIndex = 0;
    public int player2SelectedRectangleIndex = 0;
    private Fighter player1PrimaryFighter;
    private Fighter player1SecondaryFighter;
    private Fighter player2PrimaryFighter;
    private Fighter player2SecondaryFighter;
    private final List<ICharacterSelectedObserver> observers;
    public int amountOfFighters;

    private final Map<String, Fighter> chosenFighters = new HashMap<String, Fighter>();

    /**
     * Creates the controller for the character selection screen.
     */
    public CharacterSelectionScreenController() {
        this.observers = new ArrayList<>();
        characterSelectionScreen = new CharacterSelectionScreen(this);
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

    //Could probably refactor the choosePlayer to one method where you send in the fighter that supposed the be set and player1SelectedRectangle index so you can get the fighter.
    //Needs that the drawPlayer method in selectedFighterView is one that takes in parameters instead of many different
    private void choosePlayer1PrimaryFighter() {
        if (player1PrimaryFighter == null) {
            player1PrimaryFighter = FighterFactory.getFighter(characterSelectionScreen.fighterList.get(player1SelectedRectangleIndex));
            chosenFighters.put("player1PrimaryFighter", player1PrimaryFighter);
        } else {
            player1PrimaryFighter = FighterFactory.getFighter(characterSelectionScreen.fighterList.get(player1SelectedRectangleIndex));
            chosenFighters.replace("player1PrimaryFighter", player1PrimaryFighter);
        }
        characterSelectionScreen.selectedFighterView.drawPlayer1PrimaryFighter(player1PrimaryFighter);
        notifyObserversOfPlayer1SelectedCharacter();
    }

    private void choosePlayer1SecondaryFighter() {
        if (player1SecondaryFighter == null) {
            player1SecondaryFighter = FighterFactory.getFighter(characterSelectionScreen.fighterList.get(player1SelectedRectangleIndex));
            chosenFighters.put("player1SecondaryFighter", player1SecondaryFighter);
        } else {
            player1SecondaryFighter = FighterFactory.getFighter(characterSelectionScreen.fighterList.get(player1SelectedRectangleIndex));
            chosenFighters.replace("player1SecondaryFighter", player1SecondaryFighter);
        }
        characterSelectionScreen.selectedFighterView.drawPlayer1SecondaryFighter(player1SecondaryFighter);
        notifyObserversOfPlayer1SelectedCharacter();
    }

    private void choosePlayer2PrimaryFighter() {
        if (player2PrimaryFighter == null) {
            player2PrimaryFighter = FighterFactory.getFighter(characterSelectionScreen.fighterList.get(player2SelectedRectangleIndex));
            chosenFighters.put("player2PrimaryFighter", player2PrimaryFighter);
        } else {
            player2PrimaryFighter = FighterFactory.getFighter(characterSelectionScreen.fighterList.get(player2SelectedRectangleIndex));
            chosenFighters.replace("player2PrimaryFighter", player2PrimaryFighter);
        }
        characterSelectionScreen.selectedFighterView.drawPlayer2PrimaryFighter(player2PrimaryFighter);
        notifyObserversOfPlayer2SelectedCharacter();
    }

    private void choosePlayer2SecondaryFighter() {
        if (player2SecondaryFighter == null) {
            player2SecondaryFighter = FighterFactory.getFighter(characterSelectionScreen.fighterList.get(player2SelectedRectangleIndex));
            chosenFighters.put("player2SecondaryFighter", player2SecondaryFighter);
        } else {
            player2SecondaryFighter = FighterFactory.getFighter(characterSelectionScreen.fighterList.get(player2SelectedRectangleIndex));
            chosenFighters.replace("player2SecondaryFighter", player2SecondaryFighter);
        }
        characterSelectionScreen.selectedFighterView.drawPlayer2SecondaryFighter(player2SecondaryFighter);
        notifyObserversOfPlayer2SelectedCharacter();
    }

    /**
     * Handles the input for the character selection menu.
     */
    public void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.D)){
            setPlayer1SelectedRectangleIndex(1);
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.A)) {
            setPlayer1SelectedRectangleIndex(-1);
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.W)) {
            setPlayer1SelectedRectangleIndex(amountOfFighters/2);
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.S)) {
            setPlayer1SelectedRectangleIndex(-amountOfFighters/2);
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.R)) {
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

    /**
     * Registers observers to the ICharacterSelectedObserver so they can be notified on change.
     * @param observer Is the object that is observing the change.
     */
    public void registerObserver(ICharacterSelectedObserver observer) {
        observers.add(observer);
    }

    private void notifyObserversOfPlayer1SelectedCharacter() {
        if (chosenFighters.size() == 4) {
            SniperMonkey.activeController = new GameController(chosenFighters);
        } else {
            for (ICharacterSelectedObserver observer : observers) {
                observer.onPlayer1CharacterSelected();
            }
        }
    }

    private void notifyObserversOfPlayer2SelectedCharacter() {
        if (chosenFighters.size() == 4) {
            SniperMonkey.activeController = new GameController(chosenFighters);
        } else {
            for (ICharacterSelectedObserver observer : observers) {
                observer.onPlayer2CharacterSelected();
            }
        }
    }

    /**
     *
     * @param deltaTime
     */
    public void tick(float deltaTime) {
        characterSelectionScreen.render(deltaTime);
    }

    public void dispose() {
        characterSelectionScreen.dispose();
    }


}
