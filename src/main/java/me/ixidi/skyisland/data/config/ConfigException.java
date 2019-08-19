package me.ixidi.skyisland.data.config;

import me.ixidi.skyisland.util.error.SkyIslandException;

public class ConfigException extends SkyIslandException {

    public ConfigException(String message) {
        super(message, "Change corrupted property, or restore file to default.");
    }

}
