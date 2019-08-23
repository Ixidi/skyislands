package me.ixidi.skyisland.command;

import me.ixidi.skyisland.SkyIslandPlugin;
import me.ixidi.skyisland.system.command.CommandInfo;
import me.ixidi.skyisland.system.command.CommandParameters;
import me.ixidi.skyisland.system.command.PlayerCommand;
import org.bukkit.entity.Player;

public class IslandCreateCommand extends PlayerCommand {

    public IslandCreateCommand(CommandInfo commandInfo, SkyIslandPlugin plugin, String permission) {
        super(commandInfo, plugin, permission);
    }

    @Override
    protected void executePlayer(Player player, CommandParameters params) {

    }

}
