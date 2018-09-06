import javax.swing.*;
import java.awt.*;
import java.io.*;

public class HighScoreList extends JPanel {

    private HighScore beginner, intermediate, expert;

    public HighScoreList() {
        super(new GridLayout(0, 3));

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

        this.draw();
    }

    private void draw() {
        this.removeAll();

        String time, name;
        this.add(new JLabel("Beginner:"));
        if (this.beginner == null) {
            time = "None";
            name = "None";
        } else {
            time = this.beginner.getTime() + " seconds";
            name = this.beginner.getName();
        }
        this.add(new JLabel(time));
        this.add(new JLabel(name));

        this.add(new JLabel("Intermediate:"));
        if (this.intermediate == null) {
            time = "None";
            name = "None";
        } else {
            time = this.intermediate.getTime() + " seconds";
            name = this.intermediate.getName();
        }
        this.add(new JLabel(time));
        this.add(new JLabel(name));

        this.add(new JLabel("Expert:"));
        if (this.expert == null) {
            time = "None";
            name = "None";
        } else {
            time = this.expert.getTime() + " seconds";
            name = this.expert.getName();
        }
        this.add(new JLabel(time));
        this.add(new JLabel(name));
    }

    private void serialize(HighScore hs, String path) {
        try {
            FileOutputStream fileOut =
                    new FileOutputStream(path);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(hs);
            out.close();
            fileOut.close();
        } catch (IOException i) {
            i.printStackTrace();
        }
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
        this.draw();
    }

    public void logIntermediate(int time, String name) {
        this.intermediate = new HighScore(time, name);
        this.serialize(this.intermediate, "highScores/intermediate.ser");
        this.draw();
    }

    public void logExpert(int time, String name) {
        this.expert = new HighScore(time, name);
        this.serialize(this.expert, "highScores/expert.ser");
        this.draw();
    }

    public void resetScores() {
        if (this.beginner != null) {
            this.delete("highScores/beginner.ser");
            this.beginner = null;
        }
        if (this.intermediate != null) {
            this.delete("highScores/intermediate.ser");
            this.intermediate = null;
        }
        if (this.expert != null) {
            this.delete("highScores/expert.ser");
            this.expert = null;
        }
        this.draw();
    }

    private void delete(String pathName) {
        File file = new File(pathName);
        file.delete();
    }

    public HighScore getBeginner() {
        return this.beginner;
    }
    public HighScore getIntermediate() {
        return this.intermediate;
    }
    public HighScore getExpert() {
        return this.expert;
    }

    public HighScore get(String diff) {
        switch(diff) {
            case "beginner":
                return this.beginner;
            case "intermediate":
                return this.intermediate;
            case "expert":
                return this.expert;
            default:
                return null;
        }
    }
}
