package net.mochinekoserver.guard_villager.util;

import net.mochinekoserver.guard_villager.status.ItemType;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

public class PluginItemFactory {

    public static ItemStack createKitSelector() {
        return new ItemBuilder(Material.NETHER_STAR, ChatColor.GOLD + "キット選択", Arrays.asList(ChatColor.AQUA + "右クリックでキット選択"))
                .setProperty(ItemType.UNTRANSFERABLE)
                .buildItemStack();
    }
}
