package it.italiashop.commands;

import it.italiashop.ItaliaShop;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class NPCCommand implements CommandExecutor {

    private final ItaliaShop plugin;

    public NPCCommand(ItaliaShop plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("Solo i giocatori possono usare questo comando!");
            return true;
        }

        if (!player.hasPermission("italiashop.admin")) {
            player.sendMessage(ChatColor.RED + "Non hai i permessi!");
            return true;
        }

        if (args.length < 2) {
            player.sendMessage(ChatColor.RED + "Uso: /npc <place|remove> <mercato|pvp>");
            return true;
        }

        String action = args[0].toLowerCase();
        String type = args[1].toLowerCase();

        switch (action) {
            case "place" -> {
                switch (type) {
                    case "mercato" -> {
                        plugin.getNpcManager().spawnShopVillager(player.getLocation());
                        player.sendMessage(ChatColor.GREEN + "NPC Shop piazzato!");
                    }
                    case "pvp" -> {
                        plugin.getNpcManager().spawnPvpVillager(player.getLocation());
                        player.sendMessage(ChatColor.GREEN + "NPC Arena piazzato!");
                    }
                    default -> player.sendMessage(ChatColor.RED + "Tipo non valido! Usa: mercato, pvp");
                }
            }
            case "remove" -> {
                switch (type) {
                    case "mercato" -> {
                        plugin.getNpcManager().removeShopVillager();
                        player.sendMessage(ChatColor.GREEN + "NPC Shop rimosso!");
                    }
                    case "pvp" -> {
                        plugin.getNpcManager().removePvpVillager();
                        player.sendMessage(ChatColor.GREEN + "NPC Arena rimosso!");
                    }
                    default -> player.sendMessage(ChatColor.RED + "Tipo non valido! Usa: mercato, pvp");
                }
            }
            default -> player.sendMessage(ChatColor.RED + "Azione non valida! Usa: place, remove");
        }

        return true;
    }
}
