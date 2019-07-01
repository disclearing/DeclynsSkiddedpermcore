package me.declyn.core.util;

import me.declyn.core.Core;

import java.util.List;

public class Message {

    public static String getString(String path) {
        return Core.getInstance().getManagerHandler().getFileManager().getMessagesYaml().getString(path);
    }

    public static List<String> getStringList(String path) {
        return Core.getInstance().getManagerHandler().getFileManager().getMessagesYaml().getStringList(path);
    }

}
