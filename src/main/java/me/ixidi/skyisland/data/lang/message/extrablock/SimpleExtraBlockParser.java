package me.ixidi.skyisland.data.lang.message.extrablock;

import me.ixidi.skyisland.data.lang.message.ComponentParser;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SimpleExtraBlockParser implements ExtraBlockParser {

    private static final List<String> ATTRIBUTES = Arrays.asList("text", "hover", "click");

    private ComponentParser componentParser;
    private ExtraBlockMatcher matcher;
    private Pattern attributePattern;
    private int attributeNameGroup;
    private int attributeValueGroup;

    public SimpleExtraBlockParser(ComponentParser componentParser, ExtraBlockMatcher matcher, String attributeRegex, int attributeNameGroup, int attributeValueGroup) {
        this.componentParser = componentParser;
        this.matcher = matcher;
        this.attributePattern = Pattern.compile(attributeRegex);
        this.attributeNameGroup = attributeNameGroup;
        this.attributeValueGroup = attributeValueGroup;
    }

    @Override
    public BaseComponent parse(String block) {
        Matcher matcher = attributePattern.matcher(block);
        if (!matcher.find()) {
            return corrupted("No attributes inside location.");
        }

        Map<String, String> attributeMap = new HashMap<>();
        do {
            String name = matcher.group(attributeNameGroup).toLowerCase();
            System.out.println(name);
            if (attributeMap.containsKey(name)) {
                return corrupted(String.format("Doubled attribute %s.", name));
            }
            if (!ATTRIBUTES.contains(name)) {
                return corrupted(String.format("There is not attribute %s.", name), String.format("Available: %s.", String.join(", ", ATTRIBUTES)));
            }
            attributeMap.put(matcher.group(attributeNameGroup), matcher.group(attributeValueGroup));
        } while (matcher.find());

        if (!attributeMap.containsKey("text")) {
            return corrupted("text attribute is required.");
        }

        BaseComponent rootComponent = new TextComponent();
        for (Map.Entry<String, String> entry : attributeMap.entrySet()) {
            String name = entry.getKey();
            String value = entry.getValue();

            switch (name) {
                case "hover": {
                    rootComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new BaseComponent[]{componentParser.parse(value)}));
                    break;
                }
                case "click": {
                    ClickEvent.Action action;
                    if (value.startsWith("http://") || value.startsWith("https://")) {
                        action = ClickEvent.Action.OPEN_URL;
                    } else if (value.startsWith("/")) {
                        action = ClickEvent.Action.RUN_COMMAND;
                    } else if (value.startsWith("@/")) {
                        action = ClickEvent.Action.SUGGEST_COMMAND;
                        value = value.substring(1);
                    } else {
                        return corrupted("Click action must start with one of the following prefix:", "http:// https:// to open url", "/ to run command", "@/ to suggest command");
                    }
                    rootComponent.setClickEvent(new ClickEvent(action, value));
                    break;
                }
                case "text": {
                    rootComponent.addExtra(componentParser.parse(value));
                }
            }

        }

        return rootComponent;
    }

    @Override
    public ExtraBlockMatcher getMatcher() {
        return this.matcher;
    }

    private BaseComponent corrupted(String... reason) {
        BaseComponent rootComponent = new TextComponent();

        rootComponent.setColor(ChatColor.DARK_RED);
        rootComponent.setBold(true);
        rootComponent.setUnderlined(true);
        rootComponent.addExtra("CORRUPTED_BLOCK");
        rootComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new BaseComponent[]{ new TextComponent(String.join("\n", Arrays.asList(reason))) }));

        return rootComponent;
    }
}
