/*
 * HighScore.java includes the HighScore class which stores a high score
 * This class does not use any swing components or features
 * Author: Emily Pazienza
 * Created: 09/04/2018
 */

public class HighScore implements java.io.Serializable {

    private int time;
    private String name;

    /**
     * Creates a new HighScore object
     * @param t - the time
     * @param n - the name of the winner
     */
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
