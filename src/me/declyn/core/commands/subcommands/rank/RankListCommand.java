package me.declyn.core.commands.subcommands.rank;

import me.declyn.core.Core;
import me.declyn.core.objects.Rank;
import me.declyn.core.util.CC;
import me.declyn.core.util.Message;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RankListCommand {

    private Core plugin;

    public RankListCommand(Core plugin) {
        this.plugin = plugin;
    }

    public void execute(CommandSender sender, String[] args) {
        if (args.length == 1) {
            for (String rankListHeader : Message.getStringList("rank.list.header")) {
                sender.sendMessage(CC.translate(rankListHeader));
            }
            Player player = (Player) sender;
            for (Rank rank : plugin.getManagerHandler().getRankManager().getSortedRanks()) {
                BaseComponent format = new TextComponent(CC.translate(Message.getString("rank.list.format")
                        .replace("%rank%", rank.getColoredName())
                ));
                format.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(CC.translate(Message.getString("rank.list.hover")
                        .replace("%prefix%", rank.getPrefix())
                        .replace("%weight%", String.valueOf(rank.getWeight()))
                )).create()));
                player.spigot().sendMessage(format);
            }
            sender.sendMessage(CC.translate(Message.getString("rank.list.footer")));
            return;
        }

        sender.sendMessage(CC.translate(Message.getString("rank.list.usage")));
        return;
    }

}