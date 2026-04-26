package it.italiashop.managers;

import it.italiashop.ItaliaShop;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.util.*;

public class SpawnerManager {

    private final ItaliaShop plugin;

    // Tipi di spawner disponibili
    public enum SpawnerType {
        ZOMBIE("Zombie", EntityType.ZOMBIE, 100, Material.ROTTEN_FLESH,
                new int[]{16, 32}, "§cSpawner Zombie"),
        SKELETON("Scheletro", EntityType.SKELETON, 150, Material.BONE,
                new int[]{16, 32}, "§7Spawner Scheletro"),
        CREEPER("Creeper", EntityType.CREEPER, 300, Material.GUNPOWDER,
                new int[]{12, 24}, "§2Spawner Creeper"),
        IRON_GOLEM("Golem", EntityType.IRON_GOLEM, 400, Material.IRON_INGOT,
                new int[]{8, 16}, "§7Spawner Golem"),
        BLAZE("Blaze", EntityType.BLAZE, 500, Material.BLAZE_ROD,
                new int[]{8, 16}, "§6Spawner Blaze"),
        WITHER_SKELETON("Wither Skeleton", EntityType.WITHER_SKELETON, 1000, Material.WITHER_SKELETON_SKULL,
                new int[]{1, 3}, "§8Spawner Wither Skeleton");

        public final String displayName;
        public final EntityType entityType;
        public final int cost;
        public final Material lootMaterial;
        public final int[] lootRange; // {min, max}
        public final String itemName;
        public final double dropChance; // solo per wither skeleton

        SpawnerType(String displayName, EntityType entityType, int cost,
                    Material lootMaterial, int[] lootRange, String itemName) {
            this.displayName = displayName;
            this.entityType = entityType;
            this.cost = cost;
            this.lootMaterial = lootMaterial;
            this.lootRange = lootRange;
            this.itemName = itemName;
            this.dropChance = name().equals("WITHER_SKELETON") ? 0.30 : 1.0;
        }
    }

    // Dati spawner piazzati: location -> {tipo, count, lastTick, loot}
    private final Map<Location, SpawnerData> spawners = new HashMap<>();
    private File dataFile;
    private YamlConfiguration dataConfig;

    public SpawnerManager(ItaliaShop plugin) {
        this.plugin = plugin;
        loadData();
        startTickTask();
    }

    private void startTickTask() {
        // Ogni 30 minuti (36000 tick) genera loot
        plugin.getServer().getScheduler().runTaskTimer(plugin, () -> {
            for (Map.Entry<Location, SpawnerData> entry : spawners.entrySet()) {
                SpawnerData data = entry.getValue();
                generateLoot(data);
            }
            saveData();
        }, 36000L, 36000L);
    }

    private void generateLoot(SpawnerData data) {
        SpawnerType type = data.type;
        int count = data.count;
        Random rand = new Random();

        if (type == SpawnerType.WITHER_SKELETON) {
            // Drop con probabilità 30% per spawner
            int skulls = 0;
            for (int i = 0; i < count; i++) {
                for (int j = 0; j < type.lootRange[1]; j++) {
                    if (rand.nextDouble() < type.dropChance) skulls++;
                }
            }
            if (skulls > 0) addLoot(data, new ItemStack(type.lootMaterial, Math.min(skulls, 54)));
        } else {
            int min = type.lootRange[0] * count;
            int max = type.lootRange[1] * count;
            int amount = min + rand.nextInt(max - min + 1);
            // Dividi in stack da 64
            while (amount > 0) {
                int stackSize = Math.min(amount, 64);
                addLoot(data, new ItemStack(type.lootMaterial, stackSize));
                amount -= stackSize;
            }
        }
    }

    private void addLoot(SpawnerData data, ItemStack item) {
        // Aggiungi al loot, max 54 slot
        for (int i = 0; i < data.loot.length; i++) {
            if (data.loot[i] == null || data.loot[i].getType().isAir()) {
                data.loot[i] = item;
                return;
            } else if (data.loot[i].isSimilar(item) && data.loot[i].getAmount() < 64) {
                int space = 64 - data.loot[i].getAmount();
                int add = Math.min(space, item.getAmount());
                data.loot[i].setAmount(data.loot[i].getAmount() + add);
                item.setAmount(item.getAmount() - add);
                if (item.getAmount() <= 0) return;
            }
        }
    }

    public boolean placeSpawner(Player player, Location loc, SpawnerType type, int count) {
        Block block = loc.getBlock();
        block.setType(Material.SPAWNER);

        if (block.getState() instanceof CreatureSpawner cs) {
            cs.setSpawnedType(type.entityType);
            cs.update();
        }

        if (spawners.containsKey(loc)) {
            SpawnerData existing = spawners.get(loc);
            if (existing.type == type) {
                existing.count = Math.min(existing.count + count, 10);
            }
        } else {
            spawners.put(loc, new SpawnerData(type, count));
        }

        saveData();
        return true;
    }

