package me.ixidi.skyisland.system.island;

import me.ixidi.skyisland.system.grid.Grid;
import org.bukkit.entity.Player;

import java.util.Set;
import java.util.UUID;

public class SimpleIslandManager implements IslandManager {

    private Set<Island> islandSet;
    private Grid grid;

    public SimpleIslandManager(Set<Island> islandSet, Grid grid) {
        this.islandSet = islandSet;
        this.grid = grid;
    }

    @Override
    public Island getByOwner(UUID uuid) {
        return islandSet.stream().filter(island -> island.getOwner().equals(uuid)).findFirst().orElse(null);
    }

    @Override
    public boolean hasIsland(UUID uuid) {
        return getByMember(uuid) != null;
    }

    @Override
    public Island getByMember(UUID uuid) {
        return islandSet.stream().filter(island -> island.getMembers().contains(uuid)).findFirst().orElse(null);
    }

    @Override
    public Island createNew(UUID owner) {
        return null;
    }

}
