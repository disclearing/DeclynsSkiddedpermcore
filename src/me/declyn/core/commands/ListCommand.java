package me.declyn.core.commands;

import me.declyn.core.Core;
import me.declyn.core.objects.Rank;
import me.declyn.core.objects.User;
import me.declyn.core.util.CC;
import me.declyn.core.util.Message;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ListCommand implements CommandExecutor {

    private Core plugin;

    public ListCommand(Core plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!sender.hasPermission("core.command.list")) {
            sender.sendMessage(CC.translate(Message.getString("general-errors.no-permission")));
            return true;
        }

        StringBuilder ranks = new StringBuilder();
        StringBuilder players = new StringBuilder();

        boolean first = true;

        for (Rank rank : plugin.getManagerHandler().getRankManager().getSortedRanks()) {
            if (first) {
                ranks.append(rank.getColor() + rank.getName());
                first = false;
                continue;
            }
            ranks.append(Message.getString("list.seperator") + rank.getColoredName());
        }

        first = true;

        for (User user : plugin.getManagerHandler().getUserManager().getSortedUsers()) {
            if (first) {
                players.append(user.getColoredName());
                first = false;
                continue;
            }
            players.append(Message.getString("list.seperator") + user.getColoredName());
        }

        for (String list : Message.getStringList("list.message")) {
            sender.sendMessage(CC.translate(list
                    .replace("%ranks%", ranks)
                    .replace("%online%", String.valueOf(plugin.getServer().getOnlinePlayers().size()))
                    .replace("%maxplayers%", String.valueOf(plugin.getServer().getMaxPlayers()))
                    .replace("%players%", players)
            ));
        }

        return true;
    }

}
