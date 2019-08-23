package me.ixidi.skyisland.system.command;

import me.ixidi.skyisland.SkyIslandPlugin;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public abstract class PlayerCommand extends PermissionCommand {

    public PlayerCommand(CommandInfo commandInfo, SkyIslandPlugin plugin, String permission) {
        super(commandInfo, plugin, permission);
    }

    @Override
    protected final void executePermission(CommandSender sender, CommandParameters params) {
        if (!(sender instanceof Player)) {
            message("command.playerOnly").send(sender);
            return;
        }
        executePlayer((Player) sender, params);
    }

    protected abstract void executePlayer(Player player, CommandParameters params);

    @Override
    boolean canExecute(CommandSender sender) {
        return sender instanceof Player && super.canExecute(sender);
    }

    @Override
    final boolean canConsoleExecute() {
        return false;
    }
}
