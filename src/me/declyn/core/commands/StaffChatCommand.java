package me.declyn.core.commands;

import me.declyn.core.Core;
import me.declyn.core.objects.User;
import me.declyn.core.util.CC;
import me.declyn.core.util.Message;
import org.apache.commons.lang.StringUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StaffChatCommand implements CommandExecutor {

    private Core plugin;

    public StaffChatCommand(Core plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(CC.translate(Message.getString("general-errors.player-only")));
            return true;
        }

        if (!sender.hasPermission("core.command.staffchat")) {
            sender.sendMessage(CC.translate(Message.getString("general-errors.no-permission")));
            return true;
        }

        if (args.length == 0) {
            Player player = (Player) sender;
            User user = plugin.getManagerHandler().getUserManager().getUser(player);

            user.setStaffChat(!user.isStaffChat());
            sender.sendMessage(CC.translate(Message.getString("staffchat.message-toggle")
                    .replace("%player%", user.getColoredName())
                    .replace("%boolean%", user.isStaffChat() ? Message.getString("true") : Message.getString("false"))
            ));
            return true;
        }

        if (args.length >= 1) {
            Player player = (Player) sender;
            User user = plugin.getManagerHandler().getUserManager().getUser(player);
            String message = StringUtils.join(args, " ", 0, args.length);
            plugin.getManagerHandler().getRedisManager().sendPermissionMessage("core.command.staffchat", CC.translate(Message.getString("staffchat.message")
                    .replace("%server%", Message.getString("server-name"))
                    .replace("%player%", user.getColoredName())
                    .replace("%message%", message)
            ));
            return true;
        }

        sender.sendMessage(CC.translate(Message.getString("staffchat.usage")));
        return true;
    }

}
