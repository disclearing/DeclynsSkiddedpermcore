package me.declyn.core.commands.subcommands.rank;

import me.declyn.core.Core;
import me.declyn.core.util.CC;
import me.declyn.core.util.Message;
import org.bukkit.command.CommandSender;

public class RankDeleteCommand {

    private Core plugin;

    public RankDeleteCommand(Core plugin) {
        this.plugin = plugin;
    }

    public void execute(CommandSender sender, String[] args) {
        if (args.length == 2) {
            if (!plugin.getManagerHandler().getRankManager().getRanksNameSet().contains(args[1])) {
                sender.sendMessage(CC.translate(Message.getString("general-errors.rank-not-found")));
                return;
            }

            plugin.getManagerHandler().getRankManager().deleteRank(args[1]);
            sender.sendMessage(CC.translate(Message.getString("rank.deleted.message")
                    .replace("%rank%", args[1])
            ));
            return;
        }

        sender.sendMessage(CC.translate(Message.getString("rank.deleted.usage")));
        return;
    }

}
