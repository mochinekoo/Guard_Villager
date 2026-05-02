package net.mochinekoserver.guard_villager.listener;

import net.mochinekoserver.guard_villager.gui.KitSelectGUI;
import net.mochinekoserver.guard_villager.manager.KitManager;
import net.mochinekoserver.guard_villager.status.KitType;
import net.mochinekoserver.guard_villager.util.PluginUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryClickListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        var kitManager = KitManager.getInstance();
        int slot = event.getSlot();
        var view = event.getView();
        var current = event.getCurrentItem();
        var cursor = event.getCursor();
        if (KitSelectGUI.isOpenInventory(event)) {
            if (current == null) return;
            var uiList =  KitSelectGUI.UI_ITEM.keySet().stream().toList();
            if (slot < uiList.size()) {
                kitManager.setKit(player.getUniqueId(), uiList.get(slot).newInstance(player));
                PluginUtil.sendInfoMessage(player, "キットを選択しました。");
            }

            event.setCancelled(true);
        }
    }

}
