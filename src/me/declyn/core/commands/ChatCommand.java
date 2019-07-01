package me.declyn.core.commands;

import me.declyn.core.Core;
import me.declyn.core.commands.subcommands.chat.ChatClearCommand;
import me.declyn.core.commands.subcommands.chat.ChatMuteCommand;
import me.declyn.core.commands.subcommands.chat.ChatSlowCommand;
import me.declyn.core.util.CC;
import me.declyn.core.util.Message;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ChatCommand implements CommandExecutor {

    private Core plugin;

    private ChatClearCommand chatClearCommand;
    private ChatMuteCommand chatMuteCommand;
    private ChatSlowCommand chatSlowCommand;

    public ChatCommand(Core plugin) {
        this.plugin = plugin;
        chatClearCommand = new ChatClearCommand(plugin);
        chatMuteCommand = new ChatMuteCommand(plugin);
        chatSlowCommand = new ChatSlowCommand(plugin);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(CC.translate(Message.getString("general-errors.player-only")));
            return true;
        }

        if (!sender.hasPermission("core.command.chat")) {
            sender.sendMessage(CC.translate(Message.getString("general-errors.no-permission")));
            return true;
        }

        if (args.length == 0) {
            sendHelpMessage(sender);
            return true;
        }

        if (args[0].equalsIgnoreCase("clear")) {
            chatClearCommand.execute(sender, args);
            return true;
        }

        if (args[0].equalsIgnoreCase("mute")) {
            chatMuteCommand.execute(sender, args);
            return true;
        }

        if (args[0].equalsIgnoreCase("slow")) {
            chatSlowCommand.execute(sender, args);
            return true;
        }

        sendHelpMessage(sender);
        return true;
    }

    private void sendHelpMessage(CommandSender sender) {
        for (String message : Message.getStringList("chat.help")) {
            sender.sendMessage(CC.translate(message));
        }
    }

}
