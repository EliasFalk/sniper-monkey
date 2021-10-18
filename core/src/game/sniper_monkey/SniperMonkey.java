package game.sniper_monkey;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import game.sniper_monkey.model.TimerBank;

public class SniperMonkey extends ApplicationAdapter {
    private GameController gameController;

    //TODO documentation
    @Override
    public void create() {
        gameController = new GameController();
        gameController.create();
    }

    //TODO documentation
    @Override
    public void render() {
        float deltaTime = Math.min(1 / 10f, Gdx.graphics.getDeltaTime());
        if (Gdx.graphics.getDeltaTime() > 1) {
            return;
        }
        gameController.tick(deltaTime);
        TimerBank.updateTimers(deltaTime);
    }

    //TODO documentation
    @Override
    public void dispose() {
        gameController.dispose();
    }
}
