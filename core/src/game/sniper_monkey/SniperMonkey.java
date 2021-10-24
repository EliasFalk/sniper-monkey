package game.sniper_monkey;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import game.sniper_monkey.controller.CharacterSelectionScreenController;
import game.sniper_monkey.controller.IScreenController;

/**
 * Represents the application that is used by the libgdx library.
 *
 * @author Elias Falk
 * @author Vincent Hellner
 * @author Kevin Jeryd
 * @author Dadi Andrason
 */
public class SniperMonkey extends ApplicationAdapter {
    /**
     * The active screen controller that will be ticked each frame.
     */
    public static IScreenController activeController;

    @Override
    public void create() {
        activeController = new CharacterSelectionScreenController();
    }

    @Override
    public void render() {
        float deltaTime = Math.min(1 / 10f, Gdx.graphics.getDeltaTime());
        if (Gdx.graphics.getDeltaTime() > 1) {
            return;
        }
        activeController.tick(deltaTime);
    }


    @Override
    public void resize(int w, int h) {
        activeController.onResize(w, h);
    }

    @Override
    public void dispose() {
        activeController = null;
    }
}
