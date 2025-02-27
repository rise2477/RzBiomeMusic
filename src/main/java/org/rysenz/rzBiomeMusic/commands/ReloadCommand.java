package org.rysenz.rzBiomeMusic.commands;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.rysenz.rzBiomeMusic.manager.ConfigManager;

public class ReloadCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (sender instanceof Player) {
            if (!sender.hasPermission("rzbm.reload")) {
                sender.sendMessage(ChatColor.RED + "You don't Have Permission to do that");
                return false;
            }
            if (args[0].equalsIgnoreCase("reload")) {
                ConfigManager.load();
                Bukkit.getConsoleSender().sendMessage("\u001B[32mReloaded Biome Music!\u001B[0m");
            }
        }
        return false;
    }
}
