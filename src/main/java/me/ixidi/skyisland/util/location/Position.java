package me.ixidi.skyisland.util.location;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.World;

@Getter
@AllArgsConstructor
public class Position {

    private double x;
    private double y;
    private double z;

    public Location toLocation(World world) {
        return new Location(world, x, y, z);
    }

}
