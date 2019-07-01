package me.declyn.core.manager.impl;

import me.declyn.core.commands.*;
import me.declyn.core.manager.Manager;
import me.declyn.core.manager.ManagerHandler;
import org.bukkit.command.CommandExecutor;

public class CommandManager extends Manager {

    public CommandManager(ManagerHandler managerHandler) {
        super(managerHandler);
        load();
    }

    private void load() {
        registerCommand("alert", new AlertCommand(getPlugin()));
        registerCommand("chat", new ChatCommand(getPlugin()));
        registerCommand("clearinventory", new ClearInventoryCommand(getPlugin()));
        registerCommand("fly", new FlyCommand(getPlugin()));
        registerCommand("freeze", new FreezeCommand(getPlugin()));
        registerCommand("gamemode", new GamemodeCommand(getPlugin()));
        registerCommand("list", new ListCommand(getPlugin()));
        registerCommand("message", new MessageCommand(getPlugin()));
        registerCommand("ping", new PingCommand(getPlugin()));
        registerCommand("rank", new RankCommand(getPlugin()));
        registerCommand("reply", new ReplyCommand(getPlugin()));
        registerCommand("report", new ReportCommand(getPlugin()));
        registerCommand("request", new RequestCommand(getPlugin()));
        registerCommand("setslots", new SetSlotsCommand(getPlugin()));
        registerCommand("staffchat", new StaffChatCommand(getPlugin()));
        registerCommand("teleport", new TeleportCommand(getPlugin()));
        registerCommand("teleporthere", new TeleportHereCommand(getPlugin()));
        registerCommand("user", new UserCommand(getPlugin()));
        registerCommand("vanish", new VanishCommand(getPlugin()));
    }

    private void registerCommand(String name, CommandExecutor commandExecutor) {
        getPlugin().getCommand(name).setExecutor(commandExecutor);
    }

}
