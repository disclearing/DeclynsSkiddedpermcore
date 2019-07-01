package me.declyn.core.commands;

import me.declyn.core.Core;
import me.declyn.core.objects.User;
import me.declyn.core.util.CC;
import me.declyn.core.util.Message;
import org.apache.commons.lang3.math.NumberUtils;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TeleportCommand implements CommandExecutor {

    private Core plugin;

    public TeleportCommand(Core plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(CC.translate(Message.getString("general-errors.player-only")));
            return true;
        }

        if (!sender.hasPermission("core.command.teleport")) {
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
            player.teleport(target);
            User user = plugin.getManagerHandler().getUserManager().getUser(player);
            User targetUser = plugin.getManagerHandler().getUserManager().getUser(target);
            sender.sendMessage(CC.translate(Message.getString("teleport.message")
                    .replace("%player%", user.getColoredName())
                    .replace("%target%", targetUser.getColoredName())
            ));
            return true;
        }

        if (args.length == 2) {
            Player target1 = plugin.getServer().getPlayer(args[0]);
            Player target2 = plugin.getServer().getPlayer(args[1]);
            if (target1 == null || target2 == null) {
                sender.sendMessage(CC.translate(Message.getString("general-errors.player-not-online")));
                return true;
            }
            target1.teleport(target2);
            User target1User = plugin.getManagerHandler().getUserManager().getUser(target1);
            User target2User = plugin.getManagerHandler().getUserManager().getUser(target2);
            sender.sendMessage(CC.translate(Message.getString("teleport.message")
                    .replace("%player%", target1User.getColoredName())
                    .replace("%target%", target2User.getColoredName())
            ));
            return true;
        }

        if (args.length == 3) {
            Player player = (Player) sender;
            if (!NumberUtils.isDigits(args[0]) || !NumberUtils.isDigits(args[1]) || !NumberUtils.isDigits(args[2])) {
                sender.sendMessage(CC.translate(Message.getString("general-errors.must-be-double")));
                return true;
            }
            Location location = new Location(player.getWorld(), Double.valueOf(args[0]), Double.valueOf(args[1]), Double.valueOf(args[2]));
            player.teleport(location);
            User user = plugin.getManagerHandler().getUserManager().getUser(player);
            sender.sendMessage(CC.translate(Message.getString("teleport.message-location")
                    .replace("%player%", user.getColoredName())
                    .replace("%x%", args[0])
                    .replace("%y%", args[1])
                    .replace("%z%", args[2])
            ));
            return true;
        }

        if (args.length == 4) {
            Player player = plugin.getServer().getPlayer(args[0]);
            if (player == null) {
                sender.sendMessage(CC.translate(Message.getString("general-errors.player-not-online")));
                return true;
            }
            if (!NumberUtils.isDigits(args[1]) || !NumberUtils.isDigits(args[2]) || !NumberUtils.isDigits(args[3])) {
                sender.sendMessage(CC.translate(Message.getString("general-errors.must-be-double")));
                return true;
            }
            Location location = new Location(player.getWorld(), Double.valueOf(args[1]), Double.valueOf(args[2]), Double.valueOf(args[3]));
            player.teleport(location);
            User user = plugin.getManagerHandler().getUserManager().getUser(player);
            sender.sendMessage(CC.translate(Message.getString("teleport.message-location")
                    .replace("%player%", user.getColoredName())
                    .replace("%x%", args[1])
                    .replace("%y%", args[2])
                    .replace("%z%", args[3])
            ));
            return true;
        }

        sender.sendMessage(CC.translate(Message.getString("teleport.usage")));
        return true;
    }

}
