package me.ixidi.skyisland.data.message;

import net.md_5.bungee.api.chat.BaseComponent;

public interface ComponentParser {

    BaseComponent parse(String message);

}
