package net.mochinekoserver.guard_villager.listener;

import net.mochinekoserver.guard_villager.manager.KitManager;
import net.mochinekoserver.guard_villager.manager.TeamManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.UUID;

public class EntityDamageListener implements Listener {

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        var damage = event.getEntity(); //ダメージを受けた人
        var damager = event.getDamager(); //ダメージを与えた人
        var cause = event.getCause();

        if (damage instanceof Player damagePlayer && damager instanceof Player damagerPlayer) {
            var teamManager = TeamManager.getInstance();
            var damageTeam = teamManager.getJoinGameTeam(damagePlayer);
            var damagerTeam = teamManager.getJoinGameTeam(damagerPlayer);
            var IsSomeTeam = (damageTeam == damagerTeam);
            if (IsSomeTeam) {
                event.setCancelled(true);
            }
        }

        if (damager instanceof Snowball damagerSnowBall) {
            var IsGameEntity = damagerSnowBall.getMetadata("game_entity").get(0).asBoolean();
            if (!IsGameEntity) return; //ゲームエンティティでない場合は飛ばす

            if (damage instanceof Villager) { //ダメージを受けた人が村人
                event.setCancelled(true);
            }
            else {
                event.setCancelled(false);
                var playerUUIDString = damagerSnowBall.getMetadata("player_uuid").get(0).asString();
                var player = Bukkit.getPlayer(UUID.fromString(playerUUIDString));
                var kitManager = KitManager.getInstance();
                var kit = kitManager.getKit(player.getUniqueId());
                kit.setCoin(kit.getCoin() + 1);
            }
        }

    }
}
