package me.declyn.core.commands;

import me.declyn.core.Core;
import me.declyn.core.objects.User;
import me.declyn.core.util.BukkitReflection;
import me.declyn.core.util.CC;
import me.declyn.core.util.Message;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PingCommand implements CommandExecutor {

    private Core plugin;

    public PingCommand(Core plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(CC.translate(Message.getString("general-errors.player-only")));
            return true;
        }

        if (!sender.hasPermission("core.command.ping")) {
            sender.sendMessage(CC.translate(Message.getString("general-errors.no-permission")));
            return true;
        }

        if (args.length == 0) {
            Player player = (Player) sender;
            User user = plugin.getManagerHandler().getUserManager().getUser(player);
            sender.sendMessage(CC.translate(Message.getString("ping.message")
                    .replace("%player%", user.getColoredName())
                    .replace("%ping%", String.valueOf(BukkitReflection.getPing(player)))
            ));
            return true;
        }

        if (args.length == 1) {
            Player player = plugin.getServer().getPlayer(args[0]);
            if (player == null) {
                sender.sendMessage(CC.translate(Message.getString("general-errors.player-not-online")));
                return true;
            }
            User user = plugin.getManagerHandler().getUserManager().getUser(player);
            sender.sendMessage(CC.translate(Message.getString("ping.message")
                    .replace("%player%", user.getColoredName())
                    .replace("%ping%", String.valueOf(BukkitReflection.getPing(player)))
            ));
            return true;
        }

        sender.sendMessage(CC.translate(Message.getString("general-errors.no-permission")));
        return true;
    }

}
