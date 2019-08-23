package me.ixidi.skyisland.system.grid;

import me.ixidi.skyisland.util.location.LocationUtils;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.List;

public class SimpleGridPosition implements GridPosition {

    private Location center;
    private Location upper;
    private Location lower;

    private Location maxUpper;
    private Location maxLower;

    private List<Location> inside;
    private List<Location> insideMax;

    public SimpleGridPosition(Location center, int size, int maxSize, int minY, int maxY) {
        this.center = center;
        int centerX = center.getBlockX();
        int centerZ = center.getBlockZ();

        World world = center.getWorld();

        int side = size / 2;
        this.upper = new Location(world, centerX + side, maxY, centerZ + side - ((size % 2 == 0) ? 1 : 0));
        this.lower = new Location(world, centerX - side, minY, centerZ - side);

        int maxSide = maxSize / 2;
        this.maxUpper = new Location(world, centerX + maxSide, maxY, centerZ + maxSide - ((maxSize % 2 == 0) ? 1 : 0));
        this.maxLower = new Location(world, centerX - maxSide, minY, centerZ - maxSide);

        insideMax = LocationUtils.getWithin(maxLower, maxUpper);
        recalc();
    }

    @Override
    public Location getUpper() {
        return upper;
    }

    @Override
    public Location getLower() {
        return lower;
    }

    @Override
    public void setUpper(Location location) {
        if (isInsideMax(location)) {
            this.upper = location;
        }
    }

    @Override
    public void setLower(Location location) {
        if (isInsideMax(location)) {
            this.lower = location;
        }
    }

    @Override
    public Location getMaxUpper() {
        return maxUpper;
    }

    @Override
    public Location getMaxLower() {
        return maxLower;
    }

    @Override
    public boolean isInside(Location location) {
        return inside.stream().anyMatch(loc -> LocationUtils.isSimilar(loc, location));
    }

    @Override
    public boolean isInsideMax(Location location) {
        return insideMax.stream().anyMatch(loc -> LocationUtils.isSimilar(loc, location));
    }

    @Override
    public Location getCenter() {
        return null;
    }

    private void recalc() {
        inside = LocationUtils.getWithin(lower, upper);
    }

}
