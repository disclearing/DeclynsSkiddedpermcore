package me.declyn.core.manager.impl;

import me.declyn.core.manager.Manager;
import me.declyn.core.manager.ManagerHandler;
import me.declyn.core.util.License;
import org.bukkit.Bukkit;

public class LicenseManager extends Manager {

    public LicenseManager(ManagerHandler managerHandler) {
        super(managerHandler);
        license();
    }

    private void license() {
        License.ValidationType validationType = new License(
                managerHandler.getFileManager().getOptionsYaml().getString("protection.license"),
                "https://license.tradez.pw/verify.php",
                getPlugin()
        ).isValid();

        if (validationType != License.ValidationType.VALID) {
            sendConsoleMessage("&7------------------------------");
            sendConsoleMessage("&2Core &7(Version: &a1.1&7)");
            sendConsoleMessage("&cLicense key not found, plugin disabling");
            sendConsoleMessage("&cBought this plugin but don't have a license key?");
            sendConsoleMessage("&cAdd declyn#0001 on discord.");
            sendConsoleMessage("&7&m------------------------------");
            Bukkit.getPluginManager().disablePlugin(getPlugin());
            return;
        }

        sendConsoleMessage("&7------------------------------");
        sendConsoleMessage("&2Core &7(Version: &a1.1&7)");
        sendConsoleMessage("&aLicense key authenticated");
        sendConsoleMessage("&7Thank your for purchasing &2Core&7!");
        sendConsoleMessage("&7Any issues? Feel free to contact declyn#0001 on discord.");
        sendConsoleMessage("&7------------------------------");
        return;
    }

}
