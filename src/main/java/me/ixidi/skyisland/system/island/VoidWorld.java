package me.ixidi.skyisland.system.island;

import me.ixidi.skyisland.util.world.EmptyChunkGenerator;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;

public class VoidWorld {

    private String worldName;

    public VoidWorld(String worldName) {
        this.worldName = worldName;
    }

    public World get(boolean createIfNotExist) {
        World world = Bukkit.getWorld(worldName);
        if (createIfNotExist && world == null) {
            world = new WorldCreator(worldName)
                    .environment(World.Environment.NORMAL)
                    .generator(new EmptyChunkGenerator())
                    .generateStructures(false)
                    .type(WorldType.FLAT)
                    .createWorld();
        }
        return world;
    }

}
