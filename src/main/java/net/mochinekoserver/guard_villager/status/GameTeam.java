package net.mochinekoserver.guard_villager.status;

import org.bukkit.ChatColor;
import org.bukkit.Material;

public enum GameTeam {
    RED(ChatColor.RED, "赤"),
    BLUE(ChatColor.BLUE, "青"),
    ADMIN(ChatColor.GOLD, "運営");

    private final ChatColor color;
    private final String team_string;

    GameTeam(ChatColor color, String team_string) {
        this.color = color;
        this.team_string = team_string;
    }

    public ChatColor getColor() {
        return color;
    }

    public String getTeamString() {
        return team_string;
    }

}
