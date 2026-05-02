package net.mochinekoserver.guard_villager.gui;

import net.mochinekoserver.guard_villager.manager.KitManager;
import net.mochinekoserver.guard_villager.status.KitType;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class KitSelectGUI {

    static KitManager kitManager = KitManager.getInstance();
    public static Map<KitType, ItemStack> UI_ITEM =
            Arrays.stream(KitType.values())
                    .filter(kitType -> kitManager.isKitCanUse(kitType) && kitManager.isKitShowing(kitType))
                    .collect(Collectors.toMap(
                            kitType -> kitType,
                            KitType::getViewItem
                    ));

    public static final int SIZE = 9*6;
    public static final String TITLE = ChatColor.DARK_AQUA + "キットを選択してください。";

    public static void openInventory(@Nonnull Player player) {
        var inventory = Bukkit.createInventory(null, SIZE, TITLE);
        for (ItemStack item : UI_ITEM.values()) {
            inventory.addItem(item);
        }
        player.openInventory(inventory);
    }

    public static boolean isOpenInventory(InventoryClickEvent event) {
        if (event == null) return false;
        var inv = event.getView();
        return inv.getTitle().equalsIgnoreCase(TITLE);
    }

}
