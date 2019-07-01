package me.declyn.core.commands.subcommands.chat;

import me.declyn.core.Core;
import me.declyn.core.objects.User;
import me.declyn.core.util.CC;
import me.declyn.core.util.Message;
import org.apache.commons.lang3.math.NumberUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ChatSlowCommand {

    private Core plugin;

    public ChatSlowCommand(Core plugin) {
        this.plugin = plugin;
    }

    public void execute(CommandSender sender, String[] args) {

        if(args.length == 1) {
            Player player = (Player) sender;
            User user = plugin.getManagerHandler().getUserManager().getUser(player);

            if(!plugin.getManagerHandler().getChatManager().isSlowed()) {
                sender.sendMessage(CC.RED + "Chat is not slowed, you must type /chat slow <delay>.");
                return;
            }

            plugin.getManagerHandler().getChatManager().setDelay(0);
            plugin.getServer().getOnlinePlayers().forEach(all -> {
                all.sendMessage(CC.translate(Message.getString("chat.slow.message-unslowed")
                        .replace("%player%", user.getColoredName())
                ));
            });
        }

        if(args.length == 2) {
            if(!NumberUtils.isDigits(args[1])) {
                sender.sendMessage(CC.translate(Message.getString("general-errors.must-be-an-int")));
                return;
            }

            plugin.getManagerHandler().getChatManager().setDelay(Integer.valueOf(args[1]));
            Player player = (Player) sender;
            User user = plugin.getManagerHandler().getUserManager().getUser(player);
            for(Player all : plugin.getServer().getOnlinePlayers()) {
                all.sendMessage(CC.translate(Message.getString("chat.slow.message-slowed")
                        .replace("%player%", user.getColoredName())
                        .replace("%value%", args[1])
                ));
            }
            return;
        }

        sender.sendMessage(CC.translate(Message.getString("chat.slow.usage")));
        return;
    }

}
