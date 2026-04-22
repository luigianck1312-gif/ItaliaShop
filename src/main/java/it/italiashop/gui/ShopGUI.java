package it.italiashop.gui;

import it.italiashop.ItaliaShop;
import it.italiashop.models.ShopItem;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class ShopGUI {

    private final ItaliaShop plugin;

    public static final Map<UUID, String> openGUI = new HashMap<>();
    public static final Map<UUID, ShopItem> selectedItem = new HashMap<>();
    public static final Map<UUID, String> buyMode = new HashMap<>(); // "buy" or "sell"

    public ShopGUI(ItaliaShop plugin) {
        this.plugin = plugin;
    }

    public void openMainMenu(Player player) {
        Inventory inv = Bukkit.createInventory(null, 27, ChatColor.GOLD + "Shop - SteelThrone");

        inv.setItem(10, createItem(Material.DIRT, ChatColor.YELLOW + "Blocchi", ChatColor.GRAY + "Terra, Pietra, Legno..."));
        inv.setItem(11, createItem(Material.DIAMOND, ChatColor.AQUA + "Minerali", ChatColor.GRAY + "Ferro, Oro, Diamante..."));
        inv.setItem(12, createItem(Material.OAK_LEAVES, ChatColor.GREEN + "Natura", ChatColor.GRAY + "Alghe, Fiori, Piante..."));
        inv.setItem(13, createItem(Material.NETHERRACK, ChatColor.RED + "Nether", ChatColor.GRAY + "Blaze, Ghast, Nether..."));
        inv.setItem(14, createItem(Material.END_STONE, ChatColor.LIGHT_PURPLE + "End", ChatColor.GRAY + "Ender, Chorus, Shulker..."));
        inv.setItem(15, createItem(Material.TOTEM_OF_UNDYING, ChatColor.GOLD + "Rari", ChatColor.GRAY + "Totem, Elitra..."));
        inv.setItem(22, createItem(Material.BARRIER, ChatColor.RED + "Chiudi", ""));

        openGUI.put(player.getUniqueId(), "main");
        player.openInventory(inv);
    }

    public void openCategory(Player player, ShopItem.Category category) {
        List<ShopItem> items = plugin.getShopManager().getItemsByCategory(category);
        String catName = getCategoryName(category);

        Inventory inv = Bukkit.createInventory(null, 54, ChatColor.GOLD + "Shop - " + catName);

        int slot = 0;
        for (ShopItem item : items) {
            if (slot >= 45) break;
            inv.setItem(slot++, createShopItemDisplay(player, item));
        }

        // Bottone torna indietro
        inv.setItem(49, createItem(Material.ARROW, ChatColor.WHITE + "Torna indietro", ""));

        openGUI.put(player.getUniqueId(), "category_" + category.name());
        player.openInventory(inv);
    }

    public void openItemMenu(Player player, ShopItem item) {
        Inventory inv = Bukkit.createInventory(null, 27, ChatColor.GOLD + item.getDisplayName());

        // Info oggetto al centro
        inv.setItem(13, createShopItemDisplay(player, item));

        // Tasto COMPRA
        boolean hasCooldown = plugin.getShopManager().hasCooldown(player.getUniqueId(), item.getMaterial());
        if (hasCooldown && item.isRare()) {
            long remaining = plugin.getShopManager().getRemainingCooldown(player.getUniqueId(), item.getMaterial());
            long days = remaining / (1000 * 60 * 60 * 24);
            long hours = (remaining % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
            double skipCost = plugin.getShopManager().getSkipCooldownCost(item.getMaterial());

            inv.setItem(10, createItemWithLore(Material.CLOCK,
                    ChatColor.YELLOW + "In cooldown",
                    Arrays.asList(
                            ChatColor.GRAY + "Rimanente: " + ChatColor.WHITE + days + "g " + hours + "h",
                            ChatColor.GRAY + "Skip cooldown: " + ChatColor.GREEN + "$" + formatPrice(skipCost),
                            ChatColor.YELLOW + "Clicca per pagare e comprare subito!"
                    )));
        } else {
            if (item.getMaxStack() == 1) {
                inv.setItem(10, createItemWithLore(Material.LIME_WOOL,
                        ChatColor.GREEN + "Compra x1",
                        Arrays.asList(ChatColor.GRAY + "Prezzo: " + ChatColor.GREEN + "$" + formatPrice(item.getCurrentPrice()))));
            } else {
                inv.setItem(9, createItemWithLore(Material.LIME_WOOL,
                        ChatColor.GREEN + "Compra x1",
                        Arrays.asList(ChatColor.GRAY + "Prezzo: " + ChatColor.GREEN + "$" + formatPrice(item.getCurrentPrice()))));
                inv.setItem(10, createItemWithLore(Material.LIME_WOOL,
                        ChatColor.GREEN + "Compra x32",
                        Arrays.asList(ChatColor.GRAY + "Prezzo: " + ChatColor.GREEN + "$" + formatPrice(item.getCurrentPrice() * 32 / 64))));
                inv.setItem(11, createItemWithLore(Material.LIME_WOOL,
                        ChatColor.GREEN + "Compra x64",
                        Arrays.asList(ChatColor.GRAY + "Prezzo: " + ChatColor.GREEN + "$" + formatPrice(item.getCurrentPrice()))));
            }
        }

        // Tasto VENDI
        if (item.getMaxStack() == 1) {
            inv.setItem(16, createItemWithLore(Material.RED_WOOL,
                    ChatColor.RED + "Vendi x1",
                    Arrays.asList(ChatColor.GRAY + "Ricevi: " + ChatColor.GREEN + "$" + formatPrice(item.getCurrentSellPrice()))));
        } else {
            inv.setItem(15, createItemWithLore(Material.RED_WOOL,
                    ChatColor.RED + "Vendi x1",
                    Arrays.asList(ChatColor.GRAY + "Ricevi: " + ChatColor.GREEN + "$" + formatPrice(item.getCurrentSellPrice() / 64))));
            inv.setItem(16, createItemWithLore(Material.RED_WOOL,
                    ChatColor.RED + "Vendi x32",
                    Arrays.asList(ChatColor.GRAY + "Ricevi: " + ChatColor.GREEN + "$" + formatPrice(item.getCurrentSellPrice() / 2))));
            inv.setItem(17, createItemWithLore(Material.RED_WOOL,
                    ChatColor.RED + "Vendi x64",
                    Arrays.asList(ChatColor.GRAY + "Ricevi: " + ChatColor.GREEN + "$" + formatPrice(item.getCurrentSellPrice()))));
        }

        // Torna indietro
        inv.setItem(22, createItem(Material.ARROW, ChatColor.WHITE + "Torna", ""));

        selectedItem.put(player.getUniqueId(), item);
        openGUI.put(player.getUniqueId(), "item");
        player.openInventory(inv);
    }

    private ItemStack createShopItemDisplay(Player player, ShopItem item) {
        boolean hasCooldown = item.isRare() && plugin.getShopManager().hasCooldown(player.getUniqueId(), item.getMaterial());
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.GRAY + "Acquisto: " + ChatColor.GREEN + "$" + formatPrice(item.getCurrentPrice()) + (item.getMaxStack() > 1 ? " (x64)" : ""));
        lore.add(ChatColor.GRAY + "Vendita: " + ChatColor.RED + "$" + formatPrice(item.getCurrentSellPrice()) + (item.getMaxStack() > 1 ? " (x64)" : ""));
        if (item.isRare()) {
            lore.add(ChatColor.GOLD + "Oggetto Raro!");
            lore.add(ChatColor.GRAY + "Cooldown: " + ChatColor.WHITE + item.getCooldownDays() + " giorni");
        }
        if (hasCooldown) {
            lore.add(ChatColor.RED + "In cooldown!");
        }
        lore.add("");
        lore.add(ChatColor.YELLOW + "Clicca per comprare/vendere");
        lore.add(ChatColor.BLACK + item.getMaterial().name());

        ItemStack stack = new ItemStack(item.getMaterial());
        ItemMeta meta = stack.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(ChatColor.YELLOW + item.getDisplayName());
            meta.setLore(lore);
            stack.setItemMeta(meta);
        }
        return stack;
    }

    private ItemStack createItem(Material mat, String name, String lore) {
        ItemStack item = new ItemStack(mat);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(name);
            if (!lore.isEmpty()) meta.setLore(Collections.singletonList(lore));
            item.setItemMeta(meta);
        }
        return item;
    }

    private ItemStack createItemWithLore(Material mat, String name, List<String> lore) {
        ItemStack item = new ItemStack(mat);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(name);
            meta.setLore(lore);
            item.setItemMeta(meta);
        }
        return item;
    }

    private String getCategoryName(ShopItem.Category cat) {
        return switch (cat) {
            case BLOCCHI -> "Blocchi";
            case MINERALI -> "Minerali";
            case NATURA -> "Natura";
            case NETHER -> "Nether";
            case END -> "End";
            case RARI -> "Rari";
        };
    }

    public static String formatPrice(double price) {
        if (price >= 1_000_000_000) return String.format("%.1fMld", price / 1_000_000_000);
        if (price >= 1_000_000) return String.format("%.1fMln", price / 1_000_000);
        if (price >= 1_000) return String.format("%.1fK", price / 1_000);
        return String.valueOf((long) price);
    }
}
