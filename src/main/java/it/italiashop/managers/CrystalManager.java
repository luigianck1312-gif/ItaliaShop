package it.italiashop.managers;

import it.italiashop.ItaliaShop;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CrystalManager {

    private final ItaliaShop plugin;
    private final Map<UUID, Integer> crystals = new HashMap<>();
    private File dataFile;

    public CrystalManager(ItaliaShop plugin) {
        this.plugin = plugin;
        loadData();
    }

    public int getCrystals(Player player) {
        return crystals.getOrDefault(player.getUniqueId(), 0);
    }

    public void addCrystals(Player player, int amount) {
        crystals.put(player.getUniqueId(), getCrystals(player) + amount);
        saveData();
    }

    public boolean removeCrystals(Player player, int amount) {
        int current = getCrystals(player);
        if (current < amount) return false;
        crystals.put(player.getUniqueId(), current - amount);
        saveData();
        return true;
    }

    public boolean hasCrystals(Player player, int amount) {
        return getCrystals(player) >= amount;
    }

    public void setCrystals(Player player, int amount) {
        crystals.put(player.getUniqueId(), amount);
        saveData();
    }

    private void loadData() {
        dataFile = new File(plugin.getDataFolder(), "crystals.yml");
        if (!dataFile.exists()) return;
        YamlConfiguration config = YamlConfiguration.loadConfiguration(dataFile);
        for (String key : config.getKeys(false)) {
            try {
                crystals.put(UUID.fromString(key), config.getInt(key));
            } catch (Exception ignored) {}
        }
    }

    public void saveData() {
        dataFile = new File(plugin.getDataFolder(), "crystals.yml");
        YamlConfiguration config = new YamlConfiguration();
        for (Map.Entry<UUID, Integer> entry : crystals.entrySet()) {
            config.set(entry.getKey().toString(), entry.getValue());
        }
        try { config.save(dataFile); } catch (Exception e) { e.printStackTrace(); }
    }
}
