package me.ixidi.skyisland.util.location;

import me.ixidi.skyisland.util.integer.IntComparer;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

import java.util.ArrayList;
import java.util.List;

public final class LocationUtils {

    private LocationUtils() {
    }

    public static List<Location> getWithin(Location first, Location second) {

        List<Location> locationList = new ArrayList<>();
        if (first.getWorld() != second.getWorld()) {
            return locationList;
        }

        IntComparer xComparer = new IntComparer(first.getBlockX(), second.getBlockX());
        IntComparer yComparer = new IntComparer(first.getBlockY(), second.getBlockY());
        IntComparer zComparer = new IntComparer(first.getBlockZ(), second.getBlockZ());

        for (int x = xComparer.getSmaller(); x <= xComparer.getBigger(); x++) {
            for (int y = yComparer.getSmaller(); y <= yComparer.getBigger(); y++) {
                for (int z = zComparer.getSmaller(); z <= zComparer.getBigger(); z++) {
                    locationList.add(new Location(first.getWorld(), x, y, z));
                }
            }
        }

        return locationList;
    }

    public static Location firstAbove(Material material, Location start) {
        Block block = start.getBlock();
        do {
            block = block.getRelative(BlockFace.UP);
        } while (block.getType() != material);
        return block.getLocation();
    }

    public static boolean isSimilar(Location first, Location second) {
        return first.getWorld() == second.getWorld() && first.getBlockX() == second.getBlockX() && first.getBlockZ() == second.getBlockZ() && first.getBlockY() == second.getBlockY();
    }
}
