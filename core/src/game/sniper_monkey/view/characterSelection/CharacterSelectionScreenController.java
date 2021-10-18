package game.sniper_monkey.view.characterSelection;

public class CharacterSelectionScreenController {
    CharacterSelectionScreen characterSelectionScreen;

    public void create() {
        characterSelectionScreen = new CharacterSelectionScreen();

         //characterSelectionScreen.addSelectView(new SelectView());



    }

    public void tick(float deltaTime) {
        characterSelectionScreen.render(deltaTime);
    }

    public void dispose() {
        characterSelectionScreen.dispose();
    }


}
