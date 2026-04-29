package net.mochinekoserver.guard_villager.util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class PluginUtil {

    public static final String TEXT_INFO = ChatColor.AQUA + "[Game] " + ChatColor.RESET;
    public static final String TEXT_ERROR = ChatColor.RED + "[エラー] " + ChatColor.RESET;

    public PluginUtil() {
        throw new UnsupportedOperationException("ユーティリティクラスのため、インスタンス化できません。");
    }

    public static void sendGlobalInfoMessage(String message) {
        Bukkit.broadcastMessage(TEXT_INFO + message);
    }

    public static void sendGlobalErrorMessage(String message) {
        Bukkit.broadcastMessage(TEXT_ERROR + message);
    }

    public static void sendInfoMessage(CommandSender sender, String message) {
        sender.sendMessage(TEXT_INFO + message);
    }

    public static void sendErrorMessage(CommandSender sender, String message) {
        sender.sendMessage(TEXT_ERROR + message);
    }

}
