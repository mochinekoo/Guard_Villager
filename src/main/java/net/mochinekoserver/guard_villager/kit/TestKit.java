package net.mochinekoserver.guard_villager.kit;

import net.mochinekoserver.guard_villager.manager.ConfigManager;
import net.mochinekoserver.guard_villager.manager.KitBase;
import net.mochinekoserver.guard_villager.status.ItemType;
import net.mochinekoserver.guard_villager.status.KitType;
import net.mochinekoserver.guard_villager.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Snowball;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.UUID;

public class TestKit extends KitBase {

    public TestKit(UUID uuid) {
        super(uuid, KitType.TEST_KIT);
    }

    public void runClickAction() {
        var configManager = ConfigManager.getInstance();
        var world = configManager.getGameWorld();
        var player = Bukkit.getPlayer(uuid);
        var loc = player.getEyeLocation().clone();
        var direction = loc.getDirection().normalize().clone();
        var snowball = world.spawn(loc, Snowball.class);
        snowball.setVelocity(direction.multiply(1.5));
    }

    @Override
    public void release() {

    }

    public void addKitItem() {
        var player = Bukkit.getPlayer(uuid);
        var inv = player.getInventory();
        inv.clear();
        inv.addItem(createItem());
    }

    public static ItemStack createItem() {
        return new ItemBuilder(Material.STICK, "テストキット", Arrays.asList("テストキット。"))
                .setProperty(ItemType.KIT_ITEM)
                .buildItemStack();
    }
}
