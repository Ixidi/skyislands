package me.ixidi.skyisland.data.lang;

import me.ixidi.skyisland.data.config.YamlFileConfig;
import me.ixidi.skyisland.data.lang.message.Message;
import me.ixidi.skyisland.data.lang.message.MessageParser;
import me.ixidi.skyisland.data.lang.message.SimpleMessage;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class YamlLanguageManager implements LanguageManager {

    private File folder;
    private String fileNameFormat;
    private Language currentLanguage;
    private MessageParser messageParser;

    private Map<Language, Map<String, Message>> languageMap = new HashMap<>();

    public YamlLanguageManager(File folder, String fileNameFormat, Language currentLanguage, MessageParser messageParser) {
        this.folder = folder;
        this.fileNameFormat = fileNameFormat;
        this.currentLanguage = currentLanguage;
        this.messageParser = messageParser;
    }

    @Override
    public Message getMessage(String key) {
        Message message = languageMap.get(currentLanguage).get(key);
        if (message == null) {
            message = new SimpleMessage(String.format("{There is not %s message in lang %s.}", key, currentLanguage.getEnglishName()), messageParser);
        }
        return message;
    }

    @Override
    public Language getLanguage() {
        return this.currentLanguage;
    }

    @Override
    public void setLanguage(Language language) {
        this.currentLanguage = language;
    }

    @Override
    public void load() {
        languageMap.clear();
        for (Language value : Language.values()) {
            String name = String.format(fileNameFormat, value.getCode());
            File file = new File(folder, name);
            if (!file.exists()) {
                throw new LangFileException(file);
            }

            YamlFileConfig config = YamlFileConfig.create(file);
            config.load();

            Map<String, Message> messageMap = new HashMap<>();
            for (String key : config.getKeys(true)) {
                messageMap.put(key, new SimpleMessage(config.getString(key), messageParser));
            }

            languageMap.put(value, messageMap);
        }
    }

}
