package game.sniper_monkey.view.characterSelection;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import game.sniper_monkey.model.player.fighter.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Kevin Jeryd
 */
public class CharacterSelectionScreenController {
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

    public CharacterSelectionScreenController() {
        this.observers = new ArrayList<>();
    }

    public void create() {
        characterSelectionScreen = new CharacterSelectionScreen(this);
         //characterSelectionScreen.addSelectView(new SelectView());

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
        } else if (fighter == Samurai.class) {
            return FighterFactory.createSamurai();
        } else {
            throw new IllegalArgumentException("No fighter found with that class");
        }
    }

    private void choosePlayer1PrimaryFighter() {
        if (player1PrimaryFighter == null) {
            player1PrimaryFighter = chooseFighterHelper(characterSelectionScreen.fighterList.get(player1SelectedRectangleIndex));
            chosenFighters.put("player1PrimaryFighter", player1PrimaryFighter);
        } else {
            player1PrimaryFighter = chooseFighterHelper(characterSelectionScreen.fighterList.get(player1SelectedRectangleIndex));
            chosenFighters.replace("player1PrimaryFighter", player1PrimaryFighter);
        }
        characterSelectionScreen.selectedFighterView.drawPlayer1PrimaryFighter(player1PrimaryFighter);
        notifyObserversOfPlayer1SelectedCharacter();
    }

    private void choosePlayer1SecondaryFighter() {
        if (player1SecondaryFighter == null) {
            player1SecondaryFighter = chooseFighterHelper(characterSelectionScreen.fighterList.get(player1SelectedRectangleIndex));
            chosenFighters.put("player1SecondaryFighter", player1SecondaryFighter);
        } else {
            player1SecondaryFighter = chooseFighterHelper(characterSelectionScreen.fighterList.get(player1SelectedRectangleIndex));
            chosenFighters.replace("player1SecondaryFighter", player1SecondaryFighter);
        }
        notifyObserversOfPlayer1SelectedCharacter();
    }

    private void choosePlayer2PrimaryFighter() {
        if (player2PrimaryFighter == null) {
            player2PrimaryFighter = chooseFighterHelper(characterSelectionScreen.fighterList.get(player2SelectedRectangleIndex));
            chosenFighters.put("player2PrimaryFighter", player2PrimaryFighter);
        } else {
            player2PrimaryFighter = chooseFighterHelper(characterSelectionScreen.fighterList.get(player2SelectedRectangleIndex));
            chosenFighters.replace("player2PrimaryFighter", player2PrimaryFighter);
        }
        characterSelectionScreen.selectedFighterView.drawPlayer2PrimaryFighter(player2PrimaryFighter);
        notifyObserversOfPlayer2SelectedCharacter();
    }

    private void choosePlayer2SecondaryFighter() {
        if (player2SecondaryFighter == null) {
            player2SecondaryFighter = chooseFighterHelper(characterSelectionScreen.fighterList.get(player2SelectedRectangleIndex));
            chosenFighters.put("player2SecondaryFighter", player2SecondaryFighter);
        } else {
            player2SecondaryFighter = chooseFighterHelper(characterSelectionScreen.fighterList.get(player2SelectedRectangleIndex));
            chosenFighters.replace("player2SecondaryFighter", player2SecondaryFighter);
        }
        notifyObserversOfPlayer2SelectedCharacter();
    }

    public boolean allFightersPicked() {
        return player1PrimaryFighter != null && player1SecondaryFighter != null && player2PrimaryFighter != null && player2SecondaryFighter != null;
    }

    public void handleInput() {
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

    //TODO documentation
    public void registerObserver(ICharacterSelectedObserver observer) {
        observers.add(observer);
    }

    //TODO documentation
    public void unregisterObserver(ICharacterSelectedObserver observer) {
        observers.remove(observer);
    }

    private void notifyObserversOfPlayer1SelectedCharacter() {
        if (chosenFighters.size() == 4) {
            //Go to next state
            System.out.println("GOING TO THE NEXT STATE");
        } else {
            for (ICharacterSelectedObserver observer : observers) {
                observer.onPlayer1CharacterSelected();
            }
        }
    }

    private void notifyObserversOfPlayer2SelectedCharacter() {
        if (chosenFighters.size() == 4) {
            //Go to next state
            System.out.println("GOING TO THE NEXT STATE");
        } else {
            for (ICharacterSelectedObserver observer : observers) {
                observer.onPlayer2CharacterSelected();
            }
        }
    }

    private void notifyObserversOfRemovedCharacter() {
        for (ICharacterSelectedObserver observer : observers) {
            observer.onPlayer2CharacterSelected();
        }
    }


    //Make so you can remove characters
    private void onRemovedCharacter(Fighter fighter) {
        chosenFighters.remove(fighter);
    }

    public void tick(float deltaTime) {
        characterSelectionScreen.render(deltaTime);
    }

    public void dispose() {
        characterSelectionScreen.dispose();
    }


}
