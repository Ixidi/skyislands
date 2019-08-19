package me.ixidi.skyisland.data.lang.message;

import net.md_5.bungee.api.chat.BaseComponent;

import java.util.Collections;
import java.util.Map;

public interface MessageParser {

    default BaseComponent parse(String message) {
        return parse(message, Collections.emptyMap());
    }

    BaseComponent parse(String message, Map<String, Object> variables);

}
