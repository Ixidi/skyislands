package me.ixidi.skyisland.system.command;

import com.google.common.collect.ImmutableMap;
import me.ixidi.skyisland.SkyIslandPlugin;
import me.ixidi.skyisland.util.reflection.ReflectionUtils;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.help.GenericCommandHelpTopic;
import org.bukkit.help.HelpTopic;
import org.bukkit.help.HelpTopicComparator;
import org.bukkit.help.IndexHelpTopic;

import java.util.*;

public class SimpleCommandManager implements CommandManager {

    private SkyIslandPlugin plugin;
    private CommandMap commandMap;
    private Set<HelpTopic> topicSet = new TreeSet<>(HelpTopicComparator.helpTopicComparatorInstance());

    public SimpleCommandManager(SkyIslandPlugin plugin) {
        this.plugin = plugin;
        this.commandMap = (CommandMap) ReflectionUtils.getFromField("commandMap", plugin.getServer());

        String allCommand = plugin.getLanguageManager().getString("command.allCommands", ImmutableMap.of("name", SkyIslandPlugin.PLUGIN_NAME));
        plugin.getServer().getHelpMap().addTopic(new IndexHelpTopic(
             SkyIslandPlugin.PLUGIN_NAME, allCommand, null, topicSet
        ));
    }

    @Override
    public void register(Command command) {
        BukkitCommandWrapper wrapper = new BukkitCommandWrapper(command);
        commandMap.register(SkyIslandPlugin.PLUGIN_NAME.toLowerCase(), wrapper);
        topicSet.add(new GenericCommandHelpTopic(wrapper));
    }

    private void handleCommand(Command command, CommandSender sender, String[] strings) {
        List<String> params;
        if (strings.length < 1) {
            params = Collections.emptyList();
        } else {
            params = Arrays.asList(strings);
        }

        command.execute(sender, new SimpleCommandParameters(params));
    }

    private class BukkitCommandWrapper extends org.bukkit.command.Command {

        private Command command;

        protected BukkitCommandWrapper(Command command) {
            super(command.getCommandInfo().getName());
            this.command = command;

            CommandInfo info = command.getCommandInfo();
            setDescription(info.getDescription());
            setAliases(info.getAliases());
        }

        @Override
        public boolean execute(CommandSender commandSender, String s, String[] strings) {
            try {
                SimpleCommandManager.this.handleCommand(command, commandSender, strings);
            } catch (Exception e) {
                plugin.getSkyIslandLogger().throwable(e);
            }
            return true;
        }

    }

}
