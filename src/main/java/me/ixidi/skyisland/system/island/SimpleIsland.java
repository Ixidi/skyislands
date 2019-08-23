package me.ixidi.skyisland.system.island;

import me.ixidi.skyisland.system.grid.GridPosition;
import me.ixidi.skyisland.util.location.LocationUtils;
import org.bukkit.Location;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SimpleIsland implements Island {

    private IslandManager islandManager;
    private int id;
    private GridPosition gridPosition;
    private UUID owner;
    private List<UUID> members = new ArrayList<>();
    private List<UUID> blocked = new ArrayList<>();
    private Location home;

    public SimpleIsland(IslandManager islandManager, int id, GridPosition gridPosition, UUID owner) {
        this.islandManager = islandManager;
        this.id = id;
        this.gridPosition = gridPosition;
        this.owner = owner;
        this.home = LocationUtils.firstAbove(Material.AIR, gridPosition.getCenter());
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public GridPosition getGridPosition() {
        return gridPosition;
    }

    @Override
    public UUID getOwner() {
        return owner;
    }

    @Override
    public List<UUID> getMembers() {
        return new ArrayList<>(members);
    }

    @Override
    public boolean isOwner(UUID uuid) {
        return uuid.equals(owner);
    }

    @Override
    public boolean isMember(UUID uuid) {
        return members.contains(uuid);
    }

    @Override
    public void addMember(UUID uuid) {
        if (members.contains(uuid)) {
            throw new IslandException(String.format("%s is already member of this island.", uuid.toString()));
        }
        if (islandManager.hasIsland(uuid)) {
            throw new IslandException(String.format("%s is already member of another island.", uuid.toString()));
        }
        members.add(uuid);
    }

    @Override
    public void removeMember(UUID uuid) {
        if (!members.contains(uuid)) {
            throw new IslandException(String.format("%s is not member of this island.", uuid.toString()));
        }
        members.remove(uuid);
    }

    @Override
    public void changeOwner(UUID uuid) {
        if (uuid.equals(owner)) {
            throw new IslandException(String.format("%s is already owner of this island.", uuid.toString()));
        }
        if (!isMember(uuid)) {
            throw new IslandException(String.format("%s is not member of this island.", uuid.toString()));
        }
        owner = uuid;
    }

    @Override
    public boolean isBlocked(UUID uuid) {
        return blocked.contains(uuid);
    }

    @Override
    public void block(UUID uuid) {
        if (isBlocked(uuid)) {
            throw new IslandException(String.format("%s is already blocked.", uuid.toString()));
        }
        if (isMember(uuid)) {
            throw new IslandException(String.format("%s is member of this island, cannot be blocked.", uuid.toString()));
        }
        blocked.add(uuid);
    }

    @Override
    public void unlock(UUID uuid) {
        if (!isBlocked(uuid)) {
            throw new IslandException(String.format("%s is not blocked.", uuid.toString()));
        }
        blocked.remove(uuid);
    }

    @Override
    public Location getHome() {
        return home;
    }

    @Override
    public void setHome(Location location) {
        if (!gridPosition.isInside(location)) {
            throw new IslandException("Location is not inside island.");
        }
        home = location;
    }
}
