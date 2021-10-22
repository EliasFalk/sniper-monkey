package game.sniper_monkey.model.world_brick;

import com.badlogic.gdx.math.Vector2;
import game.sniper_monkey.model.world.GameObject;

/**
 * A stationary GameObject representing a brick with collision.
 *
 * @author Kevin Jeryd
 * @author Vincent Hellner
 * @author Elias Falk
 */
public class WorldBrick extends GameObject {

    /**
     * The type of WorldBrick
     */
    public final String type;

    /**
     * Creates a platform object with a position.
     *
     * @param position a Vector2 with an (x,y) value.
     * @param type The type of WorldBrick, prefixed with "ghost-" if the world brick should be ignored in collision response
     */
    public WorldBrick(Vector2 position, String type) {
        super(position, false);
        this.type = type;
        setHitboxSize(new Vector2(16, 16));
        setGhost(type.startsWith("ghost-"));
    }
}
