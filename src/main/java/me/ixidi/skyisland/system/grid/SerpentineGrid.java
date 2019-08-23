package me.ixidi.skyisland.system.grid;

import me.ixidi.skyisland.system.island.VoidWorld;
import me.ixidi.skyisland.util.location.LocationUtils;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SerpentineGrid implements Grid {

    private int size;
    private int maxSize;
    private int freeSpaceSize;
    private int minY;
    private int maxY;
    private VoidWorld world;
    private Location center;
    private Set<Location> busyCenterSet = new HashSet<>();

    public SerpentineGrid(int size, int maxSize, int freeSpaceSize, int minY, int maxY, VoidWorld world, Location center, Set<Location> busyCenterSet) {
        this.size = size;
        this.maxSize = maxSize;
        this.freeSpaceSize = freeSpaceSize;
        this.minY = minY;
        this.maxY = maxY;
        this.world = world;
        this.center = center;
        this.busyCenterSet.addAll(busyCenterSet);
    }

    @Override
    public GridPosition takeFirstFree() {
        world.get(true);
        if (busyCenterSet.stream().noneMatch(location -> LocationUtils.isSimilar(location, center))) {
            return new SimpleGridPosition(center, size, maxSize, minY, maxY);
        }

        Direction direction = Direction.first();
        double times = 1;

        Location checkLocation = center;
        do {
            for (int i = 0; i < ((int) times); i++) {
                int distance = freeSpaceSize + maxSize;
                checkLocation.add(direction.multiplierX * distance, 0, direction.multiplierZ * distance);
            }
            direction = direction.next();
        } while (busyCenterSet.stream().anyMatch(location -> LocationUtils.isSimilar(location, checkLocation)));

        GridPosition position = new SimpleGridPosition(checkLocation, size, maxSize, minY, maxY);
        busyCenterSet.add(checkLocation);

        return position;
    }

    @Override
    public void setFree(GridPosition position) {
        busyCenterSet.remove(position.getCenter());
    }

    private enum Direction {
        FORWARD(1, 0),
        BACKWARD(-1, 0),
        LEFT(0, -1),
        RIGHT(0, 1);

        private final int multiplierX;
        private final int multiplierZ;

        Direction(int multiplierX, int multiplierZ) {
            this.multiplierX = multiplierX;
            this.multiplierZ = multiplierZ;
        }

        public Direction next() {
            switch (this) {
                case FORWARD:
                    return RIGHT;
                case RIGHT:
                    return BACKWARD;
                case BACKWARD:
                    return LEFT;
                case LEFT:
                    return FORWARD;
                default:
                    throw new RuntimeException();
            }
        }

        public static Direction first() {
            return FORWARD;
        }

    }

}
