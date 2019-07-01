package me.declyn.core;

import me.declyn.core.api.CoreAPI;
import me.declyn.core.manager.ManagerHandler;
import org.bukkit.plugin.java.JavaPlugin;

public class Core extends JavaPlugin {

    private static Core instance;
    private ManagerHandler managerHandler;
    private CoreAPI coreAPI;

    @Override
    public void onEnable() {
        instance = this;
        managerHandler = new ManagerHandler(this);
        coreAPI = new CoreAPI(this);
    }

    public static Core getInstance() {
        return instance;
    }

    public ManagerHandler getManagerHandler() {
        return managerHandler;
    }

}
