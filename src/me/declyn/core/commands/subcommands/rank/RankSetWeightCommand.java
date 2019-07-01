package me.declyn.core.commands.subcommands.rank;

import me.declyn.core.Core;
import me.declyn.core.objects.Rank;
import me.declyn.core.util.CC;
import me.declyn.core.util.Message;
import org.apache.commons.lang3.math.NumberUtils;
import org.bukkit.command.CommandSender;

public class RankSetWeightCommand {

    private Core plugin;

    public RankSetWeightCommand(Core plugin) {
        this.plugin = plugin;
    }

    public void execute(CommandSender sender, String[] args) {
        if (args.length == 3) {
            if (!plugin.getManagerHandler().getRankManager().getRanksNameSet().contains(args[1])) {
                sender.sendMessage(CC.translate(Message.getString("general-errors.rank-not-found")));
                return;
            }

            if (!NumberUtils.isDigits(args[2])) {
                sender.sendMessage(CC.translate(Message.getString("general-errors.must-be-an-int")));
                return;
            }

            if (Integer.valueOf(args[2]) > 100000) {
                sender.sendMessage(CC.translate(Message.getString("general-errors.max-number-limit")));
                return;
            }

            plugin.getManagerHandler().getRankManager().setWeight(args[1], args[2]);
            Rank rank = plugin.getManagerHandler().getRankManager().getRankByName(args[1]);
            sender.sendMessage(CC.translate(Message.getString("rank.weight.message")
                    .replace("%rank%", rank.getColoredName())
                    .replaceAll("%weight%", args[2])
            ));
            return;
        }

        sender.sendMessage(CC.translate(Message.getString("rank.weight.usage")));
        return;
    }

}
