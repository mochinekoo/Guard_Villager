package net.mochinekoserver.guard_villager.listener;

import net.mochinekoserver.guard_villager.Main;
import net.mochinekoserver.guard_villager.manager.ConfigManager;
import net.mochinekoserver.guard_villager.manager.GameManager;
import net.mochinekoserver.guard_villager.manager.TeamManager;
import net.mochinekoserver.guard_villager.status.GameStatus;
import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class PlayerRespawnListener implements Listener {

    @EventHandler
    public void onRespawn(PlayerRespawnEvent event) {
        var player = event.getPlayer();
        var gameManager = GameManager.getInstance();
        var status = gameManager.getStatus();
        var teamManager = TeamManager.getInstance();
        var joinTeam = teamManager.getJoinGameTeam(player);
        var configManager = ConfigManager.getInstance();
        var lobby = configManager.getLobby();

        if (status == GameStatus.RUNNING) {
            if (joinTeam == null) {
                event.setRespawnLocation(lobby); //runningで、参加チームがない場合もロビーに飛ばす
                return;
            }
            var teamSpawn = configManager.getTeamSpawnLocation(joinTeam);
            event.setRespawnLocation(teamSpawn);
            player.setGameMode(GameMode.SPECTATOR);
            new BukkitRunnable() {
                int respawnTime = 3;
                @Override
                public void run() {
                    if (respawnTime <= 0) {
                        player.teleport(teamSpawn);
                        player.setGameMode(GameMode.ADVENTURE);
                        this.cancel();
                    }
                    else {
                        player.sendTitle("リスポーンまであと" + respawnTime + "秒", "");
                        respawnTime--;
                    }
                }
            }.runTaskTimer(Main.getPlugin(Main.class), 0L, 20L);
        }
        else {
            event.setRespawnLocation(lobby);
        }

    }

}
