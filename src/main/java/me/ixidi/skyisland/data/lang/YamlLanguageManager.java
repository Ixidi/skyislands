package me.ixidi.skyisland.data.lang;

import me.ixidi.skyisland.data.config.Config;
import me.ixidi.skyisland.data.config.YamlFileConfig;
import me.ixidi.skyisland.data.lang.message.Message;
import me.ixidi.skyisland.data.lang.message.MessageParser;
import me.ixidi.skyisland.data.lang.message.SimpleMessage;
import me.ixidi.skyisland.data.lang.message.VariablesParser;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class YamlLanguageManager implements LanguageManager {

    private File folder;
    private String fileNameFormat;
    private Language currentLanguage;
    private MessageParser messageParser;
    private VariablesParser variablesParser;

    private Map<Language, LangContent> languageMap = new HashMap<>();

    public YamlLanguageManager(File folder, String fileNameFormat, Language currentLanguage, MessageParser messageParser, VariablesParser variablesParser) {
        this.folder = folder;
        this.fileNameFormat = fileNameFormat;
        this.currentLanguage = currentLanguage;
        this.messageParser = messageParser;
        this.variablesParser = variablesParser;
    }

    @Override
    public Message getMessage(String key) {
        Message message = languageMap.get(currentLanguage).messageMap.get(key);
        if (message == null) {
            message = new SimpleMessage(String.format("{There is not %s message in lang %s.}", key, currentLanguage.getEnglishName()), messageParser);
        }
        return message;
    }

    @Override
    public String getString(String key, Map<String, Object> variables) {
        String string = languageMap.get(currentLanguage).stringMap.get(key);
        if (string == null) {
            string = String.format("Missing string %s", key);
        }
        return variablesParser.parse(string, variables);
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
            LangContent content = new LangContent();

            String name = String.format(fileNameFormat, value.getCode());
            File file = new File(folder, name);
            if (!file.exists()) {
                throw new LangFileException(file);
            }

            YamlFileConfig config = YamlFileConfig.create(file);
            config.load();

            if (config.isSection("message")) {
                Config section = config.getSection("message");
                for (String key : section.getKeys(true)) {
                    content.messageMap.put(key, new SimpleMessage(section.getString(key), messageParser));
                }
            }

            if (config.isSection("string")) {
                Config section = config.getSection("string");
                for (String key : section.getKeys(true)) {
                    content.stringMap.put(key, section.getString(key));
                }
            }

            languageMap.put(value, content);
        }
    }

    private class LangContent {

        private Map<String, Message> messageMap = new HashMap<>();
        private Map<String, String> stringMap = new HashMap<>();

    }
}
