package me.declyn.core.commands.subcommands.rank;

import me.declyn.core.Core;
import me.declyn.core.objects.Rank;
import me.declyn.core.util.CC;
import me.declyn.core.util.Message;
import org.bukkit.command.CommandSender;

public class RankDeletePermissionCommand {

    private Core plugin;

    public RankDeletePermissionCommand(Core plugin) {
        this.plugin = plugin;
    }

    public void exeucte(CommandSender sender, String[] args) {
        if (args.length == 3) {
            if (!plugin.getManagerHandler().getRankManager().getRanksNameSet().contains(args[1])) {
                sender.sendMessage(CC.translate(Message.getString("general-errors.rank-not-found")));
                return;
            }

            Rank rank = plugin.getManagerHandler().getRankManager().getRankByName(args[1]);
            if (!plugin.getManagerHandler().getFileManager().getPermissionsYaml().getStringList(rank.getName() + ".permissions").contains(args[2])) {
                sender.sendMessage(CC.translate(Message.getString("rank.permission.deleted.error")));
                return;
            }

            plugin.getManagerHandler().getFileManager().getPermissionsYaml().getStringList(rank.getName() + ".permissions").remove(args[2]);
            plugin.getManagerHandler().getFileManager().savePermissionsFile();
            sender.sendMessage(CC.translate(Message.getString("rank.permission.deleted.message")
                    .replace("%rank%", rank.getColoredName())
                    .replace("%permission%", args[2])
            ));
            return;
        }

        sender.sendMessage(CC.translate(Message.getString("rank.permission.deleted.usage")));
        return;
    }

}
