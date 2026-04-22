package it.italiashop.managers;

import it.italiashop.ItaliaShop;
import it.italiashop.models.ShopItem;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.*;

public class ShopManager {

    private final ItaliaShop plugin;
    private final List<ShopItem> items = new ArrayList<>();
    // UUID -> Material -> last buy timestamp
    private final Map<UUID, Map<Material, Long>> cooldowns = new HashMap<>();

    public ShopManager(ItaliaShop plugin) {
        this.plugin = plugin;
        registerItems();
        loadPrices();
        // Reset prezzi ogni 3 giorni
        long threeDays = 20L * 60 * 60 * 24 * 3;
        plugin.getServer().getScheduler().runTaskTimer(plugin, this::resetAllPrices, threeDays, threeDays);
    }

    private void registerItems() {
        // === BLOCCHI ===
        add(Material.DIRT, "Terra", ShopItem.Category.BLOCCHI, 200000, 80000, 50000, 500000, false, 0, 64);
        add(Material.GRASS_BLOCK, "Erba", ShopItem.Category.BLOCCHI, 250000, 100000, 60000, 600000, false, 0, 64);
        add(Material.STONE, "Pietra", ShopItem.Category.BLOCCHI, 500000, 200000, 100000, 1000000, false, 0, 64);
        add(Material.COBBLESTONE, "Ciottoli", ShopItem.Category.BLOCCHI, 400000, 150000, 80000, 800000, false, 0, 64);
        add(Material.SAND, "Sabbia", ShopItem.Category.BLOCCHI, 300000, 120000, 70000, 700000, false, 0, 64);
        add(Material.GRAVEL, "Ghiaia", ShopItem.Category.BLOCCHI, 350000, 130000, 80000, 750000, false, 0, 64);
        add(Material.OAK_LOG, "Quercia", ShopItem.Category.BLOCCHI, 800000, 320000, 200000, 2000000, false, 0, 64);
        add(Material.BIRCH_LOG, "Betulla", ShopItem.Category.BLOCCHI, 800000, 320000, 200000, 2000000, false, 0, 64);
        add(Material.SPRUCE_LOG, "Abete", ShopItem.Category.BLOCCHI, 800000, 320000, 200000, 2000000, false, 0, 64);
        add(Material.JUNGLE_LOG, "Giungla", ShopItem.Category.BLOCCHI, 900000, 360000, 200000, 2000000, false, 0, 64);
        add(Material.ACACIA_LOG, "Acacia", ShopItem.Category.BLOCCHI, 850000, 340000, 200000, 2000000, false, 0, 64);
        add(Material.DARK_OAK_LOG, "Rovere", ShopItem.Category.BLOCCHI, 850000, 340000, 200000, 2000000, false, 0, 64);
        add(Material.OAK_PLANKS, "Assi Quercia", ShopItem.Category.BLOCCHI, 600000, 240000, 150000, 1500000, false, 0, 64);
        add(Material.GLASS, "Vetro", ShopItem.Category.BLOCCHI, 700000, 280000, 150000, 1500000, false, 0, 64);
        add(Material.BRICKS, "Mattoni", ShopItem.Category.BLOCCHI, 1000000, 400000, 250000, 2500000, false, 0, 64);
        add(Material.OBSIDIAN, "Ossidiana", ShopItem.Category.BLOCCHI, 5000000, 2000000, 1000000, 10000000, false, 0, 64);
        add(Material.ICE, "Ghiaccio", ShopItem.Category.BLOCCHI, 500000, 200000, 100000, 1000000, false, 0, 64);
        add(Material.SNOW_BLOCK, "Neve", ShopItem.Category.BLOCCHI, 400000, 160000, 80000, 800000, false, 0, 64);
        add(Material.CLAY, "Argilla", ShopItem.Category.BLOCCHI, 600000, 240000, 150000, 1500000, false, 0, 64);
        add(Material.TERRACOTTA, "Terracotta", ShopItem.Category.BLOCCHI, 700000, 280000, 150000, 1500000, false, 0, 64);
        add(Material.WHITE_WOOL, "Lana", ShopItem.Category.BLOCCHI, 500000, 200000, 100000, 1000000, false, 0, 64);

        // === MINERALI ===
        add(Material.COAL, "Carbone", ShopItem.Category.MINERALI, 1000000, 400000, 200000, 3000000, false, 0, 64);
        add(Material.IRON_INGOT, "Lingotto Ferro", ShopItem.Category.MINERALI, 5000000, 2000000, 1000000, 15000000, false, 0, 64);
        add(Material.GOLD_INGOT, "Lingotto Oro", ShopItem.Category.MINERALI, 10000000, 4000000, 2000000, 25000000, false, 0, 64);
        add(Material.DIAMOND, "Diamante", ShopItem.Category.MINERALI, 50000000, 20000000, 10000000, 150000000, false, 0, 64);
        add(Material.EMERALD, "Smeraldo", ShopItem.Category.MINERALI, 30000000, 12000000, 6000000, 80000000, false, 0, 64);
        add(Material.LAPIS_LAZULI, "Lapislazzuli", ShopItem.Category.MINERALI, 3000000, 1200000, 600000, 8000000, false, 0, 64);
        add(Material.REDSTONE, "Redstone", ShopItem.Category.MINERALI, 2000000, 800000, 400000, 6000000, false, 0, 64);
        add(Material.QUARTZ, "Quarzo", ShopItem.Category.MINERALI, 2500000, 1000000, 500000, 7000000, false, 0, 64);
        add(Material.AMETHYST_SHARD, "Ametista", ShopItem.Category.MINERALI, 4000000, 1600000, 800000, 10000000, false, 0, 64);
        add(Material.IRON_ORE, "Minerale Ferro", ShopItem.Category.MINERALI, 6000000, 2400000, 1200000, 18000000, false, 0, 64);
        add(Material.GOLD_ORE, "Minerale Oro", ShopItem.Category.MINERALI, 12000000, 4800000, 2400000, 30000000, false, 0, 64);
        add(Material.DIAMOND_ORE, "Minerale Diamante", ShopItem.Category.MINERALI, 60000000, 24000000, 12000000, 180000000, false, 0, 64);

        // === NATURA ===
        add(Material.SEAGRASS, "Alghe Marine", ShopItem.Category.NATURA, 200000, 80000, 50000, 500000, false, 0, 64);
        add(Material.KELP, "Alga", ShopItem.Category.NATURA, 150000, 60000, 30000, 400000, false, 0, 64);
        add(Material.SUGAR_CANE, "Canna da Zucchero", ShopItem.Category.NATURA, 300000, 120000, 60000, 700000, false, 0, 64);
        add(Material.BAMBOO, "Bambu", ShopItem.Category.NATURA, 250000, 100000, 50000, 600000, false, 0, 64);
        add(Material.CACTUS, "Cactus", ShopItem.Category.NATURA, 400000, 160000, 80000, 900000, false, 0, 64);
        add(Material.PUMPKIN, "Zucca", ShopItem.Category.NATURA, 500000, 200000, 100000, 1200000, false, 0, 64);
        add(Material.MELON, "Anguria", ShopItem.Category.NATURA, 400000, 160000, 80000, 1000000, false, 0, 64);
        add(Material.OAK_LEAVES, "Foglie", ShopItem.Category.NATURA, 200000, 80000, 40000, 500000, false, 0, 64);
        add(Material.VINE, "Rampicante", ShopItem.Category.NATURA, 300000, 120000, 60000, 700000, false, 0, 64);
        add(Material.LILY_PAD, "Ninfea", ShopItem.Category.NATURA, 500000, 200000, 100000, 1200000, false, 0, 64);
        add(Material.DANDELION, "Dente di Leone", ShopItem.Category.NATURA, 200000, 80000, 40000, 500000, false, 0, 64);
        add(Material.POPPY, "Papavero", ShopItem.Category.NATURA, 200000, 80000, 40000, 500000, false, 0, 64);
        add(Material.BLUE_ORCHID, "Orchidea", ShopItem.Category.NATURA, 400000, 160000, 80000, 1000000, false, 0, 64);
        add(Material.BONE_MEAL, "Farina d'Osso", ShopItem.Category.NATURA, 800000, 320000, 160000, 2000000, false, 0, 64);
        add(Material.STRING, "Filo", ShopItem.Category.NATURA, 600000, 240000, 120000, 1500000, false, 0, 64);
        add(Material.FEATHER, "Piuma", ShopItem.Category.NATURA, 500000, 200000, 100000, 1200000, false, 0, 64);
        add(Material.LEATHER, "Cuoio", ShopItem.Category.NATURA, 1000000, 400000, 200000, 2500000, false, 0, 64);
        add(Material.INK_SAC, "Sacco d'Inchiostro", ShopItem.Category.NATURA, 800000, 320000, 160000, 2000000, false, 0, 64);
        add(Material.SLIME_BALL, "Palla di Muco", ShopItem.Category.NATURA, 2000000, 800000, 400000, 5000000, false, 0, 64);

        // === NETHER ===
        add(Material.NETHERRACK, "Netherrack", ShopItem.Category.NETHER, 300000, 120000, 60000, 700000, false, 0, 64);
        add(Material.NETHER_BRICK, "Mattone Nether", ShopItem.Category.NETHER, 800000, 320000, 160000, 2000000, false, 0, 64);
        add(Material.NETHER_WART, "Verruca Nether", ShopItem.Category.NETHER, 2000000, 800000, 400000, 5000000, false, 0, 64);
        add(Material.BLAZE_ROD, "Bastone Blaze", ShopItem.Category.NETHER, 5000000, 2000000, 1000000, 12000000, false, 0, 64);
        add(Material.BLAZE_POWDER, "Polvere Blaze", ShopItem.Category.NETHER, 3000000, 1200000, 600000, 8000000, false, 0, 64);
        add(Material.GHAST_TEAR, "Lacrima Ghast", ShopItem.Category.NETHER, 8000000, 3200000, 1600000, 20000000, false, 0, 64);
        add(Material.MAGMA_CREAM, "Crema Magma", ShopItem.Category.NETHER, 3000000, 1200000, 600000, 8000000, false, 0, 64);
        add(Material.SOUL_SAND, "Sabbia dell'Anima", ShopItem.Category.NETHER, 1000000, 400000, 200000, 2500000, false, 0, 64);
        add(Material.GLOWSTONE_DUST, "Polvere Glowstone", ShopItem.Category.NETHER, 2000000, 800000, 400000, 5000000, false, 0, 64);
        add(Material.GLOWSTONE, "Glowstone", ShopItem.Category.NETHER, 4000000, 1600000, 800000, 10000000, false, 0, 64);
        add(Material.WITHER_SKELETON_SKULL, "Teschio Wither", ShopItem.Category.NETHER, 20000000, 8000000, 4000000, 50000000, false, 0, 64);
        add(Material.CRIMSON_STEM, "Fungo Cremisi", ShopItem.Category.NETHER, 500000, 200000, 100000, 1200000, false, 0, 64);
        add(Material.WARPED_STEM, "Fungo Deformato", ShopItem.Category.NETHER, 500000, 200000, 100000, 1200000, false, 0, 64);

        // === END ===
        add(Material.END_STONE, "Pietra End", ShopItem.Category.END, 2000000, 800000, 400000, 5000000, false, 0, 64);
        add(Material.CHORUS_FRUIT, "Frutto Coro", ShopItem.Category.END, 3000000, 1200000, 600000, 7000000, false, 0, 64);
        add(Material.CHORUS_FLOWER, "Fiore Coro", ShopItem.Category.END, 5000000, 2000000, 1000000, 12000000, false, 0, 64);
        add(Material.PURPUR_BLOCK, "Blocco Purpur", ShopItem.Category.END, 3000000, 1200000, 600000, 7000000, false, 0, 64);
        add(Material.ENDER_PEARL, "Perla dell'Ender", ShopItem.Category.END, 5000000, 2000000, 1000000, 12000000, false, 0, 64);
        add(Material.ENDER_EYE, "Occhio dell'Ender", ShopItem.Category.END, 10000000, 4000000, 2000000, 25000000, false, 0, 64);
        add(Material.DRAGON_BREATH, "Respiro del Drago", ShopItem.Category.END, 15000000, 6000000, 3000000, 40000000, false, 0, 64);
        add(Material.SHULKER_SHELL, "Guscio Shulker", ShopItem.Category.END, 20000000, 8000000, 4000000, 50000000, false, 0, 64);

        // === RARI ===
        add(Material.TOTEM_OF_UNDYING, "Totem", ShopItem.Category.RARI, 500000000, 200000000, 100000000, 1000000000, true, 7, 1);
        add(Material.ELYTRA, "Elitra", ShopItem.Category.RARI, 1000000000, 400000000, 200000000, 2000000000, true, 7, 1);
        add(Material.HEART_OF_THE_SEA, "Cuore del Mare", ShopItem.Category.RARI, 200000000, 80000000, 40000000, 500000000, true, 3, 1);
        add(Material.NETHER_STAR, "Stella Nether", ShopItem.Category.RARI, 300000000, 120000000, 60000000, 700000000, true, 3, 1);
        add(Material.TRIDENT, "Tridente", ShopItem.Category.RARI, 150000000, 60000000, 30000000, 400000000, true, 3, 1);
    }

