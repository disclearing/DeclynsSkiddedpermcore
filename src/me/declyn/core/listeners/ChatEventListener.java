package me.declyn.core.listeners;

import me.declyn.core.Core;
import me.declyn.core.objects.User;
import me.declyn.core.util.CC;
import me.declyn.core.util.Message;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class ChatEventListener implements Listener {

    private Core plugin;

    private Set<UUID> slowSet;

    public ChatEventListener(Core plugin) {
        this.plugin = plugin;
        slowSet = new HashSet<>();
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        User user = plugin.getManagerHandler().getUserManager().getUser(player);

        if (plugin.getManagerHandler().getChatManager().isMuted() && !player.hasPermission("core.chat.mute.bypass")) {
            player.sendMessage(CC.translate(Message.getString("chat.muted.message-target")));
            event.setCancelled(true);
            return;
        }

        if (slowSet.contains(player.getUniqueId()) && plugin.getManagerHandler().getChatManager().isSlowed()) {
            player.sendMessage(CC.translate(Message.getString("chat.slow.message-target")
                    .replace("%value%", String.valueOf(plugin.getManagerHandler().getChatManager().getDelay()))
            ));
            event.setCancelled(true);
            return;
        }

        if (plugin.getManagerHandler().getChatManager().isSlowed() && !player.hasPermission("core.chat.slow.bypass")) {
            slowSet.add(player.getUniqueId());
            plugin.getServer().getScheduler().runTaskLaterAsynchronously(plugin, () -> {
                slowSet.remove(player.getUniqueId());
            }, plugin.getManagerHandler().getChatManager().getDelay() * 20);
        }

        if (user.isStaffChat() && player.hasPermission("core.command.staffchat")) {
            event.setCancelled(true);
            plugin.getManagerHandler().getRedisManager().sendPermissionMessage("core.command.staffchat", CC.translate(Message.getString("staffchat.message")
                    .replace("%server%", Message.getString("server-name"))
                    .replace("%player%", user.getColoredName()))
                    .replace("%message%", event.getMessage())
            );
        }

        if (plugin.getManagerHandler().getFileManager().getOptionsYaml().getBoolean("chat.handle-formatting")) {
            event.setFormat(CC.translate(Message.getString("chat.format.message")
                    .replace("%player%", user.getRank().getPrefix() + user.getUsername()))
                    .replace("%message%", event.getMessage())
            );
        }

    }

}
