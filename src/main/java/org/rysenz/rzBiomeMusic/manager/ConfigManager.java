package org.rysenz.rzBiomeMusic.manager;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.block.Biome;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.rysenz.rzBiomeMusic.RzBiomeMusic;

public class ConfigManager {
    private static File configPath;
    private static FileConfiguration config;
    public static final Map<Biome, String> BIOME_SOUNDS = new HashMap<>();
    public static long INTERVAL;

    public static void load() {
        RzBiomeMusic plugin = RzBiomeMusic.getInstance();
        configPath = new File(plugin.getDataFolder(), "config.yml");
        if (!configPath.exists()) {
            plugin.saveResource("config.yml", false);
        }
        config = YamlConfiguration.loadConfiguration(configPath);
        if (config == null) {
            plugin.getLogger().severe("Failed to load config.yml!");
            return;
        }

        int intervalfirst = config.getInt("INTERVAL", 600);
        INTERVAL = (long) intervalfirst * 50;


        if (!config.contains("BIOME_SOUNDS") || config.getConfigurationSection("BIOME_SOUNDS") == null) {
            plugin.getLogger().warning("BIOME_SOUNDS section is missing or empty in config.yml!");
            return;
        }

        BIOME_SOUNDS.clear();
        for (String biomeName : config.getConfigurationSection("BIOME_SOUNDS").getKeys(false)) {
            try {
                Biome biome = Biome.valueOf(biomeName.toUpperCase());
                String sound = config.getString("BIOME_SOUNDS." + biomeName, "NONE");
                BIOME_SOUNDS.put(biome, sound);
            } catch (IllegalArgumentException e) {
                plugin.getLogger().warning("Invalid biome name in config: " + biomeName);
            }
        }
        plugin.getLogger().info("Successfully loaded biome sound configurations.");
    }

    public static String getSoundForBiome(Biome biome) {
        return BIOME_SOUNDS.get(biome);
    }
}
