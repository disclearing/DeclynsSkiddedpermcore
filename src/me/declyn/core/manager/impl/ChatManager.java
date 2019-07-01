package me.declyn.core.manager.impl;

import me.declyn.core.manager.Manager;
import me.declyn.core.manager.ManagerHandler;

public class ChatManager extends Manager {

    private boolean isMuted;
    private int delay;

    public ChatManager(ManagerHandler managerHandler) {
        super(managerHandler);
        isMuted = false;
    }

    public boolean isMuted() {
        return isMuted;
    }

    public int getDelay() {
        return delay;
    }

    public boolean isSlowed() {
        return delay != 0;
    }

    public void setMuted(boolean muted) {
        isMuted = muted;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

}
