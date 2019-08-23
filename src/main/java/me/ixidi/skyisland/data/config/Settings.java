package me.ixidi.skyisland.data.config;

import lombok.AccessLevel;
import lombok.Getter;
import me.ixidi.skyisland.data.config.mapper.annotation.ConfigAfterMapping;
import me.ixidi.skyisland.data.config.mapper.annotation.ConfigExclude;
import me.ixidi.skyisland.data.config.mapper.annotation.ConfigName;
import me.ixidi.skyisland.data.lang.Language;

@Getter
public class Settings {

    @Getter(AccessLevel.NONE)
    @ConfigName("lang")
    private String _langCode;

    private String worldName;

    private int minIslandY;
    private int maxIslandY;

    private int islandSize;
    private int islandMaxSize;

    private int islandFreeSpace;

    @ConfigExclude
    private Language language;

    @SuppressWarnings("unused")
    @ConfigAfterMapping
    private void init() {
        language = Language.getByCode(_langCode);
        if (language == null) {
            throw new ConfigException(String.format("There is not language with code %s.", _langCode));
        }
    }


}