    private void add(Material mat, String name, ShopItem.Category cat,
                     double buyPrice, double sellPrice,
                     double minPrice, double maxPrice,
                     boolean rare, int cooldown, int maxStack) {
        items.add(new ShopItem(mat, name, cat, buyPrice, sellPrice, minPrice, maxPrice, rare, cooldown, maxStack));
    }

    public List<ShopItem> getItemsByCategory(ShopItem.Category category) {
        List<ShopItem> result = new ArrayList<>();
        for (ShopItem item : items) {
            if (item.getCategory() == category) result.add(item);
        }
        return result;
    }

    public ShopItem getShopItem(Material material) {
        for (ShopItem item : items) {
            if (item.getMaterial() == material) return item;
        }
        return null;
    }

    public List<ShopItem> getAllItems() { return items; }

    public boolean hasCooldown(UUID uuid, Material material) {
        if (!cooldowns.containsKey(uuid)) return false;
        Map<Material, Long> playerCooldowns = cooldowns.get(uuid);
        if (!playerCooldowns.containsKey(material)) return false;
        ShopItem item = getShopItem(material);
        if (item == null) return false;
        long cooldownMs = (long) item.getCooldownDays() * 24 * 60 * 60 * 1000;
        return System.currentTimeMillis() - playerCooldowns.get(material) < cooldownMs;
    }

