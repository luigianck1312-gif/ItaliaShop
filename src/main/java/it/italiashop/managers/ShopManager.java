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
    private final Map<UUID, Map<Material, Long>> cooldowns = new HashMap<>();

    public ShopManager(ItaliaShop plugin) {
        this.plugin = plugin;
        registerItems();
        loadPrices();
        long threeDays = 20L * 60 * 60 * 24 * 3;
        plugin.getServer().getScheduler().runTaskTimer(plugin, this::resetAllPrices, threeDays, threeDays);
    }

    private void registerItems() {
        // BLOCCHI
        add(Material.DIRT, "Terra", ShopItem.Category.BLOCCHI, 200000, 80000, 50000, 500000, false, 0, 64, true);
        add(Material.STONE, "Pietra", ShopItem.Category.BLOCCHI, 500000, 200000, 100000, 1000000, false, 0, 64, true);
        add(Material.COBBLESTONE, "Ciottoli", ShopItem.Category.BLOCCHI, 400000, 150000, 80000, 800000, false, 0, 64, true);
        add(Material.SAND, "Sabbia", ShopItem.Category.BLOCCHI, 300000, 120000, 70000, 700000, false, 0, 64, true);
        add(Material.GRAVEL, "Ghiaia", ShopItem.Category.BLOCCHI, 350000, 130000, 80000, 750000, false, 0, 64, true);
        add(Material.OAK_LOG, "Quercia", ShopItem.Category.BLOCCHI, 800000, 320000, 200000, 2000000, false, 0, 64, true);
        add(Material.BIRCH_LOG, "Betulla", ShopItem.Category.BLOCCHI, 800000, 320000, 200000, 2000000, false, 0, 64, true);
        add(Material.SPRUCE_LOG, "Abete", ShopItem.Category.BLOCCHI, 800000, 320000, 200000, 2000000, false, 0, 64, true);
        add(Material.JUNGLE_LOG, "Giungla", ShopItem.Category.BLOCCHI, 900000, 360000, 200000, 2000000, false, 0, 64, true);
        add(Material.ACACIA_LOG, "Acacia", ShopItem.Category.BLOCCHI, 850000, 340000, 200000, 2000000, false, 0, 64, true);
        add(Material.DARK_OAK_LOG, "Rovere", ShopItem.Category.BLOCCHI, 850000, 340000, 200000, 2000000, false, 0, 64, true);
        add(Material.OAK_PLANKS, "Assi Quercia", ShopItem.Category.BLOCCHI, 600000, 240000, 150000, 1500000, false, 0, 64, true);
        add(Material.GLASS, "Vetro", ShopItem.Category.BLOCCHI, 700000, 280000, 150000, 1500000, false, 0, 64, true);
        add(Material.BRICKS, "Mattoni", ShopItem.Category.BLOCCHI, 1000000, 400000, 250000, 2500000, false, 0, 64, true);
        add(Material.OBSIDIAN, "Ossidiana", ShopItem.Category.BLOCCHI, 5000000, 2000000, 1000000, 10000000, false, 0, 64, true);
        add(Material.ICE, "Ghiaccio", ShopItem.Category.BLOCCHI, 500000, 200000, 100000, 1000000, false, 0, 64, true);
        add(Material.SNOW_BLOCK, "Neve", ShopItem.Category.BLOCCHI, 400000, 160000, 80000, 800000, false, 0, 64, true);
        add(Material.CLAY, "Argilla", ShopItem.Category.BLOCCHI, 600000, 240000, 150000, 1500000, false, 0, 64, true);
        add(Material.TERRACOTTA, "Terracotta", ShopItem.Category.BLOCCHI, 700000, 280000, 150000, 1500000, false, 0, 64, true);
        add(Material.WHITE_WOOL, "Lana", ShopItem.Category.BLOCCHI, 500000, 200000, 100000, 1000000, false, 0, 64, true);
        add(Material.STONE_BRICKS, "Mattoni Pietra", ShopItem.Category.BLOCCHI, 800000, 320000, 160000, 2000000, false, 0, 64, true);
        add(Material.SANDSTONE, "Arenaria", ShopItem.Category.BLOCCHI, 700000, 280000, 140000, 1800000, false, 0, 64, true);
        add(Material.ANDESITE, "Andesite", ShopItem.Category.BLOCCHI, 500000, 200000, 100000, 1200000, false, 0, 64, true);
        add(Material.DIORITE, "Diorite", ShopItem.Category.BLOCCHI, 500000, 200000, 100000, 1200000, false, 0, 64, true);
        add(Material.GRANITE, "Granito", ShopItem.Category.BLOCCHI, 500000, 200000, 100000, 1200000, false, 0, 64, true);

        // MINERALI
        add(Material.COAL, "Carbone", ShopItem.Category.MINERALI, 1000000, 400000, 200000, 3000000, false, 0, 64, true);
        add(Material.IRON_INGOT, "Lingotto Ferro", ShopItem.Category.MINERALI, 5000000, 2000000, 1000000, 15000000, false, 0, 64, true);
        add(Material.GOLD_INGOT, "Lingotto Oro", ShopItem.Category.MINERALI, 10000000, 4000000, 2000000, 25000000, false, 0, 64, true);
        add(Material.DIAMOND, "Diamante", ShopItem.Category.MINERALI, 50000000, 20000000, 10000000, 150000000, false, 0, 64, true);
        add(Material.EMERALD, "Smeraldo", ShopItem.Category.MINERALI, 30000000, 12000000, 6000000, 80000000, false, 0, 64, true);
        add(Material.LAPIS_LAZULI, "Lapislazzuli", ShopItem.Category.MINERALI, 3000000, 1200000, 600000, 8000000, false, 0, 64, true);
        add(Material.REDSTONE, "Redstone", ShopItem.Category.MINERALI, 2000000, 800000, 400000, 6000000, false, 0, 64, true);
        add(Material.QUARTZ, "Quarzo", ShopItem.Category.MINERALI, 2500000, 1000000, 500000, 7000000, false, 0, 64, true);
        add(Material.AMETHYST_SHARD, "Ametista", ShopItem.Category.MINERALI, 4000000, 1600000, 800000, 10000000, false, 0, 64, true);
        add(Material.COPPER_INGOT, "Rame", ShopItem.Category.MINERALI, 1920000, 768000, 384000, 4800000, false, 0, 64, true);

        // NATURA
        add(Material.SEAGRASS, "Alghe", ShopItem.Category.NATURA, 200000, 80000, 50000, 500000, false, 0, 64, true);
        add(Material.KELP, "Alga", ShopItem.Category.NATURA, 150000, 60000, 30000, 400000, false, 0, 64, true);
        add(Material.SUGAR_CANE, "Canna Zucchero", ShopItem.Category.NATURA, 300000, 120000, 60000, 700000, false, 0, 64, true);
        add(Material.BAMBOO, "Bambu", ShopItem.Category.NATURA, 250000, 100000, 50000, 600000, false, 0, 64, true);
        add(Material.CACTUS, "Cactus", ShopItem.Category.NATURA, 400000, 160000, 80000, 900000, false, 0, 64, true);
        add(Material.PUMPKIN, "Zucca", ShopItem.Category.NATURA, 500000, 200000, 100000, 1200000, false, 0, 64, true);
        add(Material.OAK_LEAVES, "Foglie", ShopItem.Category.NATURA, 200000, 80000, 40000, 500000, false, 0, 64, true);
        add(Material.VINE, "Rampicante", ShopItem.Category.NATURA, 300000, 120000, 60000, 700000, false, 0, 64, true);
        add(Material.LILY_PAD, "Ninfea", ShopItem.Category.NATURA, 500000, 200000, 100000, 1200000, false, 0, 64, true);
        add(Material.BONE_MEAL, "Farina Osso", ShopItem.Category.NATURA, 800000, 320000, 160000, 2000000, false, 0, 64, true);
        add(Material.STRING, "Filo", ShopItem.Category.NATURA, 600000, 240000, 120000, 1500000, false, 0, 64, true);
        add(Material.FEATHER, "Piuma", ShopItem.Category.NATURA, 500000, 200000, 100000, 1200000, false, 0, 64, true);
        add(Material.LEATHER, "Cuoio", ShopItem.Category.NATURA, 1000000, 400000, 200000, 2500000, false, 0, 64, true);
        add(Material.INK_SAC, "Inchiostro", ShopItem.Category.NATURA, 800000, 320000, 160000, 2000000, false, 0, 64, true);
        add(Material.SLIME_BALL, "Muco", ShopItem.Category.NATURA, 2000000, 800000, 400000, 5000000, false, 0, 64, true);
        add(Material.GUNPOWDER, "Polvere da Sparo", ShopItem.Category.NATURA, 1280000, 512000, 256000, 3200000, false, 0, 64, true);
        add(Material.BONE, "Osso", ShopItem.Category.NATURA, 640000, 256000, 128000, 1600000, false, 0, 64, true);
        add(Material.FLINT, "Selce", ShopItem.Category.NATURA, 320000, 128000, 64000, 800000, false, 0, 64, true);
        add(Material.HONEYCOMB, "Favo", ShopItem.Category.NATURA, 960000, 384000, 192000, 2400000, false, 0, 64, true);
        add(Material.PHANTOM_MEMBRANE, "Membrana Phantom", ShopItem.Category.NATURA, 3200000, 1280000, 640000, 8000000, false, 0, 64, true);

        // NETHER
        add(Material.NETHERRACK, "Netherrack", ShopItem.Category.NETHER, 300000, 120000, 60000, 700000, false, 0, 64, true);
        add(Material.NETHER_BRICK, "Mattone Nether", ShopItem.Category.NETHER, 800000, 320000, 160000, 2000000, false, 0, 64, true);
        add(Material.NETHER_WART, "Verruca Nether", ShopItem.Category.NETHER, 2000000, 800000, 400000, 5000000, false, 0, 64, true);
        add(Material.BLAZE_ROD, "Bastone Blaze", ShopItem.Category.NETHER, 5000000, 2000000, 1000000, 12000000, false, 0, 64, true);
        add(Material.BLAZE_POWDER, "Polvere Blaze", ShopItem.Category.NETHER, 3000000, 1200000, 600000, 8000000, false, 0, 64, true);
        add(Material.GHAST_TEAR, "Lacrima Ghast", ShopItem.Category.NETHER, 8000000, 3200000, 1600000, 20000000, false, 0, 64, true);
        add(Material.MAGMA_CREAM, "Crema Magma", ShopItem.Category.NETHER, 3000000, 1200000, 600000, 8000000, false, 0, 64, true);
        add(Material.SOUL_SAND, "Sabbia Anima", ShopItem.Category.NETHER, 1000000, 400000, 200000, 2500000, false, 0, 64, true);
        add(Material.GLOWSTONE_DUST, "Polvere Glowstone", ShopItem.Category.NETHER, 2000000, 800000, 400000, 5000000, false, 0, 64, true);
        add(Material.GLOWSTONE, "Glowstone", ShopItem.Category.NETHER, 4000000, 1600000, 800000, 10000000, false, 0, 64, true);
        add(Material.WITHER_SKELETON_SKULL, "Teschio Wither", ShopItem.Category.NETHER, 20000000, 8000000, 4000000, 50000000, false, 0, 64, true);
        add(Material.CRIMSON_STEM, "Fungo Cremisi", ShopItem.Category.NETHER, 500000, 200000, 100000, 1200000, false, 0, 64, true);
        add(Material.WARPED_STEM, "Fungo Deformato", ShopItem.Category.NETHER, 500000, 200000, 100000, 1200000, false, 0, 64, true);

        // END
        add(Material.END_STONE, "Pietra End", ShopItem.Category.END, 2000000, 800000, 400000, 5000000, false, 0, 64, true);
        add(Material.CHORUS_FRUIT, "Frutto Coro", ShopItem.Category.END, 3000000, 1200000, 600000, 7000000, false, 0, 64, true);
        add(Material.CHORUS_FLOWER, "Fiore Coro", ShopItem.Category.END, 5000000, 2000000, 1000000, 12000000, false, 0, 64, true);
        add(Material.PURPUR_BLOCK, "Purpur", ShopItem.Category.END, 3000000, 1200000, 600000, 7000000, false, 0, 64, true);
        add(Material.ENDER_PEARL, "Perla Ender", ShopItem.Category.END, 5000000, 2000000, 1000000, 12000000, false, 0, 64, true);
        add(Material.ENDER_EYE, "Occhio Ender", ShopItem.Category.END, 10000000, 4000000, 2000000, 25000000, false, 0, 64, true);
        add(Material.DRAGON_BREATH, "Respiro Drago", ShopItem.Category.END, 15000000, 6000000, 3000000, 40000000, false, 0, 64, true);
        add(Material.SHULKER_SHELL, "Guscio Shulker", ShopItem.Category.END, 20000000, 8000000, 4000000, 50000000, false, 0, 64, true);

        // RARI
        add(Material.TOTEM_OF_UNDYING, "Totem", ShopItem.Category.RARI, 500000000, 200000000, 100000000, 1000000000, true, 7, 1, true);
        add(Material.ELYTRA, "Elitra", ShopItem.Category.RARI, 1000000000, 400000000, 200000000, 2000000000, true, 7, 1, true);
        add(Material.HEART_OF_THE_SEA, "Cuore del Mare", ShopItem.Category.RARI, 200000000, 80000000, 40000000, 500000000, true, 3, 1, true);
        add(Material.NETHER_STAR, "Stella Nether", ShopItem.Category.RARI, 300000000, 120000000, 60000000, 700000000, true, 3, 1, true);
        add(Material.TRIDENT, "Tridente", ShopItem.Category.RARI, 150000000, 60000000, 30000000, 400000000, true, 3, 1, true);
    }

    private void add(Material mat, String name, ShopItem.Category cat,
                     double buyPrice, double sellPrice, double minPrice, double maxPrice,
                     boolean rare, int cooldown, int maxStack, boolean buyable) {
        items.add(new ShopItem(mat, name, cat, buyPrice, sellPrice, minPrice, maxPrice, rare, cooldown, maxStack, buyable));
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
        Map<Material, Long> pc = cooldowns.get(uuid);
        if (!pc.containsKey(material)) return false;
        ShopItem item = getShopItem(material);
        if (item == null) return false;
        long ms = (long) item.getCooldownDays() * 24 * 60 * 60 * 1000;
        return System.currentTimeMillis() - pc.get(material) < ms;
    }

    public long getRemainingCooldown(UUID uuid, Material material) {
        if (!cooldowns.containsKey(uuid)) return 0;
        Map<Material, Long> pc = cooldowns.get(uuid);
        if (!pc.containsKey(material)) return 0;
        ShopItem item = getShopItem(material);
        if (item == null) return 0;
        long ms = (long) item.getCooldownDays() * 24 * 60 * 60 * 1000;
        return Math.max(0, ms - (System.currentTimeMillis() - pc.get(material)));
    }

    public void setCooldown(UUID uuid, Material material) {
        cooldowns.computeIfAbsent(uuid, k -> new HashMap<>()).put(material, System.currentTimeMillis());
    }

    public double getSkipCooldownCost(Material material) {
        ShopItem item = getShopItem(material);
        return item != null ? item.getCurrentPrice() * 2 : 0;
    }

    public void skipCooldown(UUID uuid, Material material) {
        if (cooldowns.containsKey(uuid)) cooldowns.get(uuid).remove(material);
    }

    public void resetAllPrices() {
        for (ShopItem item : items) item.resetPrices();
        plugin.getLogger().info("Prezzi shop resettati!");
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
        // prices loaded on startup
    }
}
