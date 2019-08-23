package me.ixidi.skyisland.system.command;

import lombok.Getter;
import me.ixidi.skyisland.SkyIslandPlugin;
import org.bukkit.command.CommandSender;

public abstract class PermissionCommand extends Command {

    @Getter
    private String permission;

    public PermissionCommand(CommandInfo commandInfo, SkyIslandPlugin plugin, String permission) {
        super(commandInfo, plugin);
        this.permission = permission;
    }

    @Override
    public final void execute(CommandSender sender, CommandParameters params) {
        if (!canExecute(sender)) {
            message("command.noPermission").send(sender);
            return;
        }
        executePermission(sender, params);
    }

    protected abstract void executePermission(CommandSender sender, CommandParameters params);

    @Override
    boolean canExecute(CommandSender sender) {
        return permission == null || sender.hasPermission(permission);
    }

    @Override
    boolean canConsoleExecute() {
        return true;
    }
}
