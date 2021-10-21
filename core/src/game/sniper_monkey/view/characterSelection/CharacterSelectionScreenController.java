package game.sniper_monkey.view.characterSelection;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import game.sniper_monkey.model.player.fighter.EvilWizard;
import game.sniper_monkey.model.player.fighter.Fighter;
import game.sniper_monkey.model.player.fighter.FighterFactory;
import game.sniper_monkey.model.player.fighter.HuntressBow;

public class CharacterSelectionScreenController {
    CharacterSelectionScreen characterSelectionScreen;

    public int player1SelectedRectangleIndex = 0;
    public int player2SelectedRectangleIndex = 0;
    private Fighter player1PrimaryFighter;
    private Fighter player1SecondaryFighter;
    private Fighter player2PrimaryFighter;
    private Fighter player2SecondaryFighter;

    public int amountOfFighters;

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
        } else {
            throw new IllegalArgumentException("No fighter found with that class");
        }
    }

    private void choosePlayer1PrimaryFighter() {
        player1PrimaryFighter = chooseFighterHelper(characterSelectionScreen.fighterList.get(player1SelectedRectangleIndex));
    }

    private void choosePlayer1SecondaryFighter() {
        player1SecondaryFighter = chooseFighterHelper(characterSelectionScreen.fighterList.get(player1SelectedRectangleIndex));
    }

    private void choosePlayer2PrimaryFighter() {
        player2PrimaryFighter = chooseFighterHelper(characterSelectionScreen.fighterList.get(player2SelectedRectangleIndex));
    }

    private void choosePlayer2SecondaryFighter() {
        player2SecondaryFighter = chooseFighterHelper(characterSelectionScreen.fighterList.get(player2SelectedRectangleIndex));
    }

    public boolean allFightersPicked() {
        if (player1PrimaryFighter != null && player1SecondaryFighter != null && player2PrimaryFighter != null && player2SecondaryFighter != null) {
            return true;
        } else {
            return false;
        }
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

    public void tick(float deltaTime) {
        characterSelectionScreen.render(deltaTime);
    }

    public void dispose() {
        characterSelectionScreen.dispose();
    }


}
