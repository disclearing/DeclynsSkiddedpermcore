package me.declyn.core.manager.impl;

import me.declyn.core.manager.Manager;
import me.declyn.core.manager.ManagerHandler;
import me.declyn.core.objects.Rank;
import me.declyn.core.objects.User;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class UserManager extends Manager {

    private Set<User> userSet;
    private Map<Player, Player> replyMap;

    public UserManager(ManagerHandler managerHandler) {
        super(managerHandler);
        userSet = new HashSet<>();
        replyMap = new HashMap<>();
    }

    public boolean playerSetDataExists(User user) {
        return userSet.contains(user);
    }

    public boolean playerSQLDataExists(User user) {
        try {
            PreparedStatement statement = getSQLConnection().prepareStatement("SELECT * FROM playerdata WHERE UUID=?");
            statement.setString(1, user.getUuid().toString());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean playerSQLDataExists(String name) {
        try {
            PreparedStatement statement = getSQLConnection().prepareStatement("SELECT * FROM playerdata WHERE NAME=?");
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void createPlayerData(User user) {
        try {
            userSet.add(user);
            if (!playerSQLDataExists(user)) {
                PreparedStatement statement = getSQLConnection().prepareStatement("INSERT INTO playerdata VALUES (?, ?, ?, ?)");
                statement.setString(1, user.getUsername());
                statement.setString(2, user.getUuid().toString());
                statement.setString(3, user.getIpAddress());
                statement.setString(4, getPlugin().getManagerHandler().getRankManager().getDefaultRank().getName());
                statement.executeUpdate();
            }
            user.setRank(getRank(user));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveAndDeletePlayerData(User user) {
        try {
            PreparedStatement statement = getSQLConnection().prepareStatement("UPDATE playerdata SET NAME=?, RANK=? WHERE UUID=?");
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getRank().getName());
            statement.setString(3, user.getUuid().toString());
            statement.executeUpdate();
            userSet.remove(user);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setOfflinePlayerRank(String name, Rank rank) {
        try {
            PreparedStatement statement = getSQLConnection().prepareStatement("UPDATE playerdata SET RANK=? WHERE NAME=?");
            statement.setString(1, rank.getName());
            statement.setString(2, name);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public Rank getRank(User user) {
        try {
            PreparedStatement statement = getSQLConnection().prepareStatement("SELECT * FROM playerdata WHERE UUID=?");
            statement.setString(1, user.getUuid().toString());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next() && !resultSet.getString("RANK").equals("none")) {
                return managerHandler.getRankManager().getRankByName(resultSet.getString("RANK"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return managerHandler.getRankManager().getDefaultRank();
    }

    public Rank getRank(String name) {
        try {
            PreparedStatement statement = getSQLConnection().prepareStatement("SELECT * FROM playerdata WHERE NAME=?");
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next() && !resultSet.getString("RANK").equals("none")) {
                return managerHandler.getRankManager().getRankByName(resultSet.getString("RANK"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return managerHandler.getRankManager().getDefaultRank();
    }

    public User getUserByName(String name) {
        for (User user : userSet) {
            if (user.getUsername().matches(name)) {
                return user;
            }
        }
        return null;
    }

    public User getUser(Player player) {
        for (User user : userSet) {
            if (user.getUsername().matches(player.getName())) {
                return user;
            }
        }
        return null;
    }

    public Set<User> getUserSet() {
        return userSet;
    }

    public List<User> getSortedUsers() {
        List<User> sortedUsers = new ArrayList<>(userSet);
        sortedUsers.sort(Comparator.comparingInt(User::getPlayerSortedWeight));
        Collections.reverse(sortedUsers);
        return sortedUsers;
    }

    public Map<Player, Player> getReplyMap() {
        return replyMap;
    }
}
