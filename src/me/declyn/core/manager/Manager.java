package me.declyn.core.manager;

import me.declyn.core.Core;
import me.declyn.core.util.CC;

import java.sql.Connection;

public class Manager {

    public ManagerHandler managerHandler;

    protected Manager(ManagerHandler managerHandler) {
        this.managerHandler = managerHandler;
    }

    protected Core getPlugin() {
        return managerHandler.getPlugin();
    }

    protected void sendConsoleMessage(String string) {
        getPlugin().getServer().getConsoleSender().sendMessage(CC.translate(string));
    }

    protected Connection getSQLConnection() {
        return managerHandler.getSQLManager().getConnection();
    }

}