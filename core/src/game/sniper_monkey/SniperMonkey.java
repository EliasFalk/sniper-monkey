package game.sniper_monkey;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;

public class SniperMonkey extends ApplicationAdapter {
    private Game game;

    //TODO documentation
    @Override
    public void create() {
        game = new Game();
        game.create();
    }

    //TODO documentation
    @Override
    public void render() {
        float deltaTime = Math.min(1 / 10f, Gdx.graphics.getDeltaTime());
        if (Gdx.graphics.getDeltaTime() > 1) {
            return;
        }
        game.tick(deltaTime);
    }

    //TODO documentation
    @Override
    public void dispose() {
        game.dispose();
    }
}
