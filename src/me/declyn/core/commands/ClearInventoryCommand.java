package me.declyn.core.commands;

import me.declyn.core.Core;
import me.declyn.core.objects.User;
import me.declyn.core.util.CC;
import me.declyn.core.util.Message;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ClearInventoryCommand implements CommandExecutor {

    private Core plugin;

    public ClearInventoryCommand(Core plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(CC.translate(Message.getString("general-errors.player-only")));
            return true;
        }

        if(!sender.hasPermission("core.command.clearinventory")) {
            sender.sendMessage(CC.translate(Message.getString("general-errors.no-permission")));
            return true;
        }

        if(args.length == 0) {
            Player player = (Player) sender;
            User user = plugin.getManagerHandler().getUserManager().getUser(player);
            player.getInventory().clear();
            player.getInventory().setArmorContents(null);
            sender.sendMessage(CC.translate(Message.getString("clear-inventory.message")
                    .replace("%player%", user.getColoredName())
            ));
            return true;
        }

        if(args.length == 1) {
            Player player = plugin.getServer().getPlayer(args[0]);
            if(player == null) {
                sender.sendMessage(CC.translate(Message.getString("general-errors.player-not-online")));
                return true;
            }
            User user = plugin.getManagerHandler().getUserManager().getUser(player);
            player.getInventory().clear();
            player.getInventory().setArmorContents(null);
            sender.sendMessage(CC.translate(Message.getString("clear-inventory.message")
                    .replace("%player%", user.getColoredName())
            ));
            return true;
        }

        sender.sendMessage(CC.translate(Message.getString("clear-inventory.usage")));
        return true;
    }

}
