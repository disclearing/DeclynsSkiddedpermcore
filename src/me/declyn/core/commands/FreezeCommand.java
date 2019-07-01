package me.declyn.core.commands;

import me.declyn.core.Core;
import me.declyn.core.objects.User;
import me.declyn.core.util.CC;
import me.declyn.core.util.Message;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FreezeCommand implements CommandExecutor {

    private Core plugin;

    public FreezeCommand(Core plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!sender.hasPermission("core.command.freeze")) {
            sender.sendMessage(CC.translate(Message.getString("general-errors.no-permission")));
            return true;
        }

        if (args.length == 1) {
            Player target = plugin.getServer().getPlayer(args[0]);
            if (target == null) {
                sender.sendMessage(CC.translate(Message.getString("general-errors.player-not-online")));
                return true;
            }
            User user = plugin.getManagerHandler().getUserManager().getUser(target);
            user.setFrozen(!user.isFrozen());
            sender.sendMessage(CC.translate(Message.getString("freeze.message")
                    .replace("%player%", user.getColoredName())
                    .replace("%boolean%", user.isFrozen() ? Message.getString("true") : Message.getString("false"))
            ));

            plugin.getServer().getScheduler().runTaskTimerAsynchronously(plugin, () -> {

            }, 10L, 10L);

            return true;
        }

        sender.sendMessage(CC.translate(Message.getString("freeze.usage")));
        return true;
    }

}
