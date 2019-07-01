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

public class ReplyCommand implements CommandExecutor {

    private Core plugin;

    public ReplyCommand(Core plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(CC.translate(Message.getString("general-errors.player-only")));
            return true;
        }

        if (!sender.hasPermission("core.command.message")) {
            sender.sendMessage(CC.translate(Message.getString("general-errors.no-permission")));
            return true;
        }

        if (args.length >= 1) {
            Player player = (Player) sender;
            Player recipient = plugin.getManagerHandler().getUserManager().getReplyMap().get(player);

            if (recipient == null) {
                sender.sendMessage(CC.translate(Message.getString("general-errors.player-not-online")));
                return true;
            }

            if (!plugin.getManagerHandler().getUserManager().getReplyMap().containsKey(player)) {
                sender.sendMessage(CC.translate(Message.getString("message.reply.error")));
                return true;
            }

            User userPlayer = plugin.getManagerHandler().getUserManager().getUser(player);
            User userRecipient = plugin.getManagerHandler().getUserManager().getUser(recipient);
            String message = StringUtils.join(args, " ", 0, args.length);
            String to = CC.translate(Message.getString("message.message-to")
                    .replace("%player%", userRecipient.getColoredName()))
                    .replace("%message%", message);
            String from = CC.translate(Message.getString("message.message-from")
                    .replace("%player%", userPlayer.getColoredName()))
                    .replace("%message%", message);
            player.sendMessage(to);
            recipient.sendMessage(from);
            return true;
        }

        sender.sendMessage(CC.translate(Message.getString("message.reply.usage")));
        return true;
    }

}
