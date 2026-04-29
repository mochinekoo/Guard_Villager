package net.mochinekoserver.guard_villager.listener;

import net.mochinekoserver.guard_villager.gui.KitSelectGUI;
import net.mochinekoserver.guard_villager.util.PluginItemFactory;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerInteractListener implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        var player = event.getPlayer();
        var playerInv = player.getInventory();
        var mainHand = playerInv.getItemInMainHand();
        var action = event.getAction();
        if (action == Action.PHYSICAL) return;

        if (mainHand.isSimilar(PluginItemFactory.createKitSelector())) {
            KitSelectGUI.openInventory(player);
        }
    }
}
