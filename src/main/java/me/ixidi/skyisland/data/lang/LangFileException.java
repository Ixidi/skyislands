package me.ixidi.skyisland.data.lang;

import java.io.File;

public class LangFileException extends RuntimeException {

    public LangFileException(File file) {
        super(String.format("Language file %s does not exist.", file.getName()));
    }

}
