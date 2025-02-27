package org.rysenz.rzBiomeMusic;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.rysenz.rzBiomeMusic.events.BiomeChange;
import org.rysenz.rzBiomeMusic.manager.ConfigManager;

public final class RzBiomeMusic extends JavaPlugin implements Listener {
    private static RzBiomeMusic instance;
    public static RzBiomeMusic getInstance() {
        return instance;
    }
    @Override
    public void onEnable() {
        instance = this;
        new BiomeChange(this);
        ConfigManager.load();

        getLogger().info("\u001B[33m==========================");
        getLogger().info("\u001B[33m🎧 Enable Rz BiomeMusic");
        getLogger().info("\u001B[33m==========================");
        getLogger().info("\u001B[32mLoaded Biome Sound Configurations!");
    }
    @Override
    public void onDisable() {
        getLogger().info("\u001B[31m==========================");
        getLogger().info("\u001B[31m🎧 Disable Rz BiomeMusic");
        getLogger().info("\u001B[31m==========================");
    }
}
