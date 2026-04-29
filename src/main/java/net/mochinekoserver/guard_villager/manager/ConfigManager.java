package net.mochinekoserver.guard_villager.manager;

import net.mochinekoserver.guard_villager.Main;
import net.mochinekoserver.guard_villager.status.GameTeam;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;
import java.util.Locale;

public class ConfigManager {

    //インスタンス
    private static ConfigManager instance;
    private final Main plugin;
    private final FileConfiguration configuration;

    private ConfigManager() {
        this.plugin = Main.getPlugin(Main.class);
        this.configuration = plugin.getConfig();
    }

    public World getGameWorld() {
        String world_name = configuration.getString("spawn.world_name");
        return Bukkit.getWorld(world_name);
    }

    public Location getLobby() {
        int x = configuration.getInt("spawn.lobby.x");
        int y = configuration.getInt("spawn.lobby.y");
        int z = configuration.getInt("spawn.lobby.z");
        return new Location(getGameWorld(), x, y, z);
    }

    public Location getTeamSpawnLocation(GameTeam team) {
        //パス例：spawn.red.x
        String team_path = "spawn." + team.name().toLowerCase(Locale.ROOT);
        int x = configuration.getInt(team_path + ".x");
        int y = configuration.getInt(team_path + ".y");
        int z = configuration.getInt(team_path + ".z");
        return new Location(getGameWorld(), x, y, z);
    }

    public int getVillagerSize() {
        String path = "game.villager_size";
        return configuration.getInt(path);
    }

    public Location getSpawnPoint(int index) {
        String path = "game.spawn_point";
        List<List<Integer>> spawnPointList = (List<List<Integer>>) configuration.getList(path);
        List<Integer> spawnPoint = spawnPointList.get(index);
        int x = spawnPoint.get(0);
        int y = spawnPoint.get(1);
        int z = spawnPoint.get(2);
        return new Location(getGameWorld(), x, y, z);
    }

    public static ConfigManager getInstance() {
        if (instance == null) {
            instance = new ConfigManager();
        }
        return instance;
    }

}
