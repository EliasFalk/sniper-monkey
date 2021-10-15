package game.sniper_monkey.utils.collision;

/**
 * Static collision utility used for masking collisions, containing predefined masks.
 *
 * @author Vincent Hellner
 */
public final class CollisionMasks
{
    private CollisionMasks() {}

    public static final int PLAYER_1 = 0b00001;
    public static final int PLAYER_2 = 0b00010;
    public static final int WALL = 0b00100;
    public static final int PROJECTILE = 0b01000;
    public static final int GHOST = 0b10000;

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
