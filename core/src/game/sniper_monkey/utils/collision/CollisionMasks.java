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

    /**
     * Checks if two collision masks are overlapping, i.e they mask each other out and should not collide
     * @param mask1 The first mask
     * @param mask2 The second mask
     * @return      A boolean representing whether or not the overlap
     */
    public static boolean areMasksOverlapping(int mask1, int mask2) {
        return (mask1 & mask2) > 0;
    }
}
