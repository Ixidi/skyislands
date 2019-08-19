package me.ixidi.skyisland.data.message;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.ixidi.skyisland.data.message.extrablock.ExtraBlockMatcher;
import me.ixidi.skyisland.data.message.extrablock.ExtraBlockParser;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;

public class SimpleMessageParser implements MessageParser {

    public enum RequiredSize {
        UPPER, LOWER, MIXED
    }

    private ComponentParser componentParser;
    private ExtraBlockParser extraBlockParser;
    private RequiredSize variableLettersSize;
    private String variableFormat;

    public SimpleMessageParser(ComponentParser componentParser, ExtraBlockParser extraBlockParser, RequiredSize variableLettersSize, String variableFormat) {
        this.componentParser = componentParser;
        this.extraBlockParser = extraBlockParser;
        this.variableLettersSize = variableLettersSize;
        this.variableFormat = variableFormat;
    }

    @Override
    public BaseComponent parse(String message, Map<String, Object> variables) {
        if (!variables.isEmpty()) {
            for (Map.Entry<String, Object> entry : variables.entrySet()) {
                String key;
                switch (this.variableLettersSize) {
                    case UPPER:
                        key = entry.getKey().toUpperCase();
                        break;
                    case LOWER:
                        key = entry.getKey().toLowerCase();
                        break;
                    case MIXED:
                    default:
                        key = entry.getKey();
                        break;
                }

                String name = String.format(this.variableFormat, key);
                message = message.replace(name, String.valueOf(entry.getValue()));
            }
        }

        ExtraBlockMatcher blockMatcher = extraBlockParser.getMatcher();
        Matcher matcher = blockMatcher.getMatcher(message);

        if (!matcher.find()) {
            return componentParser.parse(message);
        }

        Map<Integer, MatchedBlock> matches = new HashMap<>();
        do {
            matches.put(matcher.start(), new MatchedBlock(matcher.start(), matcher.end(), matcher.group(blockMatcher.getContentGroup())));
        } while (matcher.find());

        BaseComponent rootComponent = new TextComponent();
        char[] chars = message.toCharArray();
        int start = 0;

        for (int i = 0; i < chars.length; i++) {

            MatchedBlock matched = matches.get(i);
            if (matched != null) {
                String sub = message.substring(start, i);
                rootComponent.addExtra(componentParser.parse(sub));
                i = matched.end;
                start = i;
                rootComponent.addExtra(extraBlockParser.parse(matched.getText()));
            }

            if (i + 1 == chars.length) {
                rootComponent.addExtra(message.substring(start, ++i));
            }
        }

        return rootComponent;
    }

    @AllArgsConstructor
    @Getter
    private class MatchedBlock {
        private int start;
        private int end;
        private String text;
    }

}
