package game.sniper_monkey.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import game.sniper_monkey.world.GameObject;

import java.util.Vector;

public class Player extends GameObject {

    /**
     * Creates a player with a sprite and a position in the world
     * @param position
     * @param sprite
     */
    public Player(Vector2 position, Sprite sprite) {
        super(position, sprite);

    }

    /**
     * Creates a Player object with a sprite
     * @param sprite
     */
    public Player(Sprite sprite) {
        super(sprite);
    }

    /**
     * Updates the class player every frame
     * @param deltaTime
     */
    @Override
    public void update(float deltaTime) {
    }
}
