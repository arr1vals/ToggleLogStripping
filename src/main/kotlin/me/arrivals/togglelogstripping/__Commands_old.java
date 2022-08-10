package me.arrivals.togglelogstripping;

import me.arrivals.togglelogstripping.Config.ConfigManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static me.arrivals.togglelogstripping.Main.format;

public class __Commands_old implements CommandExecutor, TabExecutor {
    private final Main plugin;

    public __Commands_old(Main plugin) {
        this.plugin = plugin;
    }

    public void commandErrorMsg(CommandSender sender, String label, String[] args) {
        if (!(args.length == 0)) {
            sender.sendMessage(ChatColor.RED + "Invalid or incomplete command, try /logstrip for help.");
        }
    }


    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player player) {

            // Command: logstrip
            if (command.getName().equalsIgnoreCase("logstrip")) {


                // If no args passed for /logstrip
                if (args.length < 1) {
                    sender.sendMessage(ChatColor.GOLD + "" + "ToggleLogStripping " + plugin.getDescription().getVersion() + " by arrivals");
                    sender.sendMessage(ChatColor.GOLD + "Toggle log stripping to prevent accidents");
                    sender.sendMessage("");
                    sender.sendMessage(ChatColor.GRAY + "/logstrip" + ChatColor.WHITE + " - This dialog.");
                    sender.sendMessage(ChatColor.GRAY + "/logstrip disable" + ChatColor.WHITE + " - Disables log stripping for executor.");
                    sender.sendMessage(ChatColor.GRAY + "/logstrip enable" + ChatColor.WHITE + " - Enable log stripping for executor.");
                } else if (args.length == 1) {
                    switch (args[0].toLowerCase()) {
                        case "enable":
                            if (ConfigManager.config.contains(player.getUniqueId().toString()) && !ConfigManager.config.getBoolean(player.getUniqueId().toString())) {
                                ConfigManager.config.set(player.getUniqueId().toString(), true);
                                sender.sendMessage(format("&aLog stripping has been enabled."));
                            } else {
                                // Might as well create this entry now (by default is enabled)
                                if (!ConfigManager.config.contains(player.getUniqueId().toString())) {
                                    ConfigManager.config.set(player.getUniqueId().toString(), true);
                                }
                                sender.sendMessage(format("&aLog stripping is already enabled."));
                            }

                            plugin.saveConfig();

                            break;
                        case "disable":
                            if (!ConfigManager.config.contains(player.getUniqueId().toString()) || ConfigManager.config.getBoolean(player.getUniqueId().toString())) {
                                ConfigManager.config.set(player.getUniqueId().toString(), false);
                                sender.sendMessage(format("&aLog stripping has been disabled."));

                            } else if (ConfigManager.config.contains(player.getUniqueId().toString()) && !ConfigManager.config.getBoolean(player.getUniqueId().toString())) {
                                sender.sendMessage(format("&aLog stripping is already disabled."));
                            }

                            plugin.saveConfig();

                            break;

                        case "verbenable":
                            if (ConfigManager.config.contains(player.getUniqueId().toString() + "_verbs") && !ConfigManager.config.getBoolean(player.getUniqueId().toString() + "_verbs")) {
                                ConfigManager.config.set(player.getUniqueId().toString() + "_verbs", true);
                                sender.sendMessage(format("&aVerbose has been enabled."));
                            } else {
                                sender.sendMessage(format("&aVerbose is already enabled."));
                            }

                            plugin.saveConfig();

                            break;
                        case "verbdisable":
                            if (!ConfigManager.config.contains(player.getUniqueId().toString() + "_verbs") || ConfigManager.config.getBoolean(player.getUniqueId().toString() + "_verbs")) {
                                ConfigManager.config.set(player.getUniqueId().toString() + "_verbs", false);
                                sender.sendMessage(format("&aVerbose has been disabled."));

                            } else if (ConfigManager.config.contains(player.getUniqueId().toString() + "_verbs") && !ConfigManager.config.getBoolean(player.getUniqueId().toString() + "_verbs")) {
                                sender.sendMessage(format("&aVerbose is already disabled."));
                            }

                            plugin.saveConfig();

                            break;
                    }
                } else {
                    commandErrorMsg(sender, label, args);
                }
                return true;
            }
        }

        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {

        List<String> completions = new ArrayList<>();
        List<String> commands = new ArrayList<>();

        // without if statements it will keep suggesting the same thing past one arg
        // user types: /plugin blah, without this system repeated tabbing will result in /plugin blah blah blah blah...
        if (args.length == 1) {
            commands.add("enable");
            commands.add("disable");
            commands.add("verbenable");
            commands.add("verbdisable");

        }

        // copy matches of first argument from list (ex: if first arg is 'm' will return just 'minecraft')
        StringUtil.copyPartialMatches(args[0], commands, completions);
        // sort the list
        Collections.sort(completions);
        return completions;
    }
}
