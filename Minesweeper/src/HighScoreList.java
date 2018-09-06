/*
 * HighScoreList.java includes the HighScoreList class which is a JPanel
 * displaying the high score information for the game difficulties
 * Author: Emily Pazienza
 * Created: 09/04/2018
 */

import javax.swing.*;
import java.awt.*;
import java.io.*;

public class HighScoreList extends JPanel {

    // the high scores for each difficulty level
    private HighScore beginner, intermediate, expert;

    /**
     * Creates a new HighScoreList object
     */
    public HighScoreList() {
        super(new GridLayout(0, 3));

        File directory = new File("highScores");
        if (! directory.exists()){
            // if dir doesn't exist, make it and set all difficulties to null
            directory.mkdirs();
            this.beginner = null;
            this.intermediate = null;
            this.expert = null;
        } else {
            // if dir exists, try to deserialize all the scores
            this.beginner = this.deserialize("highScores/beginner.ser");
            this.intermediate = this.deserialize("highScores/intermediate.ser");
            this.expert = this.deserialize("highScores/expert.ser");
        }

        // create the visuals of the panel
        this.draw();
    }

    /**
     * Draws the visuals of the JPanel
     */
    private void draw() {
        // remove all old components
        this.removeAll();

        String time, name;

        this.add(new JLabel("Beginner:"));
        if (this.beginner == null) { // nothing yet
            time = "None";
            name = "None";
        } else {
            time = this.beginner.getTime() + " seconds";
            name = this.beginner.getName();
        }
        this.add(new JLabel(time));
        this.add(new JLabel(name));

        this.add(new JLabel("Intermediate:"));
        if (this.intermediate == null) { // nothing yet
            time = "None";
            name = "None";
        } else {
            time = this.intermediate.getTime() + " seconds";
            name = this.intermediate.getName();
        }
        this.add(new JLabel(time));
        this.add(new JLabel(name));

        this.add(new JLabel("Expert:"));
        if (this.expert == null) { // nothing yet
            time = "None";
            name = "None";
        } else {
            time = this.expert.getTime() + " seconds";
            name = this.expert.getName();
        }
        this.add(new JLabel(time));
        this.add(new JLabel(name));
    }

    /**
     * Serializes/saves the given high score
     * @param hs - the HighScore to save off
     * @param path - the path to save the score at
     */
    private void serialize(HighScore hs, String path) {
        // try to serialize and catch exceptions
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

    /**
     * Deserializes/loads the high score at the given path
     * @param path - the path to the file to deserialize
     * @return the HighScore that was deserialized from the path
     */
    private HighScore deserialize(String path) {
        File serFile = new File(path);
        if (! serFile.exists()) { // if it doesn't exist, return null
            return null;
        } else {
            Object o;
            // try to deserialize and catch exceptions
            try {
                FileInputStream fileIn = new FileInputStream(path);
                ObjectInputStream in = new ObjectInputStream(fileIn);
                o = in.readObject();
                in.close();
                fileIn.close();

                // case to HighScore before returning
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

    /**
     * logBeginner logIntermediate and logExpert all act the same
     * They all create and save a high score with the given stats to the correct location
     * @param time
     * @param name
     */
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

    /**
     * Deletes all current high scores and removes their files
     */
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

    /**
     * Deletes the given file
     * @param pathName - path to the file to be deleted
     */
    private void delete(String pathName) {
        File file = new File(pathName);
        file.delete();
    }

    // various getters
    public HighScore getBeginner() { return this.beginner; }
    public HighScore getIntermediate() { return this.intermediate; }
    public HighScore getExpert() { return this.expert; }

    /**
     * Gets the high score with the given difficulty
     * @param diff - the string of the difficulty to be gotten
     * @return the HighScore gotten
     */
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
