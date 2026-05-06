package it.italiashop.listeners;

import it.italiashop.ItaliaShop;
import it.italiashop.gui.PvPGUI;
import it.italiashop.gui.ShopGUI;
import it.italiashop.managers.ItemValueRegistry;
import it.italiashop.models.ShopItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ShopListener implements Listener {

    private final ItaliaShop plugin;

    public ShopListener(ItaliaShop plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent e) {
        if (!(e.getEntity() instanceof Villager villager)) return;
        if (plugin.getNpcManager().isShopVillager(villager) || plugin.getNpcManager().isPvpVillager(villager) ||
            (plugin.getNpcManager().getSpawnerUUID() != null && villager.getUniqueId().equals(plugin.getNpcManager().getSpawnerUUID()))) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onVillagerInteract(PlayerInteractEntityEvent e) {
        if (!(e.getRightClicked() instanceof Villager villager)) return;
        if (e.getHand() != EquipmentSlot.HAND) return;
        Player player = e.getPlayer();
        e.setCancelled(true);

        if (plugin.getNpcManager().isShopVillager(villager)) {
            plugin.getShopGUI().openMainMenu(player);
        } else if (plugin.getNpcManager().isPvpVillager(villager)) {
            plugin.getPvpGUI().openMainMenu(player);
        } else if (plugin.getNpcManager().getSpawnerUUID() != null &&
                   villager.getUniqueId().equals(plugin.getNpcManager().getSpawnerUUID())) {
            plugin.getSpawnerGUI().openShop(player);
        }
    }

    @EventHandler
    public void onItemSwitch(PlayerItemHeldEvent e) {
        Player player = e.getPlayer();
        if (ShopGUI.openGUI.containsKey(player.getUniqueId())) return;
        if (PvPGUI.openGUI.containsKey(player.getUniqueId())) return;

        plugin.getServer().getScheduler().runTaskLater(plugin, () -> {
            ItemStack item = player.getInventory().getItem(e.getNewSlot());
            if (item == null || item.getType().isAir()) return;
            double buy = ItemValueRegistry.getBuyPrice(item.getType());
            double sell = ItemValueRegistry.getSellPrice(item.getType());
            if (buy <= 0 && sell <= 0) return;
            updateItemLore(player, item, e.getNewSlot());
        }, 1L);
    }

    /**
     * FIX STACKING: aggiunge il lore all'item PRIMA che venga messo nell'inventario
     * con priorità LOWEST = viene eseguito prima di tutti gli altri plugin.
     * Così quando Minecraft fa lo stacking trova item con lore identico → li stacka correttamente.
     */
    @EventHandler(priority = EventPriority.LOWEST)
    public void onInventoryPickup(org.bukkit.event.player.PlayerPickupItemEvent e) {
        Player player = e.getPlayer();
        ItemStack item = e.getItem().getItemStack();
        Material mat = item.getType();

        double buyPrice = ItemValueRegistry.getBuyPrice(mat);
        double sellPrice = ItemValueRegistry.getSellPrice(mat);
        if (buyPrice <= 0 && sellPrice <= 0) return;

        // Controlla se ha già il lore aggiornato
        ItemMeta meta = item.getItemMeta();
        if (meta != null && meta.hasLore()) {
            boolean hasPrice = meta.getLore().stream()
                .anyMatch(l -> ChatColor.stripColor(l).startsWith("Vendita:") ||
                               ChatColor.stripColor(l).startsWith("Acquisto:"));
            if (hasPrice) return; // già aggiornato, non toccare
        }

        // Aggiunge il lore PRIMA che l'item entri nell'inventario
        applyLoreToItem(item, mat, buyPrice, sellPrice);
    }

    private void applyLoreToItem(ItemStack item, Material mat, double buyPrice, double sellPrice) {
        double actualSellPrice = sellPrice;
        short maxDur = mat.getMaxDurability();
        if (maxDur > 0) {
            short damage = item.getDurability();
            int remaining = maxDur - damage;
            double ratio = Math.max(0, (double) remaining / maxDur);
            actualSellPrice = sellPrice * ratio;
        }

        ItemMeta meta = item.getItemMeta();
        if (meta == null) return;

        List<String> oldLore = meta.hasLore() ? new ArrayList<>(meta.getLore()) : new ArrayList<>();
        List<String> cleanLore = new ArrayList<>();
        for (String line : oldLore) {
            String stripped = ChatColor.stripColor(line);
            if (stripped.startsWith("Acquisto:") || stripped.startsWith("Vendita:")) continue;
            cleanLore.add(line);
        }

        if (buyPrice > 0) cleanLore.add(ChatColor.DARK_GRAY + "Acquisto: " + ChatColor.GREEN + "$" + ShopGUI.formatPrice(buyPrice));
        if (actualSellPrice > 1) cleanLore.add(ChatColor.DARK_GRAY + "Vendita: " + ChatColor.RED + "$" + ShopGUI.formatPrice(actualSellPrice));
        else if (sellPrice > 0 && maxDur > 0) cleanLore.add(ChatColor.DARK_GRAY + "Vendita: " + ChatColor.RED + "Rotto - $0");
        else if (sellPrice > 0) cleanLore.add(ChatColor.DARK_GRAY + "Vendita: " + ChatColor.RED + "$" + ShopGUI.formatPrice(sellPrice));

        meta.setLore(cleanLore);
        item.setItemMeta(meta);
    }

    @EventHandler
    public void onInventoryInteract(org.bukkit.event.inventory.InventoryClickEvent e) {
        if (!(e.getWhoClicked() instanceof Player player)) return;
        UUID uuid = player.getUniqueId();

        if (ShopGUI.openGUI.containsKey(uuid)) return;
        if (PvPGUI.openGUI.containsKey(uuid)) return;
        if (it.italiashop.gui.SpawnerGUI.openGUI.containsKey(uuid)) return;

        ItemStack clicked = e.getCurrentItem();
        if (clicked == null || clicked.getType().isAir()) return;
        Material mat = clicked.getType();
        double buy = ItemValueRegistry.getBuyPrice(mat);
        double sell = ItemValueRegistry.getSellPrice(mat);
        if (buy <= 0 && sell <= 0) return;

        plugin.getServer().getScheduler().runTaskLater(plugin, () -> {
            for (int i = 0; i < player.getInventory().getSize(); i++) {
                ItemStack item = player.getInventory().getItem(i);
                if (item == null || item.getType() != mat) continue;
                ItemMeta meta = item.getItemMeta();
                if (meta != null && meta.hasLore()) {
                    boolean hasPrice = meta.getLore().stream()
                        .anyMatch(l -> ChatColor.stripColor(l).startsWith("Vendita:"));
                    if (hasPrice) continue;
                }
                updateItemLore(player, item, i);
            }
        }, 2L);
    }

    @EventHandler
    public void onInventoryOpen(org.bukkit.event.inventory.InventoryOpenEvent e) {
        if (!(e.getPlayer() instanceof Player player)) return;
        if (ShopGUI.openGUI.containsKey(player.getUniqueId())) return;
        if (PvPGUI.openGUI.containsKey(player.getUniqueId())) return;
        if (it.italiashop.gui.SpawnerGUI.openGUI.containsKey(player.getUniqueId())) return;

        plugin.getServer().getScheduler().runTaskLater(plugin, () -> {
            for (int i = 0; i < player.getInventory().getSize(); i++) {
                ItemStack item = player.getInventory().getItem(i);
                if (item == null || item.getType().isAir()) continue;
                double buy = ItemValueRegistry.getBuyPrice(item.getType());
                double sell = ItemValueRegistry.getSellPrice(item.getType());
                if (buy <= 0 && sell <= 0) continue;
                updateItemLore(player, item, i);
            }
        }, 1L);
    }

    public void updateItemLorePublic(org.bukkit.entity.Player player, ItemStack item, int slot) {
        updateItemLore(player, item, slot);
    }

    private void updateItemLore(Player player, ItemStack item, int slot) {
        Material mat = item.getType();
        double buyPrice = ItemValueRegistry.getBuyPrice(mat);
        double sellPrice = ItemValueRegistry.getSellPrice(mat);
        if (sellPrice <= 0 && buyPrice <= 0) return;

        // Salta se ha già il lore aggiornato — non rompere gli stack!
        ItemMeta existingMeta = item.getItemMeta();
        if (existingMeta != null && existingMeta.hasLore()) {
            boolean hasPrice = existingMeta.getLore().stream()
                .anyMatch(l -> ChatColor.stripColor(l).startsWith("Vendita:") ||
                               ChatColor.stripColor(l).startsWith("Acquisto:"));
            if (hasPrice) return;
        }

        double actualSellPrice = sellPrice;
        short maxDur = mat.getMaxDurability();
        if (maxDur > 0) {
            short damage = item.getDurability();
            int remaining = maxDur - damage;
            double ratio = Math.max(0, (double) remaining / maxDur);
            actualSellPrice = sellPrice * ratio;
        }

        ItemMeta meta = item.getItemMeta();
        if (meta == null) return;

        List<String> oldLore = meta.hasLore() ? new ArrayList<>(meta.getLore()) : new ArrayList<>();
        List<String> cleanLore = new ArrayList<>();
        for (String line : oldLore) {
            String stripped = ChatColor.stripColor(line);
            if (stripped.startsWith("Acquisto:") || stripped.startsWith("Vendita:")) continue;
            cleanLore.add(line);
        }

        if (buyPrice > 0) cleanLore.add(ChatColor.DARK_GRAY + "Acquisto: " + ChatColor.GREEN + "$" + ShopGUI.formatPrice(buyPrice));
        if (actualSellPrice > 1) cleanLore.add(ChatColor.DARK_GRAY + "Vendita: " + ChatColor.RED + "$" + ShopGUI.formatPrice(actualSellPrice));
        else if (sellPrice > 0 && maxDur > 0) cleanLore.add(ChatColor.DARK_GRAY + "Vendita: " + ChatColor.RED + "Rotto - $0");
        else if (sellPrice > 0) cleanLore.add(ChatColor.DARK_GRAY + "Vendita: " + ChatColor.RED + "$" + ShopGUI.formatPrice(sellPrice));

        meta.setLore(cleanLore);
        item.setItemMeta(meta);
        player.getInventory().setItem(slot, item);
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        Player victim = e.getEntity();
        if (!plugin.getArenaManager().isFighting(victim.getUniqueId())) return;
        e.getDrops().clear();
        e.setDroppedExp(0);
        e.setDeathMessage(null);
        plugin.getArenaManager().onFighterDeath(victim);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (!(e.getWhoClicked() instanceof Player player)) return;
        UUID uuid = player.getUniqueId();

        String shopGui = ShopGUI.openGUI.get(uuid);
        String pvpGui = PvPGUI.openGUI.get(uuid);

        if (shopGui == null && pvpGui == null) return;

        String title = e.getView().getTitle();
        if (!title.contains("Shop") && !title.contains("Compra") && !title.contains("Vendi") &&
            !title.contains("Arena") && !title.contains("Inviti") && !title.contains("Scommessa") &&
            !title.contains("avversario")) return;

        e.setCancelled(true);
        if (e.getCurrentItem() == null) return;

        if (shopGui != null) handleShopClick(player, shopGui, e.getSlot(), e.getCurrentItem());
        if (pvpGui != null) handlePvpClick(player, pvpGui, e.getSlot(), e.getCurrentItem(), e.isRightClick());
    }

    private void handleShopClick(Player player, String gui, int slot, ItemStack clicked) {
        UUID uuid = player.getUniqueId();

        switch (gui) {
            case "main" -> {
                if (slot == 11) { player.closeInventory(); plugin.getShopGUI().openCategoryMenu(player); }
                else if (slot == 15) { player.closeInventory(); plugin.getShopGUI().openSellInventory(player); }
                else if (slot == 22) player.closeInventory();
            }
            case "categories" -> {
                ShopItem.Category cat = switch (slot) {
                    case 10 -> ShopItem.Category.BLOCCHI;
                    case 11 -> ShopItem.Category.PIETRA;
                    case 12 -> ShopItem.Category.LEGNO;
                    case 13 -> ShopItem.Category.DECORAZIONI;
                    case 14 -> ShopItem.Category.MINERALI;
                    case 19 -> ShopItem.Category.NATURA;
                    case 20 -> ShopItem.Category.NETHER;
                    case 21 -> ShopItem.Category.END;
                    case 22 -> ShopItem.Category.REDSTONE;
                    case 23 -> ShopItem.Category.RARI;
                    default -> null;
                };
                if (cat != null) { player.closeInventory(); plugin.getShopGUI().openCategory(player, cat, 0); }
                else if (slot == 36) { player.closeInventory(); plugin.getShopGUI().openMainMenu(player); }
            }
            default -> {
                if (gui.startsWith("category_")) {
                    if (slot == 49) { player.closeInventory(); plugin.getShopGUI().openCategoryMenu(player); return; }
                    String catName = gui.replace("category_", "");
                    ShopItem.Category cat;
                    try { cat = ShopItem.Category.valueOf(catName); } catch (Exception ex) { return; }
                    int currentPage = ShopGUI.categoryPage.getOrDefault(uuid, 0);
                    if (slot == 45) { player.closeInventory(); plugin.getShopGUI().openCategory(player, cat, currentPage - 1); return; }
                    if (slot == 53) { player.closeInventory(); plugin.getShopGUI().openCategory(player, cat, currentPage + 1); return; }

                    String matName = getLoreHidden(clicked);
                    if (matName != null) {
                        try {
                            Material mat = Material.valueOf(matName);
                            ShopItem shopItem = plugin.getShopManager().getShopItem(mat);
                            if (shopItem != null) { player.closeInventory(); plugin.getShopGUI().openBuyMenu(player, shopItem); }
                        } catch (Exception ignored) {}
                    }
                } else if (gui.equals("buy_item")) {
                    handleBuyClick(player, slot, clicked);
                } else if (gui.equals("sell_inventory")) {
                    if (slot == 49) { player.closeInventory(); plugin.getShopGUI().openMainMenu(player); return; }
                    String matName = getLoreHidden(clicked);
                    if (matName != null) {
                        try {
                            Material mat = Material.valueOf(matName);
                            player.closeInventory();
                            plugin.getShopGUI().openSellQuantityMenu(player, mat);
                        } catch (Exception ignored) {}
                    }
                } else if (gui.startsWith("sell_qty_")) {
                    handleSellQtyClick(player, gui, slot);
                }
            }
        }
    }

    private void handleBuyClick(Player player, int slot, ItemStack clicked) {
        UUID uuid = player.getUniqueId();
        ShopItem item = ShopGUI.selectedItem.get(uuid);
        if (item == null) return;

        if (slot == 22) { player.closeInventory(); plugin.getShopGUI().openCategory(player, item.getCategory(), ShopGUI.categoryPage.getOrDefault(uuid, 0)); return; }

        boolean hasCooldown = item.isRare() && plugin.getShopManager().hasCooldown(uuid, item.getMaterial());

        if (hasCooldown && slot == 11) {
            double cost = plugin.getShopManager().getSkipCooldownCost(item.getMaterial());
            if (!plugin.getEconomy().has(player, cost)) { player.sendMessage(ChatColor.RED + "Non hai abbastanza soldi!"); return; }
            plugin.getEconomy().withdrawPlayer(player, cost);
            plugin.getShopManager().skipCooldown(uuid, item.getMaterial());
            player.sendMessage(ChatColor.GREEN + "Cooldown saltato!");
            player.closeInventory();
            plugin.getShopGUI().openBuyMenu(player, item);
            return;
        }

        int amount = switch (slot) {
            case 9 -> 1;
            case 11 -> item.getMaxStack() == 1 ? 1 : 16;
            case 13 -> item.getMaxStack() == 1 ? 1 : 32;
            case 15 -> item.getMaxStack() == 1 ? 1 : 64;
            default -> 0;
        };
        if (amount == 0) return;

        double cost = item.getCurrentPrice() * amount;

        if (!plugin.getEconomy().has(player, cost)) { player.sendMessage(ChatColor.RED + "Non hai abbastanza soldi!"); return; }
        if (player.getInventory().firstEmpty() == -1) { player.sendMessage(ChatColor.RED + "Inventario pieno!"); return; }

        plugin.getEconomy().withdrawPlayer(player, cost);

        ItemStack newItem = new ItemStack(item.getMaterial(), amount);
        ItemMeta meta = newItem.getItemMeta();
        if (meta != null) {
            List<String> lore = new ArrayList<>();
            double buyPrice = ItemValueRegistry.getBuyPrice(item.getMaterial());
            double sellPrice = ItemValueRegistry.getSellPrice(item.getMaterial());
            if (buyPrice > 0) lore.add(ChatColor.DARK_GRAY + "Acquisto: " + ChatColor.GREEN + "$" + ShopGUI.formatPrice(buyPrice));
            if (sellPrice > 0) lore.add(ChatColor.DARK_GRAY + "Vendita: " + ChatColor.RED + "$" + ShopGUI.formatPrice(sellPrice));
            meta.setLore(lore);
            newItem.setItemMeta(meta);
        }
        player.getInventory().addItem(newItem);
        item.onBuy(amount);
        if (item.isRare()) plugin.getShopManager().setCooldown(uuid, item.getMaterial());
        player.sendMessage(ChatColor.GREEN + "Comprato x" + amount + " " + item.getDisplayName() + " per $" + ShopGUI.formatPrice(cost));
        plugin.getShopManager().savePrices();
    }

    private void handleSellQtyClick(Player player, String gui, int slot) {
        if (slot == 22) { player.closeInventory(); plugin.getShopGUI().openSellInventory(player); return; }

        String matName = gui.replace("sell_qty_", "");
        Material mat;
        try { mat = Material.valueOf(matName); } catch (Exception e) { return; }

        double sellPrice = ItemValueRegistry.getSellPrice(mat);

        if (mat.getMaxDurability() > 0) {
            int firstSlot = player.getInventory().first(mat);
            if (firstSlot >= 0) {
                ItemStack sample = player.getInventory().getItem(firstSlot);
                if (sample != null && sample.getItemMeta() instanceof org.bukkit.inventory.meta.Damageable dmg) {
                    int maxDur = mat.getMaxDurability();
                    int remaining = maxDur - dmg.getDamage();
                    double ratio = (double) remaining / maxDur;
                    sellPrice = sellPrice * ratio;
                }
            }
        }

        int inInventory = countInInventory(player, mat);
        int amount = switch (slot) {
            case 9 -> 1;
            case 11 -> 16;
            case 13 -> 32;
            case 15 -> 64;
            case 17 -> inInventory;
            default -> 0;
        };
        if (amount == 0) return;
        if (inInventory < amount) { player.sendMessage(ChatColor.RED + "Non hai abbastanza oggetti!"); return; }

        double totalIncome = 0;
        int toRemove = amount;
        for (int i = 0; i < player.getInventory().getSize() && toRemove > 0; i++) {
            ItemStack slot2 = player.getInventory().getItem(i);
            if (slot2 == null || slot2.getType() != mat) continue;

            int take = Math.min(toRemove, slot2.getAmount());
            double itemSellPrice = sellPrice;

            if (mat.getMaxDurability() > 0 && slot2.getItemMeta() instanceof org.bukkit.inventory.meta.Damageable dmg) {
                int maxDur = mat.getMaxDurability();
                int remaining = maxDur - dmg.getDamage();
                double ratio = (double) remaining / maxDur;
                itemSellPrice = sellPrice * ratio;
            }

            totalIncome += itemSellPrice * take;

            if (take >= slot2.getAmount()) {
                player.getInventory().setItem(i, null);
            } else {
                slot2.setAmount(slot2.getAmount() - take);
            }
            toRemove -= take;
        }
        player.updateInventory();

        plugin.getEconomy().depositPlayer(player, totalIncome);

        ShopItem shopItem = plugin.getShopManager().getShopItem(mat);
        if (shopItem != null) { shopItem.onSell(amount); plugin.getShopManager().savePrices(); }

        player.sendMessage(ChatColor.GREEN + "Venduto x" + amount + " " + ShopGUI.formatMaterialName(mat.name()) + " per $" + ShopGUI.formatPrice(totalIncome));
        player.closeInventory();
    }

    private void handlePvpClick(Player player, String gui, int slot, ItemStack clicked, boolean rightClick) {
        UUID uuid = player.getUniqueId();
        switch (gui) {
            case "pvp_main" -> {
                if (slot == 11) { player.closeInventory(); plugin.getPvpGUI().openPlayerList(player); }
                else if (slot == 15) { player.closeInventory(); plugin.getPvpGUI().openInvites(player); }
                else if (slot == 22) player.closeInventory();
            }
            case "pvp_playerlist" -> {
                if (slot == 49) { player.closeInventory(); plugin.getPvpGUI().openMainMenu(player); return; }
                String uuidStr = getLoreHidden(clicked);
                if (uuidStr != null) {
                    try { UUID targetUUID = UUID.fromString(uuidStr); player.closeInventory(); plugin.getPvpGUI().openBetMenu(player, targetUUID); }
                    catch (Exception ignored) {}
                }
            }
            case "pvp_bet" -> {
                if (slot == 22) { player.closeInventory(); plugin.getPvpGUI().openPlayerList(player); return; }
                double bet = switch (slot) {
                    case 10 -> 100000;
                    case 11 -> 500000;
                    case 12 -> 1000000;
                    case 13 -> 5000000;
                    case 14 -> 10000000;
                    case 15 -> 50000000;
                    case 16 -> 100000000;
                    default -> 0;
                };
                if (bet > 0) {
                    UUID targetUUID = PvPGUI.selectedTarget.get(uuid);
                    if (targetUUID == null) return;
                    Player target = org.bukkit.Bukkit.getPlayer(targetUUID);
                    if (target == null) { player.sendMessage(ChatColor.RED + "Giocatore non online!"); return; }
                    if (!plugin.getEconomy().has(player, bet)) { player.sendMessage(ChatColor.RED + "Non hai abbastanza soldi!"); return; }
                    plugin.getArenaManager().sendChallenge(player, target, bet);
                    player.closeInventory();
                    player.sendMessage(ChatColor.GREEN + "Sfida inviata!");
                }
            }
            case "pvp_invites" -> {
                if (slot == 49) { player.closeInventory(); plugin.getPvpGUI().openMainMenu(player); return; }
                String uuidStr = getLoreHidden(clicked);
                if (uuidStr != null) {
                    try {
                        UUID challengerUUID = UUID.fromString(uuidStr);
                        if (rightClick) plugin.getArenaManager().declineChallenge(player, challengerUUID);
                        else plugin.getArenaManager().acceptChallenge(player, challengerUUID);
                        player.closeInventory();
                    } catch (Exception ignored) {}
                }
            }
        }
    }

    private String getLoreHidden(ItemStack item) {
        if (item.getItemMeta() == null) return null;
        List<String> lore = item.getItemMeta().getLore();
        if (lore == null || lore.isEmpty()) return null;
        return ChatColor.stripColor(lore.get(lore.size() - 1));
    }

    private int countInInventory(Player player, Material mat) {
        int count = 0;
        for (ItemStack stack : player.getInventory().getContents()) {
            if (stack != null && stack.getType() == mat) count += stack.getAmount();
        }
        return count;
    }

    @EventHandler
    public void onRespawn(org.bukkit.event.player.PlayerRespawnEvent e) {
        Player player = e.getPlayer();
        if (plugin.getArenaManager().isRestoringPlayer(player.getUniqueId())) {
            plugin.getArenaManager().restoreLoserOnRespawn(player);
        }
    }

    @EventHandler
    public void onClose(InventoryCloseEvent e) {
        if (!(e.getPlayer() instanceof Player player)) return;
        UUID uuid = player.getUniqueId();
        ShopGUI.openGUI.remove(uuid);
        PvPGUI.openGUI.remove(uuid);
        ShopGUI.selectedItem.remove(uuid);
        PvPGUI.selectedTarget.remove(uuid);
    }
}
