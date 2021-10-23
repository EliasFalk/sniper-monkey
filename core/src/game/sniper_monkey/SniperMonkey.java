package game.sniper_monkey;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import game.sniper_monkey.controller.CharacterSelectionScreenController;
import game.sniper_monkey.controller.IScreenController;

/**
 * Represents the application
 *
 * @author Elias Falk
 * @author Vincent Hellner
 * @author Kevin Jeryd
 * @author Dadi Andrason
 */
public class SniperMonkey extends ApplicationAdapter {
    /**
     * The active screen controller to
     */
    public static IScreenController activeController = new IScreenController() {
        @Override
        public void tick(float deltaTime) {

        }

        @Override
        public void onResize(int w, int h) {

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


    @Override
    public void resize(int w, int h) {
        activeController.onResize(w, h);
    }
}
