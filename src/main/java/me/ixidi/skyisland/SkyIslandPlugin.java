package me.ixidi.skyisland;

import lombok.Getter;
import me.ixidi.skyisland.data.config.FileConfig;
import me.ixidi.skyisland.data.config.Settings;
import me.ixidi.skyisland.data.config.YamlFileConfig;
import me.ixidi.skyisland.data.config.mapper.ConfigMapper;
import me.ixidi.skyisland.data.lang.LanguageManager;
import me.ixidi.skyisland.data.lang.YamlLanguageManager;
import me.ixidi.skyisland.data.lang.message.ComponentParser;
import me.ixidi.skyisland.data.lang.message.MessageParser;
import me.ixidi.skyisland.data.lang.message.SimpleComponentParser;
import me.ixidi.skyisland.data.lang.message.SimpleMessageParser;
import me.ixidi.skyisland.data.lang.message.SimpleVariablesParser;
import me.ixidi.skyisland.data.lang.message.VariablesParser;
import me.ixidi.skyisland.data.lang.message.extrablock.ExtraBlockMatcher;
import me.ixidi.skyisland.data.lang.message.extrablock.ExtraBlockParser;
import me.ixidi.skyisland.data.lang.message.extrablock.SimpleExtraBlockParser;
import me.ixidi.skyisland.system.command.CommandManager;
import me.ixidi.skyisland.system.command.SimpleCommandManager;
import me.ixidi.skyisland.system.grid.Grid;
import me.ixidi.skyisland.system.grid.SerpentineGrid;
import me.ixidi.skyisland.system.island.IslandManager;
import me.ixidi.skyisland.system.island.SimpleIslandManager;
import me.ixidi.skyisland.system.island.VoidWorld;
import me.ixidi.skyisland.util.world.EmptyChunkGenerator;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Collections;

public final class SkyIslandPlugin extends JavaPlugin {

    public static final String PLUGIN_NAME = "SkyIsland";
    public static final String ISSUE_URL = "https://github.com/Ixidi/SkyIsland/issues";
    private static final String CONFIG_FILE = "config.yml";
    private static final String LANG_FORMAT = "lang_%s.yml";

    @Getter
    private SkyIslandLogger skyIslandLogger;
    @Getter
    private LanguageManager languageManager;
    @Getter
    private Settings settings;
    @Getter
    private IslandManager islandManager;


    private CommandManager commandManager;

    @Override
    public void onEnable() {
        try {
            this.skyIslandLogger = new SkyIslandLogger(PLUGIN_NAME, PLUGIN_NAME);

            File configFile = new File(getDataFolder(), CONFIG_FILE);
            if (!configFile.exists()) {
                saveResource(CONFIG_FILE, false);
            }
            FileConfig config = YamlFileConfig.create(configFile);
            config.load();
            settings = new ConfigMapper().map(Settings.class, config);

            ComponentParser componentParser = new SimpleComponentParser('&');
            ExtraBlockMatcher extraBlockMatcher = new ExtraBlockMatcher("!\\{\\{(.*?)}}", 1);
            ExtraBlockParser extraBlockParser = new SimpleExtraBlockParser(componentParser, extraBlockMatcher, "\\G([a-zA-Z]+)=\"(.*?)\";", 1, 2);
            VariablesParser variablesParser = new SimpleVariablesParser(SimpleVariablesParser.RequiredSize.UPPER, "{%s}");
            MessageParser messageParser = new SimpleMessageParser(componentParser, extraBlockParser, variablesParser);
            languageManager = new YamlLanguageManager(getDataFolder(), LANG_FORMAT, settings.getLanguage(), messageParser, variablesParser);

            enableFeatures();
        } catch (Throwable throwable) {
            this.skyIslandLogger.throwable(throwable);
            this.skyIslandLogger.severe("Due to error displayed above plugin cannot run.");
            this.getServer().getPluginManager().disablePlugin(this);
        }
    }

    private void enableFeatures() {
        loadIslands();
        registerCommands();
    }

    private void loadIslands() {
        VoidWorld world = new VoidWorld(settings.getWorldName());
        World bukkitWorld = world.get(true);

        Grid grid = new SerpentineGrid(
                settings.getIslandSize(), settings.getIslandMaxSize(), settings.getIslandFreeSpace(), settings.getMinIslandY(), settings.getMaxIslandY(), world, new Location(bukkitWorld, 0, settings.getMaxIslandY() / 2, 0), Collections.emptySet()
        ); //TODO config

        islandManager = new SimpleIslandManager(Collections.emptySet(), grid); //TODO load islands
    }

    private void registerCommands() {
        commandManager = new SimpleCommandManager(this);
    }

}
