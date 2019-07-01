package me.declyn.core.manager;

import me.declyn.core.Core;
import me.declyn.core.manager.impl.*;
import me.declyn.core.util.CC;

public class ManagerHandler {

    private Core plugin;

    private LicenseManager licenseManager;
    private FileManager fileManager;
    private ListenerManager listenerManager;
    private CommandManager commandManager;
    private SQLManager sqlManager;
    private RankManager rankManager;
    private UserManager userManager;
    private PermissionManager permissionManager;
    private RedisManager redisManager;
    private ChatManager chatManager;

    public ManagerHandler(Core plugin) {
        this.plugin = plugin;
        load();
    }

    private void load() {
        //licenseManager = new LicenseManager(this);
        fileManager = new FileManager(this);
        listenerManager = new ListenerManager(this);
        commandManager = new CommandManager(this);
        sqlManager = new SQLManager(this);
        rankManager = new RankManager(this);
        userManager = new UserManager(this);
        permissionManager = new PermissionManager(this);
        redisManager = new RedisManager(this);
        chatManager = new ChatManager(this);
    }

    public Core getPlugin() {
        return plugin;
    }

    public FileManager getFileManager() {
        return fileManager;
    }

    public ListenerManager getListenerManager() {
        return listenerManager;
    }

    public CommandManager getCommandManager() {
        return commandManager;
    }

    public SQLManager getSQLManager() {
        return sqlManager;
    }

    public RankManager getRankManager() {
        return rankManager;
    }

    public UserManager getUserManager() {
        return userManager;
    }

    public PermissionManager getPermissionManager() {
        return permissionManager;
    }

    public RedisManager getRedisManager() {
        return redisManager;
    }

    public ChatManager getChatManager() {
        return chatManager;
    }

    public void sendConsoleMessage(String message) {
        getPlugin().getServer().getConsoleSender().sendMessage(CC.translate(message));
    }

}