package me.ixidi.skyisland.data.config;

import lombok.AllArgsConstructor;
import org.bukkit.configuration.ConfigurationSection;

import java.util.List;
import java.util.Set;

@AllArgsConstructor
public class BukkitConfigurationSectionWrapper implements Config {

    private String configName;
    private ConfigurationSection configuration;

    @Override
    public String getString(String key) {
        String value = configuration.getString(key);
        if (value == null) throw new MissingConfigPropertyException(configName, key);
        return value;
    }

    @Override
    public int getInt(String key) {
        Object value = configuration.get(key);
        if (value == null || value.getClass() != int.class) throw new MissingConfigPropertyException(configName, key);
        return (int) value;
    }

    @Override
    public long getLong(String key) {
        Object value = configuration.get(key);
        if (value == null || value.getClass() != long.class) throw new MissingConfigPropertyException(configName, key);
        return (long) value;
    }

    @Override
    public double getDouble(String key) {
        Object value = configuration.get(key);
        if (value == null || value.getClass() != double.class) throw new MissingConfigPropertyException(configName, key);
        return (double) value;
    }

    @Override
    public boolean getBoolean(String key) {
        Object value = configuration.get(key);
        if (value == null || value.getClass() != boolean.class) throw new MissingConfigPropertyException(configName, key);
        return (boolean) value;
    }

    @Override
    public List<String> getList(String key) {
        Object value = configuration.get(key);
        if (value == null) throw new MissingConfigPropertyException(configName, key);
        return configuration.getStringList(key);
    }

    @Override
    public Config getSection(String key) {
        if (!isSection(key)) {
            throw new MissingConfigPropertyException(configName, key);
        }
        return new BukkitConfigurationSectionWrapper(configName, configuration.getConfigurationSection(key));
    }

    @Override
    public Set<String> getKeys(boolean deep) {
        return configuration.getKeys(deep);
    }

    @Override
    public boolean isSection(String key) {
        return configuration.isConfigurationSection(key);
    }

    @Override
    public void set(String key, Object value) {
        configuration.set(key, value);
    }

}