    public SpawnerData getSpawnerData(Location loc) {
        return spawners.get(loc);
    }

    public boolean isSpawner(Location loc) {
        return spawners.containsKey(loc);
    }

    public void removeSpawner(Location loc) {
        spawners.remove(loc);
        saveData();
    }

    public ItemStack createSpawnerItem(SpawnerType type, int count) {
        ItemStack item = new ItemStack(Material.SPAWNER, count);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(type.itemName);
            List<String> lore = new ArrayList<>();
            lore.add("§7Tipo: §f" + type.displayName);
            lore.add("§7Loot: §f" + type.lootRange[0] + "-" + type.lootRange[1] +
                    " §7" + type.lootMaterial.name().toLowerCase().replace("_", " "));
            if (type == SpawnerType.WITHER_SKELETON) {
                lore.add("§7Drop chance: §f30%");
            }
            lore.add("§7Ogni: §f30 minuti");
            lore.add("§7Max stack: §f10");
            lore.add("§eClicca destro per piazzare");
            lore.add("§8spawner:" + type.name());
            meta.setLore(lore);
            item.setItemMeta(meta);
        }
        return item;
    }

    public SpawnerType getSpawnerTypeFromItem(ItemStack item) {
        if (item == null || item.getType() != Material.SPAWNER) return null;
        ItemMeta meta = item.getItemMeta();
        if (meta == null || !meta.hasLore()) return null;
        for (String line : meta.getLore()) {
            if (line.startsWith("§8spawner:")) {
                String typeName = line.replace("§8spawner:", "");
                try {
                    return SpawnerType.valueOf(typeName);
                } catch (Exception e) {
                    return null;
                }
            }
        }
        return null;
    }

    // Data class
    public static class SpawnerData {
        public SpawnerType type;
        public int count;
        public ItemStack[] loot;

        public SpawnerData(SpawnerType type, int count) {
            this.type = type;
            this.count = Math.min(count, 10);
            this.loot = new ItemStack[54];
        }
    }

    private void loadData() {
        dataFile = new File(plugin.getDataFolder(), "spawners.yml");
        if (!dataFile.exists()) return;
        dataConfig = YamlConfiguration.loadConfiguration(dataFile);

        for (String key : dataConfig.getKeys(false)) {
            try {
                String[] parts = key.split("_");
                World world = Bukkit.getWorld(dataConfig.getString(key + ".world"));
                if (world == null) continue;
                int x = dataConfig.getInt(key + ".x");
                int y = dataConfig.getInt(key + ".y");
                int z = dataConfig.getInt(key + ".z");
                Location loc = new Location(world, x, y, z);
                SpawnerType type = SpawnerType.valueOf(dataConfig.getString(key + ".type"));
                int count = dataConfig.getInt(key + ".count");
                SpawnerData data = new SpawnerData(type, count);

                List<?> lootList = dataConfig.getList(key + ".loot");
                if (lootList != null) {
                    for (int i = 0; i < Math.min(lootList.size(), 54); i++) {
                        if (lootList.get(i) instanceof ItemStack) {
                            data.loot[i] = (ItemStack) lootList.get(i);
                        }
                    }
                }
                spawners.put(loc, data);
            } catch (Exception e) {
                plugin.getLogger().warning("Error loading spawner: " + key);
            }
        }
    }

    public void saveData() {
        dataFile = new File(plugin.getDataFolder(), "spawners.yml");
        dataConfig = new YamlConfiguration();
        int i = 0;
        for (Map.Entry<Location, SpawnerData> entry : spawners.entrySet()) {
            Location loc = entry.getKey();
            SpawnerData data = entry.getValue();
            String key = "spawner_" + i++;
            dataConfig.set(key + ".world", loc.getWorld().getName());
            dataConfig.set(key + ".x", loc.getBlockX());
            dataConfig.set(key + ".y", loc.getBlockY());
            dataConfig.set(key + ".z", loc.getBlockZ());
            dataConfig.set(key + ".type", data.type.name());
            dataConfig.set(key + ".count", data.count);
            List<ItemStack> lootList = new ArrayList<>();
            for (ItemStack item : data.loot) {
                if (item != null) lootList.add(item);
            }
            dataConfig.set(key + ".loot", lootList);
        }
        try { dataConfig.save(dataFile); } catch (Exception e) { e.printStackTrace(); }
    }

    public Map<Location, SpawnerData> getSpawners() { return spawners; }
}
