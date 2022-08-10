package me.arrivals.togglelogstripping.Listeners

import me.arrivals.togglelogstripping.Config.ConfigManager.config
import me.arrivals.togglelogstripping.Main
import net.md_5.bungee.api.ChatMessageType
import net.md_5.bungee.api.chat.TextComponent
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent

class LogStripListener(private val plugin: Main) : Listener {

    @EventHandler
    fun onLogStrip(event: PlayerInteractEvent?) {
        val player: Player = event!!.player;


        if ((event.action == Action.RIGHT_CLICK_BLOCK) &&
            event.material.toString()
                .contains("_AXE") && (event.clickedBlock != null) && event.clickedBlock.toString()
                .contains("LOG")
        ) {
            if (config.contains(player.uniqueId.toString()) && !config.getBoolean(player.uniqueId.toString())) {
                event.isCancelled = true;

                if (!config.contains(player.uniqueId.toString() + "_verbs")) {
                    config[player.uniqueId.toString() + "_verbs"] = true
                }

                if (config.contains(player.uniqueId.toString() + "_verbs") && config.getBoolean(player.uniqueId.toString() + "_verbs")) {
                    player.spigot()
                        .sendMessage(ChatMessageType.ACTION_BAR, TextComponent("You have turned off log stripping."));
                }

                plugin.saveConfig()
            }
        }
    }
}