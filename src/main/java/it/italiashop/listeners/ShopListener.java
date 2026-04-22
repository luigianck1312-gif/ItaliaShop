package it.italiashop.listeners;

import it.italiashop.ItaliaShop;
import it.italiashop.gui.PvPGUI;
import it.italiashop.gui.ShopGUI;
import it.italiashop.models.ShopItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
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

    // === INTERAZIONE NPC ===
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
        }
    }

    // === TOOLTIP VALORE OGGETTO ===
    @EventHandler
    public void onItemSwitch(PlayerItemHeldEvent e) {
        Player player = e.getPlayer();
        ItemStack item = player.getInventory().getItem(e.getNewSlot());
        if (item == null || item.getType().isAir()) return;

        ShopItem shopItem = plugin.getShopManager().getShopItem(item.getType());
        if (shopItem == null) return;

        player.sendActionBar(ChatColor.GRAY + "Acquisto: " + ChatColor.GREEN + "$" + ShopGUI.formatPrice(shopItem.getCurrentPrice()) +
                ChatColor.GRAY + "  |  Vendita: " + ChatColor.RED + "$" + ShopGUI.formatPrice(shopItem.getCurrentSellPrice()));
    }

    // === MORTE IN ARENA ===
    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        Player victim = e.getEntity();
        if (!plugin.getArenaManager().isFighting(victim.getUniqueId())) return;

        e.setCancelled(true);
        e.setDeathMessage(null);
        victim.setHealth(1);

        Player killer = victim.getKiller();
        plugin.getArenaManager().onFighterDeath(victim);

        if (killer != null) {
            double totalBet = 0; // viene gestito in ArenaManager
            killer.sendMessage(ChatColor.GOLD + "Hai vinto l'arena!");
        }
        victim.sendMessage(ChatColor.RED + "Hai perso l'arena!");
    }

    // === CLICK GUI ===
    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (!(e.getWhoClicked() instanceof Player player)) return;
        UUID uuid = player.getUniqueId();

        String shopGui = ShopGUI.openGUI.get(uuid);
        String pvpGui = PvPGUI.openGUI.get(uuid);

        if (shopGui == null && pvpGui == null) return;
        e.setCancelled(true);
        if (e.getCurrentItem() == null) return;

        // === SHOP GUI ===
        if (shopGui != null) {
            handleShopClick(player, shopGui, e.getSlot(), e.getCurrentItem());
        }

        // === PVP GUI ===
        if (pvpGui != null) {
            handlePvpClick(player, pvpGui, e.getSlot(), e.getCurrentItem(), e.isRightClick());
        }
    }

    private void handleShopClick(Player player, String gui, int slot, ItemStack clicked) {
        UUID uuid = player.getUniqueId();

        if (gui.equals("main")) {
            ShopItem.Category cat = switch (slot) {
                case 10 -> ShopItem.Category.BLOCCHI;
                case 11 -> ShopItem.Category.MINERALI;
                case 12 -> ShopItem.Category.NATURA;
                case 13 -> ShopItem.Category.NETHER;
                case 14 -> ShopItem.Category.END;
                case 15 -> ShopItem.Category.RARI;
                default -> null;
            };
            if (cat != null) {
                player.closeInventory();
                plugin.getShopGUI().openCategory(player, cat);
            } else if (slot == 22) {
                player.closeInventory();
            }
            return;
        }

        if (gui.startsWith("category_")) {
            if (slot == 49) {
                player.closeInventory();
                plugin.getShopGUI().openMainMenu(player);
                return;
            }
            // Recupera l'item dal lore (nascosto con BLACK)
            if (clicked.getItemMeta() != null) {
                List<String> lore = clicked.getItemMeta().getLore();
                if (lore != null && !lore.isEmpty()) {
                    String matName = ChatColor.stripColor(lore.get(lore.size() - 1));
                    try {
                        Material mat = Material.valueOf(matName);
                        ShopItem shopItem = plugin.getShopManager().getShopItem(mat);
                        if (shopItem != null) {
                            player.closeInventory();
                            plugin.getShopGUI().openItemMenu(player, shopItem);
                        }
                    } catch (Exception ignored) {}
                }
            }
            return;
        }

        if (gui.equals("item")) {
            ShopItem item = ShopGUI.selectedItem.get(uuid);
            if (item == null) return;

            if (slot == 22) {
                // Torna alla categoria
                player.closeInventory();
                plugin.getShopGUI().openCategory(player, item.getCategory());
                return;
            }

            // Compra
            if (slot == 9 || slot == 10 || slot == 11) {
                int amount = slot == 9 ? 1 : slot == 10 ? 32 : 64;
                if (item.getMaxStack() == 1) amount = 1;

                boolean hasCooldown = item.isRare() && plugin.getShopManager().hasCooldown(uuid, item.getMaterial());
                double cost;

                if (hasCooldown) {
                    // Skip cooldown
                    cost = plugin.getShopManager().getSkipCooldownCost(item.getMaterial());
                    if (!plugin.getEconomy().has(player, cost)) {
                        player.sendMessage(ChatColor.RED + "Non hai abbastanza soldi per saltare il cooldown!");
                        return;
                    }
                    plugin.getEconomy().withdrawPlayer(player, cost);
                    plugin.getShopManager().skipCooldown(uuid, item.getMaterial());
                } else {
                    cost = item.getCurrentPrice() * amount / 64.0;
                    if (item.getMaxStack() == 1) cost = item.getCurrentPrice();

                    if (!plugin.getEconomy().has(player, cost)) {
                        player.sendMessage(ChatColor.RED + "Non hai abbastanza soldi!");
                        return;
                    }

                    if (player.getInventory().firstEmpty() == -1) {
                        player.sendMessage(ChatColor.RED + "Inventario pieno!");
                        return;
                    }

                    plugin.getEconomy().withdrawPlayer(player, cost);
                    player.getInventory().addItem(new ItemStack(item.getMaterial(), amount));
                    item.onBuy(amount);

                    if (item.isRare()) {
                        plugin.getShopManager().setCooldown(uuid, item.getMaterial());
                    }

                    player.sendMessage(ChatColor.GREEN + "Comprato x" + amount + " " + item.getDisplayName() + " per $" + ShopGUI.formatPrice(cost));
                    plugin.getShopManager().savePrices();
                }
            }

            // Vendi
            if (slot == 15 || slot == 16 || slot == 17) {
                int amount = slot == 15 ? 1 : slot == 16 ? 32 : 64;
                if (item.getMaxStack() == 1) amount = 1;

                // Controlla se ha l'oggetto nell'inventario
                int inInventory = 0;
                for (ItemStack inv : player.getInventory().getContents()) {
                    if (inv != null && inv.getType() == item.getMaterial()) {
                        inInventory += inv.getAmount();
                    }
                }

                if (inInventory < amount) {
                    player.sendMessage(ChatColor.RED + "Non hai abbastanza " + item.getDisplayName() + " nell'inventario!");
                    return;
                }

                double income = item.getCurrentSellPrice() * amount / 64.0;
                if (item.getMaxStack() == 1) income = item.getCurrentSellPrice();

                // Rimuovi oggetti
                player.getInventory().removeItem(new ItemStack(item.getMaterial(), amount));
                plugin.getEconomy().depositPlayer(player, income);
                item.onSell(amount);

                player.sendMessage(ChatColor.GREEN + "Venduto x" + amount + " " + item.getDisplayName() + " per $" + ShopGUI.formatPrice(income));
                plugin.getShopManager().savePrices();
            }
        }
    }

    private void handlePvpClick(Player player, String gui, int slot, ItemStack clicked, boolean rightClick) {
        UUID uuid = player.getUniqueId();

        if (gui.equals("pvp_main")) {
            if (slot == 11) {
                player.closeInventory();
                plugin.getPvpGUI().openPlayerList(player);
            } else if (slot == 13) {
                player.closeInventory();
                plugin.getPvpGUI().openInvites(player);
            } else if (slot == 22) {
                player.closeInventory();
            }
            return;
        }

        if (gui.equals("pvp_playerlist")) {
            if (slot == 49) {
                player.closeInventory();
                plugin.getPvpGUI().openMainMenu(player);
                return;
            }
            if (clicked.getItemMeta() != null) {
                List<String> lore = clicked.getItemMeta().getLore();
                if (lore != null && !lore.isEmpty()) {
                    String uuidStr = ChatColor.stripColor(lore.get(lore.size() - 1));
                    try {
                        UUID targetUUID = UUID.fromString(uuidStr);
                        player.closeInventory();
                        plugin.getPvpGUI().openBetMenu(player, targetUUID);
                    } catch (Exception ignored) {}
                }
            }
            return;
        }

        if (gui.equals("pvp_bet")) {
            if (slot == 22) {
                player.closeInventory();
                plugin.getPvpGUI().openPlayerList(player);
                return;
            }

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
                UUID targetUUID = PvPGUI.selectedChallenger.get(uuid);
                if (targetUUID == null) return;

                if (!plugin.getEconomy().has(player, bet)) {
                    player.sendMessage(ChatColor.RED + "Non hai abbastanza soldi per questa scommessa!");
                    return;
                }

                plugin.getArenaManager().sendChallenge(player, org.bukkit.Bukkit.getPlayer(targetUUID), bet);
                player.closeInventory();
                player.sendMessage(ChatColor.GREEN + "Sfida inviata! Aspetta che l'avversario accetti.");
            }
            return;
        }

        if (gui.equals("pvp_invites")) {
            if (slot == 49) {
                player.closeInventory();
                plugin.getPvpGUI().openMainMenu(player);
                return;
            }
            if (clicked.getItemMeta() != null) {
                List<String> lore = clicked.getItemMeta().getLore();
                if (lore != null && !lore.isEmpty()) {
                    String uuidStr = ChatColor.stripColor(lore.get(lore.size() - 1));
                    try {
                        UUID challengerUUID = UUID.fromString(uuidStr);
                        if (rightClick) {
                            plugin.getArenaManager().declineChallenge(player, challengerUUID);
                        } else {
                            plugin.getArenaManager().acceptChallenge(player, challengerUUID);
                        }
                        player.closeInventory();
                    } catch (Exception ignored) {}
                }
            }
        }
    }

    @EventHandler
    public void onClose(InventoryCloseEvent e) {
        if (!(e.getPlayer() instanceof Player player)) return;
        UUID uuid = player.getUniqueId();
        ShopGUI.openGUI.remove(uuid);
        PvPGUI.openGUI.remove(uuid);
        ShopGUI.selectedItem.remove(uuid);
        PvPGUI.selectedChallenger.remove(uuid);
    }
}
