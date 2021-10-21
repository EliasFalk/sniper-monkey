package game.sniper_monkey;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import game.sniper_monkey.view.characterSelection.CharacterSelectionScreen;
import game.sniper_monkey.view.characterSelection.CharacterSelectionScreenController;
import game.sniper_monkey.model.TimerBank;

public class SniperMonkey extends ApplicationAdapter {
    //private GameController gameController;

    private CharacterSelectionScreenController characterSelectionScreenController;
    //TODO documentation
    @Override
    public void create() {
        /*gameController = new GameController();
        gameController.create();*/
        characterSelectionScreenController = new CharacterSelectionScreenController();
        characterSelectionScreenController.create();
    }

    //TODO documentation
    @Override
    public void render() {
        float deltaTime = Math.min(1 / 10f, Gdx.graphics.getDeltaTime());
        if (Gdx.graphics.getDeltaTime() > 1) {
            return;
        }
        characterSelectionScreenController.tick(deltaTime);
        //gameController.tick(deltaTime);
        //gameController.tick(deltaTime);
        TimerBank.updateTimers(deltaTime);
    }

    //TODO documentation
    @Override
    public void dispose() {
        characterSelectionScreenController.dispose();
        //gameController.dispose();
    }

    /*
    @Override
    public void resize(int w, int h) {
        gameController.onResize(w, h);
    }
     */
}
