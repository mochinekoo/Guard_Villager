package net.mochinekoserver.guard_villager.manager;

import net.mochinekoserver.guard_villager.status.GameStatus;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import javax.annotation.Nonnull;

public abstract class GameBase {

    public static final int DEFAULT_GAME_TIME = 60*15;
    public static final int DEFAULT_COUNT_TIME = 10;

    protected final String name; //ゲームの名前
    protected int countTime; //ゲームのカウント
    protected int time; //ゲームの経過時間
    protected GameStatus status; //ゲームの状態
    protected BukkitTask mainTask; //ゲームのメインタスク

    public GameBase(String name) {
        this.name = name;
        this.time = DEFAULT_GAME_TIME;
        this.countTime = DEFAULT_COUNT_TIME;
        this.status = GameStatus.WAITING;
    }

    /**
     * ゲームの名前。何かしらの識別用。
     */
    public String getName() {
        return name;
    }

    /**
     * ゲームの時間を取得する関数。
     */
    public int getTime() {
        return time;
    }

    /**
     * ゲームの時間を設定する関数。
     */
    public void setTime(int time) {
        this.time = time;
    }

    /**
     *　ゲームの状態を返す関数
     */
    public GameStatus getStatus() {
        return status;
    }

    /**
     * ゲームの状態を設定する関数
     */
    public void setStatus(@Nonnull GameStatus status) {
        this.status = status;
    }

    /**
     * ゲームの初期化関数。 {@link JavaPlugin#onEnable()} などで呼び出す。
     */
    public abstract void initialize();

    /**
     * ゲームのデータを初期値に戻す関数。{@link JavaPlugin#onDisable()} などで呼び出す。
     */
    public abstract void reset();

    /**
     * ゲームを開始する関数。
     * @return 成功した場合は0を返し、失敗した場合は0以外を返す。
     */
    public abstract int start();

    /**
     * ゲームを停止させる関数。
     */
    public abstract void stop();

    /**
     * ゲームを開始できるか返す関数。
     */
    public abstract boolean canStart();

}
