package me.ixidi.skyisland.data.lang.message;

import lombok.AllArgsConstructor;

import java.util.Map;

@AllArgsConstructor
public class SimpleVariablesParser implements VariablesParser {

    public enum RequiredSize {
        UPPER, LOWER, MIXED
    }

    private RequiredSize variableLettersSize;
    private String variableFormat;

    @Override
    public String parse(String string, Map<String, Object> variables) {
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
                string = string.replace(name, String.valueOf(entry.getValue()));
            }
        }
        return string;
    }

}
