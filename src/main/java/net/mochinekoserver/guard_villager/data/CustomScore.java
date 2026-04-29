package net.mochinekoserver.guard_villager.data;

import org.bukkit.scoreboard.Objective;

public class CustomScore {

    private String name = null;
    private final int score;
    private final Objective objective;

    public CustomScore(Objective objective, String name, int score) {
        this.name = name;
        this.score = score;
        this.objective = objective;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public Objective getObjective() {
        return objective;
    }

    public void updateScore(String name) {
        if (this.name != null) {
            objective.getScoreboard().resetScores(this.name);
        }
        this.name = name;
        objective.getScore(name).setScore(score);
    }

}
