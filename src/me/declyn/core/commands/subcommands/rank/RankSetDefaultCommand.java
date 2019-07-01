package me.declyn.core.commands.subcommands.rank;

import me.declyn.core.Core;
import me.declyn.core.objects.Rank;
import me.declyn.core.util.CC;
import me.declyn.core.util.Message;
import org.bukkit.command.CommandSender;

public class RankSetDefaultCommand {

    private Core plugin;

    public RankSetDefaultCommand(Core plugin) {
        this.plugin = plugin;
    }

    public void execute(CommandSender sender, String[] args) {
        if (args.length == 2) {
            if (!plugin.getManagerHandler().getRankManager().getRanksNameSet().contains(args[1])) {
                sender.sendMessage(CC.translate(Message.getString("general-errors.rank-not-found")));
                return;
            }

            Rank rank = plugin.getManagerHandler().getRankManager().getRankByName(args[1]);
            plugin.getManagerHandler().getRankManager().setDefaultRank(rank.getName());
            sender.sendMessage(CC.translate(Message.getString("rank.default.message")
                    .replace("%rank%", rank.getColoredName())
            ));
            return;
        }

        sender.sendMessage(CC.translate(Message.getString("rank.default.usage")));
        return;
    }

}
