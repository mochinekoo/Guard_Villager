package net.mochinekoserver.guard_villager.listener;

import net.mochinekoserver.guard_villager.manager.TeamManager;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

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

    }
}
