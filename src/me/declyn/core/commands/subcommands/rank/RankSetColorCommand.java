package me.declyn.core.commands.subcommands.rank;

import me.declyn.core.Core;
import me.declyn.core.objects.Rank;
import me.declyn.core.util.CC;
import me.declyn.core.util.Message;
import org.bukkit.command.CommandSender;

public class RankSetColorCommand {

    private Core plugin;

    public RankSetColorCommand(Core plugin) {
        this.plugin = plugin;
    }

    public void execute(CommandSender sender, String[] args) {
        if (args.length == 3) {
            if (!plugin.getManagerHandler().getRankManager().getRanksNameSet().contains(args[1])) {
                sender.sendMessage(CC.translate(Message.getString("general-errors.rank-not-found")));
                return;
            }

            plugin.getManagerHandler().getRankManager().setColor(args[1], args[2]);
            Rank rank = plugin.getManagerHandler().getRankManager().getRankByName(args[1]);
            sender.sendMessage(CC.translate(Message.getString("rank.color.message")
                    .replace("%rank%", rank.getColoredName())
                    .replace("%color%", CC.RESET + CC.translate(args[2] + "Example"))
            ));
            return;
        }

        sender.sendMessage(CC.translate(Message.getString("rank.color.usage")));
        return;
    }

}
