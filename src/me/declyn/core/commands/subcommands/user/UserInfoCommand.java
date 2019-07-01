package me.declyn.core.commands.subcommands.user;

import me.declyn.core.Core;
import me.declyn.core.objects.Rank;
import me.declyn.core.objects.User;
import me.declyn.core.util.CC;
import me.declyn.core.util.Message;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class UserInfoCommand {

    private Core plugin;

    public UserInfoCommand(Core plugin) {
        this.plugin = plugin;
    }

    public void execute(CommandSender sender, String[] args) {
        if (args.length == 2) {
            Player player = plugin.getServer().getPlayer(args[1]);
            if (player == null) {
                if (!plugin.getManagerHandler().getUserManager().playerSQLDataExists(args[1])) {
                    sender.sendMessage(CC.translate(Message.getString("general-errors.player-never-joined")));
                    return;
                }
                Rank rank = plugin.getManagerHandler().getUserManager().getRank(args[1]);
                for (String userInfo : Message.getStringList("user.info.message")) {
                    sender.sendMessage(CC.translate(userInfo
                            .replace("%player%", rank.getColor() + args[1])
                            .replace("%rank%", rank.getColoredName())
                    ));
                }
                return;
            }

            User user = plugin.getManagerHandler().getUserManager().getUser(player);
            for (String userInfo : Message.getStringList("user.info.message")) {
                sender.sendMessage(CC.translate(userInfo
                        .replace("%player%", user.getColoredName())
                        .replace("%rank%", user.getRank().getColoredName())
                ));
            }
            return;
        }

        sender.sendMessage(CC.translate(Message.getString("user.info.usage")));
        return;
    }

}
