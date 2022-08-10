package me.arrivals.togglelogstripping

import me.arrivals.togglelogstripping.Config.ConfigManager
import me.arrivals.togglelogstripping.Main.format
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabExecutor
import org.bukkit.entity.Player
import org.bukkit.util.StringUtil
import java.util.*

class Commands(private val plugin: Main) : CommandExecutor, TabExecutor {
    fun commandErrorMsg(sender: CommandSender, label: String?, args: Array<String>) {
        if (args.isNotEmpty()) {
            sender.sendMessage(ChatColor.RED.toString() + "Invalid or incomplete command, try /logstrip for help.")
        }
    }

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<String>): Boolean {

        if (sender is Player) {
            val player = sender



            // Command: logstrip
            if (command.name.equals("logstrip", true)) {


                // If no args passed for /logstrip
                if (args.size < 1) {
                    sender.sendMessage("&6ToggleLogStripping " + plugin.getDescription().getVersion() + " by arrivals");
                    sender.sendMessage("&6Toggle log stripping to prevent accidents");
                    sender.sendMessage("");
                    sender.sendMessage(format("&7/logstrip&f - This dialog."));
                    sender.sendMessage(format("&7/logstrip disable&f - Disables log stripping for executor."));
                    sender.sendMessage(format("&7/logstrip enable&f - Enable log stripping for executor."));
                } else if (args.size == 1) {
                    when (args[0].lowercase(Locale.getDefault())) {
                        "enable" -> {
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

                        }

                        "disable" -> {
                            if (!ConfigManager.config.contains(
                                    player.getUniqueId().toString()
                                ) || ConfigManager.config.getBoolean(player.getUniqueId().toString())
                            ) {
                                ConfigManager.config.set(player.getUniqueId().toString(), false);
                                sender.sendMessage(format("&aLog stripping has been disabled."));

                            } else if (ConfigManager.config.contains(
                                    player.getUniqueId().toString()
                                ) && !ConfigManager.config.getBoolean(player.getUniqueId().toString())
                            ) {
                                sender.sendMessage(format("&aLog stripping is already disabled."));
                            }

                            plugin.saveConfig();

                        }

                        "verbenable" -> {
                            if (ConfigManager.config.contains(
                                    player.getUniqueId().toString() + "_verbs"
                                ) && !ConfigManager.config.getBoolean(player.getUniqueId().toString() + "_verbs")
                            ) {
                                ConfigManager.config.set(player.getUniqueId().toString() + "_verbs", true);
                                sender.sendMessage(format("&aVerbose has been enabled."));
                            } else {
                                sender.sendMessage(format("&aVerbose is already enabled."));
                            }

                            plugin.saveConfig();

                        }
                        "verbdisable" -> {
                            if (!ConfigManager.config.contains(
                                    player.getUniqueId().toString() + "_verbs"
                                ) || ConfigManager.config.getBoolean(player.getUniqueId().toString() + "_verbs")
                            ) {
                                ConfigManager.config.set(player.getUniqueId().toString() + "_verbs", false);
                                sender.sendMessage(format("&aVerbose has been disabled."));

                            } else if (ConfigManager.config.contains(
                                    player.getUniqueId().toString() + "_verbs"
                                ) && !ConfigManager.config.getBoolean(player.getUniqueId().toString() + "_verbs")
                            ) {
                                sender.sendMessage(format("&aVerbose is already disabled."));
                            }

                            plugin.saveConfig();

                        }
                    }
                } else {
                    commandErrorMsg(sender, label, args);
                }
                return true;
            }
        }
        return false;
    }

    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        alias: String,
        args: Array<String>
    ): List<String>? {
        val completions: MutableList<String> = ArrayList()
        val commands: MutableList<String> = ArrayList()

        // without if statements it will keep suggesting the same thing past one arg
        // user types: /plugin blah, without this system repeated tabbing will result in /plugin blah blah blah blah...
        if (args.size == 1) {
            commands.add("enable")
            commands.add("disable")
            commands.add("verbenable")
            commands.add("verbdisable")
        }

        // copy matches of first argument from list (ex: if first arg is 'm' will return just 'minecraft')
        StringUtil.copyPartialMatches(args[0], commands, completions)
        // sort the list
        completions.sort()
        return completions
    }
}