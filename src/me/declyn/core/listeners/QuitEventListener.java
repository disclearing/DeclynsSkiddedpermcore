package me.declyn.core.listeners;

import me.declyn.core.Core;
import me.declyn.core.objects.User;
import me.declyn.core.util.CC;
import me.declyn.core.util.Message;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class QuitEventListener implements Listener {

    private Core plugin;

    public QuitEventListener(Core plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        event.setQuitMessage(null);
        Player player = event.getPlayer();

        User user = plugin.getManagerHandler().getUserManager().getUser(player);
        if (player.hasPermission("core.staff")) {
            plugin.getManagerHandler().getRedisManager().sendPermissionMessage("core.staff", CC.translate(Message.getString("staff-connections.disconnect")
                    .replace("%player%", user.getColoredName())
                    .replace("%server%", Message.getString("server-name"))
            ));
        }

        /**
         * Show vanished players
         */
        for (User users : plugin.getManagerHandler().getUserManager().getUserSet()) {
            if (users.isVanished()) {
                for (Player all : plugin.getServer().getOnlinePlayers()) {
                    all.showPlayer(plugin.getServer().getPlayer(users.getUuid()));
                }
            }
        }

        plugin.getManagerHandler().getUserManager().saveAndDeletePlayerData(plugin.getManagerHandler().getUserManager().getUser(player));
    }

}
