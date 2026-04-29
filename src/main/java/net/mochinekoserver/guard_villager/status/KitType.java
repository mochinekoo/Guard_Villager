package net.mochinekoserver.guard_villager.status;

import net.mochinekoserver.guard_villager.kit.TestKit;
import net.mochinekoserver.guard_villager.manager.KitBase;
import org.bukkit.OfflinePlayer;

import javax.annotation.Nonnull;
import java.util.UUID;

public enum KitType {

    TEST_KIT(TestKit.class);

    private final Class<? extends KitBase> kit_class;

    KitType(Class<? extends KitBase> kit_class) {
        this.kit_class = kit_class;
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
}
