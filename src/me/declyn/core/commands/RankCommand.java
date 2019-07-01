package me.declyn.core.commands;

import me.declyn.core.Core;
import me.declyn.core.commands.subcommands.rank.*;
import me.declyn.core.util.CC;
import me.declyn.core.util.Message;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class RankCommand implements CommandExecutor {

    private Core plugin;

    private RankCreateCommand rankCreateCommand;
    private RankDeleteCommand rankDeleteCommand;
    private RankSetPrefixCommand rankSetPrefixCommand;
    private RankSetColorCommand rankSetColorCommand;
    private RankSetWeightCommand rankSetWeightCommand;
    private RankInfoCommand rankInfoCommand;
    private RankAddPermissionCommand rankAddPermissionCommand;
    private RankDeletePermissionCommand rankDeletePermissionCommand;
    private RankListCommand rankListCommand;
    private RankSetDefaultCommand rankSetDefaultCommand;


    public RankCommand(Core plugin) {
        this.plugin = plugin;
        rankCreateCommand = new RankCreateCommand(plugin);
        rankDeleteCommand = new RankDeleteCommand(plugin);
        rankSetPrefixCommand = new RankSetPrefixCommand(plugin);
        rankSetColorCommand = new RankSetColorCommand(plugin);
        rankSetWeightCommand = new RankSetWeightCommand(plugin);
        rankInfoCommand = new RankInfoCommand(plugin);
        rankAddPermissionCommand = new RankAddPermissionCommand(plugin);
        rankDeletePermissionCommand = new RankDeletePermissionCommand(plugin);
        rankListCommand = new RankListCommand(plugin);
        rankSetDefaultCommand = new RankSetDefaultCommand(plugin);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!sender.hasPermission("core.command.rank")) {
            sender.sendMessage(CC.translate(Message.getString("general-errors.no-permission")));
            return true;
        }

        if (args.length == 0) {
            sendHelpMessage(sender);
            return true;
        }

        if (args[0].equalsIgnoreCase("create")) {
            rankCreateCommand.execute(sender, args);
            return true;
        }

        if (args[0].equalsIgnoreCase("delete")) {
            rankDeleteCommand.execute(sender, args);
            return true;
        }

        if (args[0].equalsIgnoreCase("setprefix")) {
            rankSetPrefixCommand.execute(sender, args);
            return true;
        }

        if (args[0].equalsIgnoreCase("setcolor")) {
            rankSetColorCommand.execute(sender, args);
            return true;
        }

        if (args[0].equalsIgnoreCase("setweight")) {
            rankSetWeightCommand.execute(sender, args);
            return true;
        }

        if (args[0].equalsIgnoreCase("info")) {
            rankInfoCommand.execute(sender, args);
            return true;
        }

        if (args[0].equalsIgnoreCase("addpermission") || args[0].equalsIgnoreCase("addperm")) {
            rankAddPermissionCommand.exeucte(sender, args);
            return true;
        }

        if (args[0].equalsIgnoreCase("deletepermission") || args[0].equalsIgnoreCase("delperm")) {
            rankDeletePermissionCommand.exeucte(sender, args);
            return true;
        }

        if (args[0].equalsIgnoreCase("list")) {
            rankListCommand.execute(sender, args);
            return true;
        }

        if (args[0].equalsIgnoreCase("setdefault")) {
            rankSetDefaultCommand.execute(sender, args);
            return true;
        }

        sendHelpMessage(sender);
        return true;
    }

    private void sendHelpMessage(CommandSender sender) {
        for (String helpMessage : Message.getStringList("rank.help")) {
            sender.sendMessage(CC.translate(helpMessage));
        }
    }

}
