package me.declyn.core.commands.subcommands.chat;

import me.declyn.core.Core;
import me.declyn.core.objects.User;
import me.declyn.core.util.CC;
import me.declyn.core.util.Message;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ChatMuteCommand {

    private Core plugin;

    public ChatMuteCommand(Core plugin) {
        this.plugin = plugin;
    }

    public void execute(CommandSender sender, String[] args) {
        if(args.length == 1) {
            Player player = (Player) sender;
            User user = plugin.getManagerHandler().getUserManager().getUser(player);
            plugin.getManagerHandler().getChatManager().setMuted(!plugin.getManagerHandler().getChatManager().isMuted());
            for(Player all : plugin.getServer().getOnlinePlayers()) {
                all.sendMessage(CC.translate(Message.getString("chat.muted.message")
                        .replace("%boolean%", plugin.getManagerHandler().getChatManager().isMuted() ? Message.getString("chat.muted.display-names.muted") : Message.getString("chat.muted.display-names.unmuted"))
                        .replace("%player%", user.getColoredName())
                ));
            }
            return;
        }

        sender.sendMessage(CC.translate(Message.getString("chat.muted.usage")));
        return;
    }

}
