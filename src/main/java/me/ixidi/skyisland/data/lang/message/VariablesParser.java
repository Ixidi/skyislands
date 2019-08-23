package me.ixidi.skyisland.data.lang.message;

import java.util.Map;

public interface VariablesParser {

    String parse(String string, Map<String, Object> variables);

}
