package me.ixidi.skyisland.system.grid;

import org.bukkit.Location;

public interface GridPosition {

    Location getUpper();

    Location getLower();

    void setUpper(Location location);

    void setLower(Location location);

    Location getMaxUpper();

    Location getMaxLower();

    boolean isInside(Location location);

    boolean isInsideMax(Location location);

    Location getCenter();

}
