package me.ixidi.skyisland.system.island;

import org.bukkit.entity.Player;

import java.util.UUID;

public interface IslandManager {

    default Island getByOwner(Player player) {
        return getByOwner(player.getUniqueId());
    }

    Island getByOwner(UUID uuid);

    default Island getByMember(Player player) {
        return getByMember(player.getUniqueId());
    }

    default boolean hasIsland(Player player) {
        return hasIsland(player.getUniqueId());
    }

    boolean hasIsland(UUID uuid);

    Island getByMember(UUID uuid);

    Island createNew(UUID owner);

}
