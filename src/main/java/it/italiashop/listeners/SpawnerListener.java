package it.italiashop.listeners;

import it.italiashop.ItaliaShop;
import it.italiashop.gui.SpawnerGUI;
import it.italiashop.managers.SpawnerManager;
import it.italiashop.managers.SpawnerManager.SpawnerType;
import it.italiashop.managers.SpawnerManager.SpawnerData;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class SpawnerListener implements Listener {

    private final ItaliaShop plugin;

    public SpawnerListener(ItaliaShop plugin) {
        this.plugin = plugin;
    }

    // Piazza spawner dal inventario
    @EventHandler
    public void onSpawnerPlace(org.bukkit.event.block.BlockPlaceEvent e) {
        ItemStack item = e.getItemInHand();
        if (item.getType() != Material.SPAWNER) return;

        SpawnerType type = plugin.getSpawnerManager().getSpawnerTypeFromItem(item);
        if (type == null) return;

        Player player = e.getPlayer();
        Location loc = e.getBlock().getLocation();

        // Imposta il mob dentro lo spawner
        if (e.getBlock().getState() instanceof org.bukkit.block.CreatureSpawner cs) {
            cs.setSpawnedType(type.entityType);
            cs.setDelay(Integer.MAX_VALUE); // Non spawnare mob reali
            cs.update();
        }

        // Registra nel plugin
        plugin.getSpawnerManager().placeSpawner(player, loc, type, 1);
        player.sendMessage(ChatColor.GREEN + "Spawner " + type.displayName +
                ChatColor.GREEN + " piazzato! Produrrà loot ogni 30 minuti.");
    }

    // Clicca spawner per aprire loot
    @EventHandler
    public void onSpawnerClick(PlayerInteractEvent e) {
        if (e.getHand() != EquipmentSlot.HAND) return;
        if (e.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        Block block = e.getClickedBlock();
        if (block == null || block.getType() != Material.SPAWNER) return;

        Player player = e.getPlayer();
        Location loc = block.getLocation();

        // Controlla se è uno spawner del plugin
        if (!plugin.getSpawnerManager().isSpawner(loc)) return;

        e.setCancelled(true);

        // Se ha in mano uno spawner dello stesso tipo, aggiungilo
        ItemStack inHand = player.getInventory().getItemInMainHand();
        SpawnerType handType = plugin.getSpawnerManager().getSpawnerTypeFromItem(inHand);
        SpawnerData data = plugin.getSpawnerManager().getSpawnerData(loc);

        if (handType != null && data != null && handType == data.type) {
            if (data.count >= 10) {
                player.sendMessage(ChatColor.RED + "Questo spawner è già al massimo (10x)!");
                return;
            }
            int toAdd = Math.min(inHand.getAmount(), 10 - data.count);
            data.count += toAdd;
            inHand.setAmount(inHand.getAmount() - toAdd);
            player.sendMessage(ChatColor.GREEN + "Spawner aggiunto! Totale: " + ChatColor.YELLOW + data.count + "x");
            plugin.getSpawnerManager().saveData();
            return;
        }

        // Apri GUI loot
        plugin.getSpawnerGUI().openSpawnerLoot(player, loc);
    }

    // Rompi spawner → droppalo
    @EventHandler
    public void onSpawnerBreak(BlockBreakEvent e) {
        Block block = e.getBlock();
        if (block.getType() != Material.SPAWNER) return;
        Location loc = block.getLocation();
        if (!plugin.getSpawnerManager().isSpawner(loc)) return;

        e.setCancelled(true);
        Player player = e.getPlayer();
        SpawnerData data = plugin.getSpawnerManager().getSpawnerData(loc);
        if (data == null) return;

        // Droppa gli spawner
        ItemStack drop = plugin.getSpawnerManager().createSpawnerItem(data.type, data.count);
        block.getWorld().dropItemNaturally(loc, drop);

        // Droppa il loot accumulato
        for (ItemStack loot : data.loot) {
            if (loot != null && !loot.getType().isAir()) {
                block.getWorld().dropItemNaturally(loc, loot);
            }
        }

        block.setType(org.bukkit.Material.AIR);
        plugin.getSpawnerManager().removeSpawner(loc);
        player.sendMessage(ChatColor.YELLOW + "Spawner rimosso! Hai recuperato " + data.count + "x " + data.type.displayName);
    }

    // Click nella GUI shop spawner
    @EventHandler
    public void onSpawnerShopClick(InventoryClickEvent e) {
        if (!(e.getWhoClicked() instanceof Player player)) return;
        UUID uuid = player.getUniqueId();
        String gui = SpawnerGUI.openGUI.get(uuid);
        if (gui == null) return;
        if (!gui.equals("spawner_shop")) return;

        e.setCancelled(true);

        ItemStack clicked = e.getCurrentItem();
        if (clicked == null || clicked.getType().isAir()) return;
        if (clicked.getType() == Material.BARRIER) {
            player.closeInventory();
            return;
        }

        if (clicked.getItemMeta() == null || !clicked.getItemMeta().hasLore()) return;
        String typeLine = "";
        for (String line : clicked.getItemMeta().getLore()) {
            if (line.startsWith(ChatColor.BLACK + "spawner_buy:")) {
                typeLine = line.replace(ChatColor.BLACK + "spawner_buy:", "");
            }
        }
        if (typeLine.isEmpty()) return;

        try {
            SpawnerType type = SpawnerType.valueOf(typeLine);
            buySpawner(player, type);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void buySpawner(Player player, SpawnerType type) {
        if (!plugin.getCrystalManager().hasCrystals(player, type.cost)) {
            player.sendMessage(ChatColor.RED + "Non hai abbastanza cristalli! Ti servono " + type.cost + " 💎");
            return;
        }

        plugin.getCrystalManager().removeCrystals(player, type.cost);
        ItemStack spawner = plugin.getSpawnerManager().createSpawnerItem(type, 1);
        player.getInventory().addItem(spawner);
        player.closeInventory();
        player.sendMessage(ChatColor.GREEN + "Hai comprato 1x " + type.itemName + ChatColor.GREEN + "!");
        player.sendMessage(ChatColor.AQUA + "💎 Cristalli rimasti: " + plugin.getCrystalManager().getCrystals(player));
    }

    // Chiusura GUI loot spawner → salva contenuto
    @EventHandler
    public void onLootClose(InventoryCloseEvent e) {
        if (!(e.getPlayer() instanceof Player player)) return;
        UUID uuid = player.getUniqueId();
        String gui = SpawnerGUI.openGUI.get(uuid);
        if (gui == null) return;

        if (gui.equals("spawner_loot")) {
            plugin.getSpawnerGUI().handleLootClose(player, e.getInventory());
        }
        // Rimuovi sempre dalla mappa quando si chiude qualsiasi GUI spawner
        SpawnerGUI.openGUI.remove(uuid);
    }
}
