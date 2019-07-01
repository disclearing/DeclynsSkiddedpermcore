package me.declyn.core.commands;

import me.declyn.core.Core;
import me.declyn.core.util.BukkitReflection;
import me.declyn.core.util.CC;
import me.declyn.core.util.Message;
import org.apache.commons.lang3.math.NumberUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class SetSlotsCommand implements CommandExecutor {

    private Core plugin;

    public SetSlotsCommand(Core plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!sender.hasPermission("core.command.setslots")) {
            sender.sendMessage(CC.translate(Message.getString("general-errors.no-permission")));
            return true;
        }

        if (args.length == 1) {
            if (!NumberUtils.isDigits(args[0])) {
                sender.sendMessage(CC.translate(Message.getString("general-errors.must-be-an-int")));
                return true;
            }

            if (Integer.valueOf(args[0]) > 100000) {
                sender.sendMessage(CC.translate(Message.getString("general-errors.max-number-limit")));
                return true;
            }

            BukkitReflection.setMaxPlayers(plugin.getServer(), Integer.valueOf(args[0]));
            sender.sendMessage(CC.translate(Message.getString("setslots.message")
                    .replace("%slots%", args[0])
            ));
            return true;
        }

        sender.sendMessage(CC.translate(Message.getString("setslots.usage")));
        return true;
    }

}
