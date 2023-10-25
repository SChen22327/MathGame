public class Player {

    private String name;
    private int score;
    private int streak;

    public Player(String name) {
        this.name = name;
        score = 0;
        streak = 0;
    }

    public int getScore() {
        return score;
    }

    public String getName() {
        return name;
    }
    public int getStreak() {
        return streak;
    }

    public void incrementScore() {
        score++;
    }
    public void incrementStreak() {
        streak++;
    }
    public void reset() {
        score = 0;
    }
}