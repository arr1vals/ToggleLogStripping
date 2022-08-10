package me.arrivals.togglelogstripping;

import me.arrivals.togglelogstripping.Config.ConfigManager;
import me.arrivals.togglelogstripping.Listeners.LogStripListener;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    private static Main instance;

    // make strings less ugly
    public static String format(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public static Main getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        init();
    }

    @Override
    public void onDisable() {
        // doesnt work on reload so this is also called everytime its edited
        saveConfig();

        this.getLogger().info("ToggleLogStripping has been disabled.");
    }

    private void init() {
        new ConfigManager(this).createConfig(); // load conf
        regEvents();

        // Register commands
        this.getCommand("logstrip").setExecutor(new Commands(this));
        this.getCommand("logstrip").setTabCompleter(new Commands(this));

        this.getLogger().info("ToggleLogStripping initialized.");
    }

    private void regEvents() {
        // event listeners
        regEvent(new LogStripListener(this));
    }

    private void regEvent(Listener event) {
        // Event register function
        getServer().getPluginManager().registerEvents(event, this);
    }

}
