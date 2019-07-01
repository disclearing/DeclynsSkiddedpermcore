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

public class RequestCommand implements CommandExecutor {

    private Core plugin;
    private Set<UUID> cooldownSet;

    public RequestCommand(Core plugin) {
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

        if (args.length >= 1) {
            String message = StringUtils.join(args, " ", 0, args.length);
            Player player = (Player) sender;
            User user = plugin.getManagerHandler().getUserManager().getUser(player);
            if (cooldownSet.contains(player.getUniqueId())) {
                sender.sendMessage(CC.translate(Message.getString("request.cooldown")
                        .replace("%seconds%", String.valueOf(plugin.getManagerHandler().getFileManager().getOptionsYaml().getLong("cooldowns.request")))
                ));
                return true;
            }
            plugin.getManagerHandler().getRedisManager().sendPermissionMessage("core.staff", CC.translate(Message.getString("request.message")
                    .replace("%player%", user.getColoredName()))
                    .replace("%message%", message)
            );
            cooldownSet.add(user.getUuid());
            plugin.getServer().getScheduler().runTaskLaterAsynchronously(plugin, () -> {
                cooldownSet.remove(user.getUuid());
            }, plugin.getManagerHandler().getFileManager().getOptionsYaml().getLong("cooldowns.request") * 20);
            return true;
        }

        sender.sendMessage(CC.translate(Message.getString("request.usage")));
        return true;
    }

}
