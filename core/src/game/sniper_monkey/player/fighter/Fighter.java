package game.sniper_monkey.player.fighter;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import game.sniper_monkey.world.GameObject;

public class Fighter extends GameObject {

    public Fighter(Vector2 position, Sprite sprite) {
        super(position, sprite);
    }

    public Fighter(Sprite sprite) {
        super(sprite);
    }

    @Override
    public void update(float deltaTime) {

    }
}
