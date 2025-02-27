package org.rysenz.rzBiomeMusic.events;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.SoundCategory;
import org.bukkit.block.Biome;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.rysenz.rzBiomeMusic.manager.ConfigManager;

public class BiomeChange {
    private final HashMap<UUID, Biome> playerBiomeMap = new HashMap<>();
    private final HashMap<UUID, Boolean> isSoundPlaying = new HashMap<>();
    private final HashMap<UUID, Long> lastPlayedTime = new HashMap<>();
    private final JavaPlugin plugin;
    public BiomeChange(JavaPlugin plugin) {
        this.plugin = plugin;
        startBiomeCheckTask();
    }
    private void startBiomeCheckTask() {
        Bukkit.getScheduler().runTaskTimer(plugin, () -> {
            for (Player player : Bukkit.getOnlinePlayers()) {
                UUID playerId = player.getUniqueId();
                Biome currentBiome = player.getLocation().getBlock().getBiome();
                if (!playerBiomeMap.containsKey(playerId) || playerBiomeMap.get(playerId) != currentBiome) {
                    playerBiomeMap.put(playerId, currentBiome);
                }
                playBiomeSound(player, currentBiome);
            }
        }, 0L, ConfigManager.INTERVAL / 50);
    }
    private void playBiomeSound(Player player, Biome biome) {
        UUID playerId = player.getUniqueId();
        long currentTime = System.currentTimeMillis();
    
        if (lastPlayedTime.containsKey(playerId)) {
            long lastPlayed = lastPlayedTime.get(playerId);
            long cooldown = ConfigManager.INTERVAL;
            if (currentTime - lastPlayed < cooldown) {
                return;
            }
        }
    
        String soundName = ConfigManager.getSoundForBiome(biome);
        if (soundName == null || soundName.isEmpty()) {
            return;
        }
    
        try {
            player.playSound(player.getLocation(), soundName, SoundCategory.AMBIENT, 1.0f, 1.0f);
            lastPlayedTime.put(playerId, currentTime);
        } catch (IllegalArgumentException e) {
            player.sendMessage("§c[Error] Invalid sound: " + soundName);
        }
    }
}
