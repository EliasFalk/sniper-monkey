package game.sniper_monkey.utils.collision;

/**
 * Static collision utility used for masking collisions, containing predefined masks.
 *
 * @author Vincent Hellner
 */
public final class CollisionMasks
{
    private CollisionMasks() {}

    public static final int PLAYER_1 = 0b0001;
    public static final int PLAYER_2 = 0b0010;
    public static final int WALL = 0b0100;
    public static final int PROJECTILE = 0b1000;

    public static boolean areMasksOverlapping(int mask1, int mask2) {
        return (mask1 & mask2) > 0;
    }
}
