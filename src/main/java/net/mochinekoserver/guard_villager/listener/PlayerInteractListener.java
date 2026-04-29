package net.mochinekoserver.guard_villager.listener;

import net.mochinekoserver.guard_villager.gui.KitSelectGUI;
import net.mochinekoserver.guard_villager.manager.KitManager;
import net.mochinekoserver.guard_villager.status.ItemType;
import net.mochinekoserver.guard_villager.util.ItemBuilder;
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

    @EventHandler
    public void onKitInteract(PlayerInteractEvent event) {
        var player = event.getPlayer();
        var playerInv = player.getInventory();
        var mainHand = playerInv.getItemInMainHand();
        var action = event.getAction();
        var kitManager = KitManager.getInstance();
        var kit = kitManager.getKit(player.getUniqueId());
        if (action == Action.PHYSICAL) return;

        if (ItemBuilder.containType(mainHand, ItemType.KIT_ITEM)) {
            kit.runClickAction();
        }
    }
}
