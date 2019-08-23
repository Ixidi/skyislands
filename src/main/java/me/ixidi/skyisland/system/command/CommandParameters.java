package me.ixidi.skyisland.system.command;

import org.bukkit.entity.Player;

import java.util.List;

public interface CommandParameters {

    List<String> asStringList();

    boolean isEmpty();

    int size();

    boolean isSet(int position);

    boolean isString(int position);

    String getString(int position);

    boolean isInt(int position);

    Integer getInt(int position);

    boolean isDouble(int position);

    Double getDouble(int position);

    boolean isLong(int position);

    Long getLong(int position);

    boolean isBoolean(int position);

    Boolean getBoolean(int position);

    boolean isPlayer(int position);

    Player getPlayer(int position);

}
