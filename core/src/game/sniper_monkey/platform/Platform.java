package game.sniper_monkey.platform;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import game.sniper_monkey.world.GameObject;

public class Platform extends GameObject {

    /**
     * Creates a platform object with a position.
     * @param position a Vector2 with an (x,y) value.
     */
    public Platform(Vector2 position) {
        super(position);
        setHitboxSize(new Vector2(16, 16));
    }

    @Override
    public void update(float deltaTime) {
        //TODO issa smell here, how fix?
    }
}
