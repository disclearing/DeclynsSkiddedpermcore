package me.declyn.core.objects;

import org.bukkit.entity.Player;

import java.util.UUID;

public class User {

    private UUID uuid;
    private String username;
    private Rank rank;
    private String ipAddress;
    private boolean isVanished;
    private boolean isStaffChat;
    private boolean isFrozen;

    public User(Player player) {
        this.uuid = player.getUniqueId();
        this.username = player.getName();
        this.rank = null;
        this.ipAddress = player.getAddress().getHostName();
        this.isVanished = false;
        this.isStaffChat = false;
        this.isFrozen = false;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getUsername() {
        return username;
    }

    public Rank getRank() {
        return rank;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public boolean isVanished() {
        return isVanished;
    }

    public boolean isStaffChat() {
        return isStaffChat;
    }

    public boolean isFrozen() {
        return isFrozen;
    }

    public boolean hasRank() {
        return rank != null;
    }

    public void setRank(Rank rank) {
        this.rank = rank;
    }

    public void setVanished(boolean vanished) {
        isVanished = vanished;
    }

    public void setStaffChat(boolean staffChat) {
        isStaffChat = staffChat;
    }

    public void setFrozen(boolean frozen) {
        isFrozen = frozen;
    }

    public int getPlayerSortedWeight() {
        return rank.getWeight();
    }

    public String getColoredName() {
        return rank.getColor() + getUsername();
    }

}
