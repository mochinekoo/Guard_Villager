package net.mochinekoserver.guard_villager.manager;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import net.mochinekoserver.guard_villager.Main;
import net.mochinekoserver.guard_villager.status.GameStatus;
import net.mochinekoserver.guard_villager.util.PluginUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameManager extends GameBase {

    private static GameManager instance = null;
    private final Map<String, Villager> villagerMap = new HashMap<>();

    public GameManager() {
        super("Game");
    }

    public static GameManager getInstance() {
        if (instance == null) {
            instance = new GameManager();
        }
        return instance;
    }

    @Override
    public void initialize() {
    }

    @Override
    public void reset() {
        this.mainTask.cancel();
        this.mainTask = null;
        this.time = 0;
    }

    @Override
    public int start() {
        if (status != GameStatus.WAITING) return -1;
        this.mainTask = new BukkitRunnable() {
            @Override
            public void run() {
                if (status == GameStatus.WAITING || status == GameStatus.COUNTING) {
                    updateWaitCountScene();
                }
                else if (status == GameStatus.RUNNING) {
                    updateRunningScene();
                }
                else if (status == GameStatus.ENDING) {
                    updateEndingScene();
                }
            }
        }.runTaskTimer(Main.getPlugin(Main.class), 0L, 20L);

        return 0;
    }

    private void updateWaitCountScene() {
        if (status != GameStatus.WAITING && status != GameStatus.COUNTING) return;
        var configManager = ConfigManager.getInstance();
        var teamManager = TeamManager.getInstance();

        if (countTime <= 0) { //0秒以下
            //ゲーム開始の処理
            PluginUtil.sendGlobalInfoMessage("ゲーム開始!");
            status = GameStatus.RUNNING;
            teamManager.assignTeam(); //チームを割り当てる
            for (Player online : Bukkit.getOnlinePlayers()) {
                var villagerCount = configManager.getVillagerSize();
                var world = configManager.getGameWorld();
                for (int n = 0; n < villagerCount; n++) { //村人をスポーンさせる処理
                    var loc = configManager.getSpawnPoint(n);
                    var villager = world.spawn(loc, Villager.class);
                    villager.setProfession(Villager.Profession.NONE);
                    villagerMap.put(String.valueOf(n), villager);
                }
                var scoreboardManager = ScoreboardManager.getInstance(online.getUniqueId());
                var joinTeam = teamManager.getJoinGameTeam(online);
                var teamSpawn = configManager.getTeamSpawnLocation(joinTeam);
                scoreboardManager.setScoreboard(); //スコアボードを設定する
                online.teleport(teamSpawn); //チームのスポーンにテレポート
            }
        }
        else {
            //カウントの処理
            status = GameStatus.COUNTING;
            PluginUtil.sendGlobalInfoMessage("ゲーム開始まであと" + countTime + "秒です。");
            countTime--;
        }
    }

    private void updateRunningScene() {
        if (status != GameStatus.RUNNING) return;
        time--;

        for (Player player : Bukkit.getOnlinePlayers()) { //全プレイヤーの処理
            var spigot = player.spigot();
            var kitManager = KitManager.getInstance();
            var kit = kitManager.getKit(player.getUniqueId());
            if (kit == null) return;
            spigot.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("現在のコイン：" + kit.getCoin()));
        }

        if (time <= 0) { //ゲーム時間が0秒以下になった場合
            status = GameStatus.ENDING;
        }
    }

    private void updateEndingScene() {
        if (status != GameStatus.ENDING) return;
        PluginUtil.sendGlobalInfoMessage("ゲーム終了!");
        reset();
    }

    @Override
    public void stop() {

    }

    @Override
    public boolean canStart() {
        return true;
    }
}
