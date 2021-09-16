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

    public boolean isOverlapping(Hitbox other, Vector2 offset)
    {
        Vector2 offsetPosition = position;
        offsetPosition.add(offset);

        return  (offsetPosition.x < other.position.x + other.size.x &&
                offsetPosition.x + size.x > other.position.x &&
                offsetPosition.y < other.position.y + other.size.y &&
                offsetPosition.y + size.y > other.position.y);
    }

    public void setPosition(Vector2 newPos)
    {
        position = newPos;
    }
    public Vector2 getPosition() { return position; }
}
