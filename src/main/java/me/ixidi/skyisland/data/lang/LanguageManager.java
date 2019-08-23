package me.ixidi.skyisland.data.lang;

import me.ixidi.skyisland.data.lang.message.Message;

import java.util.Collections;
import java.util.Map;

public interface LanguageManager {

    Message getMessage(String key);

    default String getString(String key) {
        return getString(key, Collections.emptyMap());
    }

    String getString(String key, Map<String, Object> variables);

    Language getLanguage();

    void setLanguage(Language language);

    void load();

}
