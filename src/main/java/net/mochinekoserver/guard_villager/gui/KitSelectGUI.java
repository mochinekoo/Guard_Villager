package net.mochinekoserver.guard_villager.gui;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import javax.annotation.Nonnull;

public class KitSelectGUI {

    public static final int SIZE = 9*6;
    public static final String TITLE = ChatColor.DARK_AQUA + "キットを選択してください。";

    public static void openInventory(@Nonnull Player player) {
        var inventory = Bukkit.createInventory(null, SIZE, TITLE);
        player.openInventory(inventory);
    }

    public static boolean isOpenInventory(InventoryClickEvent event) {
        if (event == null) return false;
        var inv = event.getView();
        return inv.getTitle().equalsIgnoreCase(TITLE);
    }

}
