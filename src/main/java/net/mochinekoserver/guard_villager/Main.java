package net.mochinekoserver.guard_villager;

import net.mochinekoserver.guard_villager.command.*;
import net.mochinekoserver.guard_villager.listener.*;
import net.mochinekoserver.guard_villager.manager.GameManager;
import net.mochinekoserver.guard_villager.manager.JsonManager;
import net.mochinekoserver.guard_villager.manager.ScoreboardManager;
import net.mochinekoserver.guard_villager.manager.TeamManager;
import net.mochinekoserver.guard_villager.status.FileType;
import net.mochinekoserver.guard_villager.util.PluginItemFactory;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        initCommand();
        initListener();
        saveDefaultConfig();
        for (Player online : Bukkit.getOnlinePlayers()) {
            var scoreboardManager = ScoreboardManager.getInstance(online.getUniqueId());
            scoreboardManager.setScoreboard();
            var inventory = online.getInventory();
            inventory.addItem(PluginItemFactory.createKitSelector());
        }
        for (FileType fileType : FileType.values()) {
            JsonManager.getInstance(fileType).createJson();
        }

        getLogger().info("プラグインが有効になりました。");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        var gameManager = GameManager.getInstance();
        var teamManager = TeamManager.getInstance();
        gameManager.reset();
        teamManager.emptyTeam();
        for (Player online : Bukkit.getOnlinePlayers()) {
            var scoreboardManager = ScoreboardManager.getInstance(online.getUniqueId());
            scoreboardManager.release();
        }
    }

    private void initCommand() {
        var startstop_command = new GameStartStopCommand();
        var team_command = new GameTeamCommand();
        var kit_command = new GameKitCommand();
        var player_command = new PlayerCommand();
        var chat_command = new ChatCommand();

        getCommand("game_start").setExecutor(startstop_command);
        getCommand("game_stop").setExecutor(startstop_command);
        getCommand("game_team").setExecutor(team_command);
        getCommand("game_kit").setExecutor(kit_command);
        getCommand("suicide").setExecutor(player_command);
        getCommand("global").setExecutor(chat_command);

        getCommand("game_team").setTabCompleter(team_command);
        getCommand("game_kit").setTabCompleter(kit_command);
    }

    private void initListener() {
        var pluginManager = getServer().getPluginManager();

        //ブロック
        pluginManager.registerEvents(new BlockBreakPlaceListener(), this);

        //プレイヤー
        pluginManager.registerEvents(new PlayerChatListener(), this);
        pluginManager.registerEvents(new PlayerDeathListener(), this);
        pluginManager.registerEvents(new PlayerJoinQuitListener(), this);
        pluginManager.registerEvents(new PlayerRespawnListener(), this);
        pluginManager.registerEvents(new PlayerInteractListener(), this);

        //other
        pluginManager.registerEvents(new InventoryClickListener(), this);
        pluginManager.registerEvents(new EntityDamageListener(), this);

    }
}
