package me.declyn.core.manager.impl;

import me.declyn.core.manager.Manager;
import me.declyn.core.manager.ManagerHandler;
import me.declyn.core.objects.Rank;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;

import java.util.Arrays;

public class PermissionManager extends Manager {

    public PermissionManager(ManagerHandler managerHandler) {
        super(managerHandler);
        setupPermissionsConfig();
    }

    private void setupPermissionsConfig() {
        for (Rank rank : managerHandler.getRankManager().getSortedRanks()) {
            managerHandler.getFileManager().getPermissionsYaml().addDefault(rank.getName() + ".permissions", Arrays.asList(""));
            managerHandler.getFileManager().getPermissionsYaml().addDefault(rank.getName() + ".inherits", Arrays.asList(""));
        }
    }

    public void addPermission(Player player, String permission) {
        PermissionAttachment attachment = player.addAttachment(getPlugin());
        if (attachment == null) {
            return;
        }
        attachment.setPermission(permission, true);
        player.recalculatePermissions();
    }

    public void removePermissions(Player player, String permission) {
        PermissionAttachment attachment = player.addAttachment(getPlugin());
        if (attachment == null) {
            return;
        }
        attachment.unsetPermission(permission);
        player.recalculatePermissions();
    }

}
