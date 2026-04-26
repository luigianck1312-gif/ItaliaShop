package it.italiashop.commands;

import it.italiashop.ItaliaShop;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CrystalCommand implements CommandExecutor {

    private final ItaliaShop plugin;

    public CrystalCommand(ItaliaShop plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        // /cristalli - vedi i tuoi cristalli
        if (args.length == 0) {
            if (!(sender instanceof Player player)) {
                sender.sendMessage("Solo i giocatori possono usare questo comando!");
                return true;
            }
            int crystals = plugin.getCrystalManager().getCrystals(player);
            player.sendMessage(ChatColor.AQUA + "💎 Hai " + ChatColor.WHITE + crystals + ChatColor.AQUA + " cristalli!");
            return true;
        }

        // /cristalli give <player> <amount>
        if (args[0].equalsIgnoreCase("give")) {
            if (!sender.hasPermission("italiashop.cristalli.give")) {
                sender.sendMessage(ChatColor.RED + "Non hai il permesso!");
                return true;
            }
            if (args.length < 3) {
                sender.sendMessage(ChatColor.RED + "Uso: /cristalli give <player> <quantità>");
                return true;
            }
            Player target = Bukkit.getPlayer(args[1]);
            if (target == null) {
                sender.sendMessage(ChatColor.RED + "Giocatore non trovato!");
                return true;
            }
            try {
                int amount = Integer.parseInt(args[2]);
                if (amount <= 0) throw new NumberFormatException();
                plugin.getCrystalManager().addCrystals(target, amount);
                sender.sendMessage(ChatColor.GREEN + "Dati " + amount + " 💎 cristalli a " + target.getName());
                target.sendMessage(ChatColor.AQUA + "💎 Hai ricevuto " + ChatColor.WHITE + amount + ChatColor.AQUA + " cristalli!");
                target.sendMessage(ChatColor.GRAY + "Totale: " + plugin.getCrystalManager().getCrystals(target));
            } catch (NumberFormatException ex) {
                sender.sendMessage(ChatColor.RED + "Quantità non valida!");
            }
            return true;
        }

        // /cristalli set <player> <amount>
        if (args[0].equalsIgnoreCase("set")) {
            if (!sender.hasPermission("italiashop.cristalli.give")) {
                sender.sendMessage(ChatColor.RED + "Non hai il permesso!");
                return true;
            }
            if (args.length < 3) {
                sender.sendMessage(ChatColor.RED + "Uso: /cristalli set <player> <quantità>");
                return true;
            }
            Player target = Bukkit.getPlayer(args[1]);
            if (target == null) {
                sender.sendMessage(ChatColor.RED + "Giocatore non trovato!");
                return true;
            }
            try {
                int amount = Integer.parseInt(args[2]);
                plugin.getCrystalManager().setCrystals(target, amount);
                sender.sendMessage(ChatColor.GREEN + "Impostati " + amount + " 💎 cristalli a " + target.getName());
                target.sendMessage(ChatColor.AQUA + "💎 I tuoi cristalli sono stati impostati a " + amount + "!");
            } catch (NumberFormatException ex) {
                sender.sendMessage(ChatColor.RED + "Quantità non valida!");
            }
            return true;
        }

        // /cristalli check <player>
        if (args[0].equalsIgnoreCase("check")) {
            if (!sender.hasPermission("italiashop.cristalli.give")) {
                sender.sendMessage(ChatColor.RED + "Non hai il permesso!");
                return true;
            }
            if (args.length < 2) {
                sender.sendMessage(ChatColor.RED + "Uso: /cristalli check <player>");
                return true;
            }
            Player target = Bukkit.getPlayer(args[1]);
            if (target == null) {
                sender.sendMessage(ChatColor.RED + "Giocatore non trovato!");
                return true;
            }
            int crystals = plugin.getCrystalManager().getCrystals(target);
            sender.sendMessage(ChatColor.AQUA + target.getName() + " ha " + ChatColor.WHITE + crystals + ChatColor.AQUA + " 💎 cristalli");
            return true;
        }

        sender.sendMessage(ChatColor.RED + "Uso: /cristalli [give|set|check] <player> <quantità>");
        return true;
    }
}
