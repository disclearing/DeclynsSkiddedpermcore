package me.declyn.core.commands.subcommands.chat;

import me.declyn.core.Core;
import me.declyn.core.objects.User;
import me.declyn.core.util.CC;
import me.declyn.core.util.Message;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ChatClearCommand {

    private Core plugin;
    private String[] clearChat;

    public ChatClearCommand(Core plugin) {
        this.plugin = plugin;
        clearChat = new String[101];
    }

    public void execute(CommandSender sender, String[] args) {

        if(args.length == 1) {
            Player player = (Player) sender;
            User user = plugin.getManagerHandler().getUserManager().getUser(player);

            for (Player all : plugin.getServer().getOnlinePlayers()) {
                all.sendMessage(clearChat);
                all.sendMessage(CC.translate(Message.getString("chat.clear.message")
                        .replace("%player%", user.getColoredName())
                ));
            }
            return;
        }

        sender.sendMessage(CC.translate(Message.getString("chat.clear.usage")));
        return;
    }

}
