package me.ixidi.skyisland.system.island;

import me.ixidi.skyisland.system.grid.GridPosition;
import org.bukkit.Location;

import java.util.List;
import java.util.UUID;

public interface Island {

    int getId();

    GridPosition getGridPosition();

    UUID getOwner();

    List<UUID> getMembers();

    boolean isOwner(UUID uuid);

    boolean isMember(UUID uuid);

    void addMember(UUID uuid);

    void removeMember(UUID uuid);

    void changeOwner(UUID uuid);

    boolean isBlocked(UUID uuid);

    void block(UUID uuid);

    void unlock(UUID uuid);

    Location getHome();

    void setHome(Location location);
}
