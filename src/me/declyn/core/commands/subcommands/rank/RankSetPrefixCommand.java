package me.declyn.core.commands.subcommands.rank;

import me.declyn.core.Core;
import me.declyn.core.objects.Rank;
import me.declyn.core.util.CC;
import me.declyn.core.util.Message;
import org.apache.commons.lang.StringUtils;
import org.bukkit.command.CommandSender;

public class RankSetPrefixCommand {

    private Core plugin;

    public RankSetPrefixCommand(Core plugin) {
        this.plugin = plugin;
    }

    public void execute(CommandSender sender, String[] args) {
        if (args.length >= 3) {
            if (!plugin.getManagerHandler().getRankManager().getRanksNameSet().contains(args[1])) {
                sender.sendMessage(CC.translate(Message.getString("general-errors.rank-not-found")));
                return;
            }

            String prefix = StringUtils.join(args, " ", 2, args.length);
            Rank rank = plugin.getManagerHandler().getRankManager().getRankByName(args[1]);
            plugin.getManagerHandler().getRankManager().setPrefix(args[1], prefix);
            sender.sendMessage(CC.translate(Message.getString("rank.prefix.message")
                    .replace("%rank%", rank.getColoredName())
                    .replace("%prefix%", prefix)
            ));
            return;
        }

        sender.sendMessage(CC.translate(Message.getString("rank.prefix.usage")));
        return;
    }

}
