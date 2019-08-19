package me.ixidi.skyisland;

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
import me.ixidi.skyisland.data.lang.message.extrablock.ExtraBlockMatcher;
import me.ixidi.skyisland.data.lang.message.extrablock.ExtraBlockParser;
import me.ixidi.skyisland.data.lang.message.extrablock.SimpleExtraBlockParser;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class SkyIslandPlugin extends JavaPlugin implements Listener {

    public static final String PLUGIN_NAME = "SkyIsland";
    public static final String ISSUE_URL = "https://github.com/Ixidi/SkyIsland/issues";
    private static final String CONFIG_FILE = "config.yml";
    private static final String MESSAGES_FORMAT = "messages_%s.yml";

    private SkyIslandLogger skyIslandLogger;
    private LanguageManager languageManager;
    private Settings settings;

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
            settings = ConfigMapper.map(Settings.class, config);

            ComponentParser componentParser = new SimpleComponentParser('&');
            ExtraBlockMatcher extraBlockMatcher = new ExtraBlockMatcher("!\\{\\{(.*?)}}", 1);
            ExtraBlockParser extraBlockParser = new SimpleExtraBlockParser(componentParser, extraBlockMatcher, "\\G([a-zA-Z]+)=\"(.*?)\";", 1, 2);
            MessageParser messageParser = new SimpleMessageParser(componentParser, extraBlockParser, SimpleMessageParser.RequiredSize.UPPER, "{%s}");

            languageManager = new YamlLanguageManager(getDataFolder(), MESSAGES_FORMAT, settings.getLanguage(), messageParser);

            enableFeatures();
        } catch (Throwable throwable) {
            this.skyIslandLogger.throwable(throwable);
            this.skyIslandLogger.severe("Due to error displayed above plugin cannot run.");
            this.getServer().getPluginManager().disablePlugin(this);
        }
    }

    private void enableFeatures() {
        this.getServer().getPluginManager().registerEvents(this, this);
    }

    public LanguageManager getLanguageManager() {
        return languageManager;
    }

    public Settings getSettings() {
        return settings;
    }
}
