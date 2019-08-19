package me.ixidi.skyisland.data.config;

import me.ixidi.skyisland.util.error.SkyIslandException;

public class MissingConfigPropertyException extends SkyIslandException {

    public MissingConfigPropertyException(String configName, String key) {
        super(String.format("Missing property %s in %s.", key, configName), "Create missing property, or restore file to default.");
    }
}
