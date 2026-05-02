package net.mochinekoserver.guard_villager.status;

import net.mochinekoserver.guard_villager.kit.TestKit;
import net.mochinekoserver.guard_villager.manager.KitBase;
import net.mochinekoserver.guard_villager.util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.UUID;

public enum KitType {

    TEST_KIT("テストキット", TestKit.class,
            new ItemBuilder(Material.STICK, "テストキット", Arrays.asList("テストキット。"))
                    .setProperty(ItemType.KIT_ITEM)
                    .buildItemStack());

    private final String name;
    private final Class<? extends KitBase> kit_class;
    private final ItemStack viewItem;

    KitType(String name, Class<? extends KitBase> kit_class, ItemStack viewItem) {
        this.name = name;
        this.kit_class = kit_class;
        this.viewItem = viewItem;
    }

    public String getName() {
        return name;
    }

    public Class<? extends KitBase> getKitClass() {
        return kit_class;
    }

    public KitBase newInstance(@Nonnull OfflinePlayer player) {
        try {
            return kit_class.getDeclaredConstructor(UUID.class).newInstance(player.getUniqueId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 外部に表示用のアイテムを取得する関数。
     * @return ItemStackとして返す。
     * @apiNote UIなどの表示用に使う。
     */
    public ItemStack getViewItem() {
        return viewItem;
    }
}
