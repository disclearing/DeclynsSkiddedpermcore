package me.declyn.core.commands;

import me.declyn.core.Core;
import me.declyn.core.objects.User;
import me.declyn.core.util.CC;
import me.declyn.core.util.Message;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GamemodeCommand implements CommandExecutor {

    private Core plugin;

    public GamemodeCommand(Core plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(CC.translate(Message.getString("general-errors.player-only")));
            return true;
        }

        if (!sender.hasPermission("core.command.gamemode")) {
            sender.sendMessage(CC.translate(Message.getString("general-errors.no-permission")));
            return true;
        }

        if (args.length == 1) {
            Player player = (Player) sender;
            User user = plugin.getManagerHandler().getUserManager().getUser(player);
            String arg = args[0].toUpperCase();
            switch (arg) {
                case "CREATIVE":
                case "C":
                case "1":
                    if (player.getGameMode() == GameMode.CREATIVE) {
                        player.sendMessage(CC.translate(Message.getString("gamemode.errors.already-gamemode")
                                .replace("%player%", user.getUsername())
                                .replace("%mode%", "creative")
                        ));
                        return true;
                    }
                    player.setGameMode(GameMode.CREATIVE);
                    player.sendMessage(CC.translate(Message.getString("gamemode.message")
                            .replace("%player%", user.getColoredName())
                            .replace("%mode%", Message.getString("gamemode.display-names.creative"))
                    ));
                    return true;
                case "SURVIVAL":
                case "S":
                case "0":
                    if (player.getGameMode() == GameMode.SURVIVAL) {
                        player.sendMessage(CC.translate(Message.getString("gamemode.errors.already-gamemode")
                                .replace("%player%", user.getUsername())
                                .replace("%mode%", "survival")
                        ));
                        return true;
                    }
                    player.setGameMode(GameMode.SURVIVAL);
                    player.sendMessage(CC.translate(Message.getString("gamemode.message")
                            .replace("%player%", user.getColoredName())
                            .replace("%mode%", Message.getString("gamemode.display-names.survival"))
                    ));
                    return true;
                case "ADVENTURE":
                case "A":
                case "2":
                    if (player.getGameMode() == GameMode.ADVENTURE) {
                        player.sendMessage(CC.translate(Message.getString("gamemode.errors.already-gamemode")
                                .replace("%player%", user.getUsername())
                                .replace("%mode%", "adventure")
                        ));
                        return true;
                    }
                    player.setGameMode(GameMode.ADVENTURE);
                    player.sendMessage(CC.translate(Message.getString("gamemode.message")
                            .replace("%player%", user.getColoredName())
                            .replace("%mode%", Message.getString("gamemode.display-names.adventure"))
                    ));
                    return true;
                default:
                    sender.sendMessage(CC.translate(Message.getString("gamemode.errors.gamemode-provided")));
                    return true;
            }
        }

        if (args.length == 2) {
            Player player = plugin.getServer().getPlayer(args[1]);
            if (player == null) {
                sender.sendMessage(CC.translate(Message.getString("general-errors.player-not-online")));
                return true;
            }
            User senderUser = plugin.getManagerHandler().getUserManager().getUser((Player) sender);
            User user = plugin.getManagerHandler().getUserManager().getUser(player);
            String arg = args[0].toUpperCase();
            switch (arg) {
                case "CREATIVE":
                case "C":
                case "1":
                    if (player.getGameMode() == GameMode.CREATIVE) {
                        sender.sendMessage(CC.translate(Message.getString("gamemode.errors.already-gamemode")
                                .replace("%player%", user.getUsername())
                                .replace("%mode%", "creative")
                        ));
                        return true;
                    }
                    player.setGameMode(GameMode.CREATIVE);
                    player.sendMessage(CC.translate(Message.getString("gamemode.message-target")
                            .replace("%player%", senderUser.getRank().getColor() + senderUser.getUsername())
                            .replace("%mode%", Message.getString("gamemode.display-names.creative"))
                    ));
                    sender.sendMessage(CC.translate(Message.getString("gamemode.message")
                            .replace("%player%", user.getColoredName())
                            .replace("%mode%", Message.getString("gamemode.display-names.creative"))
                    ));
                    return true;
                case "SURVIVAL":
                case "S":
                case "0":
                    if (player.getGameMode() == GameMode.SURVIVAL) {
                        sender.sendMessage(CC.translate(Message.getString("gamemode.errors.already-gamemode")
                                .replace("%player%", user.getUsername())
                                .replace("%mode%", "survival")
                        ));
                        return true;
                    }
                    player.setGameMode(GameMode.SURVIVAL);
                    player.sendMessage(CC.translate(Message.getString("gamemode.message-target")
                            .replace("%player%", senderUser.getRank().getColor() + senderUser.getUsername())
                            .replace("%mode%", Message.getString("gamemode.display-names.survival"))
                    ));
                    sender.sendMessage(CC.translate(Message.getString("gamemode.message")
                            .replace("%player%", user.getColoredName())
                            .replace("%mode%", Message.getString("gamemode.display-names.survival"))
                    ));
                    return true;
                case "ADVENTURE":
                case "A":
                case "2":
                    if (player.getGameMode() == GameMode.ADVENTURE) {
                        sender.sendMessage(CC.translate(Message.getString("gamemode.errors.already-gamemode")
                                .replace("%player%", user.getUsername())
                                .replace("%mode%", "adventure")
                        ));
                        return true;
                    }
                    player.setGameMode(GameMode.SURVIVAL);
                    player.sendMessage(CC.translate(Message.getString("gamemode.message-target")
                            .replace("%player%", senderUser.getRank().getColor() + senderUser.getUsername())
                            .replace("%mode%", Message.getString("gamemode.display-names.adventure"))
                    ));
                    sender.sendMessage(CC.translate(Message.getString("gamemode.message")
                            .replace("%player%", user.getColoredName())
                            .replace("%mode%", Message.getString("gamemode.display-names.adventure"))
                    ));
                    return true;
                default:
                    sender.sendMessage(CC.translate(Message.getString("gamemode.errors.gamemode-provided")));
                    return true;
            }
        }

        sender.sendMessage(CC.translate(Message.getString("gamemode.usage")));
        return true;
    }

}
