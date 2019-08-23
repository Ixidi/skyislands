package me.ixidi.skyisland.system.command;

import com.google.common.collect.ImmutableMap;
import me.ixidi.skyisland.SkyIslandPlugin;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public final class ParameterizedCommand extends Command {

    private List<Command> parameterCommands;

    public ParameterizedCommand(CommandInfo commandInfo, SkyIslandPlugin plugin, Command... parameterCommands) {
        super(commandInfo, plugin);
        this.parameterCommands = Arrays.asList(parameterCommands);
    }

    @Override
    void execute(CommandSender sender, CommandParameters params) {
        if (parameterCommands.stream().noneMatch(command -> command.canExecute(sender))) {
            message("command.noPermission").send(sender);
            return;
        }
        if (params.isEmpty()) {
            message("message.command.help.header").send(sender);
            for (Command command : parameterCommands) {
                if (sender instanceof ConsoleCommandSender ? command.canConsoleExecute() : command.canExecute(sender)) {
                    CommandInfo info = command.getCommandInfo();
                    message("message.command.help.row").send(sender, ImmutableMap.of("name", info.getName(), "description", info.getDescription()));
                }
            }
            return;
        }

        String name = params.getString(0);
        Stream<Command> stream = parameterCommands.stream().filter(command -> command.getCommandInfo().getName().equalsIgnoreCase(name) || command.getCommandInfo().getAliases().contains(name.toLowerCase()));

        Optional<Command> any = stream.findAny();
        if (!any.isPresent()) {
            message("command.noCommand").send(sender);
            return;
        }

        CommandParameters parameters;
        if (params.size() == 1) {
            parameters = new SimpleCommandParameters(Collections.emptyList());
        } else {
            parameters = new SimpleCommandParameters(params.asStringList().subList(1, params.size() - 1));
        }

        any.get().execute(sender, parameters);
    }

    @Override
    boolean canExecute(CommandSender sender) {
        return true;
    }

    @Override
    boolean canConsoleExecute() {
        return true;
    }

}
