package game.sniper_monkey.collision;

import com.badlogic.gdx.math.Vector2;

public class Hitbox
{
    private Vector2 position;
    private Vector2 size;

    public Hitbox(Vector2 position, Vector2 size)
    {
        this.position = position;
        this.size = size;
    }

    public boolean isOverlapping(Hitbox other)
    {
        return  (position.x < other.position.x + other.size.x &&
                position.x + size.x > other.position.x &&
                position.y < other.position.y + other.size.y &&
                position.y + size.y > other.position.y);
    }

    public void setPosition(Vector2 newPos)
    {
        position = newPos;
    }
}
