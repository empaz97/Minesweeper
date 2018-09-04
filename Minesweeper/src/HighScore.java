public class HighScore implements java.io.Serializable {

    private int time;
    private String name;

    public HighScore(int t, String n) {
        this.time = t;
        this.name = n;
    }

    public int getTime() {
        return this.time;
    }

    public String getName() {
        return this.name;
    }
}
