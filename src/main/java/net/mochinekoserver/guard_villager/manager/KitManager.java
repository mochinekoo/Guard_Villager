package net.mochinekoserver.guard_villager.manager;

import net.mochinekoserver.guard_villager.status.KitType;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class KitManager {

    private static KitManager instance;
    private final static Map<UUID, KitBase> kit_map = new HashMap<>();

    private final static Map<KitType, Boolean> kit_showingMap = new HashMap<>();
    private final static Map<KitType, Boolean> kit_canUseMap = new HashMap<>();

    private KitManager() {}

    public static KitManager getInstance() {
        if (instance == null) instance = new KitManager();
        return instance;
    }

    public void setKit(@Nonnull UUID uuid, @Nonnull KitBase kit) {
        OfflinePlayer player = Bukkit.getOfflinePlayer(uuid);
        kit_map.put(uuid, kit);
        if (player.isOnline()) {
            kit_map.get(player.getUniqueId()).addKitItem();
        }
    }

    public void unSetKit(@Nonnull UUID uuid) {
        kit_map.get(uuid).release();
        kit_map.remove(uuid);
    }

    public List<KitBase> getKitList() {
        return kit_map.values().stream().toList();
    }

    /**
     * キットが一般モードから表示されるかを返す関数。
     * @param type キット
     * @apiNote falseでUI等からは非表示になる。falseにしても、見えなくなるだけなのでコマンドからは利用可能。
     */
    public boolean isKitShowing(KitType type) {
        return kit_showingMap.getOrDefault(type, true);
    }

    public void setKitShowing(@Nonnull KitType type, boolean value) {
        kit_showingMap.put(type, value);
    }

    /**
     * キットが使える状態かを返す関数。
     * @param type キット
     * @return キットが使えない場合はfalseを返す。
     */
    public boolean isKitCanUse(KitType type) {
        return kit_canUseMap.getOrDefault(type, true);
    }

    public  void setKitCanUse(@Nonnull KitType type, boolean value) {
        kit_canUseMap.put(type, value);
    }

    /**
     * 現在のキットを取得する関数
     * @param uuid 取得したいプレイヤー
     */
    public KitBase getKit(@Nonnull UUID uuid) {
        Player player = Bukkit.getPlayer(uuid);
        return kit_map.getOrDefault(uuid, null);
    }

}
