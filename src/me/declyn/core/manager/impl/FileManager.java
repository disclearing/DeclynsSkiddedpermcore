package me.declyn.core.manager.impl;

import me.declyn.core.manager.Manager;
import me.declyn.core.manager.ManagerHandler;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class FileManager extends Manager {

    private File folder;

    private File optionsFile, messagesFile, permissionsFile;
    private YamlConfiguration optionsYaml, messagesYaml, permissionsYaml;

    public FileManager(ManagerHandler managerHandler) {
        super(managerHandler);
        setupDataFolder();
        setupFiles();
    }

    private void setupDataFolder() {
        this.folder = getPlugin().getDataFolder();
        if (!this.folder.exists()) {
            this.folder.mkdirs();
        }
    }

    private void setupFiles() {
        this.optionsFile = new File(this.folder, "options.yml");
        if (!this.optionsFile.exists()) {
            getPlugin().saveResource("options.yml", false);
        }
        this.optionsYaml = YamlConfiguration.loadConfiguration(this.optionsFile);

        this.messagesFile = new File(this.folder, "messages.yml");
        if (!this.messagesFile.exists()) {
            getPlugin().saveResource("messages.yml", false);
        }
        this.messagesYaml = YamlConfiguration.loadConfiguration(this.messagesFile);

        this.permissionsFile = new File(this.folder, "permissions.yml");
        if (!this.permissionsFile.exists()) {
            getPlugin().saveResource("permissions.yml", false);
        }
        this.permissionsYaml = YamlConfiguration.loadConfiguration(this.permissionsFile);
    }

    public void saveOptionsFile() {
        try {
            this.optionsYaml.save(this.optionsFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveMessagesFile() {
        try {
            this.messagesYaml.save(this.messagesFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void savePermissionsFile() {
        try {
            this.permissionsYaml.save(this.permissionsFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public YamlConfiguration getOptionsYaml() {
        return optionsYaml;
    }

    public YamlConfiguration getMessagesYaml() {
        return messagesYaml;
    }

    public YamlConfiguration getPermissionsYaml() {
        return permissionsYaml;
    }

    public void replaceMessages() {
        getPlugin().saveResource("messages.yml", true);
        saveMessagesFile();
    }

}
