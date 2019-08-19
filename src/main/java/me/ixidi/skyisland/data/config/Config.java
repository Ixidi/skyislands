package me.ixidi.skyisland.data.config;

import java.util.List;
import java.util.Set;

public interface Config {

    String getString(String key);

    int getInt(String key);

    long getLong(String key);

    double getDouble(String key);

    boolean getBoolean(String key);

    List<String> getList(String key);

    Config getSection(String key);

    Set<String> getKeys(boolean deep);

    boolean isSection(String key);

    void set(String key, Object value);

}
