package me.ixidi.skyisland.data.lang;

import me.ixidi.skyisland.data.lang.message.Message;

public interface LanguageManager {

    Message getMessage(String key);

    Language getLanguage();

    void setLanguage(Language language);

    void load();

}
