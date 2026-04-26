package it.italiashop;

import it.italiashop.commands.CrystalCommand;
import it.italiashop.commands.NPCCommand;
import it.italiashop.gui.PvPGUI;
import it.italiashop.gui.ShopGUI;
import it.italiashop.gui.SpawnerGUI;
import it.italiashop.listeners.ShopListener;
import it.italiashop.listeners.SpawnerListener;
import it.italiashop.managers.ArenaManager;
import it.italiashop.managers.CrystalManager;
import it.italiashop.managers.NPCManager;
import it.italiashop.managers.ShopManager;
import it.italiashop.managers.SpawnerManager;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class ItaliaShop extends JavaPlugin {

    private ShopManager shopManager;
    private ArenaManager arenaManager;
    private NPCManager npcManager;
    private ShopGUI shopGUI;
    private PvPGUI pvpGUI;
    private SpawnerManager spawnerManager;
    private SpawnerGUI spawnerGUI;
    private CrystalManager crystalManager;
    private Economy economy;

    @Override
    public void onEnable() {
        getLogger().info("ItaliaShop avviato!");
        getDataFolder().mkdirs();

        if (!setupEconomy()) {
            getLogger().severe("Vault non trovato! ItaliaShop si disabilita.");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        shopManager = new ShopManager(this);
        arenaManager = new ArenaManager(this);
        shopGUI = new ShopGUI(this);
        pvpGUI = new PvPGUI(this);
        npcManager = new NPCManager(this);
        crystalManager = new CrystalManager(this);
        spawnerManager = new SpawnerManager(this);
        spawnerGUI = new SpawnerGUI(this);

        getCommand("npc").setExecutor(new NPCCommand(this));
        getCommand("cristalli").setExecutor(new CrystalCommand(this));

        ShopListener shopListener = new ShopListener(this);
        getServer().getPluginManager().registerEvents(shopListener, this);
        getServer().getPluginManager().registerEvents(new SpawnerListener(this), this);

        // Aggiorna lore prezzi ogni 2 secondi per tutti i giocatori online
        getServer().getScheduler().runTaskTimer(this, () -> {
            for (org.bukkit.entity.Player player : getServer().getOnlinePlayers()) {
                if (it.italiashop.gui.ShopGUI.openGUI.containsKey(player.getUniqueId())) continue;
                if (it.italiashop.gui.PvPGUI.openGUI.containsKey(player.getUniqueId())) continue;
                for (int i = 0; i < player.getInventory().getSize(); i++) {
                    org.bukkit.inventory.ItemStack item = player.getInventory().getItem(i);
                    if (item == null || item.getType().isAir()) continue;
                    double buy = it.italiashop.managers.ItemValueRegistry.getBuyPrice(item.getType());
                    double sell = it.italiashop.managers.ItemValueRegistry.getSellPrice(item.getType());
                    if (buy <= 0 && sell <= 0) continue;
                    shopListener.updateItemLorePublic(player, item, i);
                }
            }
        }, 40L, 40L);

        getLogger().info("ItaliaShop caricato con successo!");
    }

    @Override
    public void onDisable() {
        if (shopManager != null) shopManager.savePrices();
        if (npcManager != null) npcManager.save();
        if (crystalManager != null) crystalManager.saveData();
        if (spawnerManager != null) spawnerManager.saveData();
        getLogger().info("ItaliaShop disattivato!");
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) return false;
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) return false;
        economy = rsp.getProvider();
        return economy != null;
    }

    public ShopManager getShopManager() { return shopManager; }
    public ArenaManager getArenaManager() { return arenaManager; }
    public NPCManager getNpcManager() { return npcManager; }
    public ShopGUI getShopGUI() { return shopGUI; }
    public PvPGUI getPvpGUI() { return pvpGUI; }
    public SpawnerManager getSpawnerManager() { return spawnerManager; }
    public SpawnerGUI getSpawnerGUI() { return spawnerGUI; }
    public CrystalManager getCrystalManager() { return crystalManager; }
    public Economy getEconomy() { return economy; }
}
