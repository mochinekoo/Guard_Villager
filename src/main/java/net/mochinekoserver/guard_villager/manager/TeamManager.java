package net.mochinekoserver.guard_villager.manager;

import net.mochinekoserver.guard_villager.status.GameTeam;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.*;

public class TeamManager {

    private static TeamManager instance = null;
    private final Map<GameTeam, Team> team_map;

    private TeamManager() {
        this.team_map = new HashMap<>();
        Scoreboard new_scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        for (GameTeam team : GameTeam.values()) {
            Team registerTeam = new_scoreboard.registerNewTeam(team.name());
            registerTeam.setDisplayName(team.getTeamString());
            registerTeam.setColor(team.getColor());
            registerTeam.setPrefix(team.getColor() + "");
            team_map.put(team, registerTeam);
        }
    }

    public static TeamManager getInstance() {
        if (instance == null) {
            instance = new TeamManager();
        }
        return instance;
    }

    /**
     * プレイヤーを特定のチームに参加させる関数
     * @param team 参加させたいチーム
     * @param player 参加させるプレイヤー
     */
    public boolean joinTeam(@NonNull GameTeam team, @NonNull Player player) {
        if (!isJoinTeam(player)) {
            Team board_team = getConvertBoardTeam(team);
            board_team.addEntry(player.getName());
            player.setPlayerListName(team.getColor() + player.getName());
            return true;
        }
        return false;
    }

    /**
     * プレイヤーをランダムに参加させる関数
     */
    public GameTeam randomJoinTeam(@NonNull Player player) {
        if (!isJoinTeam(player)) {
            List<GameTeam> gameTeamList = new ArrayList<>(Arrays.stream(GameTeam.values()).toList());
            Collections.shuffle(gameTeamList);
            return gameTeamList.get(0);
        }
        return getJoinGameTeam(player);
    }

    /**
     * チームを割り当てる関数
     */
    public void assignTeam() {
        emptyTeam();
        List<Player> playerList = new ArrayList<>(Bukkit.getOnlinePlayers());
        Collections.shuffle(playerList);

        for (int i = 0; i < playerList.size(); i++) {
            switch (i % GameTeam.values().length) {
                case 0 -> joinTeam(GameTeam.RED, playerList.get(i));
                case 1 -> joinTeam(GameTeam.BLUE, playerList.get(i));
            }
        }
    }

    /**
     * チームを空にする関数
     */
    public void emptyTeam() {
        for (GameTeam gameTeam : GameTeam.values()) {
            Team board_team = getConvertBoardTeam(gameTeam);
            for (String entry : board_team.getEntries()) {
                OfflinePlayer player = Bukkit.getOfflinePlayer(entry);
                leaveTeam(player);
            }
        }
    }

    /**
     * プレイヤーをチームから抜けさせる関数
     * @param player 抜けさせたいプレイヤー
     */
    public boolean leaveTeam(@NonNull OfflinePlayer player) {
        if (isJoinTeam(player)) {
            Team board_team = getJoinBoardTeam(player);
            board_team.removeEntry(player.getName());
            if (player.isOnline()) {
                player.getPlayer().setPlayerListName(ChatColor.RESET + player.getName());
            }
            return true;
        }
        return false;
    }

    /**
     * プレイヤーがどこかのチームに参加しているかを確認する関数
     * @param player 確認したいプレイヤー
     * @return 参加している場合はtrueを返す。
     */
    public boolean isJoinTeam(@NonNull OfflinePlayer player) {
        for (Map.Entry<GameTeam, Team> entry : team_map.entrySet()) {
            Team board_team = entry.getValue();
            if (board_team.getEntries().contains(player.getName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * ゲームチームから、スコアボードのチームを取得するもの
     * @param team 取得したいチーム
     */
    public Team getConvertBoardTeam(GameTeam team) {
        return team_map.get(team);
    }

    /**
     * スコアボードのチームから、ゲームチームを取得するもの
     * @param team 取得したいチーム
     */
    public GameTeam getConvertGameTeam(Team team) {
        for (Map.Entry<GameTeam, Team> entry : team_map.entrySet()) {
            GameTeam game_team = entry.getKey();
            Team board_team = entry.getValue();
            if (board_team.getName().equalsIgnoreCase(team.getName())) {
                return game_team;
            }
        }
        return null;
    }

    /**
     * プレイヤーがどのチームに所属しているかを返す
     * @return スコアボードのチームを返す。見つからない場合はnull
     */
    public Team getJoinBoardTeam(OfflinePlayer player) {
        for (Map.Entry<GameTeam, Team> entry : team_map.entrySet()) {
            Team board_team = entry.getValue();

            Set<String> team_player_set = board_team.getEntries();
            for (String team_player_name : team_player_set) {
                OfflinePlayer team_player = Bukkit.getOfflinePlayer(team_player_name);
                if (player.equals(team_player)) {
                    return board_team;
                }
            }
        }
        return null;
    }

    /**
     * プレイヤーがどのチームに所属しているかを返す
     * @return ゲームチームを返す。見つからない場合はnull
     */
    public GameTeam getJoinGameTeam(OfflinePlayer player) {
        Team board_team = getJoinBoardTeam(player);
        if (board_team != null) {
            return getConvertGameTeam(board_team);
        }

        return null;
    }

}

