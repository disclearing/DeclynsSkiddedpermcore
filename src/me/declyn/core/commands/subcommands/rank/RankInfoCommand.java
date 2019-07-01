package me.declyn.core.commands.subcommands.rank;

import me.declyn.core.Core;
import me.declyn.core.objects.Rank;
import me.declyn.core.util.CC;
import me.declyn.core.util.Message;
import org.bukkit.command.CommandSender;

public class RankInfoCommand {

    private Core plugin;

    public RankInfoCommand(Core plugin) {
        this.plugin = plugin;
    }

    public void execute(CommandSender sender, String[] args) {

        if (args.length == 2) {
            if (!plugin.getManagerHandler().getRankManager().getRanksNameSet().contains(args[1])) {
                sender.sendMessage(CC.translate(Message.getString("general-errors.rank-not-found")));
                return;
            }

            Rank rank = plugin.getManagerHandler().getRankManager().getRankByName(args[1]);
            for (String infoMessage : Message.getStringList("rank.info.message")) {
                sender.sendMessage(CC.translate(infoMessage
                        .replace("%rank%", rank.getColoredName())
                        .replace("%prefix%", rank.getPrefix())
                        .replace("%weight%", String.valueOf(rank.getWeight()))
                ));
            }
            return;
        }

        sender.sendMessage(CC.translate(Message.getString("rank.info.usage")));
        return;
    }

}
