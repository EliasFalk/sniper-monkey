package game.sniper_monkey;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import game.sniper_monkey.controller.CharacterSelectionScreenController;
import game.sniper_monkey.controller.GameController;
import game.sniper_monkey.controller.IController;

public class SniperMonkey extends ApplicationAdapter {
    private GameController gameController;
    public static IController activeController = new IController() {
        @Override
        public void tick(float deltaTime) {

        }
    };

    private CharacterSelectionScreenController characterSelectionScreenController;
    //TODO documentation
    @Override
    public void create() {
        activeController = new CharacterSelectionScreenController();
    }

    //TODO documentation
    @Override
    public void render() {
        float deltaTime = Math.min(1 / 10f, Gdx.graphics.getDeltaTime());
        if (Gdx.graphics.getDeltaTime() > 1) {
            return;
        }
        activeController.tick(deltaTime);
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
