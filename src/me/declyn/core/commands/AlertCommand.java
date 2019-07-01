package me.declyn.core.commands;

import me.declyn.core.Core;
import me.declyn.core.util.CC;
import me.declyn.core.util.Message;
import org.apache.commons.lang.StringUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AlertCommand implements CommandExecutor {

    private Core plugin;

    public AlertCommand(Core plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!sender.hasPermission("core.command.broadcast")) {
            sender.sendMessage(CC.translate(Message.getString("general-errors.no-permission")));
            return true;
        }

        if (args.length >= 2) {

            if (!(args[0].equalsIgnoreCase("local") || args[0].equalsIgnoreCase("global"))) {
                sender.sendMessage(CC.translate(Message.getString("alert.error")));
                return true;
            }

            if (args[0].equalsIgnoreCase("global") && !plugin.getManagerHandler().getFileManager().getOptionsYaml().getBoolean("database.redis.use")) {
                sender.sendMessage(CC.RED + "You can only use local messaging whilst redis is not being utilized.");
                return true;
            }

            String message = StringUtils.join(args, " ", 1, args.length);
            if (args[0].equalsIgnoreCase("local")) {
                for (Player all : plugin.getServer().getOnlinePlayers()) {
                    all.sendMessage(CC.translate(Message.getString("alert.message")
                            .replace("%message%", message)
                    ));
                }
                return true;
            }

            if (args[0].equalsIgnoreCase("global")) {
                plugin.getManagerHandler().getRedisManager().sendMessage(Message.getString("alert.message")
                        .replace("%message%", message)
                );
                return true;
            }
        }

        sender.sendMessage(CC.translate(Message.getString("alert.usage")));
        return true;
    }


}