    public long getRemainingCooldown(UUID uuid, Material material) {
        if (!cooldowns.containsKey(uuid)) return 0;
        Map<Material, Long> playerCooldowns = cooldowns.get(uuid);
        if (!playerCooldowns.containsKey(material)) return 0;
        ShopItem item = getShopItem(material);
        if (item == null) return 0;
        long cooldownMs = (long) item.getCooldownDays() * 24 * 60 * 60 * 1000;
        long remaining = cooldownMs - (System.currentTimeMillis() - playerCooldowns.get(material));
        return Math.max(0, remaining);
    }

    public void setCooldown(UUID uuid, Material material) {
        cooldowns.computeIfAbsent(uuid, k -> new HashMap<>()).put(material, System.currentTimeMillis());
    }

    public double getSkipCooldownCost(Material material) {
        ShopItem item = getShopItem(material);
        if (item == null) return 0;
        return item.getCurrentPrice() * 2;
    }

    public void skipCooldown(UUID uuid, Material material) {
        if (cooldowns.containsKey(uuid)) {
            cooldowns.get(uuid).remove(material);
        }
    }

    public void resetAllPrices() {
        for (ShopItem item : items) {
            item.resetPrices();
        }
        plugin.getLogger().info("Prezzi dello shop resettati!");
        savePrices();
    }

    public void savePrices() {
        File file = new File(plugin.getDataFolder(), "prices.yml");
        YamlConfiguration config = new YamlConfiguration();
        for (ShopItem item : items) {
            config.set("prices." + item.getMaterial().name() + ".buy", item.getCurrentPrice());
            config.set("prices." + item.getMaterial().name() + ".sell", item.getCurrentSellPrice());
        }
        try { config.save(file); } catch (Exception e) { e.printStackTrace(); }
    }

    public void loadPrices() {
        File file = new File(plugin.getDataFolder(), "prices.yml");
        if (!file.exists()) return;
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        for (ShopItem item : items) {
            String key = "prices." + item.getMaterial().name();
            if (config.contains(key)) {
                double buy = config.getDouble(key + ".buy");
                double sell = config.getDouble(key + ".sell");
                if (buy > 0) item.onBuy(0); // dummy to set price
            }
        }
    }
}
