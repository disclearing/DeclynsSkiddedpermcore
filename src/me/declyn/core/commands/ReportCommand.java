package me.declyn.core.commands;

import me.declyn.core.Core;
import me.declyn.core.objects.User;
import me.declyn.core.util.CC;
import me.declyn.core.util.Message;
import org.apache.commons.lang.StringUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class ReportCommand implements CommandExecutor {

    private Core plugin;
    private Set<UUID> cooldownSet;

    public ReportCommand(Core plugin) {
        this.plugin = plugin;
        cooldownSet = new HashSet<>();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(CC.translate(Message.getString("general-errors.player-only")));
            return true;
        }

        if (!sender.hasPermission("core.command.request")) {
            sender.sendMessage(CC.translate(Message.getString("general-errors.no-permission")));
            return true;
        }

        if (args.length >= 2) {
            String message = StringUtils.join(args, " ", 1, args.length);
            Player player = (Player) sender;
            Player reported = plugin.getServer().getPlayer(args[0]);
            if (cooldownSet.contains(player.getUniqueId())) {
                sender.sendMessage(CC.translate(Message.getString("report.cooldown")
                        .replace("%seconds%", String.valueOf(plugin.getManagerHandler().getFileManager().getOptionsYaml().getLong("cooldowns.report")))
                ));
                return true;
            }
            if (reported == null) {
                sender.sendMessage(CC.translate(Message.getString("general-errors.player-not-online")));
                return true;
            }
            User user = plugin.getManagerHandler().getUserManager().getUser(player);
            User reportedUser = plugin.getManagerHandler().getUserManager().getUser(reported);
            plugin.getManagerHandler().getRedisManager().sendPermissionMessage("core.staff", CC.translate(Message.getString("report.message")
                    .replace("%player%", user.getColoredName()))
                    .replace("%reported%", reportedUser.getColoredName())
                    .replace("%message%", message)
            );
            cooldownSet.add(user.getUuid());
            plugin.getServer().getScheduler().runTaskLaterAsynchronously(plugin, () -> {
                cooldownSet.remove(user.getUuid());
            }, plugin.getManagerHandler().getFileManager().getOptionsYaml().getLong("cooldowns.report") * 20);
            return true;
        }

        sender.sendMessage(CC.translate(Message.getString("report.usage")));
        return true;
    }

}
