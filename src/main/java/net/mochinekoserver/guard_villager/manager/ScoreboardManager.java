package net.mochinekoserver.guard_villager.manager;

import net.mochinekoserver.guard_villager.Main;
import net.mochinekoserver.guard_villager.data.CustomScore;
import net.mochinekoserver.guard_villager.status.GameStatus;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import javax.annotation.Nonnull;
import java.io.Closeable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class ScoreboardManager {

    private static final Map<UUID, ScoreboardManager> board_map = new HashMap<>();
    private static final String SCOREBOARD_NAME = "Guard_Villager";
    private static final String DISPLAY_NAME = ChatColor.AQUA + "Guard_Villager";

    //スコアボード
    private final UUID uuid;
    private final Objective objective;
    private BukkitTask task;
    private Map<Integer, CustomScore> score_map;

    private ScoreboardManager(UUID uuid) {
        Scoreboard new_scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        this.uuid = uuid;
        this.objective = new_scoreboard.registerNewObjective(SCOREBOARD_NAME, "dummy", DISPLAY_NAME);
        this.score_map = new HashMap<>();

        board_map.put(uuid, this);
    }

    /**
     * スコアボードインスタンスを取得する関数
     * @param uuid スコアボードを取得したいプレイヤー
     */
    public static ScoreboardManager getInstance(@Nonnull UUID uuid) {
        if (!board_map.containsKey(uuid)) {
            board_map.put(uuid, new ScoreboardManager(uuid));
        }
        return board_map.get(uuid);
    }

    /**
     * インスタンスを開放する関数
     */
    public void release() {
        task.cancel();
        task = null;
        score_map.clear();
        board_map.remove(uuid);
    }

    /**
     * スコアを取得する関数
     * @param score スコアを取得したい場所
     */
    public CustomScore getScore(int score) {
        if (!score_map.containsKey(score)) {
            score_map.put(score, new CustomScore(getObjective(), null, score));
        }

        return score_map.get(score);
    }

    /**
     * スコアボードを設定する関数
     */
    public void setScoreboard() {
        Objective player_obj = getObjective();
        var gameManager = GameManager.getInstance();
        var gameStatus = gameManager.getStatus();
        resetScore();
        player_obj.setDisplaySlot(DisplaySlot.SIDEBAR);

        if (gameStatus == GameStatus.WAITING) {
            //ゲーム開始前
            player_obj.getScore("===============").setScore(30);
            player_obj.getScore(" ").setScore(29);
            player_obj.getScore( ChatColor.GOLD + "ゲーム開始待機中...").setScore(28);
            player_obj.getScore("  ").setScore(27);
            // player_obj.getScore("現在の人数：" + ).setScore(26);
            player_obj.getScore("   ").setScore(25);
            player_obj.getScore("============").setScore(24);
        }
        else if (gameStatus == GameStatus.RUNNING) {
            //ゲーム中
            player_obj.getScore("===============").setScore(30);
            player_obj.getScore("  ").setScore(29);
            // player_obj.getScore("残り時間: " + time).setScore(28);
            //player_obj.getScore(ChatColor.RED + "赤：" + ChatColor.GOLD + "").setScore(27);
            //player_obj.getScore(ChatColor.BLUE + "青：" + ChatColor.GOLD + "").setScore(26);
            player_obj.getScore(" ").setScore(25);
            player_obj.getScore("============").setScore(24);
        }
        else if (gameStatus == GameStatus.ENDING) {
            //ゲーム終了
            player_obj.getScore("===============").setScore(30);
            player_obj.getScore("  ").setScore(29);
            player_obj.getScore("ゲーム終了!!").setScore(28);
            // player_obj.getScore(ChatColor.RED + "赤：" + ChatColor.GOLD).setScore(27);
            // player_obj.getScore(ChatColor.BLUE + "青：" + ChatColor.GOLD).setScore(26);
            player_obj.getScore(" ").setScore(25);
            player_obj.getScore("============").setScore(24);
        }

        updateScoreboard();
    }

    /**
     * スコアボードを更新する関数
     * @apiNote 手動で呼び出す必要なし
     */
    private void updateScoreboard() {
        if (task == null) {
            this.task = new BukkitRunnable() {
                @Override
                public void run() {
                    var gameManager = GameManager.getInstance();
                    var gameStatus = gameManager.getStatus();
                    if (gameStatus == GameStatus.WAITING) {
                        getScore(26).updateScore(ChatColor.GOLD + "現在の人数：" + Bukkit.getOnlinePlayers().size());
                    }
                    else if (gameStatus == GameStatus.RUNNING) {
                        getScore(28).updateScore("残り時間：" + String.format("%02d:%02d", gameManager.getTime() / 60, gameManager.getTime() % 60));
                    }
                    else if (gameStatus == GameStatus.ENDING) {
                        //空白
                    }

                    //スコアボードセット
                    objective.setDisplaySlot(DisplaySlot.SIDEBAR);
                    var player = Bukkit.getPlayer(uuid);
                    if (player != null) {
                        player.setScoreboard(objective.getScoreboard());
                    }
                }
            }.runTaskTimer(Main.getPlugin(Main.class), 0L, 2L);
        }
    }

    /**
     * スコアボードをリセットする関数
     * @apiNote 手動で呼び出す必要なし
     */
    public void resetScore() {
        Set<String> scores = getScoreboard().getEntries();
        for (String score : scores) {
            getScoreboard().resetScores(score);
        }
    }

    public Objective getObjective() {
        return objective;
    }

    public Scoreboard getScoreboard() {
        return objective.getScoreboard();
    }
}
