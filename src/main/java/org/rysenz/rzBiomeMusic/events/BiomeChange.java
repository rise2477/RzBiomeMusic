package org.rysenz.rzBiomeMusic.events;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.block.Biome;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.rysenz.rzBiomeMusic.manager.ConfigManager;

import java.util.HashMap;
import java.util.UUID;

public class BiomeChange {
    private final HashMap<UUID, Biome> playerBiomeMap = new HashMap<>();
    private final HashMap<UUID, Boolean> isSoundPlaying = new HashMap<>();
    private final HashMap<UUID, Long> lastPlayedTime = new HashMap<>();
    private final JavaPlugin plugin;
    private final int intervalTicks;
    public BiomeChange(JavaPlugin plugin) {
        this.plugin = plugin;
        this.intervalTicks = ConfigManager.INTERVAL;
        startBiomeCheckTask();
    }
    private void startBiomeCheckTask() {
        Bukkit.getScheduler().runTaskTimer(plugin, () -> {
            long currentTime = System.currentTimeMillis();
            for (Player player : Bukkit.getOnlinePlayers()) {
                UUID playerId = player.getUniqueId();
                Biome currentBiome = player.getLocation().getBlock().getBiome();
                if (!playerBiomeMap.containsKey(playerId) || playerBiomeMap.get(playerId) != currentBiome) {
                    playerBiomeMap.put(playerId, currentBiome);
                    playBiomeSound(player, currentBiome);
                    continue;
                }
                if (isSoundPlaying.getOrDefault(playerId, false)) {
                    continue;
                }
                long lastTime = lastPlayedTime.getOrDefault(playerId, 0L);
                if (currentTime - lastTime >= intervalTicks * 50) {
                    playBiomeSound(player, currentBiome);
                }
            }
        }, 0L, intervalTicks);
    }
    private void playBiomeSound(Player player, Biome biome) {
        UUID playerId = player.getUniqueId();
        String soundName = ConfigManager.getSoundForBiome(biome);
        try {
            Sound sound = Sound.valueOf(soundName.toUpperCase());
            player.playSound(player.getLocation(), sound, 1.0f, 1.0f);
            lastPlayedTime.put(playerId, System.currentTimeMillis());
            isSoundPlaying.put(playerId, true);
            Bukkit.getScheduler().runTaskLater(plugin, () -> isSoundPlaying.put(playerId, false), 600L);
        } catch (IllegalArgumentException e) {
            player.sendMessage("§c[Error] Invalid sound: " + soundName);
        }
    }
}
