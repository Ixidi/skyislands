package me.ixidi.skyisland.data.config;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class YamlFileConfig extends BukkitConfigurationSectionWrapper implements FileConfig {

    private File file;
    private YamlConfiguration yaml;

    public static YamlFileConfig create(File file) {
        return new YamlFileConfig(file, new YamlConfiguration());
    }

    private YamlFileConfig(File file, YamlConfiguration yaml) {
        super(file.getName(), yaml);
        this.file = file;
        this.yaml = yaml;
    }

    @Override
    public void save() {
        try {
            yaml.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void load() {
        try {
            yaml.load(file);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

}
