package net.mochinekoserver.guard_villager.listener;

import net.mochinekoserver.guard_villager.data.AreaData;
import net.mochinekoserver.guard_villager.manager.GameManager;
import net.mochinekoserver.guard_villager.manager.JsonManager;
import net.mochinekoserver.guard_villager.status.FileType;
import net.mochinekoserver.guard_villager.util.LocationUtil;
import net.mochinekoserver.guard_villager.util.PluginUtil;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockBreakPlaceListener implements Listener {

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        var player = event.getPlayer();
        var world = player.getWorld();
        var breakBlock = event.getBlock();
        var breakLoc = breakBlock.getLocation();
        if (IsGuardBlock(player, breakBlock)) {
            event.setCancelled(true);
            PluginUtil.sendInfoMessage(player, "ブロックを破壊することはできません。");
        }
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        var player = event.getPlayer();
        var world = player.getWorld();
        var placeBlock = event.getBlock();
        if (IsGuardBlock(player, placeBlock)) {
            event.setCancelled(true);
            PluginUtil.sendInfoMessage(player, "ブロックを設置することはできません。");
        }
    }

    private boolean IsGuardBlock(Player player, Block block) {
        var world = player.getWorld();
        var loc = block.getLocation();
        AreaData json = (AreaData) JsonManager.getInstance(FileType.AREA_DATA).getDeserializedJson();
        for (AreaData.GuardData guardData : json.guard_data.values()) {
            var startLoc = LocationUtil.convertLocation(guardData.start, world);
            var endLoc = LocationUtil.convertLocation(guardData.end, world);
            if (LocationUtil.IsAABB(loc, startLoc, endLoc)) {
                return true;
            }
        }
        return false;
    }

}
