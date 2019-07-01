package me.declyn.core.listeners;

import me.declyn.core.Core;
import me.declyn.core.objects.User;
import me.declyn.core.util.CC;
import me.declyn.core.util.Message;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinEventListener implements Listener {

    private Core plugin;

    public JoinEventListener(Core plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        event.setJoinMessage(null);
        Player player = event.getPlayer();

        User user = new User(player);
        plugin.getManagerHandler().getUserManager().createPlayerData(user);

        for (String joinMessage : Message.getStringList("join-message")) {
            player.sendMessage(CC.translate(joinMessage
                    .replace("%player%", user.getColoredName())
            ));
        }

        if (player.hasPermission("core.staff")) {
            plugin.getManagerHandler().getRedisManager().sendPermissionMessage("core.staff", CC.translate(Message.getString("staff-connections.connect")
                    .replace("%player%", user.getColoredName())
                    .replace("%server%", Message.getString("server-name"))
            ));
        }

        /**
         * Hide vanished players
         */
        for (User users : plugin.getManagerHandler().getUserManager().getUserSet()) {
            if (users.isVanished()) {
                for (Player all : plugin.getServer().getOnlinePlayers()) {
                    if (!all.hasPermission("core.command.vanish")) {
                        all.hidePlayer(plugin.getServer().getPlayer(users.getUuid()));
                    }
                }
            }
        }

    }

}
