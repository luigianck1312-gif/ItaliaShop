package it.italiashop.managers;

import it.italiashop.ItaliaShop;
import org.bukkit.*;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Villager;

import java.io.File;

public class NPCManager {

    private final ItaliaShop plugin;
    private Villager shopVillager = null;
    private Villager pvpVillager = null;
    private Location shopLocation = null;
    private Location pvpLocation = null;

    public NPCManager(ItaliaShop plugin) {
        this.plugin = plugin;
        load();
    }

    public void spawnShopVillager(Location loc) {
        if (shopVillager != null && !shopVillager.isDead()) shopVillager.remove();
        shopLocation = loc.clone();
        shopVillager = spawnVillager(loc, ChatColor.GOLD + "" + ChatColor.BOLD + "Shop", Villager.Profession.CARTOGRAPHER);
        save();
    }

    public void spawnPvpVillager(Location loc) {
        if (pvpVillager != null && !pvpVillager.isDead()) pvpVillager.remove();
        pvpLocation = loc.clone();
        pvpVillager = spawnVillager(loc, ChatColor.RED + "" + ChatColor.BOLD + "Arena PvP", Villager.Profession.ARMORER);
        save();
    }

    public void removeShopVillager() {
        if (shopVillager != null) { shopVillager.remove(); shopVillager = null; }
        shopLocation = null;
        save();
    }

    public void removePvpVillager() {
        if (pvpVillager != null) { pvpVillager.remove(); pvpVillager = null; }
        pvpLocation = null;
        save();
    }

    private Villager spawnVillager(Location loc, String name, Villager.Profession profession) {
        Villager v = (Villager) loc.getWorld().spawnEntity(loc, EntityType.VILLAGER);
        v.setCustomName(name);
        v.setCustomNameVisible(true);
        v.setAI(false);
        v.setInvulnerable(true);
        v.setSilent(true);
        v.setProfession(profession);
        v.setVillagerLevel(5);
        v.setPersistent(true); // Non viene rimosso dal server
        v.setRemoveWhenFarAway(false); // Non viene rimosso quando il player si allontana
        // Impedisce che venga considerato come mob ostile
        v.setNoDamageTicks(Integer.MAX_VALUE);
        return v;
    }

    public boolean isShopVillager(Entity entity) {
        return shopVillager != null && shopVillager.equals(entity);
    }

    public boolean isPvpVillager(Entity entity) {
        return pvpVillager != null && pvpVillager.equals(entity);
    }

    public void save() {
        File file = new File(plugin.getDataFolder(), "npcs.yml");
        YamlConfiguration config = new YamlConfiguration();

        if (shopLocation != null) {
            config.set("shop.world", shopLocation.getWorld().getName());
            config.set("shop.x", shopLocation.getX());
            config.set("shop.y", shopLocation.getY());
            config.set("shop.z", shopLocation.getZ());
            config.set("shop.yaw", (double) shopLocation.getYaw());
        }

        if (pvpLocation != null) {
            config.set("pvp.world", pvpLocation.getWorld().getName());
            config.set("pvp.x", pvpLocation.getX());
            config.set("pvp.y", pvpLocation.getY());
            config.set("pvp.z", pvpLocation.getZ());
            config.set("pvp.yaw", (double) pvpLocation.getYaw());
        }

        try { config.save(file); } catch (Exception e) { e.printStackTrace(); }
    }

    public void load() {
        File file = new File(plugin.getDataFolder(), "npcs.yml");
        if (!file.exists()) return;

        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

        if (config.contains("shop")) {
            World w = Bukkit.getWorld(config.getString("shop.world", "world"));
            if (w != null) {
                shopLocation = new Location(w,
                        config.getDouble("shop.x"), config.getDouble("shop.y"), config.getDouble("shop.z"),
                        (float) config.getDouble("shop.yaw"), 0);
                spawnShopVillager(shopLocation);
            }
        }

        if (config.contains("pvp")) {
            World w = Bukkit.getWorld(config.getString("pvp.world", "world"));
            if (w != null) {
                pvpLocation = new Location(w,
                        config.getDouble("pvp.x"), config.getDouble("pvp.y"), config.getDouble("pvp.z"),
                        (float) config.getDouble("pvp.yaw"), 0);
                spawnPvpVillager(pvpLocation);
            }
        }
    }
}
