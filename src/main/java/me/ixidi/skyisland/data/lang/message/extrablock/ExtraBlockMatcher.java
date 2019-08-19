package me.ixidi.skyisland.data.lang.message.extrablock;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExtraBlockMatcher {

    private Pattern pattern;
    private int contentGroup;

    public ExtraBlockMatcher(String regex, int contentGroup) {
        this.pattern = Pattern.compile(regex);
        this.contentGroup = contentGroup;
    }

    public Matcher getMatcher(String text) {
        return pattern.matcher(text);
    }

    public int getContentGroup() {
        return contentGroup;
    }
}
