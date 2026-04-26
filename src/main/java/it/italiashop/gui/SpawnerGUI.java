package it.italiashop.gui;

import it.italiashop.ItaliaShop;
import it.italiashop.managers.CrystalManager;
import it.italiashop.managers.SpawnerManager;
import it.italiashop.managers.SpawnerManager.SpawnerType;
import it.italiashop.managers.SpawnerManager.SpawnerData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class SpawnerGUI {

    private final ItaliaShop plugin;
    public static final Map<UUID, String> openGUI = new HashMap<>();
    public static final Map<UUID, Location> openSpawnerLoc = new HashMap<>();

    public SpawnerGUI(ItaliaShop plugin) {
        this.plugin = plugin;
    }

    public void openShop(Player player) {
        Inventory inv = Bukkit.createInventory(null, 27, ChatColor.DARK_PURPLE + "Spawner Shop");

        int slot = 10;
        for (SpawnerType type : SpawnerType.values()) {
            ItemStack item = createShopItem(type, player);
            inv.setItem(slot++, item);
            if (slot == 14) slot = 19;
        }

        inv.setItem(22, createItem(Material.BARRIER, ChatColor.RED + "Chiudi"));
        openGUI.put(player.getUniqueId(), "spawner_shop");
        player.openInventory(inv);
    }

    private ItemStack createShopItem(SpawnerType type, Player player) {
        ItemStack item = new ItemStack(Material.SPAWNER);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(type.itemName);
            int crystals = plugin.getCrystalManager().getCrystals(player);
            boolean canAfford = crystals >= type.cost;
            List<String> lore = new ArrayList<>();
            lore.add(ChatColor.GRAY + "Mob: " + ChatColor.WHITE + type.displayName);
            lore.add(ChatColor.GRAY + "Loot: " + ChatColor.WHITE + type.lootRange[0] + "-" + type.lootRange[1] +
                    " " + formatMaterial(type.lootMaterial));
            if (type == SpawnerType.WITHER_SKELETON) {
                lore.add(ChatColor.GRAY + "Drop chance: " + ChatColor.WHITE + "30%");
            }
            lore.add(ChatColor.GRAY + "Ogni: " + ChatColor.WHITE + "30 minuti");
            lore.add(ChatColor.GRAY + "Max stack: " + ChatColor.WHITE + "10");
            lore.add("");
            lore.add(ChatColor.DARK_AQUA + "💎 Costo: " + ChatColor.AQUA + type.cost + " cristalli");
            lore.add(ChatColor.GRAY + "Hai: " + (canAfford ? ChatColor.GREEN : ChatColor.RED) + crystals + " cristalli");
            lore.add("");
            lore.add(canAfford ? ChatColor.GREEN + "Clicca per comprare!" : ChatColor.RED + "Cristalli insufficienti!");
            lore.add(ChatColor.BLACK + "spawner_buy:" + type.name());
            meta.setLore(lore);
            item.setItemMeta(meta);
        }
        return item;
    }

    public void openSpawnerLoot(Player player, Location loc) {
        SpawnerData data = plugin.getSpawnerManager().getSpawnerData(loc);
        if (data == null) return;

        String title = ChatColor.DARK_PURPLE + data.type.displayName + " (" + data.count + "x) - Loot";
        Inventory inv = Bukkit.createInventory(null, 54, title);

        for (int i = 0; i < 54; i++) {
            if (data.loot[i] != null && !data.loot[i].getType().isAir()) {
                inv.setItem(i, data.loot[i]);
            }
        }

        openGUI.put(player.getUniqueId(), "spawner_loot");
        openSpawnerLoc.put(player.getUniqueId(), loc);
        player.openInventory(inv);
    }

    public void handleLootClose(Player player, Inventory inv) {
        Location loc = openSpawnerLoc.get(player.getUniqueId());
        if (loc == null) return;
        SpawnerData data = plugin.getSpawnerManager().getSpawnerData(loc);
        if (data == null) return;

        // Aggiorna loot con quello rimasto nell'inventario
        for (int i = 0; i < 54; i++) {
            data.loot[i] = inv.getItem(i);
        }
        plugin.getSpawnerManager().saveData();
        openSpawnerLoc.remove(player.getUniqueId());
    }

    private ItemStack createItem(Material mat, String name) {
        ItemStack item = new ItemStack(mat);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) { meta.setDisplayName(name); item.setItemMeta(meta); }
        return item;
    }

    private String formatMaterial(Material mat) {
        String name = mat.name().toLowerCase().replace("_", " ");
        String[] words = name.split(" ");
        StringBuilder sb = new StringBuilder();
        for (String word : words) {
            sb.append(Character.toUpperCase(word.charAt(0))).append(word.substring(1)).append(" ");
        }
        return sb.toString().trim();
    }
}
