package net.mochinekoserver.guard_villager.listener;

import net.mochinekoserver.guard_villager.manager.GameManager;
import net.mochinekoserver.guard_villager.manager.TeamManager;
import net.mochinekoserver.guard_villager.status.GameStatus;
import net.mochinekoserver.guard_villager.status.GameTeam;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerChatEvent;

import java.util.Set;

public class PlayerChatListener implements Listener {

    @EventHandler
    public void onChat(PlayerChatEvent event) {
        var message = event.getMessage();
        var gameManager = GameManager.getInstance();
        var teamManager = TeamManager.getInstance();
        var player = event.getPlayer();
        GameTeam chat_team = teamManager.getJoinGameTeam(player); //チャットした人の所属チーム
        if (chat_team == null)  return; //所属チームがnullの場合は何もしない

        Set<Player> recipient_list = event.getRecipients();
        recipient_list.removeIf(recipient_player -> {
            GameTeam recipient_team = teamManager.getJoinGameTeam(recipient_player); //受信者の所属チーム
            return chat_team != recipient_team; //チャットした人と受信者が違うチームの場合は、受信者から削除する（removeIfでfalseを返す）
        });

        if (gameManager.getStatus() == GameStatus.RUNNING) {
            //フォーマット：[チームチャット] [赤]<mochi__neko>: メッセージ
            String format = String.format("%s[チームチャット] %s[%s]%s<%s>:%s",
                    ChatColor.GRAY, chat_team.getColor(), chat_team.getTeamString(), ChatColor.RESET, player.getName(), event.getMessage());
            event.setFormat(format);
        }
    }

}
