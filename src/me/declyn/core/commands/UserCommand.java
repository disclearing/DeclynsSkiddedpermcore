package me.declyn.core.commands;

import me.declyn.core.Core;
import me.declyn.core.commands.subcommands.user.UserInfoCommand;
import me.declyn.core.commands.subcommands.user.UserSetRankCommand;
import me.declyn.core.util.CC;
import me.declyn.core.util.Message;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class UserCommand implements CommandExecutor {

    private Core plugin;

    private UserSetRankCommand userSetRankCommand;
    private UserInfoCommand userInfoCommand;

    public UserCommand(Core plugin) {
        this.plugin = plugin;
        userSetRankCommand = new UserSetRankCommand(plugin);
        userInfoCommand = new UserInfoCommand(plugin);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!sender.hasPermission("core.command.user")) {
            sender.sendMessage(CC.translate(Message.getString("general-errors.no-permission")));
            return true;
        }

        if (args.length == 0) {
            sendHelpMessage(sender);
            return true;
        }

        if (args[0].equalsIgnoreCase("setrank")) {
            userSetRankCommand.execute(sender, args);
            return true;
        }

        if (args[0].equalsIgnoreCase("info")) {
            userInfoCommand.execute(sender, args);
            return true;
        }

        sendHelpMessage(sender);
        return true;
    }

    private void sendHelpMessage(CommandSender sender) {
        for (String helpMessage : Message.getStringList("user.help")) {
            sender.sendMessage(CC.translate(helpMessage));
        }
    }

}
