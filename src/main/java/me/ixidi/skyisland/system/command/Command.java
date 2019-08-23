package me.ixidi.skyisland.system.command;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import me.ixidi.skyisland.SkyIslandPlugin;
import me.ixidi.skyisland.data.lang.message.Message;
import org.bukkit.command.CommandSender;

import java.util.Collections;
import java.util.Map;

@AllArgsConstructor
public abstract class Command {

    @Getter
    private CommandInfo commandInfo;
    @Getter(AccessLevel.PROTECTED)
    private SkyIslandPlugin plugin;

    abstract void execute(CommandSender sender, CommandParameters params);

    protected final Message message(String key) {
        return plugin.getLanguageManager().getMessage(key);
    }

    protected final String langString(String key, Map<String, Object> variables) {
        return plugin.getLanguageManager().getString(key, variables);
    }

    protected final String langString(String key) {
        return langString(key, Collections.emptyMap());
    }

    abstract boolean canExecute(CommandSender sender);

    abstract boolean canConsoleExecute();
}
