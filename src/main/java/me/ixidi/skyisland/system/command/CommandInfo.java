package me.ixidi.skyisland.system.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class CommandInfo {

    private String name;
    private String description;
    private List<String> aliases;

}
