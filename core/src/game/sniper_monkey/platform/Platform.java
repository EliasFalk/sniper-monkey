package game.sniper_monkey.platform;

import com.badlogic.gdx.math.Vector2;
import game.sniper_monkey.world.GameObject;

/**
 * A stationary GameObject representing a platform with collision.
 *
 * @author Kevin Jeryd
 */
public class Platform extends GameObject {

    /**
     * Creates a platform object with a position.
     *
     * @param position a Vector2 with an (x,y) value.
     */
    public Platform(Vector2 position) {
        super(position, false);
        setHitboxSize(new Vector2(16, 16));
    }
}
