package net.mochinekoserver.guard_villager.util;

import org.bukkit.Location;
import org.bukkit.World;

import javax.annotation.Nonnull;

public class LocationUtil {

    /**
     * AABB判定。範囲内にいるかに使う
     * @param checkLoc 判定したい座標
     * @param startLoc エリアの開始地点
     * @param endLoc エリアの最終地点
     * @return checkLocがエリア内の場合はtrueを返す。
     */
    public static boolean IsAABB(Location checkLoc, @Nonnull Location startLoc, @Nonnull Location endLoc) {
        if (checkLoc == null) return false;
        int minX = Math.min(startLoc.getBlockX(), endLoc.getBlockX());
        int minY = Math.min(startLoc.getBlockY(), endLoc.getBlockY());
        int minZ = Math.min(startLoc.getBlockZ(), endLoc.getBlockZ());
        int maxX = Math.max(startLoc.getBlockX(), endLoc.getBlockX());
        int maxY = Math.max(startLoc.getBlockY(), endLoc.getBlockY());
        int maxZ = Math.max(startLoc.getBlockZ(), endLoc.getBlockZ());

        int x = checkLoc.getBlockX();
        int y = checkLoc.getBlockY();
        int z = checkLoc.getBlockZ();

        return (minX <= x && maxX >= x) && (minY <= y && maxY >= y) && (minZ <= z && maxZ >= z);
    }

    public static Location convertLocation(int[] locArray, World world) {
        return new Location(world, locArray[0], locArray[1], locArray[2]);
    }
}
