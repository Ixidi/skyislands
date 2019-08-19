package me.ixidi.skyisland.data.config;

import me.ixidi.skyisland.data.config.mapper.annotation.ConfigAfterMapping;
import me.ixidi.skyisland.data.config.mapper.annotation.ConfigExclude;
import me.ixidi.skyisland.data.config.mapper.annotation.ConfigName;
import me.ixidi.skyisland.data.lang.Language;

public class Settings {

    @ConfigName("lang")
    private String langCode;

    @ConfigExclude
    private Language language;

    @SuppressWarnings("unused")
    @ConfigAfterMapping
    private void init() {
        language = Language.getByCode(langCode);
        if (language == null) {
            throw new ConfigException(String.format("There is not language with code %s.", langCode));
        }
    }

    public Language getLanguage() {
        return language;
    }
}
