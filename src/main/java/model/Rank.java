package model;

public class Rank {
    private int rank;
    private String name;
    private int score;
    private String replay_data;
    private String time;

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
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

    public void setScore(int score) {
        this.score = score;
    }

    public String getReplay_data() {
        return replay_data;
    }

    public void setReplay_data(String replay_data) {
        this.replay_data = replay_data;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}