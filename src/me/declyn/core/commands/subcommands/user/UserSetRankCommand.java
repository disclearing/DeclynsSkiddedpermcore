package me.declyn.core.commands.subcommands.user;

import me.declyn.core.Core;
import me.declyn.core.objects.Rank;
import me.declyn.core.objects.User;
import me.declyn.core.util.CC;
import me.declyn.core.util.Message;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class UserSetRankCommand {

    private Core plugin;

    public UserSetRankCommand(Core plugin) {
        this.plugin = plugin;
    }

    public void execute(CommandSender sender, String[] args) {
        if (args.length == 3) {
            Player target = plugin.getServer().getPlayer(args[1]);
            if (!plugin.getManagerHandler().getRankManager().getRanksNameSet().contains(args[2])) {
                sender.sendMessage(CC.translate(Message.getString("general-errors.rank-not-found")));
                return;
            }

            Rank rank = plugin.getManagerHandler().getRankManager().getRankByName(args[2]);
            if (target == null) {
                if (!plugin.getManagerHandler().getUserManager().playerSQLDataExists(args[1])) {
                    sender.sendMessage(CC.translate(Message.getString("general-errors.player-never-joined")));
                    return;
                }

                plugin.getManagerHandler().getUserManager().setOfflinePlayerRank(args[1], plugin.getManagerHandler().getRankManager().getRankByName(args[2]));
                sender.sendMessage(CC.translate(Message.getString("user.setrank.message")
                        .replace("%player%", rank.getColor() + args[1])
                        .replace("%rank%", rank.getColoredName())
                ));
                return;
            }

            User user = plugin.getManagerHandler().getUserManager().getUser(target);
            user.setRank(rank);
            sender.sendMessage(CC.translate(Message.getString("user.setrank.message")
                    .replace("%player%", user.getColoredName())
                    .replace("%rank%", rank.getColoredName())
            ));
            return;
        }

        sender.sendMessage(CC.translate(Message.getString("user.setrank.usage")));
        return;
    }

}