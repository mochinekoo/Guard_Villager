package net.mochinekoserver.guard_villager.listener;

import net.mochinekoserver.guard_villager.manager.ConfigManager;
import net.mochinekoserver.guard_villager.manager.GameManager;
import net.mochinekoserver.guard_villager.manager.ScoreboardManager;
import net.mochinekoserver.guard_villager.manager.TeamManager;
import net.mochinekoserver.guard_villager.status.GameStatus;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerJoinQuitListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        var player = event.getPlayer();
        var gameManager = GameManager.getInstance();
        var scoreboardManager = ScoreboardManager.getInstance(player.getUniqueId());
        var teamManager = TeamManager.getInstance();
        var configManager = ConfigManager.getInstance();
        scoreboardManager.setScoreboard();

        if (gameManager.getStatus() == GameStatus.RUNNING) {
            var joinTeam = teamManager.getJoinGameTeam(player);
            if (joinTeam == null) {
                teamManager.randomJoinTeam(player); //ランダムにチームに参加させる
            }
            var teamSpawn = configManager.getTeamSpawnLocation(joinTeam);
            player.teleport(teamSpawn);
        }
        else {
            var lobbyLoc = configManager.getLobby();
            player.teleport(lobbyLoc);
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        var player = event.getPlayer();
        var gameManager = GameManager.getInstance();
        var scoreboardManager = ScoreboardManager.getInstance(player.getUniqueId());
        scoreboardManager.release();
    }

}
