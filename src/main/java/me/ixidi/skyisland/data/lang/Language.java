package me.ixidi.skyisland.data.lang;

import java.util.Arrays;

public enum Language {

    ENGLISH("English", "English", "en"),
    POLISH("Polish", "Polski", "pl");

    public static Language getByCode(String code) {
        return Arrays.stream(values()).filter(language -> language.code.equalsIgnoreCase(code)).findFirst().orElse(null);
    }

    private String englishName;
    private String localName;
    private String code;

    Language(String englishName, String localName, String code) {
        this.englishName = englishName;
        this.localName = localName;
        this.code = code;
    }

    public String getEnglishName() {
        return englishName;
    }

    public String getLocalName() {
        return localName;
    }

    public String getCode() {
        return code;
    }

}
