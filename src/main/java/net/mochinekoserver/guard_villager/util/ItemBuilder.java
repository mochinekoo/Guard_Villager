package net.mochinekoserver.guard_villager.util;

import net.mochinekoserver.guard_villager.status.ItemType;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class ItemBuilder {

    private final ItemStack itemStack;

    public ItemBuilder(@Nonnull Material material) {
        this.itemStack = new ItemStack(material);
    }

    public ItemBuilder(@Nonnull Material material, String displayName, List<String> lore) {
        this.itemStack = new ItemStack(material);
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(displayName);
        meta.setLore(lore);
        itemStack.setItemMeta(meta);
    }

    public ItemBuilder setProperty(ItemType... types) {
        ItemMeta meta = itemStack.getItemMeta();
        List<String> getLore = meta.getLore() == null ? new ArrayList<>() : new ArrayList<>(meta.getLore());
        for (ItemType itemtype : types) {
            getLore.add(ChatColor.GOLD + itemtype.name());
        }
        meta.setLore(getLore);
        itemStack.setItemMeta(meta);
        return this;
    }

    public ItemStack buildItemStack() {
        return itemStack;
    }

    public static boolean containType(ItemStack itemStack, ItemType type) {
        ItemMeta meta = itemStack.getItemMeta();
        if (meta == null) return false;
        if (meta.getLore() == null) return false;
        for (String lore : meta.getLore()) {
            if (lore.contains(type.name())) {
                return true;
            }
        }
        return false;
    }

}
