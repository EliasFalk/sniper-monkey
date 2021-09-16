package game.sniper_monkey.collision;

import game.sniper_monkey.*;

public class CollisionPair
{
    public final GameObject gameObject;
    public final Hitbox hitbox;
    public CollisionPair(GameObject gameObject, Hitbox hitbox)
    {
        this.gameObject = gameObject;
        this.hitbox = hitbox;
    }
}
