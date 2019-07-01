package me.declyn.core.manager.impl;

import me.declyn.core.listeners.ChatEventListener;
import me.declyn.core.listeners.JoinEventListener;
import me.declyn.core.listeners.QuitEventListener;
import me.declyn.core.manager.Manager;
import me.declyn.core.manager.ManagerHandler;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

public class ListenerManager extends Manager {

    public ListenerManager(ManagerHandler managerHandler) {
        super(managerHandler);
        load();
    }

    private void load() {
        registerListener(new JoinEventListener(getPlugin()));
        registerListener(new QuitEventListener(getPlugin()));
        registerListener(new ChatEventListener(getPlugin()));
    }

    private void registerListener(Listener listener) {
        Bukkit.getPluginManager().registerEvents(listener, getPlugin());
    }

}
