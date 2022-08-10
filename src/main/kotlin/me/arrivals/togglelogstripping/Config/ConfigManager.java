package me.arrivals.togglelogstripping.Config;

import me.arrivals.togglelogstripping.Main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class ConfigManager {
    public static FileConfiguration config;
    private static File cfile;
    private Main plugin;

    public ConfigManager(Main plugin) {
        this.plugin = plugin;
    }

    public static void reloadConfig() {
        // re assign config to current cfile
        config = YamlConfiguration.loadConfiguration(cfile);
    }

    public void createConfig() {

        config = plugin.getConfig();
        cfile = new File(plugin.getDataFolder(), "config.yml");

        // Copy defaults if not present
        if (!cfile.exists()) {
            config.options().copyDefaults(true);
            plugin.saveDefaultConfig();
        }


        plugin.getLogger().info("Config loaded.");

        /*
        cfile = new File(plugin.getDataFolder(), "config.yml");

        if (!cfile.exists()) {
            plugin.saveDefaultConfig();
        }
        //config = plugin.getConfig();
        config = YamlConfiguration.loadConfiguration(cfile);

        plugin.getLogger().info("Config loaded.");
        */

    }

}
