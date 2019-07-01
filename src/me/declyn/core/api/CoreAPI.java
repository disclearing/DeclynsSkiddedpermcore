package me.declyn.core.api;

import me.declyn.core.Core;
import me.declyn.core.objects.Rank;
import me.declyn.core.objects.User;
import org.bukkit.entity.Player;

import java.util.List;

public class CoreAPI {

    private Core plugin;
    public static CoreAPI instance;

    public CoreAPI(Core plugin) {
        this.plugin = plugin;
        instance = this;
    }

    public Rank getRank(String rank) {
        return plugin.getManagerHandler().getRankManager().getRankByName(rank);
    }

    public List<Rank> getSortedRanks() {
        return plugin.getManagerHandler().getRankManager().getSortedRanks();
    }

    public User getPlayer(Player player) {
        return plugin.getManagerHandler().getUserManager().getUser(player);
    }

    public List<User> getSortedUsers() {
        return plugin.getManagerHandler().getUserManager().getSortedUsers();
    }

}
