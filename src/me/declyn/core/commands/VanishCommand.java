package me.declyn.core.commands;

import me.declyn.core.Core;
import me.declyn.core.objects.User;
import me.declyn.core.util.CC;
import me.declyn.core.util.Message;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class VanishCommand implements CommandExecutor {

    private Core plugin;

    public VanishCommand(Core plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(CC.translate(Message.getString("general-errors.player-only")));
            return true;
        }

        if (!sender.hasPermission("core.command.vanish")) {
            sender.sendMessage(CC.translate(Message.getString("general-errors.no-permission")));
            return true;
        }

        if (args.length == 0) {
            Player player = (Player) sender;
            User user = plugin.getManagerHandler().getUserManager().getUser(player);

            if (user.isVanished()) {
                for (Player all : plugin.getServer().getOnlinePlayers()) {
                    if (!all.hasPermission("core.command.vanish")) {
                        all.showPlayer(player);
                    }
                }
            } else {
                for (Player all : plugin.getServer().getOnlinePlayers()) {
                    if (!all.hasPermission("core.command.vanish")) {
                        all.hidePlayer(player);
                    }
                }
            }

            user.setVanished(!user.isVanished());
            sender.sendMessage(CC.translate(Message.getString("vanish.message")
                    .replace("%player%", user.getColoredName())
                    .replace("%boolean%", user.isVanished() ? Message.getString("true") : Message.getString("false"))
            ));
            return true;
        }

        if (args.length == 1) {
            Player player = plugin.getServer().getPlayer(args[0]);
            if (player == null) {
                sender.sendMessage(CC.translate(Message.getString("general-errors.player-not-online")));
                return true;
            }
            User senderUser = plugin.getManagerHandler().getUserManager().getUser((Player) sender);
            User user = plugin.getManagerHandler().getUserManager().getUser(player);

            if (user.isVanished()) {
                for (Player all : plugin.getServer().getOnlinePlayers()) {
                    if (!all.hasPermission("core.command.vanish")) {
                        all.showPlayer(player);
                    }
                }
            } else {
                for (Player all : plugin.getServer().getOnlinePlayers()) {
                    if (!all.hasPermission("core.command.vanish")) {
                        all.hidePlayer(player);
                    }
                }
            }

            user.setVanished(!user.isVanished());
            sender.sendMessage(CC.translate(Message.getString("vanish.message")
                    .replace("%player%", user.getColoredName())
                    .replace("%boolean%", user.isVanished() ? Message.getString("true") : Message.getString("false"))
            ));
            player.sendMessage(CC.translate(Message.getString("vanish.message-target")
                    .replace("%player%", senderUser.getColoredName())
                    .replace("%boolean%", user.isVanished() ? Message.getString("true") : Message.getString("false"))
            ));
            return true;
        }

        sender.sendMessage(CC.translate(Message.getString("vanish.usage")));
        return true;
    }

}
