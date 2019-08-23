package me.ixidi.skyisland.system.command;

import lombok.AllArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class SimpleCommandParameters implements CommandParameters {

    private List<String> parameters;

    @Override
    public List<String> asStringList() {
        return new ArrayList<>(parameters);
    }

    @Override
    public boolean isEmpty() {
        return parameters.isEmpty();
    }

    @Override
    public int size() {
        return parameters.size();
    }

    @Override
    public boolean isSet(int position) {
        return position >= 0 && position <= size() - 1;
    }

    @Override
    public boolean isString(int position) {
        assertSet(position);
        return true;
    }

    @Override
    public String getString(int position) {
        assertSet(position);
        return parameters.get(position);
    }

    @Override
    public boolean isInt(int position) {
        return getInt(position) != null;
    }

    @Override
    public Integer getInt(int position) {
        Integer i;
        try {
            i = Integer.parseInt(getString(position));
        } catch (NumberFormatException e) {
            return null;
        }
        return i;
    }

    @Override
    public boolean isDouble(int position) {
        return getDouble(position) != null;
    }

    @Override
    public Double getDouble(int position) {
        Double i;
        try {
            i = Double.parseDouble(getString(position));
        } catch (NumberFormatException e) {
            return null;
        }
        return i;
    }

    @Override
    public boolean isLong(int position) {
        return getLong(position) != null;
    }

    @Override
    public Long getLong(int position) {
        Long i;
        try {
            i = Long.parseLong(getString(position));
        } catch (NumberFormatException e) {
            return null;
        }
        return i;
    }

    @Override
    public boolean isBoolean(int position) {
        return getBoolean(position) != null;
    }

    @Override
    public Boolean getBoolean(int position) {
        switch (getString(position).toLowerCase()) {
            case "true":
            case "yes":
            case "y":
                return true;
            case "false":
            case "no":
            case "n":
                return false;
            default:
                return null;
        }
    }

    @Override
    public boolean isPlayer(int position) {
        return getPlayer(position) != null;
    }

    @Override
    public Player getPlayer(int position) {
        return Bukkit.getPlayer(getString(position));
    }

    private void assertSet(int position) {
        if (!isSet(position)) throw new IndexOutOfBoundsException(String.format("There is not parameter at position %d.", position));
    }
}
