package org.rysenz.rzBiomeMusic;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.rysenz.rzBiomeMusic.commands.ReloadCommand;
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
        getLogger().info("\u001B[33m==========================\u001B[0m");
        getLogger().info("\u001B[33m🎧 Enable Rz BiomeMusic\u001B[0m");
        getLogger().info("\u001B[33m==========================\u001B[0m");
        getLogger().info("\u001B[32mLoaded Biome Sound Configurations!\u001B[0m");
        this.getCommand("rzbm").setExecutor(new ReloadCommand());
    }
    @Override
    public void onDisable() {
        getLogger().info("\u001B[31m==========================\u001B[0m");
        getLogger().info("\u001B[31m🎧 Disable Rz BiomeMusic\u001B[0m");
        getLogger().info("\u001B[31m==========================\u001B[0m");
    }
}
