package net.mochinekoserver.guard_villager.command;

import net.mochinekoserver.guard_villager.Main;
import net.mochinekoserver.guard_villager.manager.GameManager;
import net.mochinekoserver.guard_villager.util.PluginUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.event.Event;

import java.util.List;

public class GameStartStopCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender send, Command cmd, String label, String[] args) {
        var gameManager = GameManager.getInstance();
        if (cmd.getName().equalsIgnoreCase("game_start")) {
            PluginUtil.sendInfoMessage(send, "ゲームを開始しています...");
            var runResult = gameManager.start();
            if (runResult != 0) {
                PluginUtil.sendInfoMessage(send, "問題が発生しました。（コード：" + runResult + ")");
            }
        }
        else if (cmd.getName().equalsIgnoreCase("game_stop")) {
            PluginUtil.sendInfoMessage(send, "ゲームを停止しています...");
            gameManager.stop();
        }
        return false;
    }

}
