import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class HighScoreList extends JPanel {

    private HighScore beginner, intermediate, expert;

    public HighScoreList() {
        super();

        File directory = new File("highScores");
        if (! directory.exists()){
            directory.mkdirs();
            this.beginner = null;
            this.intermediate = null;
            this.expert = null;
        } else {
            this.beginner = this.deserialize("highScores/beginner.ser");
            this.intermediate = this.deserialize("highScores/intermediate.ser");
            this.expert = this.deserialize("highScores/expert.ser");
        }



    }

    private void serialize(HighScore hs, String path) {

    }

    private HighScore deserialize(String path) {
        File serFile = new File(path);
        if (! serFile.exists()) {
            return null;
        } else {
            Object o;
            try {
                FileInputStream fileIn = new FileInputStream(path);
                ObjectInputStream in = new ObjectInputStream(fileIn);
                o = in.readObject();
                in.close();
                fileIn.close();
                return (HighScore) o;
            } catch (IOException i) {
                i.printStackTrace();
                return null;
            } catch (ClassNotFoundException c) {
                System.out.println("Class not found");
                c.printStackTrace();
                return null;
            }
        }
    }

    public void logBeginner(int time, String name) {
        this.beginner = new HighScore(time, name);
        this.serialize(this.beginner, "highScores/beginner.ser");
    }

    public void logIntermediate(int time, String name) {
        this.intermediate = new HighScore(time, name);
        this.serialize(this.intermediate, "highScores/intermediate.ser");
    }

    public void logExpert(int time, String name) {
        this.expert = new HighScore(time, name);
        this.serialize(this.expert, "highScores/expert.ser");
    }
}
