package me.declyn.core.commands;

import me.declyn.core.Core;
import me.declyn.core.objects.User;
import me.declyn.core.util.CC;
import me.declyn.core.util.Message;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TeleportHereCommand implements CommandExecutor {

    private Core plugin;

    public TeleportHereCommand(Core plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(CC.translate(Message.getString("general-errors.player-only")));
            return true;
        }

        if (!sender.hasPermission("core.command.teleporthere")) {
            sender.sendMessage(CC.translate(Message.getString("general-errors.no-permission")));
            return true;
        }

        if (args.length == 1) {
            Player player = (Player) sender;
            Player target = plugin.getServer().getPlayer(args[0]);
            if (target == null) {
                sender.sendMessage(CC.translate(Message.getString("general-errors.player-not-online")));
                return true;
            }
            User user = plugin.getManagerHandler().getUserManager().getUser(player);
            User targetUser = plugin.getManagerHandler().getUserManager().getUser(target);
            target.teleport(player);
            sender.sendMessage(CC.translate(Message.getString("teleport-here.message")
                    .replace("%player%", targetUser.getColoredName())
            ));
            target.sendMessage(CC.translate(Message.getString("teleport-here.message-target")
                    .replace("%player%", user.getColoredName())
            ));
            return true;
        }

        sender.sendMessage(CC.translate(Message.getString("teleport-here.usage")));
        return true;
    }

}
