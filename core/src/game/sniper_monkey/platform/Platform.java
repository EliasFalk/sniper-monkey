package game.sniper_monkey.platform;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import game.sniper_monkey.world.GameObject;

public class Platform extends GameObject {

    public Platform(Vector2 position) {
        super(position, new Sprite(new Texture("platform.png")));
    }

    @Override
    public void update(float deltaTime) {

    }
}
