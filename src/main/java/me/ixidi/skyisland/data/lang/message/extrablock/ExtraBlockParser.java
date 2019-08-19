package me.ixidi.skyisland.data.lang.message.extrablock;

import net.md_5.bungee.api.chat.BaseComponent;

public interface ExtraBlockParser {

    BaseComponent parse(String block);

    ExtraBlockMatcher getMatcher();

}
