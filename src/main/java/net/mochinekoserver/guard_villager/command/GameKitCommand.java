package net.mochinekoserver.guard_villager.command;

import net.mochinekoserver.guard_villager.manager.KitBase;
import net.mochinekoserver.guard_villager.manager.KitManager;
import net.mochinekoserver.guard_villager.status.GameTeam;
import net.mochinekoserver.guard_villager.status.KitType;
import net.mochinekoserver.guard_villager.util.PluginUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class GameKitCommand implements CommandExecutor, TabExecutor {

    @Override
    public boolean onCommand(CommandSender send, Command cmd, String s, String[] args) {
        if (cmd.getName().equalsIgnoreCase("game_kit")) {
            var kitManager = KitManager.getInstance();
            if (args[0].equalsIgnoreCase("select")) {
                var player = Bukkit.getPlayer(args[0]);
                var kit = KitType.valueOf(args[1]);
                kitManager.setKit(player.getUniqueId(), kit.newInstance(player));
                PluginUtil.sendInfoMessage(send, "キットが設定されました。");
            }
            else if (args[0].equalsIgnoreCase("unselect")) {
                var player = Bukkit.getPlayer(args[0]);
                var kit = KitType.valueOf(args[1]);
                kitManager.unSetKit(player.getUniqueId());
                PluginUtil.sendInfoMessage(send, "キットが解除されました。");
            }
            else if (args[0].equalsIgnoreCase("list")) {
                StringBuilder builder = new StringBuilder();
                builder.append("=========\n");
                for (KitBase kitBase : kitManager.getKitList()) {
                    builder.append(kitBase.getUUID().toString() + ":" + kitBase.getKitType().name() + "\n");
                }
                builder.append("=========");
                PluginUtil.sendInfoMessage(send, builder.toString());
            }
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender send, Command cmd, String s, String[] args) {
        if (cmd.getName().equalsIgnoreCase("game_team")) {
            if (args.length == 1) {
                return Arrays.asList("select", "unselect", "list");
            }

            if (args.length == 2) {
                if (args[0].equalsIgnoreCase("select") || args[0].equalsIgnoreCase("unselect")) {
                    return Bukkit.getOnlinePlayers().stream()
                            .map(Player::getName)
                            .filter(filter -> filter.startsWith(args[1]))
                            .collect(Collectors.toList());
                }
            }

            if (args.length == 2) {
                if (args[0].equalsIgnoreCase("select") || args[0].equalsIgnoreCase("unselect")) {
                    return Arrays.stream(KitType.values())
                            .map(KitType::name)
                            .filter(filter -> filter.startsWith(args[1]))
                            .collect(Collectors.toList());
                }
            }
        }
        return List.of();
    }
}
