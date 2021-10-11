package game.sniper_monkey.model.collision;

import com.badlogic.gdx.math.Vector2;
import game.sniper_monkey.model.world.GameObject;

import java.util.*;
import java.util.Map.Entry;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * A spatial hash which partitions space into multiple cells in a grid. Every partition stores a set
 * of hitboxes and associated GameObjects. It can later be queried for these.
 *
 * @author Vincent Hellner
 */
public final class SpatialHash {

    private final Map<PartitionLocation, ArrayList<CollisionPair>> hash;
    private final int partitionWidth;
    private final int partitionHeight;

    /**
     * Create a spatial hash which partitions hitboxes into a grid that can later be queried.
     *
     * @param partitionWidth  The width of each grid cell.
     * @param partitionHeight The height of each grid cell.
     */
    public SpatialHash(int partitionWidth, int partitionHeight) {
        this.partitionWidth = partitionWidth;
        this.partitionHeight = partitionHeight;

        hash = new HashMap<>();
    }

    /**
     * Clears the spatial hash.
     */
    public void clear() {
        hash.clear();
    }

    /**
     * Removes all references in all partitions of a GameObject in the spatial hash.
     *
     * @param gameObject The GameObject to remove.
     */
    public void remove(GameObject gameObject) {
        for (Entry<PartitionLocation, ArrayList<CollisionPair>> entry : hash.entrySet()) {
            entry.getValue().removeIf(pair -> pair.gameObject == gameObject);
        }
    }

    /**
     * Clears and regenerates the entire spatial hash. This takes into account the updated
     * movement of hitboxes stored in the hash.
     */
    public void regenerate() {
        List<CollisionPair> allPairs = new ArrayList<>();

        for (Entry<PartitionLocation, ArrayList<CollisionPair>> entry : hash.entrySet()) {
            allPairs.addAll(entry.getValue());
        }
        hash.clear();
        insert(allPairs.stream().distinct().collect(Collectors.toList()));
    }

    /**
     * A function generating TilePosition keys for all partitions over the square area overlapped by
     * an offset hitbox and executes a function for every key
     *
     * @param hitbox The hitbox to check overlap with
     * @param offset A vector to offset the hitbox.
     * @param func   The function to execute with the key.
     */
    private void MultiPartitionFunction(Hitbox hitbox, Vector2 offset, Consumer<PartitionLocation> func) {
        Vector2 position = hitbox.getPosition().add(offset);

        //Have to floor before cast because cast turns -0.1 -> 0 while it should be -1 (As floor does)
        int leftX = (int) Math.floor(position.x / partitionWidth);
        int rightX = (int) Math.floor((position.x + hitbox.getSize().x) / partitionWidth);

        int topY = (int) Math.floor((position.y + hitbox.getSize().y) / partitionHeight);
        int bottomY = (int) Math.floor(position.y / partitionHeight);

        for (int x = leftX; x <= rightX; x++) {
            for (int y = bottomY; y <= topY; y++) {
                PartitionLocation key = new PartitionLocation(x, y);
                func.accept(key);
            }
        }
    }

    /**
     * Inserts a GameObject and an associated hitbox into the spacial hash so it can later be queried.
     *
     * @param object The associated GameObject.
     */
    public void insert(GameObject object) {
        CollisionPair pair = new CollisionPair(object, object.getHitbox());
        insert(pair);
    }

    private void insert(List<CollisionPair> pairs) {
        for (CollisionPair pair : pairs) insert(pair);
    }

    private void insert(CollisionPair pair) {
        //Might need to associate with multiple tiles if it overlaps both
        MultiPartitionFunction(pair.hitbox, new Vector2(0, 0), key -> {
            if (hash.containsKey(key)) {
                hash.get(key).add(pair);
            } else {
                ArrayList<CollisionPair> partition = new ArrayList<>();
                partition.add(pair);
                hash.put(key, partition);
            }
        });
    }

    /**
     * Queries the spatial hash and returns all collisionPairs associated with partitions overlapped by an offset hitbox
     *
     * @param hitbox The hitbox to query with.
     * @param offset A vector used to offset the hitbox.
     * @return A list of all collisionPairs
     */
    public List<CollisionPair> query(Hitbox hitbox, Vector2 offset) {
        List<CollisionPair> result = new ArrayList<>();

        MultiPartitionFunction(hitbox, offset, key -> {
            if (hash.containsKey(key))
                result.addAll(hash.get(key));
        });

        //Removes duplicates
        return result.stream().distinct().collect(Collectors.toList());
    }

    /**
     * An x and y position representing the location of a single cell in the partitioning grid.
     *
     * @author Vincent Hellner
     */
    private static final class PartitionLocation {

        private final int x;
        private final int y;

        private PartitionLocation(int x, int y) {
            this.x = x;
            this.y = y;
        }

        // todo documentation
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            PartitionLocation that = (PartitionLocation) o;
            return x == that.x && y == that.y;
        }

        //TODO documentation
        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }
}
