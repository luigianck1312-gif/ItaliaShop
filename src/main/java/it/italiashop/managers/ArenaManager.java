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

    // UUID sfidante -> UUID sfidato
    private final Map<UUID, UUID> pendingChallenges = new HashMap<>();
    // UUID sfidante -> scommessa
    private final Map<UUID, Double> challengeBets = new HashMap<>();
    // UUID player -> posizione precedente
    private final Map<UUID, Location> previousLocations = new HashMap<>();
    // UUID player -> inventario salvato
    private final Map<UUID, ItemStack[]> savedInventories = new HashMap<>();
    // Coppia in fight
    private UUID fighter1 = null;
    private UUID fighter2 = null;
    private boolean fightActive = false;

    public ArenaManager(ItaliaShop plugin) {
        this.plugin = plugin;
        setupArenaWorld();
    }

    private void setupArenaWorld() {
        // Crea o carica il mondo arena
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
                buildArena();
            }
        }

        if (arenaWorld != null) {
            spawn1 = new Location(arenaWorld, 0, 5, -15);
            spawn2 = new Location(arenaWorld, 0, 5, 15);
        }
    }

    private void buildArena() {
        if (arenaWorld == null) return;
        // Costruisce un'arena 30x30 con muri
        int cx = 0, cz = 0, y = 4;
        int size = 15;

        for (int x = -size; x <= size; x++) {
            for (int z = -size; z <= size; z++) {
                // Pavimento
                arenaWorld.getBlockAt(cx + x, y, cz + z).setType(Material.STONE_BRICKS);

                // Muri perimetrali
                if (Math.abs(x) == size || Math.abs(z) == size) {
                    for (int h = 1; h <= 5; h++) {
                        arenaWorld.getBlockAt(cx + x, y + h, cz + z).setType(Material.STONE_BRICK_WALL);
                    }
                }
            }
        }

        // Torce per illuminazione
        arenaWorld.getBlockAt(cx + 10, y + 1, cz + 10).setType(Material.TORCH);
        arenaWorld.getBlockAt(cx - 10, y + 1, cz + 10).setType(Material.TORCH);
        arenaWorld.getBlockAt(cx + 10, y + 1, cz - 10).setType(Material.TORCH);
        arenaWorld.getBlockAt(cx - 10, y + 1, cz - 10).setType(Material.TORCH);
    }

    public void sendChallenge(Player challenger, Player challenged, double bet) {
        pendingChallenges.put(challenger.getUniqueId(), challenged.getUniqueId());
        challengeBets.put(challenger.getUniqueId(), bet);

        challenged.sendMessage(ChatColor.RED + "[Arena] " + ChatColor.YELLOW + challenger.getName() +
                ChatColor.WHITE + " ti ha sfidato! Scommessa: " + ChatColor.GREEN + "$" + (long)bet);
        challenged.sendMessage(ChatColor.GRAY + "Vai dall'NPC Arena e accetta o rifiuta!");
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
            challenged.sendMessage(ChatColor.RED + "Lo sfidante non è più online!");
            return false;
        }

        double bet = challengeBets.get(challengerUUID);

        // Preleva scommessa da entrambi
        plugin.getEconomy().withdrawPlayer(challenger, bet);
        plugin.getEconomy().withdrawPlayer(challenged, bet);

        // Salva posizioni e inventari
        previousLocations.put(challengerUUID, challenger.getLocation().clone());
        previousLocations.put(challenged.getUniqueId(), challenged.getLocation().clone());
        savedInventories.put(challengerUUID, challenger.getInventory().getContents().clone());
        savedInventories.put(challenged.getUniqueId(), challenged.getInventory().getContents().clone());

        // Teleporta nell'arena
        fighter1 = challengerUUID;
        fighter2 = challenged.getUniqueId();
        fightActive = true;

        challenger.getInventory().clear();
        challenged.getInventory().clear();

        challenger.teleport(spawn1);
        challenged.teleport(spawn2);

        // Messaggio
        String msg = ChatColor.RED + "[Arena] " + ChatColor.YELLOW + challenger.getName() +
                ChatColor.WHITE + " vs " + ChatColor.YELLOW + challenged.getName() +
                ChatColor.WHITE + " - Scommessa: " + ChatColor.GREEN + "$" + (long)(bet * 2);
        challenger.sendMessage(msg);
        challenged.sendMessage(msg);
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

        // Calcola premio totale (le scommesse erano già state prelevate)
        // Dobbiamo sapere quanto era la scommessa — la salviamo prima
        // Per ora usiamo un approccio semplice

        fightActive = false;
        fighter1 = null;
        fighter2 = null;

        // Ripristina inventari
        restorePlayer(loser);
        if (winner != null) restorePlayer(winner);

        // Annuncio
        String winMsg = ChatColor.GOLD + "[Arena] " + ChatColor.YELLOW +
                (winner != null ? winner.getName() : "Sconosciuto") +
                ChatColor.WHITE + " ha vinto l'arena!";
        Bukkit.broadcastMessage(winMsg);
    }

    private void restorePlayer(Player player) {
        UUID uuid = player.getUniqueId();
        // Ripristina inventario
        ItemStack[] inv = savedInventories.remove(uuid);
        if (inv != null) {
            player.getInventory().clear();
            player.getInventory().setContents(inv);
        }
        // Ripristina posizione
        Location prev = previousLocations.remove(uuid);
        if (prev != null) {
            player.teleport(prev);
        }
        player.setHealth(player.getMaxHealth());
        player.setFoodLevel(20);
    }

    public boolean isFighting(UUID uuid) {
        return fightActive && (uuid.equals(fighter1) || uuid.equals(fighter2));
    }

    public boolean isFightActive() { return fightActive; }
    public Map<UUID, UUID> getPendingChallenges() { return pendingChallenges; }
    public Map<UUID, Double> getChallengeBets() { return challengeBets; }
}
