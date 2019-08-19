package me.ixidi.skyisland.data.message;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;

import java.util.Map;

public class SimpleComponentParser implements ComponentParser {

    private char colorChar;

    public SimpleComponentParser(char colorChar) {
        this.colorChar = colorChar;
    }

    @Override
    public BaseComponent parse(String message) {

        StringBuilder builder = new StringBuilder();

        BaseComponent rootComponent = new TextComponent();
        BaseComponent currentComponent = new TextComponent();

        char[] chars = message.toCharArray();
        boolean wasLastColor = false;

        for (int i = 0; i < chars.length; i++) {
            char currentChar = chars[i];
            if (currentChar == colorChar) {
                if (i + 1 == chars.length) {
                    builder.append(currentChar);
                    currentComponent.addExtra(builder.toString());
                    break;
                }
                char nextChar = chars[++i];
                ChatColor color = ChatColor.getByChar(nextChar);
                System.out.println(color);
                if (color != null) {
                    if (color == ChatColor.RESET || !wasLastColor) {
                        currentComponent.addExtra(builder.toString());
                        builder.setLength(0);
                        rootComponent.addExtra(currentComponent);
                        currentComponent = new TextComponent();
                    }
                    switch (color) {
                        case RESET:
                            wasLastColor = false;
                            continue;
                        case MAGIC:
                            currentComponent.setObfuscated(true);
                            break;
                        case BOLD:
                            currentComponent.setBold(true);
                            break;
                        case ITALIC:
                            currentComponent.setItalic(true);
                            break;
                        case UNDERLINE:
                            currentComponent.setUnderlined(true);
                            break;
                        case STRIKETHROUGH:
                            currentComponent.setStrikethrough(true);
                            break;
                        default:
                            currentComponent.setColor(color);
                            break;
                    }
                    wasLastColor = true;
                    continue;
                }
            }
            wasLastColor = false;
            builder.append(currentChar);
            if (i + 1 == chars.length) {
                currentComponent.addExtra(builder.toString());
                rootComponent.addExtra(currentComponent);
                break;
            }
        }

        return rootComponent;
    }

}
