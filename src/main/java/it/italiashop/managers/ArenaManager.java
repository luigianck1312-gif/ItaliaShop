package it.italiashop.managers;

import it.italiashop.ItaliaShop;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class ArenaManager {

    private final ItaliaShop plugin;
    private World arenaWorld;
    private Location spawn1;
    private Location spawn2;

    private final Map<UUID, UUID> pendingChallenges = new HashMap<>();
    private final Map<UUID, Double> challengeBets = new HashMap<>();
    private final Map<UUID, Location> previousLocations = new HashMap<>();
    private final Map<UUID, ItemStack[]> savedInventories = new HashMap<>();
    private UUID fighter1 = null;
    private UUID fighter2 = null;
    private double currentBet = 0;
    private boolean fightActive = false;

    public ArenaManager(ItaliaShop plugin) {
        this.plugin = plugin;
        setupArenaWorld();
    }

    private void setupArenaWorld() {
        if (Bukkit.getWorld("steelthrone_arena") != null) {
            arenaWorld = Bukkit.getWorld("steelthrone_arena");
        } else {
            WorldCreator creator = new WorldCreator("steelthrone_arena");
            creator.environment(World.Environment.NORMAL);
            creator.type(WorldType.FLAT);
            creator.generateStructures(false);
            arenaWorld = Bukkit.createWorld(creator);
            if (arenaWorld != null) {
                arenaWorld.setGameRule(GameRule.DO_MOB_SPAWNING, false);
                arenaWorld.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
                arenaWorld.setGameRule(GameRule.DO_WEATHER_CYCLE, false);
                arenaWorld.setTime(6000);
                arenaWorld.setDifficulty(org.bukkit.Difficulty.HARD);
                buildArena();
            }
        }

        if (arenaWorld != null) {
            spawn1 = new Location(arenaWorld, 0, 5, -40);
            spawn2 = new Location(arenaWorld, 0, 5, 40);
        }
    }

    private void giveArenaGear(Player player) {
        // Rimuovi tutti gli effetti
        player.getActivePotionEffects().forEach(e -> player.removePotionEffect(e.getType()));
        // Imposta gamemode survival per danno normale
        player.setGameMode(org.bukkit.GameMode.SURVIVAL);
        player.getInventory().setHelmet(new ItemStack(Material.DIAMOND_HELMET));
        player.getInventory().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
        player.getInventory().setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
        player.getInventory().setBoots(new ItemStack(Material.DIAMOND_BOOTS));
        player.getInventory().setItemInMainHand(new ItemStack(Material.DIAMOND_SWORD));
        player.setHealth(player.getMaxHealth());
        player.setFoodLevel(20);
        player.setSaturation(20);
    }

    private void buildArena() {
        if (arenaWorld == null) return;
        int cx = 0, cz = 0, y = 4;
        int size = 35; // Colosseo 70x70

        // Pavimento colosseo - sabbia dell'arena
        for (int x = -size; x <= size; x++) {
            for (int z = -size; z <= size; z++) {
                double dist = Math.sqrt(x*x + z*z);
                if (dist <= size) {
                    // Pavimento solido senza buchi
                    Material floor;
                    if (dist < 5) floor = Material.CHISELED_STONE_BRICKS;
                    else if ((Math.abs(x) + Math.abs(z)) % 4 == 0) floor = Material.COARSE_DIRT;
                    else floor = Material.SAND;
                    arenaWorld.getBlockAt(cx+x, y, cz+z).setType(floor);
                    // Assicurati che non ci siano buchi sotto
                    arenaWorld.getBlockAt(cx+x, y-1, cz+z).setType(Material.STONE);
                }
            }
        }

        // Muri del colosseo - forma ovale con pietra romana
        for (int x = -size; x <= size; x++) {
            for (int z = -size; z <= size; z++) {
                double dist = Math.sqrt(x*x + z*z);
                if (dist >= size-2 && dist <= size) {
                    for (int h = 1; h <= 10; h++) {
                        Material wall;
                        if (h == 1 || h == 10) wall = Material.STONE_BRICKS;
                        else if (h % 3 == 0) wall = Material.CHISELED_STONE_BRICKS;
                        else wall = Material.STONE_BRICK_WALL;
                        arenaWorld.getBlockAt(cx+x, y+h, cz+z).setType(wall);
                    }
                    // Archi ogni 8 blocchi
                    if ((Math.abs(x) % 8 == 0 || Math.abs(z) % 8 == 0) && dist >= size-1) {
                        for (int h = 11; h <= 14; h++) {
                            arenaWorld.getBlockAt(cx+x, y+h, cz+z).setType(Material.STONE_BRICKS);
                        }
                    }
                }
            }
        }

        // Gradinate - file di posti a sedere intorno all'arena
        for (int tier = 0; tier < 4; tier++) {
            int r = size - 6 - tier*2;
            int height = tier + 1;
            for (int x = -r; x <= r; x++) {
                for (int z = -r; z <= r; z++) {
                    double dist = Math.sqrt(x*x + z*z);
                    if (dist >= r-1 && dist <= r) {
                        arenaWorld.getBlockAt(cx+x, y+height, cz+z).setType(
                            tier % 2 == 0 ? Material.STONE_BRICK_STAIRS : Material.SMOOTH_STONE
                        );
                    }
                }
            }
        }

        // Torce romane - 8 punti cardinali
        int[] torchAngles = {0, 45, 90, 135, 180, 225, 270, 315};
        for (int angle : torchAngles) {
            double rad = Math.toRadians(angle);
            int tx = cx + (int)(Math.cos(rad) * (size-1));
            int tz = cz + (int)(Math.sin(rad) * (size-1));
            arenaWorld.getBlockAt(tx, y+11, tz).setType(Material.SEA_LANTERN);
            arenaWorld.getBlockAt(tx, y+12, tz).setType(Material.SEA_LANTERN);
        }

        // Centro arena - stella gladiatoria
        arenaWorld.getBlockAt(cx, y, cz).setType(Material.DIAMOND_BLOCK);
        for (int[] d : new int[][]{{1,0},{-1,0},{0,1},{0,-1}}) {
            arenaWorld.getBlockAt(cx+d[0], y, cz+d[1]).setType(Material.GOLD_BLOCK);
        }
        for (int[] d : new int[][]{{2,0},{-2,0},{0,2},{0,-2},{1,1},{1,-1},{-1,1},{-1,-1}}) {
            arenaWorld.getBlockAt(cx+d[0], y, cz+d[1]).setType(Material.IRON_BLOCK);
        }

        // Porte d'ingresso - Nord e Sud
        for (int sign = -1; sign <= 1; sign += 2) {
            int gx = cx;
            int gz = cz + sign * (size-1);
            for (int h = 1; h <= 5; h++) {
                arenaWorld.getBlockAt(gx-1, y+h, gz).setType(Material.AIR);
                arenaWorld.getBlockAt(gx, y+h, gz).setType(Material.AIR);
                arenaWorld.getBlockAt(gx+1, y+h, gz).setType(Material.AIR);
            }
            arenaWorld.getBlockAt(gx-1, y+6, gz).setType(Material.STONE_BRICKS);
            arenaWorld.getBlockAt(gx, y+6, gz).setType(Material.CHISELED_STONE_BRICKS);
            arenaWorld.getBlockAt(gx+1, y+6, gz).setType(Material.STONE_BRICKS);
        }

        // Illuminazione interna
        for (int x = -25; x <= 25; x += 10) {
            for (int z = -25; z <= 25; z += 10) {
                double dist = Math.sqrt(x*x + z*z);
                if (dist < size - 5) {
                    arenaWorld.getBlockAt(cx+x, y+1, cz+z).setType(Material.SEA_LANTERN);
                }
            }
        }
    }

    public void sendChallenge(Player challenger, Player challenged, double bet) {
        pendingChallenges.put(challenger.getUniqueId(), challenged.getUniqueId());
        challengeBets.put(challenger.getUniqueId(), bet);

        challenged.sendMessage(ChatColor.RED + "[Arena] " + ChatColor.YELLOW + challenger.getName() +
                ChatColor.WHITE + " ti ha sfidato!");
        challenged.sendMessage(ChatColor.GRAY + "Scommessa: " + ChatColor.GREEN + "$" + formatMoney(bet));
        challenged.sendMessage(ChatColor.GRAY + "Vai dall'NPC Arena per accettare o rifiutare!");
    }

    public List<Map.Entry<UUID, Double>> getPendingChallengesFor(UUID playerUUID) {
        List<Map.Entry<UUID, Double>> result = new ArrayList<>();
        for (Map.Entry<UUID, UUID> entry : pendingChallenges.entrySet()) {
            if (entry.getValue().equals(playerUUID)) {
                result.add(Map.entry(entry.getKey(), challengeBets.getOrDefault(entry.getKey(), 0.0)));
            }
        }
        return result;
    }

    public boolean acceptChallenge(Player challenged, UUID challengerUUID) {
        if (!pendingChallenges.containsKey(challengerUUID)) return false;
        if (!pendingChallenges.get(challengerUUID).equals(challenged.getUniqueId())) return false;

        Player challenger = Bukkit.getPlayer(challengerUUID);
        if (challenger == null) {
            pendingChallenges.remove(challengerUUID);
            challengeBets.remove(challengerUUID);
            challenged.sendMessage(ChatColor.RED + "Lo sfidante non e piu online!");
            return false;
        }

        double bet = challengeBets.get(challengerUUID);
        currentBet = bet;

        // Preleva scommessa solo se > 0 e hanno i soldi
        if (bet > 0) {
            if (!plugin.getEconomy().has(challenger, bet)) {
                challenger.sendMessage(ChatColor.RED + "Non hai abbastanza soldi per la scommessa!");
                return false;
            }
            if (!plugin.getEconomy().has(challenged, bet)) {
                challenged.sendMessage(ChatColor.RED + "Non hai abbastanza soldi per la scommessa!");
                return false;
            }
            plugin.getEconomy().withdrawPlayer(challenger, bet);
            plugin.getEconomy().withdrawPlayer(challenged, bet);
        }

        // Salva posizioni e inventari
        previousLocations.put(challengerUUID, challenger.getLocation().clone());
        previousLocations.put(challenged.getUniqueId(), challenged.getLocation().clone());
        savedInventories.put(challengerUUID, challenger.getInventory().getContents().clone());
        savedInventories.put(challenged.getUniqueId(), challenged.getInventory().getContents().clone());

        fighter1 = challengerUUID;
        fighter2 = challenged.getUniqueId();
        fightActive = true;

        // Salva e pulisci inventari
        challenger.getInventory().clear();
        challenged.getInventory().clear();

        // Teletrasporta
        challenger.teleport(spawn1);
        challenged.teleport(spawn2);

        // Dai armatura e spada diamante
        giveArenaGear(challenger);
        giveArenaGear(challenged);

        String msg = ChatColor.RED + "[Arena] " + ChatColor.YELLOW + challenger.getName() +
                ChatColor.WHITE + " vs " + ChatColor.YELLOW + challenged.getName() +
                ChatColor.WHITE + " | Scommessa totale: " + ChatColor.GREEN + "$" + formatMoney(bet * 2);
        Bukkit.broadcastMessage(msg);
        challenger.sendMessage(ChatColor.RED + "FIGHT!");
        challenged.sendMessage(ChatColor.RED + "FIGHT!");

        pendingChallenges.remove(challengerUUID);
        challengeBets.remove(challengerUUID);
        return true;
    }

    public void declineChallenge(Player challenged, UUID challengerUUID) {
        pendingChallenges.remove(challengerUUID);
        challengeBets.remove(challengerUUID);
        Player challenger = Bukkit.getPlayer(challengerUUID);
        if (challenger != null) {
            challenger.sendMessage(ChatColor.RED + "[Arena] " + challenged.getName() + " ha rifiutato la sfida!");
        }
        challenged.sendMessage(ChatColor.YELLOW + "Sfida rifiutata!");
    }

    public void onFighterDeath(Player loser) {
        if (!fightActive) return;
        UUID loserUUID = loser.getUniqueId();
        if (!loserUUID.equals(fighter1) && !loserUUID.equals(fighter2)) return;

        UUID winnerUUID = loserUUID.equals(fighter1) ? fighter2 : fighter1;
        Player winner = Bukkit.getPlayer(winnerUUID);

        fightActive = false;

        // Il vincitore prende tutta la scommessa
        if (currentBet > 0 && winner != null) {
            plugin.getEconomy().depositPlayer(winner, currentBet * 2);
            winner.sendMessage(ChatColor.GOLD + "Hai vinto! Incassato: $" + formatMoney(currentBet * 2));
        }

        loser.sendMessage(ChatColor.RED + "Hai perso l'arena!");

        UUID f1 = fighter1;
        UUID f2 = fighter2;
        fighter1 = null;
        fighter2 = null;
        double betWon = currentBet;
        currentBet = 0;

        // Ripristina il vincitore subito
        if (winner != null) restorePlayer(winner);

        // Il perdente viene ripristinato al respawn (gestito dall'evento PlayerRespawnEvent)
        // Salva la posizione precedente del perdente per il respawn
        previousLocations.put(loserUUID, previousLocations.getOrDefault(loserUUID,
            Bukkit.getWorld("world").getSpawnLocation()));

        String winMsg = ChatColor.GOLD + "[Arena] " + ChatColor.YELLOW +
                (winner != null ? winner.getName() : "Sconosciuto") +
                ChatColor.WHITE + " ha vinto l'arena!";
        Bukkit.broadcastMessage(winMsg);
    }

    public boolean isRestoringPlayer(UUID uuid) {
        return previousLocations.containsKey(uuid) && savedInventories.containsKey(uuid);
    }

    public void restoreLoserOnRespawn(Player player) {
        plugin.getServer().getScheduler().runTaskLater(plugin, () -> {
            restorePlayer(player);
        }, 5L);
    }

    private void restorePlayer(Player player) {
        UUID uuid = player.getUniqueId();
        ItemStack[] inv = savedInventories.remove(uuid);
        if (inv != null) {
            player.getInventory().clear();
            player.getInventory().setContents(inv);
        }
        Location prev = previousLocations.remove(uuid);
        if (prev != null) player.teleport(prev);
        player.setHealth(player.getMaxHealth());
        player.setFoodLevel(20);
    }

    private String formatMoney(double amount) {
        if (amount >= 1_000_000_000) return String.format("%.1fMld", amount / 1_000_000_000);
        if (amount >= 1_000_000) return String.format("%.1fMln", amount / 1_000_000);
        if (amount >= 1_000) return String.format("%.0fK", amount / 1_000);
        return String.valueOf((long) amount);
    }

    public boolean isFighting(UUID uuid) {
        return fightActive && (uuid.equals(fighter1) || uuid.equals(fighter2));
    }

    public boolean isFightActive() { return fightActive; }
    public Map<UUID, UUID> getPendingChallenges() { return pendingChallenges; }
    public Map<UUID, Double> getChallengeBets() { return challengeBets; }
}
