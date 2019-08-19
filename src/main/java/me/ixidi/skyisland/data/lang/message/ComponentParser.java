package me.ixidi.skyisland.data.lang.message;

import net.md_5.bungee.api.chat.BaseComponent;

public interface ComponentParser {

    BaseComponent parse(String message);

}
