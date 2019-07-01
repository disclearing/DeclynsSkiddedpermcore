package me.declyn.core.manager.impl;

import me.declyn.core.manager.Manager;
import me.declyn.core.manager.ManagerHandler;
import me.declyn.core.objects.Rank;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class RankManager extends Manager {

    private Set<String> ranksNameSet;
    private Map<String, Rank> ranksMap;

    public RankManager(ManagerHandler managerHandler) {
        super(managerHandler);
        ranksNameSet = new HashSet<>();
        ranksMap = new HashMap<>();
        if (getSQLConnection() != null) {
            loadRanks();
            setupDefault();
            setupRanks();
        }
    }

    private void loadRanks() {
        try {
            PreparedStatement statement = getSQLConnection().prepareStatement("SELECT * FROM ranks");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                ranksNameSet.add(resultSet.getString("NAME"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createRank(String name) {
        try {
            PreparedStatement statement = getSQLConnection().prepareStatement("INSERT INTO ranks VALUES (?, '&f', '&f', '0', 'false')");
            statement.setString(1, name);
            statement.executeUpdate();
            ranksNameSet.add(name);
            Rank rank = new Rank(name);
            ranksMap.put(name, rank);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void setupDefault() {
        try {
            PreparedStatement statement = getSQLConnection().prepareStatement("SELECT * FROM ranks");
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next() || resultSet.getString("DEFAULT").equals("false") && !ranksNameSet.contains("Member")) {
                createDefaultRank();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private void createDefaultRank() {
        try {
            PreparedStatement statement = getSQLConnection().prepareStatement("INSERT INTO ranks VALUES (?, ?, ?, ?, ?)");
            statement.setString(1, "Member");
            statement.setString(2, "&f");
            statement.setString(3, "&f");
            statement.setString(4, "0");
            statement.setString(5, "true");
            statement.executeUpdate();
            Rank rank = new Rank("Member");
            rank.setFirstJoin(true);
            ranksNameSet.add("Member");
            ranksMap.put("Member", rank);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Rank getDefaultRank() {
        try {
            PreparedStatement statement = getSQLConnection().prepareStatement("SELECT * FROM ranks");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                if (resultSet.getString("DEFAULT").equals("true")) {
                    return getRankByName(resultSet.getString("NAME"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setDefaultRank(String name) {
        try {
            PreparedStatement statement = getSQLConnection().prepareStatement("UPDATE ranks SET `DEFAULT`=? WHERE NAME=?");
            statement.setString(1, "false");
            statement.setString(2, getDefaultRank().getName());
            getDefaultRank().setFirstJoin(false);
            statement.executeUpdate();

            PreparedStatement statement1 = getSQLConnection().prepareStatement("UPDATE ranks SET `DEFAULT`=? WHERE NAME=?");
            statement1.setString(1, "true");
            statement1.setString(2, name);
            statement1.executeUpdate();
            getDefaultRank().setFirstJoin(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteRank(String name) {
        try {
            PreparedStatement statement = getSQLConnection().prepareStatement("DELETE FROM ranks WHERE NAME=?");
            statement.setString(1, name);
            statement.executeUpdate();
            ranksNameSet.remove(name);
            ranksMap.remove(name);
            managerHandler.getFileManager().getPermissionsYaml().set(name, null);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setPrefix(String rank, String prefix) {
        try {
            PreparedStatement statement = getSQLConnection().prepareStatement("UPDATE ranks SET PREFIX=? WHERE NAME=?");
            statement.setString(1, prefix);
            statement.setString(2, rank);
            statement.executeUpdate();
            getRankByName(rank).setPrefix(prefix);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setColor(String rank, String color) {
        try {
            PreparedStatement statement = getSQLConnection().prepareStatement("UPDATE ranks SET COLOR=? WHERE NAME=?");
            statement.setString(1, color);
            statement.setString(2, rank);
            statement.executeUpdate();
            getRankByName(rank).setColor(color);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setWeight(String rank, String weight) {
        try {
            PreparedStatement statement = getSQLConnection().prepareStatement("UPDATE ranks SET WEIGHT=? WHERE NAME=?");
            statement.setString(1, weight);
            statement.setString(2, rank);
            statement.executeUpdate();
            getRankByName(rank).setWeight(Integer.parseInt(weight));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getPrefix(String rank) {
        try {
            PreparedStatement statement = getSQLConnection().prepareStatement("SELECT * FROM ranks WHERE NAME=?");
            statement.setString(1, rank);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next() && !resultSet.getString("PREFIX").equals("none")) {
                return resultSet.getString("PREFIX");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "&f";
    }

    public String getColor(String rank) {
        try {
            PreparedStatement statement = getSQLConnection().prepareStatement("SELECT * FROM ranks WHERE NAME=?");
            statement.setString(1, rank);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next() && !resultSet.getString("COLOR").equals("none")) {
                return resultSet.getString("COLOR");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "&f";
    }

    public int getWeight(String rank) {
        try {
            PreparedStatement statement = getSQLConnection().prepareStatement("SELECT * FROM ranks WHERE NAME=?");
            statement.setString(1, rank);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next() && !resultSet.getString("WEIGHT").equals("none")) {
                return Integer.parseInt(resultSet.getString("WEIGHT"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public Rank getRankByName(String name) {
        for (Rank ranks : ranksMap.values()) {
            if (ranks.getName().matches(name)) {
                return ranks;
            }
        }
        return null;
    }

    public List<Rank> getSortedRanks() {
        List<Rank> sortedRanks = new ArrayList<>(ranksMap.values());
        sortedRanks.sort(Comparator.comparingInt(Rank::getWeight));
        Collections.reverse(sortedRanks);
        return sortedRanks;
    }

    private void setupRanks() {
        for (String rank : ranksNameSet) {
            Rank ranks = new Rank(rank);
            ranks.setPrefix(getPrefix(rank));
            ranks.setColor(getColor(rank));
            ranks.setWeight(getWeight(rank));
            ranks.setFirstJoin(false);
            ranksMap.put(rank, ranks);
        }
    }

    public Set<String> getRanksNameSet() {
        return ranksNameSet;
    }

    public Map<String, Rank> getRanksMap() {
        return ranksMap;
    }

}

